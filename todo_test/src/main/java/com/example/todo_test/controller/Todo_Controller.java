package com.example.todo_test.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo_test.Models.*;
import com.example.todo_test.Servises.ModelService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@ResponseBody
public class Todo_Controller {
    @Autowired
    private ModelService ms;
    private static final Logger logger = LoggerFactory.getLogger(Todo_Controller.class);

    private int counter = 0;

    static final MeterRegistry meterRegistry = new SimpleMeterRegistry();
    static final Counter requests = Counter.builder("counterTest")
            .tags("type", "increment").register(meterRegistry);
    @GetMapping("/counterIncrease")
    public double increaseCounter() {
        requests.increment();
        //++counter;
        //System.out.println(counter);
        System.out.println(requests);
        logger.info("Counter increased. Current count: {}", requests.count());
        return requests.count();
    }

    @GetMapping("/")
    public String StartPage()
    {
        return "TODO Startpage";
    }

    @GetMapping("/getTitel/{titel}")
    public ResponseEntity<String> getFromtitel(@PathVariable("titel") String titel) {
        List<Model> list = ms.getTODOwithTitel(titel);
        String result = "";
        for (Model model : list) {
            result += makeJson(model) + "\r\n";
        }

        if (result == "") {
            logger.info("getTitle FAILED: {}", titel);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no TODOs with this title" + "\r\n");
        }
        logger.info("getTitle: {}", titel);

        return ResponseEntity.status(HttpStatus.OK).body(result + "\r\n");

    }

    @GetMapping("/getID/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> getFromID(@PathVariable("id") long id) {

        if (ms.exist(id)) {
            logger.info("GetID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(ms.getTODOwithID(id).toString());

        } else {
            logger.info("GetID: FAILED{}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NOT FOUND ERROR (CODE 404)\n" + "TODO mit der ID: " + id + " wurde nicht gefunden");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<String> getAll() {
        List<Model> list = ms.list();
        String result = "";

        for (Model model : list) {
            result += makeJson(model) + "\r\n";
        }

        if (result == "") {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no TODOs" + "\r\n");
        }
        // System.out.println("Result: "+result);
        return ResponseEntity.status(HttpStatus.OK).body(result + "\r\n");

    }

    @RequestMapping(method = RequestMethod.POST, value = "/todoPost")
    public ResponseEntity<String> addTODO(@RequestBody Model model) {
        long id = ms.postTODO(model);
        System.out.println("THE ID " + id);
        if (ms.exist(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("TODO: " + model.getTitle() + " mit der ID: " + id + " wurde hinzugefügt" + "\r\n");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("TODO wurde nicht hinzugefügt " + "\r\n");

    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteTODO(@PathVariable Long id) {
        if (!ms.exist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("TODO mit der ID: " + id + " wurde nicht gefunden " + "\r\n");
        }

        ms.delete(id);

        if (!ms.exist(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("TODO mit der ID: " + id + " wurde gelöcht " + "\r\n");
        } else if (ms.exist(id)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("TODO mit der ID: " + id + " wurde nicht gelöscht " + "\r\n");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Die Anfrage hat aus unbekannten Gründen nicht funktioniert " + "\r\n");

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Model model) {
        if (!ms.exist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Das zu ändernte TODO wurde nicht gefunden" + "\r\n");
        }

        ms.putTODO(id, model);
        if (ms.checkChanges(model, ms.getTODOwithID(id))) {
            return ResponseEntity.status(HttpStatus.OK).body("Die Änderung wurde durchgeführt" + "\r\n");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Die Daten wurden nicht geändert " + "\r\n");

    }

   

    @PatchMapping("/patch/{id}")
    public ResponseEntity<String> patch(@PathVariable long id, @RequestBody Map<Object, Object> fields) {
        if (!ms.exist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Die ID: " + id + " wurde nicht gefunden \r\n");
        }
        Optional<Model> model = ms.findById(id);

        try 
        {
            if (model.isPresent()) {
                fields.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(Model.class, (String) key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, model.get(), value);
                    ms.save(model.get());
                });
            }

        } 
        catch (NullPointerException e) 
        {            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field exestiert nicht" + "\r\n");
        }
        if(ms.checkChanges(model.get(), ms.getTODOwithID(id))) 
        {
            return ResponseEntity.status(HttpStatus.OK).body("Die Änderung wurde durchgeführt" + "\r\n");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Die Änderung wurde nicht durchgeführt" + "\r\n");
    }

    // Hilfs Methoden
    private String makeJson(Model model) {
        ObjectMapper Obj = new ObjectMapper();

        try {
            // Converting the Java object into a JSON string
            Obj.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String jsonStr = Obj.writeValueAsString(model);
            // Displaying Java object into a JSON string
            System.out.println(jsonStr);
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    


}

