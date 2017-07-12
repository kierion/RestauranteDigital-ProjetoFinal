package trabalhofinal.usj.com.br.restaurantedigital.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import trabalhofinal.usj.com.br.restaurantedigital.dao.CadastroDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;

/**
 * Created by Édipo on 01/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "BancoMenu";
    private static int version = 18;


    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MenuDAO.criarTabela());
        db.execSQL(CadastroDAO.criarTabela());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS menu");
        db.execSQL("DROP TABLE IF EXISTS cadastro");
        onCreate(db);

    }
}
