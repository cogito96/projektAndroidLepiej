package com.example.aa.systemymobilne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class KategoriaHistoriaRezultat extends AppCompatActivity {

    DanePomoc danePomoc;
    TextView imie , iloscBiezacychPunktow, iloscRekordowychPunktow;

    Button powrotDoMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoria_historia_rezultat);

        zaladujWidoki();

        int points = danePomoc.zwrocIloscPunktow("PUNKTYHISTORIA", 0);
        int best = danePomoc.zwrocIloscPunktow("NAJLEPSZY_WYNIK_HISTORIA", 0);
        iloscBiezacychPunktow = (TextView) findViewById(R.id.rezultat_biezacyWynik);
        iloscRekordowychPunktow = (TextView) findViewById(R.id.rezultat_najlepszyWynik);

        imie.setText("Good luck next time, "+ danePomoc.receiveDataString("NAME", "User"));

        iloscBiezacychPunktow.setText(""+points);
        if (points > best){
            best = points;
            danePomoc.zapiszIlosPunktow("NAJLEPSZY_WYNIK_HISTORIA", best);
        }
        iloscRekordowychPunktow.setText(""+best);


        powrotDoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KategoriaHistoriaRezultat.this , WyborKategorii.class));
                finish();
            }
        });
    }

    private void zaladujWidoki()
    {
        imie = (TextView) findViewById(R.id.rezultat_imie);
        iloscBiezacychPunktow = (TextView) findViewById(R.id.rezultat_biezacyWynik);
        iloscRekordowychPunktow = (TextView) findViewById(R.id.rezultat_najlepszyWynik);

        powrotDoMenu = (Button) findViewById(R.id.rezultat_powrotDom);
    }
}
