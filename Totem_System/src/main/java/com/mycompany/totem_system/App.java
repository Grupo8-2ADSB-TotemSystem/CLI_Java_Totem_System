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

import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.servicos.ServicoGrupo;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import com.github.britooo.looca.api.util.Conversor;

import org.springframework.dao.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Matheus Nascimento
 */
public class App {

    public static void main(String[] args) throws InterruptedException, SQLException {
        Scanner leitor = new Scanner(System.in);
        Scanner leitor2 = new Scanner(System.in);

        Looca looca = new Looca();
        Connection con = DriverManager.getConnection("jdbc:sqlserver://animix.database.windows.net:1433;database=animix;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;", "admin-1adsb-grupo07", "#Gfgrupo7");
//        Connection connection = new Connection();
//        JdbcTemplate con = connection.getConnection();

        System.out.println("digite seu usuario");

        String user = leitor.nextLine();
        System.out.println("digite sua senha");

        String senha = leitor.nextLine();
        Statement stm = con.createStatement();
        String login = "select * from funcionario where email='" + user + "' and senha='" + senha + "'";
        ResultSet rs = stm.executeQuery(login);

        Statement stm2 = con.createStatement();
        String login2 = "select * from funcionario where email='" + user + "' and senha='" + senha + "'";
        ResultSet rs2 = stm2.executeQuery(login2);

        Integer fkTotem = 3;

        // Inserir na tabela memoria
        long memoriaTotal = looca.getMemoria().getTotal();
        String memoriaTotalInsert = Conversor.formatarBytes(memoriaTotal);

        String insertStatementMemoria = "INSERT INTO memoria VALUES (?,  ?);";

        coletarDados(insertStatementMemoria, fkTotem, memoriaTotalInsert);

        // Inserir na tabela processador
        String fabricanteProcessador = looca.getProcessador().getFabricante();
        String nomeProcessador = looca.getProcessador().getNome();
        String microArq = looca.getProcessador().getMicroarquitetura();
        long frequenciaProcessador = looca.getProcessador().getFrequencia();

        String insertStatementProcessador = "INSERT INTO processador VALUES (?, ?, ?, ?, ?);";

        con.update(insertStatementProcessador, fkTotem, fabricanteProcessador, nomeProcessador, microArq, frequenciaProcessador);
//        conSQL.update(insertStatementProcessador, fkTotem, fabricanteProcessador, nomeProcessador, microArq, frequenciaProcessador);
//        System.out.println("Inseriu na tabela processador"); 

        // Inserir na tabela dado 
        // Fica constantemente inserindo dados
        while (!rs.next()) {

            System.out.println("digite seu usuario");

            user = leitor.nextLine();
            System.out.println("digite sua senha");

            senha = leitor.nextLine();
            stm = con.createStatement();
            login = "select * from funcionario where email='" + user + "' and senha='" + senha + "'";
            rs = stm.executeQuery(login);

            stm2 = con.createStatement();
            login2 = "select * from funcionario where email='" + user + "' and senha='" + senha + "'";
            rs2 = stm2.executeQuery(login2);
        }
        if (rs.next()) {

            try {
                System.out.println("Coletando Dados...");
                TimeUnit.SECONDS.sleep(20);
//          Dados volateis
//          Memoria
                long memoriaUso = looca.getMemoria().getEmUso();
                String memoriaUsoForm = Conversor.formatarBytes(memoriaUso).replace("GiB", "").replace(",", ".");
                Double memoriaUsoInsert = Double.parseDouble(memoriaUsoForm);
//          RAM
                long memoriaDisponivel = looca.getMemoria().getDisponivel();
                String memoriaDisponiveForm = Conversor.formatarBytes(memoriaDisponivel).replace("GiB", "").replace(",", ".").replace("MiB", "");
                Double memoriaDisponivelInsert = Double.parseDouble(memoriaDisponiveForm);
//          Processador
                Double processadorUso = looca.getProcessador().getUso();
                String processadorUsoForm = String.format("%.2f", processadorUso).replace(",", ".");
                Double processadorUsoInsert = Double.parseDouble(processadorUsoForm);
//          Temperatura
                Double temperatura = looca.getTemperatura().getTemperatura();

                String insertStatement = "INSERT INTO dado VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

                con.update(insertStatement, fkTotem, memoriaUsoInsert, memoriaDisponivelInsert, processadorUsoInsert, temperatura);
                System.out.println("Inseriu na tabela dado");
            } catch (Exception e) {
                System.out.println(e);
            }
        } if (rs2.next()) {

            try {
                System.out.println("Coletando Dados...");
                TimeUnit.SECONDS.sleep(20);
//          Dados volateis
//          Memoria
                long memoriaUso = looca.getMemoria().getEmUso();
                String memoriaUsoForm = Conversor.formatarBytes(memoriaUso).replace("GiB", "").replace(",", ".");
                Double memoriaUsoInsert = Double.parseDouble(memoriaUsoForm);
//          RAM
                long memoriaDisponivel = looca.getMemoria().getDisponivel();
                String memoriaDisponiveForm = Conversor.formatarBytes(memoriaDisponivel).replace("GiB", "").replace(",", ".").replace("MiB", "");
                Double memoriaDisponivelInsert = Double.parseDouble(memoriaDisponiveForm);
//          Processador
                Double processadorUso = looca.getProcessador().getUso();
                String processadorUsoForm = String.format("%.2f", processadorUso).replace(",", ".");
                Double processadorUsoInsert = Double.parseDouble(processadorUsoForm);
//          Temperatura
                Double temperatura = looca.getTemperatura().getTemperatura();

                String insertStatement = "INSERT INTO dado VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

                con.update(insertStatement, fkTotem, memoriaUsoInsert, memoriaDisponivelInsert, processadorUsoInsert, temperatura);
                System.out.println("Inseriu na tabela dado");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
