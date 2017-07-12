package trabalhofinal.usj.com.br.restaurantedigital.entity;

import java.io.Serializable;

/**
 * Created by Édipo on 12/07/2017.
 */

public class Funcionarios implements Serializable {

    private int id;
    private String senha;
    private String login, cargo;


    public Funcionarios(int id, String login, String senha, String cargo) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;

    }

    public Funcionarios(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {this.login = login;}

    public String getSenha() {return senha; }

    public void setSenha(String senha) {this.senha = senha;}

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {this.cargo = cargo;}
}