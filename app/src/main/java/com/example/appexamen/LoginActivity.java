package com.example.appexamen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PopularDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PopularDatos() throws Exception{
        SharedPreferences login = getSharedPreferences("Login", Context.MODE_PRIVATE);

        TextView dato = (TextView)findViewById(R.id.usuario);
        dato.setText("Bienvenidos => "+login.getString("nombre",""));

        BufferedReader bf = new BufferedReader(
                new InputStreamReader(openFileInput("credenciales.txt")));

        String texto = bf.readLine();
        int x = 1;
    }

    public void Logout(View v){
        SharedPreferences login = getSharedPreferences("Login", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = login.edit();
        editor.putInt("estado",0);
        editor.commit();

        startActivity(new Intent(this,LoginActivity.class));
    }
}
