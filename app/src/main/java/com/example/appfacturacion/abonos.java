package com.example.appfacturacion;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class abonos extends AppCompatActivity {
   EditText pagoab,facturaab,fechaab,valorab;
   Button guardarab;
   double msaldo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonos);
        pagoab = findViewById(R.id.etnropago);
        facturaab = findViewById(R.id.etfactura);
        fechaab = findViewById(R.id.etfecham);
        valorab = findViewById(R.id.etvalorm);
        guardarab = findViewById(R.id.btnguardarm);
        facturaab.setText(getIntent().getStringExtra("eNrofactura"));
        msaldo = parseDouble(getIntent().getStringExtra("eSaldof"));

        guardarab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String mvalora = valorab.getText().toString();
            String pago = pagoab.getText().toString();
            double total = 0;
            //
                {
                    database ohdb = new database(getApplicationContext(),"bdfacturacion1",null,1);
                    SQLiteDatabase dbwf = ohdb.getWritableDatabase(); //factura
                    SQLiteDatabase dbwa = ohdb.getWritableDatabase(); //abono
                    //Agregar la cuenta para este cliente con la cedula del cliente logeado
                    /*SQLiteDatabase dbw = ohdb.getWritableDatabase();*/
                    //Crear una tabla tipo ContentValues para que tenga los mismos campos de la tabla fisica
                        try{
                            ContentValues cvabono = new ContentValues();
                            cvabono.put("nrofactura",facturaab.getText().toString().trim());
                            cvabono.put("fecha",fechaab.getText().toString().trim());
                            cvabono.put("valor",valorab.getText().toString().trim());
                            //Insert para la tabla de abono
                            dbwa.insert("abono",null,cvabono);
                            //dbwa.close();
                            msaldo = msaldo - parseDouble(valorab.getText().toString().trim());
                            //update tabla factura
                            dbwf.execSQL("UPDATE factura set saldo = '"+msaldo+"' where nrofactura = '"+facturaab.getText().toString().trim()+"'");
                            Toast.makeText(getApplicationContext(),"abono realizado correctamente",Toast.LENGTH_SHORT).show();
                            dbwa.close();
                            dbwf.close();
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                //
        });
   }
}