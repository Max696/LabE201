package com.example.lam_m.laboratorio1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_SOTARGE= 1000;
    private static final int RQS_OPEN_DOCUMENT_TREE = 2;
    private static final int READ_REQUEST_CODE= 42;
    Button btn_load,btn_save;
    TextView txt_out;
    EditText name,nameunz;
    Huffman hf;
    String codificacion;
    String totalName="";
    Switch hfOrLzw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_SOTARGE);

        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);

        }
        btn_load=(Button)findViewById(R.id.btn_load);
        btn_save =(Button)findViewById(R.id.bt_save);




        txt_out = (TextView)findViewById(R.id.tV_out);
        name = (EditText)findViewById(R.id.editText);
        nameunz =(EditText)findViewById(R.id.editText2) ;
        hfOrLzw = (Switch) findViewById(R.id.hforlzw);

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
            }
        });





      /*  btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename =name.getText().toString();
                String filename2 = nameunz.getText().toString();
                hf.descromprimir(hf.metodoFinal());

                try {
                    saveTextAsFile(filename,filename2,hf.decompress(),hf.metodoFinal());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });*/

      btn_save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent((Intent.ACTION_OPEN_DOCUMENT_TREE));
              startActivityForResult(intent,RQS_OPEN_DOCUMENT_TREE);

          }
      });

    }
    private String  readText(Uri uri) throws IOException {

        InputStream input = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder stringBuilder = new StringBuilder();
        String Line;
        while ((Line = reader.readLine()) != null) {
            stringBuilder.append(Line);
        }
        input.close();
        reader.close();
        return stringBuilder.toString();
    }
    public void performFileSearch()
        {
        Intent inten = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        inten.addCategory(Intent.CATEGORY_OPENABLE);
        inten.setType("text/*");
        startActivityForResult(inten,READ_REQUEST_CODE);
    }

    public void performFolderSearch()
    {
        Intent inten = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        inten.addCategory(Intent.CATEGORY_OPENABLE);
        inten.setType("text/*");
        startActivityForResult(inten,READ_REQUEST_CODE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            boolean hol = hfOrLzw.isChecked();
            String asda = "";


            if (data != null) {
                Uri uri = data.getData();
                totalName = uri.getLastPathSegment();
                Toast.makeText(this, uri.getPath(), Toast.LENGTH_LONG).show();

                try {
                    readText(uri);
                    String s = readText(uri);

                    if (!hol) {
                        hf = new Huffman(s);
                        codificacion = hf.cifrado1();
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == RQS_OPEN_DOCUMENT_TREE) {
            Uri uriTree = data.getData();
            Uri uri;

            String path = "";
            DocumentFile documentFile = DocumentFile.fromTreeUri(this, uriTree);

            path = uriTree.getLastPathSegment();
            // Toast.makeText(this, path,Toast.LENGTH_SHORT).show();
            String filename = name.getText().toString();
            String filename2 = nameunz.getText().toString();
            hf.descromprimir(hf.metodoFinal());

            try {
                saveTextAsFile(filename, filename2, hf.decompress(), hf.metodoFinal(), path
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


    }






    }

    public void irCompresion(View view){
        Intent intent = new Intent(this, Compresiones.class);
        startActivity(intent);
    }

    private void saveTextAsFile(String filename , String filename2,String content2, String content ,String path) throws FileNotFoundException {
        String fileName = filename+".huff";
        String fileName2 = filename2+".txt";
        File file = new File(path,fileName);
        File file2 = new File(path,fileName2);
        FileOutputStream fos = null;
        try
        {
           fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "guardado",Toast.LENGTH_SHORT).show();

            fos =null;
            fos =new FileOutputStream(file2);
            fos.write(content2.getBytes());
            fos.close();
            Toast.makeText(this, "guardado",Toast.LENGTH_SHORT).show();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Archivo no encontrado",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_REQUEST_SOTARGE)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
