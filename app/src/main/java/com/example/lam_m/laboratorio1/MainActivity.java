package com.example.lam_m.laboratorio1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
    LZW lzw;
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
    public void performFileSearch()       {
        Intent inten = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        inten.addCategory(Intent.CATEGORY_OPENABLE);
        inten.setType("text/*");
        startActivityForResult(inten,READ_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean hol = hfOrLzw.isChecked();
        if (data != null) {
            Uri uri = data.getData();
            totalName = uri.getLastPathSegment();
            if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                String s = "";




                try {
                    s=readText(uri);



                    if (!hol) {
                        hf = new Huffman(s);
                        codificacion = hf.cifrado1();
                    } else {
                        lzw = new LZW(s);
                        codificacion = lzw.textoComprimido;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (resultCode == Activity.RESULT_OK && requestCode == RQS_OPEN_DOCUMENT_TREE) {
                Uri uriTree = data.getData();
                String s = "";
                try {
                    s = readText(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }



                String path = "";
                DocumentFile documentFile = DocumentFile.fromTreeUri(this, uriTree);
                uriTree = documentFile.getUri();
                Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uriTree,
                        DocumentsContract.getTreeDocumentId(uriTree));
                path = getPath(this, docUri);
                String filename = name.getText().toString();
                String filename2 = nameunz.getText().toString();


                try {
                    if (!hol) {
                        hf =new Huffman(s);
                        codificacion = hf.cifrado1();
                        saveTextAsFile(filename, filename2,hf.decompress(),hf.metodoFinal(), path);
                        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    } else {
                        lzw = new LZW(s);
                        saveTextAsFile(filename, filename2,lzw.deCompressArchivo(lzw.textoComprimido),lzw.textoComprimido, path);
                        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }

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


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
