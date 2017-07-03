package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.Constantes;

/**
 * Created by Édipo on 01/07/2017.
 */

public class CadastroMenuListActivity extends Activity {

    private ListView listView;
    private IDAO<Menu> dao;
    private List<Map<String, Object>> itens;
    private int itemSelecionada;
    private AlertDialog menu, menuConfirmar;


    private AdapterView.OnItemClickListener listener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    itemSelecionada = position;
                    menu.show();
                }
            };

    private DialogInterface.OnClickListener listenerMenu =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface,
                                    int item) {
                    Integer idMenu = (Integer) itens.get(itemSelecionada).get(MenuDAO.ID);

                    String teste1 = String.valueOf(idMenu);
                    Toast.makeText(getApplicationContext(), teste1, Toast.LENGTH_LONG).show();

                    Intent intent;
                    switch (item){
                        case 0:
                            intent = new Intent(getApplicationContext(), CadastroItemActivity.class);
                            intent.putExtra(MenuDAO.ID, idMenu);
                            startActivity(intent);
                            break;
                        case 1:
                            menuConfirmar.show();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            Boolean sucesso = dao.excluir(idMenu);
                            if (sucesso){
                                itens.remove(itemSelecionada);
                                Toast.makeText(getApplicationContext(), R.string.remover_item_sucesso,
                                        Toast.LENGTH_LONG).show();
                                menuConfirmar.dismiss();
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

        String[] de = {MenuDAO.NOME_PRATO, MenuDAO.PRECO};
        int[] para = {R.id.idListaItens_Nome_Item, R.id.idListaItens_Preco_Item};
        itens = listarItens();

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                itens,
                R.layout.layout_cadastro_listar_itens_cadastrados,
                de,
                para);
        //setando as opcoes da list view
        listView.setAdapter(adapter);

        //setando as acoes a serem tomadas ao clicar em cada opcao
        listView.setOnItemClickListener(listener);

        menu = criarAlertDialog();
        menuConfirmar = criarConfirmacaoDialog();
    }

    private List<Map<String, Object>> listarItens(){

        itens = new ArrayList<Map<String, Object>>();

        for(Menu p: dao.listar()){
            Map<String, Object> item = new HashMap<String, Object>();
            item.put(MenuDAO.ID, p.getId());
            item.put(MenuDAO.PRECO, p.getPreco());
            item.put(MenuDAO.NOME_PRATO, p.getNomePrato());
            item.put(MenuDAO.DESCRICAO, p.getDescricao());

            itens.add(item);
        }

        return itens;
    }

    private AlertDialog criarAlertDialog(){
        final CharSequence[] itens = {
                getString(R.string.editar),
                getString(R.string.excluir)
        };
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(itens, listenerMenu);

        return builder.create();
    }

    private AlertDialog criarConfirmacaoDialog(){

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
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
    }
}
