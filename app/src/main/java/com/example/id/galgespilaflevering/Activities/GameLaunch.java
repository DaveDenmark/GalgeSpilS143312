package com.example.id.galgespilaflevering.Activities;

import android.app.*;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Color;
import android.widget.TextView;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import com.example.id.galgespilaflevering.R;
import com.example.id.galgespilaflevering.logik.Galgelogik;

public class GameLaunch extends Activity implements View.OnClickListener {
    Galgelogik logik = new Galgelogik();
    private TextView Info, Info2;
    private Button Play, HelpText, Nytspil;
    private EditText Textedit;
    private ImageView imagestatus;
    ProgressDialog pd;

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            logik.setOrdet(getIntent().getStringExtra("ordet"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch);
        logik.nulstil();

        //Preference setup
        SharedPreferences game_startup =
                PreferenceManager.getDefaultSharedPreferences(this);
        int intValue = game_startup.getInt("intValue", 0);
        SharedPreferences.Editor editor = game_startup.edit();
        editor.putInt("intValue", ++intValue);
        editor.commit();



        Info = (TextView) findViewById(R.id.InfoTekst);
        Info.setText("Start spil ved skrive ét bogstav og tryk dernæst gæt");
        Info.setTextColor(Color.parseColor("#ff0000ff"));

        Info2 = (TextView) findViewById(R.id.textView2);
        Info2.setText("Se statistik, hvis du vil se hvor mange gange du har spillet");
        Info2.setTextColor(Color.parseColor("#ff0000ff"));

        Play = (Button) findViewById(R.id.button);
        Play.setText("Gæt");
        Play.setTextColor(Color.parseColor("#ff0000ff"));

        Nytspil = (Button) findViewById(R.id.button3);
        Nytspil.setText("Nyt Spil");
        Nytspil.setTextColor(Color.parseColor("#ff0000ff"));

        HelpText = (Button) findViewById(R.id.button2);
        HelpText.setTextColor(Color.parseColor("#ff0000ff"));

        Textedit = (EditText) findViewById(R.id.editText);
        Textedit.setTextColor(Color.parseColor("#ff0000ff"));

        imagestatus = (ImageView) findViewById(R.id.imageView);
        imagestatus.setImageResource(R.drawable.galge);

        //Sætter onClicks
        Nytspil.setOnClickListener(this);
        Info.setOnClickListener(this);
        Play.setOnClickListener(this);
        Textedit.setOnClickListener(this);
        HelpText.setOnClickListener(this);

        //sætter bagrundsfarve
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

    }

    //Sætter tilbage knappen til den aktivitet jeg vil have.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(GameLaunch.this, hovedmenu.class));
        finish();
    }

    //Async som kører hentOrdFraDr metoden som erstatter ens array af ord
    public void getAsyncWords() {
        pd = ProgressDialog.show(this, "Vent", "Et øjeblik, henter data...");
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {logik.hentOrdFraDr();
                    opdaterSkærm();
                    return "Ordene blev hentet fra DRs Server";
                } catch (Exception e) {
                    //System.out.print(StackTraceElement.class);
                    return "Ordene blev ikke hentet korrekt";
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }

        } .execute();
    }
    //
    @Override
    public void onClick(View v) {
        if (v == Play) {
            String bogstav   = Textedit.getText().toString();
            if (bogstav.length() != 1) {
                Textedit.setError("Skriv præcis ét bogstav");
                return;
            }
            logik.gætBogstav(bogstav);
            Textedit.setText("");
            Textedit.setError(null);
            imagecheck();
            opdaterSkærm();
            System.out.println(logik.getOrdet());
        }

        else if (v == HelpText) {
            Intent i = new Intent(this, HelpView.class);
            startActivity(i);
        }
        else if (v == Nytspil) {
            showDialog();
            logik.nulstil();
            getAsyncWords();
            opdaterSkærm();
            Info2.setText("Se statistik, hvis du vil se hvor mange gange du har spillet");

        }
    }
    private void opdaterSkærm() {
        Info.setText("Gæt ordet: " + logik.getSynligtOrd());
        Info.append("\n\nDu har " + logik.getAntalForkerteBogstaver() + " forkerte:" + logik.getBrugteBogstaver());

        if (logik.erSpilletTabt()) {
            Info.setText("Du har tabt, ordet var: " + logik.getOrdet());
        }

        if (logik.erSpilletVundet()) {
            Info.setText("Du har vundet");
            Info2.setText("Vælg nyt spil for at prøve igen");

        }
    }
    private void imagecheck() {
        if (logik.getAntalForkerteBogstaver() == 1)
            imagestatus.setImageResource(R.drawable.forkert1);

        else if (logik.getAntalForkerteBogstaver() == 2)
            imagestatus.setImageResource(R.drawable.forkert2);

        else if (logik.getAntalForkerteBogstaver() == 3)
            imagestatus.setImageResource(R.drawable.forkert3);

        else if (logik.getAntalForkerteBogstaver() == 4)
            imagestatus.setImageResource(R.drawable.forkert4);

        else if (logik.getAntalForkerteBogstaver() == 5)
            imagestatus.setImageResource(R.drawable.forkert5);

        else if (logik.getAntalForkerteBogstaver() == 6)
            imagestatus.setImageResource(R.drawable.forkert6);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hvordan vil du finde dit ord?")
                .setCancelable(true)
                .setPositiveButton("Vælg ord fra liste", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GoToDr();
                    }
                })
                .setNegativeButton("Tilfældigt ord", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Skifter aktivitet til GoToDr
    public void GoToDr() {
        Intent i = new Intent(this, Listen.class);
        //tilføjer logik.getMuligerOrd() til intent for at kunne bruge den i Listen.
        i.putExtra("muligeord",logik.getMuligeOrd());

        startActivity(i);
    }
}