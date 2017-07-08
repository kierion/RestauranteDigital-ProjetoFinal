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
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.CadastroAdapter;

/**
 * Created by Édipo on 01/07/2017.
 */

public class CadastroMenuListActivity extends Activity {

    private ListView listView;
    private IDAO<Menu> dao;
    private int itemSelecionada, idSelecionado;
    private AlertDialog menu, menuConfirmar;
    private CadastroAdapter adapter = null;

    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> layout, View contentView, int position, long id) {
                    itemSelecionada = position;
                    idSelecionado = (int) id;
                    menu.show();
                }
            };

    private DialogInterface.OnClickListener listenerMenu =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int item) {

                    Integer idMenu = dao.buscarIdporPosicao(itemSelecionada);

                    Intent intent;
                    switch (item){
                        case 0:
                            intent = new Intent(getApplicationContext(), CadastroItemActivity.class);
                            intent.putExtra(MenuDAO.ID, idMenu);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            break;
                        case 1:
                            menuConfirmar.show();
                            break;

                        case DialogInterface.BUTTON_POSITIVE:
                            Boolean sucesso = dao.excluir(idMenu);
                            if (sucesso){
                                Toast.makeText(getApplicationContext(), R.string.remover_item_sucesso,
                                        Toast.LENGTH_LONG).show();
                               intent = new Intent(getApplicationContext(),CadastroMenuListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                adapter.notifyDataSetChanged();
                            }
                            listView.invalidateViews();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            menuConfirmar.dismiss();
                            break;
                    }
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_listview_cadastro_itens);
        dao = new MenuDAO(getApplicationContext());

        listView = (ListView) findViewById(R.id.idCadastroListView);
        adapter = new CadastroAdapter(this, dao.listar());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);

        menu = criarAlertDialog();
        menuConfirmar = criarConfirmacaoDialog();
    }

    private AlertDialog criarAlertDialog(){
        final CharSequence[] itens = {
                getString(R.string.editar),
                getString(R.string.excluir)
        };
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle(R.string.opcoes);
        builder.setItems(itens, listenerMenu);

        return builder.create();
    }

    private AlertDialog criarConfirmacaoDialog(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage(R.string.confirmacao_exclusao);
        builder.setPositiveButton(R.string.sim, listenerMenu);
        builder.setNegativeButton(R.string.nao, listenerMenu);
        return builder.create();
    }

    public void novoCadastro(View view) {

        Intent intent = new Intent(getApplicationContext(),
                CadastroItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(CadastroItemActivity.EXTRA_ID_MENU, 0);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
