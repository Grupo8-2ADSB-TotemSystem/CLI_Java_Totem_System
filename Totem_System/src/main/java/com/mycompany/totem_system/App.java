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
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.concurrent.TimeUnit;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Vinícius
 */
public class App {

    public static void main(String[] args) throws InterruptedException {

        Looca looca = new Looca();
        Connection connection = new Connection();
        JdbcTemplate con = connection.getConnection();
//        ConnectionSQL connectionSQL = new ConnectionSQL();
//        JdbcTemplate conSQL = connectionSQL.getConnection();

        Integer fkTotem = 1;
//        Boolean first = true;
        Boolean start = true;

        Login log = new Login();
        log.show();

        // Limpar as tabelas
        try {
            String deleteDisco = String.format("DELETE FROM disco WHERE fkTotem = %d;", fkTotem);
            con.update(deleteDisco);
            String deleteMemoria = String.format("DELETE FROM memoria WHERE fkTotem = %d;", fkTotem);
            con.update(deleteMemoria);
            String deleteProcessador = String.format("DELETE FROM processador WHERE fkTotem = %d;", fkTotem);
            con.update(deleteProcessador); 
            
        } catch (DataAccessException e) {
            
        }

//        System.out.println("Deletou???");
        // Inserir na tabela disco

//        long volumeTotal = looca.getGrupoDeDiscos().getTamanhoTotal();
//        String volumeTotalInsert = Conversor.formatarBytes(volumeTotal);
//
//        String insertStatementDisco = "INSERT INTO disco VALUES (?, ?);";

//        con.update(insertStatementDisco, fkTotem, volumeTotalInsert);
//        conSQL.update(insertStatementDisco, fkTotem, volumeTotalInsert);
//        System.out.println("Inseriu na tabela disco");

        // Inserir na tabela memoria
        long memoriaTotal = looca.getMemoria().getTotal();
        String memoriaTotalInsert = Conversor.formatarBytes(memoriaTotal);

        String insertStatementMemoria = "INSERT INTO memoria VALUES (?,  ?);";

        con.update(insertStatementMemoria, fkTotem, memoriaTotalInsert);
//        conSQL.update(insertStatementMemoria, fkTotem, memoriaTotalInsert);
//        System.out.println("Inseriu na tabela memoria");

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
        while (start) {
            TimeUnit.SECONDS.sleep(20);
//          Dados volateis
//          Memoria
            long memoriaUso = looca.getMemoria().getEmUso();
            String memoriaUsoForm = Conversor.formatarBytes(memoriaUso).replace("GiB", "").replace(",", ".");
            Double memoriaUsoInsert = Double.parseDouble(memoriaUsoForm);
//          RAM
            long memoriaDisponivel = looca.getMemoria().getDisponivel();
            String memoriaDisponiveForm = Conversor.formatarBytes(memoriaDisponivel).replace("GiB", "").replace(",", ".").replace("MiB","");
            Double memoriaDisponivelInsert = Double.parseDouble(memoriaDisponiveForm);
//          Processador
            Double processadorUso = looca.getProcessador().getUso();
            String processadorUsoForm = String.format("%.2f", processadorUso).replace(",", ".");
            Double processadorUsoInsert = Double.parseDouble(processadorUsoForm);
//          Temperatura
            Double temperatura = looca.getTemperatura().getTemperatura();

            String insertStatement = "INSERT INTO dado VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
//            String insertStatement2 = "INSERT INTO dado VALUES (null, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

            con.update(insertStatement, fkTotem, memoriaUsoInsert, memoriaDisponivelInsert, processadorUsoInsert, temperatura);
//            conSQL.update(insertStatement2, fkTotem, memoriaUsoInsert, memoriaDisponivelinsert, processadorUsoInsert, temperatura);
            System.out.println("Inseriu na tabela dado");
        }

    }
}
