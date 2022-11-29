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
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 *
 * @author Vinícius
 */
public class App {

    public Integer totem;

    public static void main(String[] args) throws InterruptedException, IOException {
        JSONObject mensage = new JSONObject();
        Date dataHoraAtual = new Date();
        Looca looca = new Looca();
        Connection connection = new Connection();
        JdbcTemplate con = connection.getConnection();
        Scanner leitor = new Scanner(System.in);
//        ConnectionSQL connectionSQL = new ConnectionSQL();
//        JdbcTemplate conSQL = connectionSQL.getConnection();

        Integer fkTotem = 1;
//        Boolean first = true;
        Boolean start = true;
        
        Boolean meMsg = false;
        Boolean proMsg = false;
        Boolean tempMsg = false;

//        Login log = new Login();
//        log.show();
        System.out.println("Bem vindo ao Totem System!");
        Boolean validation = false;
        String emailCad = "";
        while (validation == false) {
            System.out.println("Insira seu e-mail:");
            String email = leitor.nextLine();
            System.out.println("Insira sua senha:");
            String senha = leitor.nextLine();

            List emailVal = con.queryForList("SELECT email FROM usuario WHERE email = '" + email + "' and senha = '" + senha + "'");
            String emailString = String.valueOf(emailVal);
            List senhaVal = con.queryForList("SELECT senha FROM usuario WHERE senha = '" + senha + "' and email = '" + email + "'");
            String senhaString = String.valueOf(senhaVal);
//        System.out.println(emailString);
//        System.out.println(senhaVal);

            if (emailString.equals("[{email=" + email + "}]") && senhaString.equals("[{senha=" + senha + "}]")) {
                System.out.println("O sistema está rodando!");
                validation = true;
                emailCad = email;
            } else {
                System.out.println("Acesso negado!!\n"
                        + "Tente novamente\n");
            }
        }
        System.out.println("Este totem já está cadastrado? y/n");
        String totemCad = leitor.nextLine();

        if (totemCad.equals("y")) {
            System.out.println("Inserir o id do Totem:");
            fkTotem = leitor.nextInt();
        } else if (totemCad.equals("n")) {
            System.out.println("O totem será cadastrado!");
            System.out.println("Qual a marca do totem?");
            String totemMarca = leitor.nextLine();
            String totemSo = looca.getSistema().getSistemaOperacional();
            List<Estacao> estacao = con.query("select idEstacao, fkEndereco, nomeEstacao from [dbo].[empresa] as e\n"
                    + "join [dbo].[usuario] as u on e.idEmpresa = u.fkEmpresa\n"
                    + "join [dbo].[estacao] as es on es.idEstacao = e.fkEstacao\n"
                    + "where nomeUsuario = (select nomeUsuario from [dbo].[usuario] where email = '" + emailCad + "');", new BeanPropertyRowMapper(Estacao.class));
            Integer idEstacao = 0;
            for (Estacao dado : estacao) {
                idEstacao = dado.getIdEstacao();

            }
            String insertStatementTotemCad = "INSERT INTO totem VALUES (?,  ?, ?);";

            con.update(insertStatementTotemCad, idEstacao, totemMarca, totemSo);

            List<Totem> totemT2 = con.query("select idTotem ,fkEstacao, marca, so from [dbo].[totem] where idTotem = (select max(idTotem) from [dbo].[totem]) and fkEstacao = 1;", new BeanPropertyRowMapper(Totem.class));
            for (Totem dado : totemT2) {
                fkTotem = dado.getIdTotem();
            }
        }
        try {

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
//             Inserir na tabela disco
            long volumeTotal = looca.getGrupoDeDiscos().getTamanhoTotal();
            String volumeTotalForm = Conversor.formatarBytes(volumeTotal).replace("GiB", "").replace(",", ".").replace("MiB", "");
            Double volumeTotalInsert = Double.parseDouble(volumeTotalForm);

            String insertStatementDisco = "INSERT INTO disco VALUES (?, ?);";
            con.update(insertStatementDisco, fkTotem, volumeTotalInsert);
//        conSQL.update(insertStatementDisco, fkTotem, volumeTotalInsert);
//        System.out.println("Inseriu na tabela disco");
            // Inserir na tabela memoria
            long memoriaTotal = looca.getMemoria().getTotal();
            String memoriaTotalForm = Conversor.formatarBytes(memoriaTotal).replace("GiB", "").replace(",", ".").replace("MiB", "");
            Double memoriaTotalInsert = Double.parseDouble(memoriaTotalForm);
            System.out.println("@@@@" + memoriaTotalInsert);

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
                Long memTotal = looca.getMemoria().getTotal();
                String memTotalForm = Conversor.formatarBytes(memTotal).replace("GiB", "").replace(",", ".");
                Double memTotalInsert = Double.parseDouble(memTotalForm);
                
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
//            String insertStatement2 = "INSERT INTO dado VALUES (null, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

                con.update(insertStatement, fkTotem, memoriaUsoInsert, memoriaDisponivelInsert, processadorUsoInsert, temperatura);
//            conSQL.update(insertStatement2, fkTotem, memoriaUsoInsert, memoriaDisponivelinsert, processadorUsoInsert, temperatura);
                System.out.println("Inseriu na tabela dado");

//            Alerta memoria
                
                List<Parametro> cpuT1 = con.query("SELECT idParametro, memoriaUsoMax, processadorUsoMax, temperaturaMax, fkTotem FROM parametros WHERE fkTotem = " + fkTotem + ";", new BeanPropertyRowMapper(Parametro.class));
                Double memoriaMax;
                Double processadorMax;
                Double temperaturaMax;
                for (Parametro dado : cpuT1) {
                    memoriaMax = dado.getMemoriaUsoMax();
                    processadorMax = dado.getProcessadorUsoMax();
                    temperaturaMax = dado.getTemperaturaMax();
                    
                    Double aa = memoriaUsoInsert / (memTotalInsert / 100);
                    System.out.println("####" + aa);
                    if ((memoriaUsoInsert / (memTotalInsert / 100)) > memoriaMax && meMsg == false) {
                        mensage.put("text", "O uso da memória superou o limite!!\n"
                                + "Uso de memória: " + memoriaUsoInsert);
                        meMsg = true;
                        Slack.sendMensage(mensage);
                        System.out.println("Mensagem enviada!!");
                    }
//                    memoriaUsoInsert = 0.5;
                    if (meMsg && (memoriaUsoInsert / (memTotalInsert / 100)) < memoriaMax) {
                        mensage.put("text", "O uso da memória voltou ao normal!!\n"
                                + "Uso de memória: " + memoriaUsoInsert);
                        meMsg = false;
                        Slack.sendMensage(mensage);
                        System.out.println("Mensagem enviada2!!");
                    }

//            Alerta processador
                    
                    if (processadorUsoInsert > processadorMax && proMsg == false) {
                        mensage.put("text", "O uso do processador superou o limite!!\n"
                                + "Uso de memória: " + processadorUsoInsert);
                        proMsg = true;
                        Slack.sendMensage(mensage);
                        System.out.println("Mensagem enviada!!");
                    }
//                    processadorUsoInsert = 0.5;
                    if (proMsg && processadorUsoInsert < processadorMax) {
                        mensage.put("text", "O uso do processador voltou ao normal!!\n"
                                + "Uso de memória: " + processadorUsoInsert);
                        proMsg = false;
                        Slack.sendMensage(mensage);
                        System.out.println("Mensagem enviada2!!");
                    }
//            Alerta temperatura
                    
                    if (temperatura > temperaturaMax && tempMsg == false) {
                        mensage.put("text", "O uso da temperatura superou o limite!!\n"
                                + "Uso de memória: " + temperatura);
                        tempMsg = true;
                        Slack.sendMensage(mensage);
                        System.out.println("Mensagem enviada!!");
                    }
//                    temperatura = 0.5;
                    if (tempMsg && memoriaUsoInsert < temperaturaMax) {
                        mensage.put("text", "O uso da temperatura voltou ao normal!!\n"
                                + "Uso de memória: " + temperatura);
                        tempMsg = false;
                        Slack.sendMensage(mensage);
                        System.out.println("Mensagem enviada2!!");
                    }
                }
            }
        } catch (Exception e) {
            String data = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(dataHoraAtual);
            System.out.println("@@@@" + e);
            String logName = "ERROR-" + data + ".txt";
            OutputStream os = new FileOutputStream(logName); // nome do arquivo que será escrito
            Writer wr = new OutputStreamWriter(os); // criação de um escritor
            BufferedWriter br = new BufferedWriter(wr); // adiciono a um escritor de buffer

            br.write("ERROR!!!");
            br.newLine();
            br.newLine();
            br.write(e + "\n");
            br.close();
        }
    }

    public Integer getTotem() {
        return totem;
    }

    public void setTotem(Integer totem) {
        this.totem = totem;
    }

    public App(Integer totem) {
        this.totem = totem;
    }

}
