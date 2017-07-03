package trabalhofinal.usj.com.br.restaurantedigital.entity;

import java.io.Serializable;

/**
 * Created by Édipo on 01/07/2017.
 */

public class Menu implements Serializable{

    private Integer id;
    private String preco;
    private String nomePrato, descricao;

    public Menu(){}

    public Menu(Integer id, String preco, String nomePrato, String descricao) {
        this.id = id;
        this.preco = preco;
        this.nomePrato = nomePrato;
        this.descricao = descricao;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
