package com.example.appfacturacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class factura extends AppCompatActivity implements View.OnClickListener{
    EditText nrofactura, identificacion, fecha, valorf, saldof;
    Button guardarf, buscarf,abono, actualizarf, eliminarf,limpiar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        nrofactura = findViewById(R.id.etnrofacturaf);
        identificacion = findViewById(R.id.etidentf);
        fecha = findViewById(R.id.etfechaf);
        valorf = findViewById(R.id.etvalorf);
        saldof = findViewById(R.id.etsaldof);
        guardarf = findViewById(R.id.btnguardarf);
        buscarf = findViewById(R.id.btnbuscarf);
        abono = findViewById(R.id.btnabonarf);
        actualizarf = findViewById(R.id.btnactualizarf);
        eliminarf = findViewById(R.id.btneliminarf);
        limpiar = findViewById(R.id.btnlimpiar);

        identificacion.setText(getIntent().getStringExtra("eCedula"));

        abono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aAbonos = new Intent(getApplicationContext(), abonos.class);
                aAbonos.putExtra("eNrofactura", nrofactura.getText().toString().trim());
                aAbonos.putExtra("eSaldof",saldof.getText().toString().trim());
                startActivity(aAbonos);
            }
        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nrofactura.setText("");
                identificacion.setText("");
                fecha.setText("");
                valorf.setText("");
                saldof.setText("");

            }
        });




        guardarf.setOnClickListener(this);
        buscarf.setOnClickListener(this);
        actualizarf.setOnClickListener(this);
        eliminarf.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String mnrofactura = nrofactura.getText().toString().trim();
        String midentificacion = identificacion.getText().toString().trim();
        String mfecha = fecha.getText().toString().trim();
        String mvalorf = valorf.getText().toString().trim();
        String msaldof = saldof.getText().toString().trim();

        switch (view.getId()){
            case R.id.btnguardarf:
                guardarFactura(mnrofactura,midentificacion,mfecha,mvalorf,msaldof);
                break;
            case R.id.btnbuscarf:
                buscarFactura(mnrofactura,midentificacion);
                break;
            case R.id.btnactualizarf:
                actualizarFactura(mnrofactura,midentificacion,mfecha,mvalorf,msaldof);
                break;
            case R.id.btneliminarf:
                eliminarFactura(mnrofactura);
                break;

        }
    }

    private void eliminarFactura(String mnrofactura) {
        database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);
        SQLiteDatabase dbr = ohdb.getReadableDatabase();//Buscar
        String sqlquery = "Select saldo From Factura where nrofactura = '"+mnrofactura+"' and saldo = '0'";
        //Crear una tabla cursor
        Cursor cfactura = dbr.rawQuery(sqlquery,null);
        if (cfactura.moveToFirst()){
            //Confirmaciòn de borrado de la cuenta, a travès de AlertDialog
            AlertDialog.Builder adborrado = new AlertDialog.Builder(factura.this);
            adborrado.setMessage("Està seguro de eliminar la factura nùmero: "+mnrofactura);
            adborrado.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLiteDatabase dbw = ohdb.getWritableDatabase();// eliminar
                    dbw.execSQL("Delete From Factura Where nrofactura = '"+mnrofactura+"'");
                    Toast.makeText(getApplicationContext(),"Factura eliminada correctamente...", Toast.LENGTH_SHORT).show();
                }
            });
            adborrado.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog adborradom = adborrado.create();
            adborradom.show();
            // Fin confirmación de eliminación de cuenta

            SQLiteDatabase dbw = ohdb.getWritableDatabase(); //eliminar
            dbw.execSQL("Delete From Factura Where nrofactura = '"+mnrofactura+"'");
            Toast.makeText(getApplicationContext(),"Factura eliminada correctamente...",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
        }
    }



    private void actualizarFactura(String mnrofactura, String midentificacion, String mfecha, String mvalorf, String msaldof) {
        database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);
        SQLiteDatabase dbw = ohdb.getWritableDatabase();
        dbw.execSQL("UPDATE factura set saldo = '"+msaldof+"', fecha = '"+mfecha+"' where nrofactura = '"+mnrofactura+"'");
        Toast.makeText(getApplicationContext(),"Factura actualizada correctamente!...",Toast.LENGTH_SHORT).show();
    }

    private void buscarFactura(String mnrofactura,String midentificacion) {
        database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);
        SQLiteDatabase dbr = ohdb.getReadableDatabase(); //Buscar y Listar
        String sqlquery = "Select * From factura where nrofactura = '"+mnrofactura+"' and cedula = '"+midentificacion+"'";
        //Crear una tabla cursor
        Cursor ccuenta = dbr.rawQuery(sqlquery,null);
        if (ccuenta.moveToFirst()){
            nrofactura.setText(ccuenta.getString(0));
            identificacion.setText(ccuenta.getString(1));
            fecha.setText(ccuenta.getString(2));
            valorf.setText(ccuenta.getString(3));
            saldof.setText(ccuenta.getString(4));
        }
        else{
            Toast.makeText(getApplicationContext(),"La cuenta no existe...",Toast.LENGTH_SHORT).show();
        }

    }

    private void guardarFactura(String mnrofactura, String midentificacion, String mfecha, String mvalorf, String msaldof) {
        database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);
        SQLiteDatabase dbr = ohdb.getReadableDatabase(); //Buscar y Listar
        SQLiteDatabase dbw = ohdb.getWritableDatabase(); //Guardar, Actualizar, Eliminar
        //Buscar el nùmero de cuenta
        String sqlquery = "Select nrofactura From Factura where nrofactura = '"+mnrofactura+"'";
        //Crear una tabla cursor
        Cursor ccuenta = dbr.rawQuery(sqlquery,null);
        if (ccuenta.moveToFirst()){
            Toast.makeText(getApplicationContext(),"Nùmero de factura existente..Intèntelo con otro nùmero!!",Toast.LENGTH_SHORT).show();
        }
        else{
            //Agregar la cuenta para este cliente con la cedula del cliente logeado
            /*SQLiteDatabase dbw = ohdb.getWritableDatabase();*/
            //Crear una tabla tipo ContentValues para que tenga los mismos campos de la tabla fisica
            try{
                ContentValues cvfactura = new ContentValues();
                cvfactura.put("nrofactura",mnrofactura);
                cvfactura.put("cedula",midentificacion);
                cvfactura.put("fecha",mfecha);
                cvfactura.put("saldo",msaldof);
                cvfactura.put("vlrfactura",mvalorf);
                dbw.insert("factura",null,cvfactura);
                dbw.close();
                Toast.makeText(getApplicationContext(),"Factura agregada correctamente",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

    }

}