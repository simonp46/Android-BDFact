package com.example.appfacturacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    EditText identificacion,nombres, correo, contrasena;
    TextView registrar;
    Button iniciarsesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = findViewById(R.id.etnombres);
        correo = findViewById(R.id.etcorreo);
        contrasena = findViewById(R.id.etcontras);
        registrar = findViewById(R.id.tvregistrar);
        iniciarsesion = findViewById(R.id.btniniciarsesion);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VA A REGISTRO
                startActivity(new Intent(getApplicationContext(), registroclientes.class));
               }


        });
        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mcorreo = correo.getText().toString().trim();
                String mcontrasena = contrasena.getText().toString().trim();
                IniciarSesion(mcorreo,mcontrasena);

            }
        });

    }

    private void IniciarSesion(String mcorreo, String mcontrasena) {
        database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);
        SQLiteDatabase dbread = ohdb.getReadableDatabase();
        String sqlquery = "Select correo, contrasena From Cliente Where correo='"+mcorreo+"' and contrasena= '"+mcontrasena+"'";
        //tabla cusor.
        Cursor ccliente = dbread.rawQuery(sqlquery,null);
        if (ccliente.moveToFirst()){
            Intent aFactura = new Intent (getApplicationContext(),factura.class);
            aFactura.putExtra("eCedula", identificacion.getText().toString().trim());
            startActivity(aFactura);
        }
        else{
            Toast.makeText(getApplicationContext(),"Correo y/o contraseña invalidos",Toast.LENGTH_SHORT).show();
        }
    }



}