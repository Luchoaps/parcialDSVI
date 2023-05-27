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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RadioGroup rbgTipoLogin;
    EditText txtUsuario,txtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InicializarControles();
        this.ValidarSession();

    }

    private void ValidarSession() {
        SharedPreferences login = getSharedPreferences("Login",Context.MODE_PRIVATE);
        int estado = login.getInt("estado",0);
        if (estado == 1){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void InicializarControles() {
        txtUsuario = (EditText)findViewById(R.id.txtUsuario);
        txtContrasena = (EditText)findViewById(R.id.txtContrasena);

        rbgTipoLogin = (RadioGroup)findViewById(R.id.rbgTipoLogin);
    }

    public void Loguear(View v){
        try {
            SharedPreferences credenciales = getSharedPreferences("Credenciales",Context.MODE_PRIVATE);
            String usuarioRegistrado = credenciales.getString("usuario","");
            String contrasenaRegistrado = credenciales.getString("contraseña","");

            String user = txtUsuario.getText().toString();
            String contra = txtContrasena.getText().toString();

            boolean existeUsuario = VerificarUsuariosArchivo(user,contra);
            if (existeUsuario){

            }

            if (user.equals(usuarioRegistrado) && contra.equals(contrasenaRegistrado)){

                int tipoLogin = rbgTipoLogin.getCheckedRadioButtonId();
                SharedPreferences login = getSharedPreferences("Login",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = login.edit();

                switch (tipoLogin){
                    case R.id.rbtAdministrador:
                        editor.putInt("estado",1);
                        editor.putString("nombre","kakaroto Sanjur");
                        editor.putString("usuario","kakaroto");
                        editor.commit();
                        break;
                    case R.id.rbtCliente:
                        boolean existeUsuaro = VerificarUsuariosArchivo(user,contra);
                        if (existeUsuaro){
                            editor.putInt("estado",1);
                            editor.putString("nombre","kakaroto Sanjur");
                            editor.putString("usuario","kakaroto");
                            editor.commit();
                        }
                        break;
                    default:
                        break;
                }
                startActivity(new Intent(this,MainActivity.class));
            }else{
                Toast.makeText(this,"No esta en lista",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(this,"Error "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private boolean VerificarUsuariosArchivo(String user, String contra) {
        try {
            List<Usuarios> usuarios = ConvertirArchivoList();

            for (Usuarios usuario : usuarios){
                if (user.equals(usuario.getUsuario()) && contra.equals(usuario.getContrasena())){
                    return true;
                }
            }

        }catch (Exception e){
            Toast.makeText(this,"Error "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;

    }

    private List<Usuarios> ConvertirArchivoList() {
        try{
            List<Usuarios> usuarios = new ArrayList<>();
            String texto = new BufferedReader(
                    new InputStreamReader(openFileInput("credenciales.txt"))).readLine();

            String[] arrUsuarios = texto.split("~");

            for (String usuario : arrUsuarios){
                String[] arrUsuario = usuario.split("\\|");
                Usuarios newUsuario = new Usuarios(
                        arrUsuario[0],
                        arrUsuario[1],
                        arrUsuario[2],
                        arrUsuario[3]
                );

                usuarios.add(newUsuario);
            }
            return usuarios;

        }catch (Exception e){

        }
        return new ArrayList<Usuarios>();
    }


}