package com.example.appexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class Parkins_Clientes extends AppCompatActivity {

    ListView ListViewEventos;
    List<Eventos>lst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkins_clientes);

        ListViewEventos=findViewById(R.id.list_Event);
    }
}