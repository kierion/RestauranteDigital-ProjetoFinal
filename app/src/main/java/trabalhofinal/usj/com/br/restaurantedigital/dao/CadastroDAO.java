package trabalhofinal.usj.com.br.restaurantedigital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Funcionarios;
import trabalhofinal.usj.com.br.restaurantedigital.util.DatabaseHelper;

/**
 * Created by Édipo on 12/07/2017.
 */

public class CadastroDAO implements IDAO<Funcionarios> {

    private static DatabaseHelper helper;


    public static final String TABELA = "cadastro";
    public static final String ID = "_id";
    public static final String LOGIN = "login";
    public static final String SENHA = "senha";
    public static final String CARGO = "cargo";


    public CadastroDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static final String[] COLUNAS = new String[]{
            ID, LOGIN, SENHA, CARGO };

    public static String criarTabela() {
        return "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                LOGIN + " TEXT, " +
                SENHA + " TEXT, " +
                CARGO + " TEXT);";
    }

    @Override
    public boolean salvar(Funcionarios funcionarios) {
        ContentValues values = new ContentValues();
        values.put(LOGIN, funcionarios.getLogin());
        values.put(SENHA, funcionarios.getSenha());
        values.put(CARGO, funcionarios.getCargo());


        long resultado = helper.getWritableDatabase().insert(TABELA,null,values);

        return (resultado > 0);
    }


    @Override
    public boolean excluir(Integer id) {

        if(id == null){
            return false;
        }

        int resultado = helper.getWritableDatabase().delete(TABELA, ID +" = ?", new String[]{id.toString()});
        return resultado > 0;

    }
    @Override
    public boolean atualizar(Funcionarios p) {

        if(p == null){
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(ID, p.getId());
        values.put(LOGIN, p.getLogin());
        values.put(SENHA, p.getSenha());
        values.put(CARGO, p.getCargo());


        String id = String.valueOf(p.getId());
        int resultado = helper.getWritableDatabase().update(TABELA, values, ID+" = ?", new String[]{id});

        return resultado > 0;

    }

    @Override
    public ArrayList<Funcionarios> listar() {

        ArrayList<Funcionarios> lista = new ArrayList<>();
        String sql = "SELECT "+ obterColunasConsulta() +" FROM "+ TABELA;
        Cursor cursor =  helper.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++){
            Funcionarios funcionarios = new Funcionarios();
            preencherFuncionarios(funcionarios, cursor);
            lista.add(funcionarios);
            cursor.moveToNext();
        }
        cursor.close();

        return lista;

    }

    private static String obterColunasConsulta(){
        String colunasString = "";
        for(int i = 0; i < COLUNAS.length; i++){
            colunasString+= COLUNAS[i];
            if(i < (COLUNAS.length-1)){
                colunasString+=", ";
            }
        }
        return colunasString;
    }

    @Override
    public Funcionarios buscarPorId(Integer id) {
        String sql = "SELECT " + obterColunasConsulta() + " FROM " + TABELA + " WHERE " + ID + " = ?";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql, new String[]{id.toString()});
        cursor.moveToFirst();

        Funcionarios funcionario = null;

        if (cursor.getCount() > 0) {
            funcionario = new Funcionarios();
            preencherFuncionarios(funcionario, cursor);
        }
        cursor.close();

        return funcionario;
    }

    private static void preencherFuncionarios(Funcionarios p, Cursor cursor) {
        p.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        p.setLogin(cursor.getString(cursor.getColumnIndex(LOGIN)));
        p.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
        p.setCargo(cursor.getString(cursor.getColumnIndex(CARGO)));
    }


    public Integer buscarIdporPosicao(Integer posicao) {
        String sql = "SELECT " + ID + " FROM " + TABELA;
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
        ArrayList<Integer> arrID = new ArrayList<>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            arrID.add(cursor.getInt(cursor.getColumnIndex(ID)));
            cursor.moveToNext();
        }
        cursor.close();
        return (arrID.get(posicao));
    }

    public Boolean validarLogin(String login, String senha) {

        String[] selectionArgs = new String[]{login, senha};
        Cursor cursor;
        String sql = "SELECT " + LOGIN + ", " + SENHA + " FROM " + TABELA + " WHERE " + LOGIN + " =? AND " + SENHA + " =? ";
        cursor = helper.getReadableDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }
}