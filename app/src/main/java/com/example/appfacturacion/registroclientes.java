package com.example.appfacturacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class registroclientes extends AppCompatActivity {
    EditText cedula,nombres,correo,contrasena;
    TextView iniciarsesion;
    Button registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroclientes);
        cedula = findViewById(R.id.etcedula);
        nombres = findViewById(R.id.etnombres);
        correo = findViewById(R.id.etcorreo);
        contrasena = findViewById(R.id.etcontras);
        iniciarsesion = findViewById(R.id.tviniciarsesion);
        registrar = findViewById(R.id.btnregistrar);

        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String mcedula = cedula.getText().toString().trim();
            String mnombres = nombres.getText().toString().trim();
            String mcorreo = correo.getText().toString().trim();
            String mcontras = contrasena.getText().toString().trim();

            registrarCliente(mcedula,mnombres,mcorreo,mcontras);
            }
        });
    }
    private void registrarCliente(String mcedula, String mnombres,String mcorreo, String mcontras) {
        database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);

        SQLiteDatabase db = ohdb.getReadableDatabase();
        String sqlquery = "Select cedula From Cliente where cedula = '"+mcedula+"'";

        Cursor ccliente = db.rawQuery(sqlquery,null);
        if (ccliente.moveToFirst()){
            Toast.makeText(getApplicationContext(),"correo Existente!!",Toast.LENGTH_SHORT).show();
        }
        else{
            SQLiteDatabase dbag = ohdb.getWritableDatabase();

            try{
                ContentValues cvcliente = new ContentValues();
                cvcliente.put("cedula",mcedula);
                cvcliente.put("nombres",mnombres);
                cvcliente.put("correo",mcorreo);
                cvcliente.put("contrasena",mcontras);
                dbag.insert("Cliente",null,cvcliente);
                dbag.close();
                Toast.makeText(getApplicationContext(),"Cliente registrado correctamente",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }
}