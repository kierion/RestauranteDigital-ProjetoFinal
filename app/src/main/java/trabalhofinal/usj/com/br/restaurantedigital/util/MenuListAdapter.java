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

public class MenuListAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<Menu> menuList;

    public MenuListAdapter(Context context, int layout, ArrayList<Menu> menuList) {
        this.context = context;
        this.layout = layout;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuList.indexOf(position);
    }


    private class ViewHolder{
        ImageView imageView;
        TextView txtNome, txtPreco;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtNome = (TextView) row.findViewById(R.id.txtNome);
            holder.txtPreco = (TextView) row.findViewById(R.id.txtPreco);
            holder.imageView = (ImageView) row.findViewById(R.id.idTeste);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Menu menu  = menuList.get(position);

        holder.txtNome.setText(menu.getNomePrato());
        holder.txtPreco.setText(menu.getPreco());
        byte[] foodImage = menu.getImagem();
        //DECODIFICA PARA UM BITMAP
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
