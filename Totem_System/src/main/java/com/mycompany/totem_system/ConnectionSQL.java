///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mycompany.totem_system;
//
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.springframework.jdbc.core.JdbcTemplate;
//
///**
// *
// * @author Vinícius
// */
//public class ConnectionSQL {
//    
//    private JdbcTemplate connection;
//    
//    public ConnectionSQL(){
//        BasicDataSource datasource = new BasicDataSource();
//
//        datasource.setDriverClassName("com.mysql.jdbc.Driver");
//
//        datasource.setUrl("jdbc:mysql://localhost:3306/totembd");
//
//        datasource.setUsername("root");
//
//        datasource.setPassword("urubu100");
//        connection = new JdbcTemplate(datasource);
//    }
//
//    public JdbcTemplate getConnection() {
//        return connection;
//    }
//}
