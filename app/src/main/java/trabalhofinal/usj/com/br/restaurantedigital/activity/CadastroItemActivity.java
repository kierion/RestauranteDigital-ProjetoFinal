package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.Constantes;

/**
 * Created by Édipo on 02/07/2017.
 */

public class CadastroItemActivity  extends Activity {

    private EditText preco, nomeItem, descricao;
    private IDAO<Menu> menuDAO;
    private int idItem = 0;
    public static final String EXTRA_ID_MENU = "ID_MENU";
    public String voltar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_cadastrar_item);

        preco = (EditText) findViewById(R.id.idCadastrarPrecoItem);
        nomeItem = (EditText) findViewById(R.id.idCadastrarNomeItem);
        descricao = (EditText) findViewById(R.id.idCadastrarDescricaoItem);
        menuDAO = new MenuDAO(getApplicationContext());

        Integer idItem = getIntent().getIntExtra(MenuDAO.ID, 0);
        if (idItem == null){
            idItem = 0;
        }
        if(idItem > 0){
            prepararEdicao(idItem);
        }

    }

    public void cadastrar(View view) {
        Menu p = new Menu();
        p.setPreco(preco.getText().toString());
        p.setNomePrato(nomeItem.getText().toString());
        p.setDescricao(descricao.getText().toString());

        boolean sucesso = false;

        Integer id = getIntent().getIntExtra(MenuDAO.ID, 0);

        if(id == 0){
            sucesso = menuDAO.salvar(p);
        }
        else{
            p.setId(id);
            sucesso = menuDAO.atualizar(p);
        }

        if(sucesso){
            Toast.makeText(this, getString(R.string.registro_item_salvo),
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(
                    this, CadastroMenuListActivity.class));
        }
        else{
            Toast.makeText(this, getString(R.string.registro_item_erro),
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void prepararEdicao(Integer id){
        Menu p = menuDAO.buscarPorId(id);

        if(p == null){
            return;
        }

        preco.setText(p.getPreco());
        nomeItem.setText(p.getNomePrato());
        descricao.setText(p.getDescricao());


    }

    //modifica a ação do botão voltar
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CadastroMenuListActivity.class);
        startActivity(intent);
    }


}
