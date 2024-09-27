package com.ITOPW.itopw.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
public class DbSysTime {
    @Id
    private Long id;

    private String dbsystime;

    // Constructors
    public DbSysTime() {}

    public DbSysTime(Long id, String dbsystime) {
        this.id = id;
        this.dbsystime = dbsystime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDbsystime() {
        return dbsystime;
    }

    public void setDbsystime(String dbsystime) {
        this.dbsystime = dbsystime;
    }
}
