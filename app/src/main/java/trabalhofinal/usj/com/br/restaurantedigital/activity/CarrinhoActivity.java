package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import trabalhofinal.usj.com.br.restaurantedigital.R;

/**
 * Created by Édipo on 01/07/2017.
 */

public class CarrinhoActivity extends Activity {
    private Button finaliza;
    private TextView comanda;
    private MenuListActivity lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carrinho_itens);

        finaliza = (Button) findViewById(R.id.idBotaoFinalizarPedido);
        comanda = (TextView) findViewById(R.id.idTextViewComanda);

        /**
        Set keys = lista.carrinho.keySet();

        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            String value = lista.carrinho.get(key);
            comanda.setText(key + " = " + value + "\n");
        }


        finaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //// TODO: criar lógica que envie via rest as informações para outro usuário
                Toast.makeText(CarrinhoActivity.this, R.string.pedido_solicitado, Toast.LENGTH_SHORT).show();

            }
        });

    }*/
    }
}
