package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import trabalhofinal.usj.com.br.restaurantedigital.R;

/**
 * Created by Édipo on 01/07/2017.
 */

public class DashboardActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_restaurante);
    }

    public void selecionarOpcao(View view) {
        switch (view.getId()){
            case R.id.idMenuInicial_Funcionario_Carrinho:
                //startActivity(new Intent(this, CarrinhoActivity.class));
                //break;
                Toast.makeText(this, "nao cadastrado Carrinho", Toast.LENGTH_SHORT).show();
                break;

            case R.id.id_MenuInicia_Funcionario_Funcionarios:
                    //startActivity(new Intent(this, GerenciarFuncionariosActivity.class));
                    //break;
                Toast.makeText(this, "nao cadastrado Cadastro Funcionarios", Toast.LENGTH_SHORT).show();
                break;

            case R.id.id_MenuInicial_Funcionario_Menu:
                startActivity(new Intent(this, MenuListActivity.class));
                break;

            case R.id.id_MenuInicial_Funcionario_Cadastro_Menu:
                startActivity(new Intent(this, CadastroMenuListActivity.class));
                break;


            case R.id.id_MenuInicial_Funcionario_Configuracoes:
                //startActivity(new Intent(this, CadastroItemActivity.class));
               //break;
                Toast.makeText(this, "nao cadastrado Configurações", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle(R.string.sair);
        builder.setMessage(R.string.realmente_sair);
        builder.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(getApplicationContext(),RestauranteDigitalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        builder.setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }
}
