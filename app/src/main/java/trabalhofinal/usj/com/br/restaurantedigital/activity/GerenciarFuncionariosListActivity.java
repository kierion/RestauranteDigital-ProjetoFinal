package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.CadastroDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Funcionarios;
import trabalhofinal.usj.com.br.restaurantedigital.util.FuncionarioAdapter;

/**
 * Created by jaqueline on 11/07/2017.
 */

public class GerenciarFuncionariosListActivity extends Activity {

    private ListView listView;
    private IDAO<Funcionarios> funcionarioDAO;
    private int itemSelecionada, idSelecionado;
    private AlertDialog funcionarios, funcionariosConfirmar;
    private FuncionarioAdapter adapter = null;

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> layout, View contentView, int position, long id) {
                    itemSelecionada = position;
                    idSelecionado = (int) id;
                    funcionarios.show();
                }
            };

    private DialogInterface.OnClickListener listenerFuncionarios =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int item) {

                    Integer idFuncionarios = funcionarioDAO.buscarIdporPosicao(itemSelecionada);

                    Intent intent;
                    switch (item){
                        case 0:
                            intent = new Intent(getApplicationContext(), GerenciarFuncionariosActivity.class);
                            intent.putExtra(CadastroDAO.ID, idFuncionarios);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            break;
                        case 1:
                            funcionariosConfirmar.show();
                            break;

                        case DialogInterface.BUTTON_POSITIVE:
                            Boolean sucesso = funcionarioDAO.excluir(idFuncionarios);
                            if (sucesso){
                                Toast.makeText(getApplicationContext(), R.string.remover_item_sucesso,
                                        Toast.LENGTH_LONG).show();
                                intent = new Intent(getApplicationContext(),GerenciarFuncionariosListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                adapter.notifyDataSetChanged();
                            }
                            listView.invalidateViews();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            funcionariosConfirmar.dismiss();
                            break;
                    }
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_funcionarios_listview);
        funcionarioDAO = new CadastroDAO(getApplicationContext());

        listView = (ListView) findViewById(R.id.idFuncListView);
        adapter = new FuncionarioAdapter(getApplicationContext(), funcionarioDAO.listar());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

        funcionarios = criarAlertDialog();
        funcionariosConfirmar = criarConfirmacaoDialog();
    }

    private AlertDialog criarAlertDialog(){
        final CharSequence[] itens = {
                getString(R.string.editar),
                getString(R.string.excluir)
        };
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle(R.string.opcoes);
        builder.setItems(itens, listenerFuncionarios);

        return builder.create();
    }

    private AlertDialog criarConfirmacaoDialog(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage(R.string.confirmacao_exclusao);
        builder.setPositiveButton(R.string.sim, listenerFuncionarios);
        builder.setNegativeButton(R.string.nao, listenerFuncionarios);
        return builder.create();
    }

    public void novoCadastroFunc(View view) {

        Intent intent = new Intent(getApplicationContext(),
                GerenciarFuncionariosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(GerenciarFuncionariosActivity.EXTRA_ID_FUNCIONARIO, 0);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}