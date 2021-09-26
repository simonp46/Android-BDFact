package com.example.appfacturacion;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class abonos extends AppCompatActivity {
   EditText pagoab,facturaab,fechaab,valorab;
   Button guardarab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abonos);
        pagoab = findViewById(R.id.etnropago);
        facturaab = findViewById(R.id.etfactura);
        fechaab = findViewById(R.id.etfecham);
        valorab = findViewById(R.id.etvalorm);
        guardarab = findViewById(R.id.btnguardarm);

        guardarab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String mvalora = valorab.getText().toString();
            String pago = pagoab.getText().toString();
            double total = 0;


          }


        });
   }
}