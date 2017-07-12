package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.MenuAdapter;

/**
 * Created by Édipo on 02/07/2017.
 */

public class MenuDetalhadoActivity extends Activity {
    private GridView gridView;
    private IDAO<Menu> dao;
    private MenuAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_listview_menu);
        dao = new MenuDAO(getApplicationContext());

        gridView = (GridView) findViewById(R.id.idMenuListView);
        adapter = new MenuAdapter(this, dao.listar());
        gridView.setAdapter(adapter);

    }

}
