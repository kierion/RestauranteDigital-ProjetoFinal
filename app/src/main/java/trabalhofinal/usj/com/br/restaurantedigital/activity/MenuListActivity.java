package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.NumberPicker;

import java.util.HashMap;
import java.util.Map;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.MenuListAdapter;

/**
 * Created by Édipo on 01/07/2017.
 */

public class MenuListActivity extends AppCompatActivity {
    public Map<String, String> carrinho = new HashMap<>();
    private GridView gridView1;
    private IDAO<Menu> dao;
    private MenuListAdapter adapter = null;
    private int itemSelecionado, quantidade;
    public double[] total;
    private String s;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_gridview);
        dao = new MenuDAO(getApplicationContext());

        gridView1 = (GridView) findViewById(R.id.gridView);
        adapter = new MenuListAdapter(this, R.layout.teste_parametro_gridview, dao.listar());
        gridView1.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                itemSelecionado = position;

                NumberPicker picker = new NumberPicker(MenuListActivity.this);
                picker.setMaxValue(20);
                picker.setMinValue(0);
                NumberPicker.OnValueChangeListener pickerListener = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int valorAntigo, int valorNovo) {
                        quantidade = valorNovo;
                        s = dao.buscarPorId(dao.buscarIdporPosicao(itemSelecionado)).getNomePrato();
                        double d = Double.parseDouble(dao.buscarPorId(dao.buscarIdporPosicao(itemSelecionado)).getPreco());
                        String dd = (dao.buscarPorId(dao.buscarIdporPosicao(itemSelecionado)).getPreco());
                        carrinho.put(s, dd);
                        total = new double[]{(d * valorNovo)};

                    }
                };
                picker.setOnValueChangedListener(pickerListener);
                AlertDialog.Builder builder = new  AlertDialog.Builder(MenuListActivity.this).setView(picker);
                builder.setTitle("Quantidade").setIcon(R.drawable.logo);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                    builder.show();

                //todo criar forma de passar alguns valores para outra classe ao finalizar pedido

            }
        });
    }
}