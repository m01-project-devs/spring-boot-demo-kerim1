package com.K3rim.demo1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

    @RestController
    public class DBTestController {

        @Autowired
        DataSource dataSource;

        @GetMapping("/api/test/db")
        String test() throws Exception{
            try(var c = dataSource.getConnection()){
                return "Connected to " + c.getMetaData().getURL();
            }
        }

    }




