package com.negrete.deyvis.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button bIngresar, bActualizar, bConsultar, bBorrar,bInventario,bVentas,bGanancia;
    EditText eCodigo, eNombre, eCantidad,eValor;
    TextView resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bIngresar = (Button) findViewById(R.id.bInsertar);
        bActualizar = (Button) findViewById(R.id.bActualizar);
        bConsultar = (Button) findViewById(R.id.bBuscar);
        bBorrar = (Button) findViewById(R.id.bEliminar);
        bInventario = (Button) findViewById(R.id.bInventario);
        bVentas = (Button) findViewById(R.id.bVentas);
        bGanancia = (Button) findViewById(R.id.bGanancia);

        eCodigo = (EditText) findViewById(R.id.eCodigo);
        eNombre = (EditText) findViewById(R.id.eNombre);
        eCantidad = (EditText) findViewById(R.id.eCantidad);
        eValor=(EditText) findViewById(R.id.eValor);
        resultado = (TextView) findViewById(R.id.resultado);



        UsuariosSQLiteHelper usuario = new UsuariosSQLiteHelper(this);
        db = usuario.getWritableDatabase();

        ver_Tabla();

        bIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = eNombre.getText().toString();
                String cantidad = eCantidad.getText().toString();
                String valor = eValor.getText().toString();/////----<<<<
                if ((nombre != null) && (!nombre.equals(""))) {
                    // cadena no está vacía
                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("nombre", nombre);
                    nuevoRegistro.put("valor", valor);/////---<<<<
                    nuevoRegistro.put("cantidad", cantidad);
                    db.insert("Usuarios", null, nuevoRegistro);
                    ver_Tabla();
                } else {
                    Toast.makeText(getApplicationContext(), "Favor ingrese un nombre ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        bActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = eNombre.getText().toString();
                String cantidad = eCantidad.getText().toString();
                String codigo = eCodigo.getText().toString();
                if ( (codigo != null) && (!codigo.equals("")) ) {
                    if ( (nombre != null) && (!nombre.equals("")) ){
                        if ( (cantidad!= null) && (!cantidad.equals("")) ){


                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre",nombre); ///---<<<<<
                nuevoRegistro.put("cantidad",cantidad);//----<<<<codigo
                nuevoRegistro.put("codigo",codigo);

                db.update("Usuarios",nuevoRegistro,"codigo="+codigo,null);
                ver_Tabla();} else {Toast.makeText(getApplicationContext(),"Favor ingrese la cantidad a actualizar cantidad",Toast.LENGTH_SHORT).show();}
            } else {  Toast.makeText(getApplicationContext(),"Favor ingrese el Código y el nombre a actualizar cantidad",Toast.LENGTH_SHORT).show(); }
                } else {  Toast.makeText(getApplicationContext(),"Favor ingrese el Código y el nombre a actualizar cantidad",Toast.LENGTH_SHORT).show(); }}
        });

        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = eCodigo.getText().toString();
                if ( (codigo != null) && (!codigo.equals("")) ) {
                    db.delete("Usuarios", "codigo=" + codigo, null);
                    ver_Tabla();
                } else { Toast.makeText(getApplicationContext(),"Favor ingrese el Código del peluche para eliminar",Toast.LENGTH_SHORT).show();}

            }
        });

        bConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eNombre.getText().toString();
                String year = eCantidad.getText().toString();
                String id = eCodigo.getText().toString();


               if  ( (name != null) && (!name.equals("")) ) {

                String[] campos = new String[]{"codigo","nombre","cantidad"};
                String[] args = new String[]{name};

                Cursor c = db.query("Usuarios", campos, "nombre=?", args,null,null,null);
                if (c.moveToFirst()) {
                    resultado.setText("");

                    do {
                        String codigo = c.getString(0);
                        String nombre = c.getString(1);
                        int cantidad = c.getInt(2);
                        resultado.append(" " + codigo + " - " + nombre + " - " + cantidad + "\n");
                    } while (c.moveToNext());
                }
                } else { Toast.makeText(getApplicationContext(),"Favor ingrese el nombre de busqueda",Toast.LENGTH_SHORT).show();}
                //ver_Tabla();
            }
        });


        bInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventario();
            }
        });

        bVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bGanancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText("Las ganancias totales son: ");

            }
        });

        bVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado.setText("Venta realizada: ");
            }
        });



    }
    protected void inventario (){
        Cursor c =db.rawQuery("SELECT codigo, nombre, cantidad, valor FROM Usuarios", null);
        resultado.setText("");
        if (c.moveToFirst()) {
            do {
                String codigo = c.getString(0);
                String nombre = c.getString(1);
                int cantidad = c.getInt(2);
                int valor=c.getInt(3);
                resultado.append( "Nombre: " + nombre +"--->Cantidad: " + cantidad + "\n");
            } while (c.moveToNext());


        }
    }

    protected void ver_Tabla(){
        Cursor c =db.rawQuery("SELECT codigo, nombre, cantidad, valor FROM Usuarios", null);
        resultado.setText("");
        if (c.moveToFirst()){
           do{
               String codigo = c.getString(0);
               String nombre = c.getString(1);
               int cantidad = c.getInt(2);
               int valor=c.getInt(3);
                resultado.append("Código: "+codigo+"\n"+"Nombre: "+nombre+"\n"+"Cantidad: "+cantidad+"\n"+"valor: "+valor+"\n\n\n");
            }while (c.moveToNext());
        }
    }

}


