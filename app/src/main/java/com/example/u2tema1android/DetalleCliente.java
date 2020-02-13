package com.example.u2tema1android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DetalleCliente extends AppCompatActivity {
    HttpURLConnection conexion;
    private String res;
    String codpersona;
    TextView cod;
    private EditText apellido;
    private Spinner sexo;
    private EditText nombre;
    private EditText telefono;
    private EditText direccion;

    private DetalleCliente detalle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);
        Bundle extras = getIntent().getExtras();

        codpersona = extras.getString("codigocli");
        cod = (TextView) findViewById(R.id.tv_codcliente);
        cod.setText(codpersona);

        nombre = (EditText) findViewById(R.id.txtnombre);
        apellido = (EditText) findViewById(R.id.txtapellido);
        telefono = (EditText) findViewById(R.id.txttelefono);
        direccion = (EditText) findViewById(R.id.txtdireccion);
        sexo = (Spinner) findViewById(R.id.sexo);

        try {

            URL url = new URL(this.getString(R.string.dominio) + this.getString(R.string.detallecliente) + codpersona);
            conexion = (HttpURLConnection) url.openConnection();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                res = linea;
            } else {
                Log.e("mierror", conexion.getResponseMessage());
            }
        } catch (Exception e) {

        }

        try {
            JSONArray json_array = new JSONArray(res);
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject objeto = json_array.getJSONObject(i);
                nombre.setText(objeto.getString("Nombre"));
                apellido.setText(objeto.getString("Apellidos"));
                telefono.setText(objeto.getString("celular"));
                direccion.setText(objeto.getString("Domicilio"));
            }
        } catch (JSONException e) {
            Log.i("MI error", e.toString());
            e.printStackTrace();
        }

    }


}
