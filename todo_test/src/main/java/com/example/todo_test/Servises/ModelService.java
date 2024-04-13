package com.example.todo_test.Servises;

import com.example.todo_test.Models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ModelService {
    
    @Autowired
    private ModelRepository mr;

    public void save(Model model)
    {
        mr.save(model);
    }

    public  List<Model> list() {
        return (List<Model>)mr.findAll();
    }

    public Model getTODOwithID(long id)
    {
        return (Model)mr.findById(id).orElse(null);
    }

    public Optional<Model> findById(long id)
    {
        return mr.findById(id);
    }

    public List<Model> getTODOwithTitel(String titel)
    {
        List<Model> lm = (List<Model>)mr.findAll();
        List<Model> filtertList = new ArrayList<Model>();
        
        for (Model model : lm) {
            if(model.getTitle().toLowerCase().contains(titel.toLowerCase()))
            {
                filtertList.add(model);
            }
        }
        return filtertList;
         
    }

    public long postTODO(Model model)
    {        
       long id = mr.save(model).getId();
        if(exist(id))
        {
            return id;
        }
        return -99;
    }

    public void delete(Long id) {

       mr.deleteById(id);
    }

    public void putTODO(long Id, Model model)
    {

        Model model2 = getTODOwithID(Id);

        model2.setTitle(model.getTitle());
        model2.setDesci(model.getDesci());
        model2.setCreatedate(model.getCreatedate());
        model2.setStartDate(model.getStartDate());
        model2.setEndDate(model.getEndDate());
        model2.setLastupdate(model.getLastupdate());
        model2.setDone(model.getDone());
        model2.setPriority(model.getPriority());
        
        mr.save(model2);

    }

    public boolean exist(long id)
    {
        return mr.existsById(id);
    }

    public boolean checkChanges(Model model_old, Model model_new)
    {
        if(model_new.toString2().equals(model_old.toString2()))
        {
            return true;
        }
        return false;
    }

    public void delAll()
    {
        mr.deleteAll();
    }



}
