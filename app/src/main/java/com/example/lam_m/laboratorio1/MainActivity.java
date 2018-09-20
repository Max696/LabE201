package com.example.lam_m.laboratorio1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_SOTARGE= 1000;
    private static final int READ_REQUEST_CODE= 42;
    Button btn_load,btn_save;
    TextView txt_out;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_SOTARGE);
            // requestPermissions(new String [](Manifest.permission.READ_EXTERNAL_STORAGE),PERMISSION_REQUEST_SOTARGE);

        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
            // requestPermissions(new String [](Manifest.permission.READ_EXTERNAL_STORAGE),PERMISSION_REQUEST_SOTARGE);

        }
        btn_load=(Button)findViewById(R.id.btn_load);
        btn_save =(Button)findViewById(R.id.bt_save);
        txt_out = (TextView)findViewById(R.id.tV_out);
        name = (EditText)findViewById(R.id.editText);

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performFileSearch();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename =name.getText().toString();

                try {
                    saveTextAsFile(filename,"hola");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private String readText(String input){

        File file = new File(input);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line= br.readLine())!=null)
            {
                text.append(line);
                text.append("\n");

            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  text.toString();
    }

    private void performFileSearch()
    {
        Intent inten = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        inten.addCategory(Intent.CATEGORY_OPENABLE);
        inten.setType("text/*");
        startActivityForResult(inten,READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==READ_REQUEST_CODE&&resultCode==Activity.RESULT_OK)
        {
            if(data!=null)
            {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":")+1);
               // Toast.makeText(this, ""+path, Toast.LENGTH_SHORT).show();
                txt_out.setText(readText(path));//texto
                Huffman hf = new Huffman(readText(path));

                Toast.makeText(this, "prueba", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void saveTextAsFile(String filename , String content ) throws FileNotFoundException {
        String fileName = filename+".huff";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        FileOutputStream fos = null;
        try
        {
           fos = new FileOutputStream(file);
            fos.write(content.getBytes());
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
