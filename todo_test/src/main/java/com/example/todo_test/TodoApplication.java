package com.example.todo_test;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.todo_test.Models.Model;
import com.example.todo_test.Models.ModelRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ap =  SpringApplication.run(TodoApplication.class, args);
		
		Date sd = new GregorianCalendar(2022, 8, 23).getTime();
		Date ed = new GregorianCalendar(2022, 8, 25).getTime();	
		Date cd = new GregorianCalendar(2022, 8, 23).getTime();
		Date lud = new GregorianCalendar(2022, 8, 23).getTime();
		
	
		


		//String title, String desc, boolean done, Date startDate, Date endDate, Date createdate, Date lastupdate, int priority
		Model m1 = new Model("Test_titel", "test_des", false, sd, ed, cd, lud, 1  );
		Model m2 = new Model("Test_titel", "test_des2", false, sd, ed, cd, lud, 2  );

		ModelRepository mr = ap.getBean(ModelRepository.class);
		mr.save(m1);
		mr.save(m2);

		System.out.print("\n APPLICATION STARTED!");
	}

	
    @PostConstruct
    public void init(){
      // Setting Spring Boot SetTimeZone
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }



}

