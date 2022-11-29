/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.totem_system;

/**
 *
 * @author Vinícius
 */
public class Parametro {
    private Integer idParametro;
    private Double memoriaUsoMax;
    private Double processadorUsoMax;
    private Double temperaturaMax;
    private Integer fkTotem;



    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Double getMemoriaUsoMax() {
        return memoriaUsoMax;
    }

    public void setMemoriaUsoMax(Double memoriaUsoMax) {
        this.memoriaUsoMax = memoriaUsoMax;
    }

    public Double getProcessadorUsoMax() {
        return processadorUsoMax;
    }

    public void setProcessadorUsoMax(Double processadorUsoMax) {
        this.processadorUsoMax = processadorUsoMax;
    }

    public Double getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(Double temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public Integer getFkTotem() {
        return fkTotem;
    }

    public void setFkTotem(Integer fkTotem) {
        this.fkTotem = fkTotem;
    }

    @Override
    public String toString() {
        return "Parametro{" + "idParametro=" + idParametro + ", memoriaUsoMax=" + memoriaUsoMax + ", processadorUsoMax=" + processadorUsoMax + ", temperaturaMax=" + temperaturaMax + ", fkTotem=" + fkTotem + '}';
    }
    
    
}
