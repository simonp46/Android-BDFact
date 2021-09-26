package com.example.appfacturacion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
    // Definir las tablas de la base de datos
    String tblCliente= "Create table Cliente(cedula text primary key, nombres text,correo text, contrasena text)";
    String tblFactura = "Create table Factura(nrofactura integer primary key, cedula text, fecha text,vlrfactura integer, saldo integer)";
    String tblAbono= "Create table Abono(nropago integer  primary key autoincrement, nrofactura integer, fecha text, valor integer)";
    public database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creaciòn de la(s) tabla (s)
        db.execSQL(tblCliente);
        db.execSQL(tblFactura);
        db.execSQL(tblAbono);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE Cliente");
        db.execSQL(tblCliente);
        db.execSQL("DROP TABLE Factura");
        db.execSQL(tblFactura);
        db.execSQL("DROP TABLE Abono");
        db.execSQL(tblAbono);

    }
}
