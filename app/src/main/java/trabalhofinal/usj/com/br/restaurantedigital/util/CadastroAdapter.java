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
 * Created by Édipo on 07/07/2017.
 */

public class CadastroAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Menu> lista;

    public CadastroAdapter(Context context, ArrayList<Menu> lista){
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
            layout = inflater.inflate(R.layout.layout_cadastro_listar_itens_cadastrados, null);
        }else {
            layout = convertView;
        }

        TextView txNome = (TextView) layout.findViewById(R.id.idListaItens_Nome_Item);
        TextView txPreco = (TextView) layout.findViewById(R.id.idListaItens_Preco_Item);
        ImageView ivImagem = (ImageView) layout.findViewById(R.id.idListaItens_Imagem_do_Item);

        txNome.setText(menu.getNomePrato());
        txPreco.setText(menu.getPreco());

        byte[] imagem = menu.getImagem();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
        ivImagem.setImageBitmap(bitmap);

        return layout;
    }
}
