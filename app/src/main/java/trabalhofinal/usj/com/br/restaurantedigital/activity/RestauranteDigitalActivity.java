package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.CadastroDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Funcionarios;

public class RestauranteDigitalActivity extends Activity {

    private EditText idUsuario, idSenha;
    private CheckBox liberaUsuario;
    private IDAO<Funcionarios> funcDAO;
    private String login, senha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tela_login);
        this.idUsuario = (EditText) findViewById(R.id.id_Texto_Usuario_Login);
        this.idSenha = (EditText) findViewById(R.id.idTexto_Senha_Login);
        liberaUsuario = (CheckBox) findViewById(R.id.IdCheckBox_Libera_Login);
        funcDAO = new CadastroDAO(getApplicationContext());
        login = getString(R.string.loginadm);
        senha = getString(R.string.senhaadm);

    }

    public void loginSistema(View view) {
        String log = idUsuario.getText().toString();
        String sen = idSenha.getText().toString();


        if (liberaUsuario.isChecked()) {
            Intent intent = new Intent(this, DashboardClienteActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            Toast t = Toast.makeText(this,
                    R.string.login_sucesso_cliente, Toast.LENGTH_LONG);
            t.show();

        }else if (funcDAO.validarLogin(log, sen)){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }else if (login.equals(log) & senha.equals(sen)){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            Toast t = Toast.makeText(this, R.string.login_adm, Toast.LENGTH_SHORT);t.show();
        }else{
            Toast t = Toast.makeText(this, R.string.usuarioInvalido, Toast.LENGTH_SHORT);t.show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
