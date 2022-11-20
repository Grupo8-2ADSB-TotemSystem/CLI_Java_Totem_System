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

/**
 *
 * @author Vinícius
 */
public class Dado {
    Integer idDado;
    Integer fkTotem;
    String memoriaUso;
    String memoriaDisponivel;
    String processadorUso;
    Double temperatura;
    String data_dado;

    public Integer getIdDado() {
        return idDado;
    }

    public void setIdDado(Integer idDado) {
        this.idDado = idDado;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

    public String getMemoriaUso() {
        return memoriaUso;
    }

    public void setMemoriaUso(String memoriaUso) {
        this.memoriaUso = memoriaUso;
    }

    public String getMemoriaDisponivel() {
        return memoriaDisponivel;
    }

    public void setMemoriaDisponivel(String memoriaDisponivel) {
        this.memoriaDisponivel = memoriaDisponivel;
    }

    public String getProcessadorUso() {
        return processadorUso;
    }

    public void setProcessadorUso(String processadorUso) {
        this.processadorUso = processadorUso;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public String getData_dado() {
        return data_dado;
    }

    public void setData_dado(String data_dado) {
        this.data_dado = data_dado;
    }

    @Override
    public String toString() {
        return "Dado{" + "idDado=" + idDado + ", fkTotem=" + fkTotem + ", memoriaUso=" + memoriaUso + ", memoriaDisponivel=" + memoriaDisponivel + ", processadorUso=" + processadorUso + ", temperatura=" + temperatura + ", data_dado=" + data_dado + '}';
    }
    
    
}
