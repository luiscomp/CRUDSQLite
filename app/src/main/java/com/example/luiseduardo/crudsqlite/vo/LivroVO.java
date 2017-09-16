package com.example.luiseduardo.crudsqlite.vo;

/**
 * Created by Luis Eduardo on 20/08/2017.
 */

public class LivroVO extends BaseVO {
    private String titulo;
    private String autor;
    private Integer ano;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
