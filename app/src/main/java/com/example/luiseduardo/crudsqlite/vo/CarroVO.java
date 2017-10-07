package com.example.luiseduardo.crudsqlite.vo;

/**
 * Created by Luis Eduardo on 20/08/2017.
 */

public class CarroVO extends BaseVO {
    private String modelo;
    private String marca;
    private Integer ano;
    private int cor;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }
}
