package trabalhofinal.usj.com.br.restaurantedigital.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;

/**
 * Created by Édipo on 08/07/2017.
 */

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Menu> lista;

    public MenuAdapter(Context context, ArrayList<Menu> lista){
        this.context =context;
        this.lista = lista;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Menu menu = lista.get(position);
        View layout;

        //Inflater transforma o xml em um objeto
        if (convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.layout_menu_menu_detalhado, null);
        }else {
            layout = convertView;
        }

        TextView txNome = (TextView) layout.findViewById(R.id.idLabel_Descricao_Menu_Detalhado);
        TextView txPreco = (TextView) layout.findViewById(R.id.idPreço_do_Item_Menu_Detalhado);
        TextView txDescricao = (TextView) layout.findViewById(R.id.idTexto_Descricao_Menu_Detalhado);
        ImageView ivImagem = (ImageView) layout.findViewById(R.id.idImagem_view_pager_menu_detalhado);

        txNome.setText(menu.getNomePrato());
        txPreco.setText(menu.getPreco());
        txDescricao.setText(menu.getDescricao());

        byte[] imagem = menu.getImagem();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
        ivImagem.setImageBitmap(bitmap);

        return layout;
    }
}

