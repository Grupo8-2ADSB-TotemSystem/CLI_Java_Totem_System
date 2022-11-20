/*
 * The MIT License
 *
 * Copyright 2022 Vinícius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.totem_system;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Vinícius
 */
public class Connection {

    private JdbcTemplate connection;

    // Exemplo de configuração utilizando H2
    // Obs. O código comentado é um exemplo de como se conectar ao mysql
    public Connection() {
//        BasicDataSource datasource = new BasicDataSource();
//
//        datasource.setDriverClassName("com.mysql.jdbc.Driver");
//
//        datasource.setUrl("jdbc:mysql://localhost/totem1");
//
//        datasource.setUsername("root");
//
//        datasource.setPassword("urubu100");

        BasicDataSource datasource = new BasicDataSource();

        datasource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
// exemplo para MySql: "com.mysql.cj.jdbc.Driver"
        datasource.setUrl("jdbc:sqlserver://svr-totemsystem.database.windows.net:1433;database=bd-totemsystem;encryp\n" +
"t=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;");
// exemplo para MySql: "jdbc:mysql://localhost:3306/meubanco"
        datasource.setUsername("admin-totemsystem");
        datasource.setPassword("Urubu100@");

        //this.datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //this.datasource.setUrl("jdbc:mysql://localhost:3360/meu_banco");
        //this.datasource.setUsername("root");
        //this.datasource.setPassword("teste");
        connection = new JdbcTemplate(datasource);
    }

    public JdbcTemplate getConnection() {
        return connection;
    }
}
