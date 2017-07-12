package trabalhofinal.usj.com.br.restaurantedigital.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Funcionarios;

/**
 * Created by Édipo on 12/07/2017.
 */

public class FuncionarioAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Funcionarios> lista;

    public FuncionarioAdapter(Context context, ArrayList<Funcionarios> lista){
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

        Funcionarios funcionarios = lista.get(position);
        View layout;

        //Inflater transforma o xml em um objeto
        if (convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.layout_cadastro_cadastrar_funcionarios, null);
        }else {
            layout = convertView;
        }

        TextView txLogin = (TextView) layout.findViewById(R.id.idLoginFunc);
        TextView txSenha = (TextView) layout.findViewById(R.id.idSenhaFunc);
        TextView txCargo = (TextView) layout.findViewById(R.id.idCargoFunc);

        txLogin.setText(funcionarios.getLogin());
        txSenha.setText(funcionarios.getSenha());
        txCargo.setText(funcionarios.getCargo());


        return layout;
    }
}