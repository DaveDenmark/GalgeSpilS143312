package com.example.id.galgespilaflevering.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.id.galgespilaflevering.R;
import com.example.id.galgespilaflevering.logik.Galgelogik;

import java.util.ArrayList;

public class Listen extends android.app.ListActivity {

    Galgelogik logik = new Galgelogik();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList drord = logik.getMuligeOrd();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, drord);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }
}
