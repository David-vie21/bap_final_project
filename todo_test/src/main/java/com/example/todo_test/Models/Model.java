package com.example.todo_test.Models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table (name = "Todos")
public class Model {
    @Id
    @GeneratedValue
    @Column (name= "Id")
    private long Id;    

    @Column (name= "Title")
    private String title;

    @Column (name= "desci")
    private String desci;


    @Column (name= "done")
    private boolean done;


    @Column (name= "startDate")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")

    private Date startDate;

    @Column (name= "endDate")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    private Date endDate;

    @Column (name= "createdate")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    private Date createdate;

    @Column (name= "lastUpdate")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    private Date lastupdate;
    
    @Column (name= "prio")
    private int priority;
    

    public Model() {
    }
    

    public Model(String title, String desc) {
        this.title = title;
        this.desci = desc;
        this.createdate = Calendar.getInstance ().getTime();
    }


    public Model(String title, String desc, boolean done, Date startDate, Date endDate, Date createdate,
            Date lastupdate, int priority) {
        this.title = title;
        this.desci = desc;
        this.done = done;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdate = createdate;
        this.lastupdate = lastupdate;
        this.priority = priority;
    }


    public Model(long id, String title, String desc,  boolean done, Date startDate, Date endDate,
            Date createdate, Date lastupdate, int priority) {
        Id = id;
        this.title = title;
        this.desci = desc;
        this.done = done;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdate = createdate;
        this.lastupdate = lastupdate;
        this.priority = priority;
    }

   




    @Override
    public String toString() {
        return "Model [Id=" + Id + ", createdate=" + createdate + ", desci=" + desci + ", done=" + done + ", endDate="
                + endDate + ", lastupdate=" + lastupdate + ", priority=" + priority + ", startDate=" + startDate
                + ", title=" + title + "]";
    }

    public String toString2() {
        return "Model [ createdate=" + createdate + ", dessci=" + desci + ", done=" + done + ", endDate="
                + endDate + ", lastupdate=" + lastupdate + ", priority=" + priority + ", startDate=" + startDate
                + ", title=" + title + "]";
    }


    public long getId() {
        return Id;
    }


    public void setId(long id) {
        Id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDesci() {
        return desci;
    }


    public void setDesci(String desc) {
        this.desci = desc;
    }


    public boolean getDone() {
        return done;
    }


    public void setDone(boolean done) {
        this.done = done;
    }


    public Date getStartDate() {
        return startDate;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public Date getCreatedate() {
        return createdate;
    }


    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }


    public Date getLastupdate() {
        return lastupdate;
    }


    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }


    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }
}

   