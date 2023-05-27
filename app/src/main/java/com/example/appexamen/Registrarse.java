package com.example.appexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Registrarse extends AppCompatActivity {

    EditText txtNombre, txtCedula, txtUsuario, txtContrasena;
    RadioGroup rbtTipoRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        InicializarControler();
    }

    private void InicializarControler() {
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtCedula =(EditText)findViewById(R.id.txtCedula);
        txtUsuario =(EditText)findViewById(R.id.txtUsuario);
        txtContrasena =(EditText)findViewById(R.id.txtContrasena);
        rbtTipoRegistro = (RadioGroup)findViewById(R.id.rbgTipoRegistro);
    }

    private void RegistrarUsuario(View v){
        try {
            int rbtSeleccionado = rbtTipoRegistro.getCheckedRadioButtonId();
            switch (rbtSeleccionado){
                case R.id.rbtAdministrador:
                    SharedPreferences login = getSharedPreferences("credenciales.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = login.edit();

                    editor.putString("nombre",txtNombre.getText().toString());
                    editor.putString("cedula",txtCedula.getText().toString());
                    editor.putString("usuario",txtUsuario.getText().toString());
                    editor.putString("contraseña",txtContrasena.getText().toString());
                    editor.commit();

                    startActivity(new Intent(this,LoginActivity.class));
                    break;
                case R.id.rbtCliente:
                    boolean hayCliente = VerificarExistensiaCliente ();
                    String registro = txtNombre.getText().toString() + "|" +
                                    txtCedula.getText().toString() + "|" +
                                    txtUsuario.getText().toString() + "|" +
                                    txtContrasena.getText().toString() +"~";

                    if (hayCliente){
                        SobreEscribirCliente(registro);
                    }else {
                        OutputStreamWriter out = new OutputStreamWriter(
                                openFileOutput("credenciales.txt",Context.MODE_PRIVATE)
                        );
                        out.write(registro);
                        out.close();
                    }
                    break;

            }
        }catch (Exception e){
            Toast.makeText(this, "Error en "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void SobreEscribirCliente(String registro) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("credenciales.txt")));
            String texto = bf.readLine();
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("credenciales.txt",Context.MODE_PRIVATE));
            out.write(texto + registro);
            out.close();
        }catch (Exception e) {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean VerificarExistensiaCliente() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("credenciales.txt")));
            String texto = bf.readLine();
            if (!texto.isEmpty())
                return true;
        }catch (Exception e) {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}