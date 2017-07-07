package trabalhofinal.usj.com.br.restaurantedigital.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import trabalhofinal.usj.com.br.restaurantedigital.R;
import trabalhofinal.usj.com.br.restaurantedigital.dao.IDAO;
import trabalhofinal.usj.com.br.restaurantedigital.dao.MenuDAO;
import trabalhofinal.usj.com.br.restaurantedigital.entity.Menu;
import trabalhofinal.usj.com.br.restaurantedigital.util.Constantes;

/**
 * Created by Édipo on 02/07/2017.
 */

public class CadastroItemActivity  extends Activity {

    private EditText preco, nomeItem, descricao;
    private IDAO<Menu> menuDAO;
    private int idItem = 0;
    public static final String EXTRA_ID_MENU = "ID_MENU";
    public static final int EXTRA_TIRA_FOTO = 2;
    public static final int EXTRA_BUSCA_FOTO = 1;
    public String voltar;
    public ImageView imagemCadastrar;
    private Button tiraFoto, buscaFoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_cadastrar_item);

        imagemCadastrar = (ImageView) findViewById(R.id.idCadastrar_Imagem);
        preco = (EditText) findViewById(R.id.idCadastrarPrecoItem);
        nomeItem = (EditText) findViewById(R.id.idCadastrarNomeItem);
        descricao = (EditText) findViewById(R.id.idCadastrarDescricaoItem);
        tiraFoto = (Button)  findViewById(R.id.idTirarFoto);
        buscaFoto = (Button)  findViewById(R.id.idBotaoSD);
        menuDAO = new MenuDAO(getApplicationContext());

        //Compara versão do android para poder usar um método depreciado ou não.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imagemCadastrar.setImageDrawable(getApplicationContext().getDrawable(R.drawable.icons17));
        } else {
            imagemCadastrar.setImageDrawable(getResources().getDrawable(R.drawable.icons17));
        }

        //...
        Integer idItem = getIntent().getIntExtra(MenuDAO.ID, 0);
        if (idItem == null){
            idItem = 0;
        }
        if(idItem > 0){
            prepararEdicao(idItem);
        }


        buscaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new  Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, EXTRA_BUSCA_FOTO);
            }
        });

        tiraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
                startActivityForResult(cameraIntent, EXTRA_TIRA_FOTO);
            }
        });

    }

    /////VERIFICA SE FOI SELECIONADA UMA FOTO OU NAO E COLOCA ELA NO IMAGEVIEW PARA APRESENTAÇÃO
    protected void onActivityResult(int requestCode, int resultCode, Intent data){


        if (requestCode == EXTRA_BUSCA_FOTO && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imagemCadastrar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }else if (requestCode == EXTRA_TIRA_FOTO && resultCode == RESULT_OK) {
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            Uri imgUri=Uri.fromFile(picture);
            imagemCadastrar.setImageURI(imgUri);
        }

    }


    public void cadastrar(View view) {
        Menu p = new Menu();
        p.setPreco(preco.getText().toString());
        p.setNomePrato(nomeItem.getText().toString());
        p.setDescricao(descricao.getText().toString());

        if (imagemCadastrar != null) {
            p.setImagem(conversorImagemEmByte(imagemCadastrar));
        }else {
            Toast.makeText(this, "erro imageview nulo",
                    Toast.LENGTH_SHORT).show();
            imagemCadastrar.setImageResource(R.drawable.icon22);
            p.setImagem(conversorImagemEmByte(imagemCadastrar));

        }
        boolean sucesso = false;

        Integer id = getIntent().getIntExtra(MenuDAO.ID, 0);

        if(id == 0){
            sucesso = menuDAO.salvar(p);
        }
        else{
            p.setId(id);
            sucesso = menuDAO.atualizar(p);
        }

        if(sucesso){
            Toast.makeText(this, getString(R.string.registro_item_salvo),
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(
                    this, CadastroMenuListActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else{
            Toast.makeText(this, getString(R.string.registro_item_erro),
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void prepararEdicao(Integer id){
        Menu p = menuDAO.buscarPorId(id);

        if(p == null){
            return;
        }

        preco.setText(p.getPreco());
        nomeItem.setText(p.getNomePrato());
        descricao.setText(p.getDescricao());
        //imagemCadastrar.setImageResource(R.drawable.icons29);
        converteByteEmImg(p.getImagem());

    }

    //modifica a ação do botão voltar
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CadastroMenuListActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private byte[] conversorImagemEmByte (ImageView view){

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bm = view.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        return byteArray;
    }

    private void converteByteEmImg (byte[] bts){

        Bitmap bm = BitmapFactory.decodeByteArray(bts, 0, bts.length);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        imagemCadastrar.setMinimumHeight(dm.heightPixels);
        imagemCadastrar.setMinimumWidth(dm.widthPixels);
        imagemCadastrar.setImageBitmap(bm);
    }

}