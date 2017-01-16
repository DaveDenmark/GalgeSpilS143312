package com.example.id.galgespilaflevering.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class Listen extends android.app.ListActivity {
    ArrayList<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showDialog();
        Random rng = new Random();
        ArrayList<String> muligeOrd = getIntent().getExtras().getStringArrayList("muligeord");

            for (int i = 0; i<10; i++) {
                list.add(muligeOrd.get(rng.nextInt(muligeOrd.size())));
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
            setListAdapter(adapter);
            System.out.println("Valgte ord er" + list.toString());
        }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vælg det ord du gerne vil spille med")
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onListItemClick(ListView Drlist, View v, int position, long id) {

        //Tilføjer valgte item på listen til intentet.
        Intent i = new Intent(this,GameLaunch.class);
        i.putExtra("ordet",list.get(position));
        startActivity(i);

        System.out.println("hello "  + list.get(position));
    }

    }
