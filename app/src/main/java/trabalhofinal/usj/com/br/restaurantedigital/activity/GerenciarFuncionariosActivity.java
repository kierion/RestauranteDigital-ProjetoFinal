package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.CadastroDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Funcionarios;

/**
 * Created by Édipo on 01/07/2017.
 */

public class GerenciarFuncionariosActivity extends Activity {

    private EditText login, senha, cargo;
    private IDAO<Funcionarios> funcDAO;
    public static final String EXTRA_ID_FUNCIONARIO = "ID_FUNCIONARIO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_func);

        login = (EditText) findViewById(R.id.idLoginTxt);
        senha = (EditText) findViewById(R.id.idSenhaTxt);
        cargo = (EditText) findViewById(R.id.idCargoTxt);
        funcDAO = new CadastroDAO(getApplicationContext());

        Integer idItem = getIntent().getIntExtra(CadastroDAO.ID, 0);
        if (idItem == null){
            idItem = 0;
        }
        if(idItem > 0){
            prepararEdicao(idItem);
        }
    }

    public void cadastrarFuncionario(View view) {

        if (login.getText().toString().equals("")&&senha.getText().toString().equals("")&&cargo.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), R.string.preencher_todos_os_campos, Toast.LENGTH_LONG).show();
            return;
        }
            if (login.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), R.string.preencher_login, Toast.LENGTH_LONG).show();
                return;
            }else if (senha.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), R.string.preencher_senha, Toast.LENGTH_LONG).show();
                return;
            }else if (cargo.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), R.string.preencher_cargo, Toast.LENGTH_LONG).show();
                return;
            }

        Funcionarios funcionarios = new Funcionarios();
        funcionarios.setLogin(login.getText().toString());
        funcionarios.setSenha(senha.getText().toString());
        funcionarios.setCargo(cargo.getText().toString());

        boolean sucesso = false;
        Integer id = getIntent().getIntExtra(MenuDAO.ID, 0);

        if(id == 0){
            sucesso = funcDAO.salvar(funcionarios);
        }
        else{
            funcionarios.setId(id);
            sucesso = funcDAO.atualizar(funcionarios);
        }
        if(sucesso){
            Toast.makeText(this, getString(R.string.registro_item_salvo),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),GerenciarFuncionariosListActivity.class);
            //flag: limpa todas as outras activitys
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else{
            Toast.makeText(this, getString(R.string.registro_item_erro),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void prepararEdicao(Integer id){
        Funcionarios funcionarios = funcDAO.buscarPorId(id);
        if(funcionarios == null){
            return;
        }
        login.setText(funcionarios.getLogin());
        senha.setText(funcionarios.getSenha());
        cargo.setText(funcionarios.getCargo());
    }

    //modifica a ação do botão voltar
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}