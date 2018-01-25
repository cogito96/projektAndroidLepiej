package com.example.aa.systemymobilne;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aa.systemymobilne.database.PytaniaDbAdapter;
import com.example.aa.systemymobilne.model.PytaniaGra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KategoriaHistoria extends AppCompatActivity {

List<PytaniaGra> listaPytanTest;
Cursor kursor;
PytaniaDbAdapter pytaniaDbAdapter;
   DanePomoc danePomoc;
   TextView pytanieText , punktyText , imieGracza ;
   ImageButton graOdpowiedzTak , graOdpowiedzNie;

   Random r = new Random();
   int n;
   int punkty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoria_historia);

        znajdzWidoki();
        danePomoc = new DanePomoc(this);

        pytaniaDbAdapter = new PytaniaDbAdapter(getApplicationContext());
        pytaniaDbAdapter.open();

        listaPytanTest = new ArrayList<PytaniaGra>();
        kursor = getAllEntriesFromDb();
        updateTaskList();
        if(listaPytanTest.isEmpty())
        {
            Toast.makeText(getBaseContext(), "NIE MA PYTAN Z TEJ KATEGORII" , Toast.LENGTH_SHORT).show();
            return;
        }
        n = r.nextInt(listaPytanTest.size());

        pytanieText.setText(listaPytanTest.get(n).getPytanie()); //take random question

        graOdpowiedzTak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaPytanTest.get(n).getOdpowiedz().equals("PRAWDA")){
                    punkty++;
                    listaPytanTest.remove(n);
                    punktyText.setText("Score: "+punkty);
                    if (listaPytanTest.size() == 0){
                        rezultat();
                    }else{
                        n = r.nextInt(listaPytanTest.size());
                        pytanieText.setText(listaPytanTest.get(n).getPytanie());
                    }
                }else{
                    rezultat();
                }
            }
        });

        graOdpowiedzNie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaPytanTest.get(n).getOdpowiedz().equals("FALSZ")){
                    punkty++;
                    listaPytanTest.remove(n);
                    punktyText.setText("Score: "+punkty);
                    if (listaPytanTest.size() == 0){
                        rezultat();
                    }else{
                        n = r.nextInt(listaPytanTest.size());
                        pytanieText.setText(listaPytanTest.get(n).getPytanie());
                    }
                }else{
                    rezultat();
                }
            }
        });
    }

    private void znajdzWidoki()
    {
        pytanieText = (TextView) findViewById(R.id.gra_pytanie);
        punktyText = (TextView) findViewById(R.id.gra_punkty);
        imieGracza = (TextView) findViewById(R.id.gra_imieGracza);
        graOdpowiedzTak = (ImageButton) findViewById(R.id.gra_odpowiedzTak);
        graOdpowiedzNie = (ImageButton) findViewById(R.id.gra_odpowiedzNie);
    }



    public void rezultat()
    {
        danePomoc.zapiszIlosPunktow("PUNKTYHISTORIA", punkty);
        startActivity(new Intent(KategoriaHistoria.this, KategoriaHistoriaRezultat.class));
        finish();
    }




    private Cursor getAllEntriesFromDb() {
        kursor = pytaniaDbAdapter.getAllTodos();
        if(kursor != null) {
            startManagingCursor(kursor);
            kursor.moveToFirst();
        }
        return kursor;
    }

    private void updateTaskList() {
        if(kursor != null && kursor.moveToFirst()) {
            do {
                long id = kursor.getLong(PytaniaDbAdapter.ID_COLUMN);
                String description = kursor.getString(PytaniaDbAdapter.PYTANIE_KOLUMNA);
                String completed = kursor.getString(PytaniaDbAdapter.ODPOWIEDZ_KOLUMNA);
                String kategoria = kursor.getString(PytaniaDbAdapter.KATEGORIA_KOLUMNA);

                if(kategoria.equals("HISTORIA"));
                {
                    listaPytanTest.add(new PytaniaGra(id, description, completed, kategoria));
                }
            } while(kursor.moveToNext());
        }
    }
}
