package model.tabelas;

import lombok.Getter;

@Getter
public class Maquina {
    private Integer id;
    private String nome;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Maquina() {
    }
}
