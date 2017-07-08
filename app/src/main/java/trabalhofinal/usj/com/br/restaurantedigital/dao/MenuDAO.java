package trabalhofinal.usj.com.br.restaurantedigital.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.DatabaseHelper;


/**
 * Created by Édipo on 01/07/2017.
 */

public class MenuDAO implements IDAO<Menu> {

    private DatabaseHelper helper;


    public static final String TABELA = "menu";
    public static final String ID = "_id";
    public static final String PRECO = "preco";
    public static final String NOME_PRATO = "nomePrato";
    public static final String DESCRICAO = "descricao";
    public static final String IMAGEM = "imagem";


    public MenuDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static final String[] COLUNAS = new String[]{
            ID, PRECO, NOME_PRATO, DESCRICAO, IMAGEM };

    public static String criarTabela() {
        return "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                PRECO + " DOUBLE, " +
                NOME_PRATO + " TEXT, " +
                DESCRICAO + " TEXT, " +
                IMAGEM + " BLOB);";
    }

    @Override
    public boolean salvar(Menu p) {
        ContentValues values = new ContentValues();
        values.put(PRECO, p.getPreco());
        values.put(NOME_PRATO, p.getNomePrato());
        values.put(DESCRICAO, p.getDescricao());
        values.put(IMAGEM, p.getImagem());

        long resultado = helper.getWritableDatabase().insert(TABELA,null,values);

        return (resultado > 0);
    }

    @Override
    public boolean excluir(Integer id) {

        if(id == null){
            return false;
        }

        int resultado = helper.getWritableDatabase().delete(MenuDAO.TABELA, MenuDAO.ID +" = ?", new String[]{id.toString()});
        return resultado > 0;

    }

    @Override
    public boolean atualizar(Menu p) {

        if(p == null){
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(ID, p.getId());
        values.put(PRECO, p.getPreco());
        values.put(NOME_PRATO, p.getNomePrato());
        values.put(DESCRICAO, p.getDescricao());
        values.put(IMAGEM, p.getImagem());

        String id = p.getId().toString();
        int resultado = helper.getWritableDatabase().update(TABELA, values, ID+" = ?", new String[]{id});

        return resultado > 0;

    }

    @Override
    public ArrayList<Menu> listar() {

        ArrayList<Menu> lista = new ArrayList<>();
        String sql = "SELECT "+ obterColunasConsulta() +" FROM "+ TABELA;
        Cursor cursor =  helper.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i++){
            Menu p = new Menu();
            preencherMenu(p, cursor);
            lista.add(p);
            cursor.moveToNext();
        }
        cursor.close();

        return lista;

    }
    private String obterColunasConsulta(){
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
    public Menu buscarPorId(Integer id) {
            String sql = "SELECT " + obterColunasConsulta() + " FROM " + TABELA + " WHERE " + ID + " = ?";
            Cursor cursor = helper.getReadableDatabase().rawQuery(sql, new String[]{id.toString()});
            cursor.moveToFirst();

            Menu p = null;

            if (cursor.getCount() > 0) {
                p = new Menu();
                preencherMenu(p, cursor);
            }
            cursor.close();

            return p;
        }


    private void preencherMenu(Menu p, Cursor cursor){
        p.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        p.setPreco(cursor.getString(cursor.getColumnIndex(PRECO)));
        p.setNomePrato(cursor.getString(cursor.getColumnIndex(NOME_PRATO)));
        p.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
        p.setImagem(cursor.getBlob(cursor.getColumnIndex(IMAGEM)));

    }

    public Integer buscarIdporPosicao(Integer posicao) {
        String sql = "SELECT " + ID + " FROM " + TABELA;
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
        ArrayList<Integer> arrID = new ArrayList<Integer>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            arrID.add(cursor.getInt(cursor.getColumnIndex(ID)));
            cursor.moveToNext();
        }
        cursor.close();
        Integer id = arrID.get(posicao);
        return  id;
    }

}
