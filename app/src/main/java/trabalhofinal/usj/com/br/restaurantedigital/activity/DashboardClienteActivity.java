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
 * Created by Édipo on 11/07/2017.
 */

public class DashboardClienteActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_cliente);
    }

    public void selecionarOpcao(View view) {
        switch (view.getId()){
            case R.id.id_MenuInicial_Cliente_Carrinho:
                Toast.makeText(getApplicationContext(), R.string.em_construcao, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MenuListActivity.class));
                break;

            case R.id.id_MeniInicial_Cliente_Chamar_Garcom:
                Toast.makeText(getApplicationContext(), R.string.aguardar_garcom, Toast.LENGTH_LONG).show();
                //todo implementar serviço de rest para comunicar o usuário garçom.
                break;

            case R.id.id_MenuInicial_Cliente_Menu:
                startActivity(new Intent(this, MenuDetalhadoActivity.class));
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