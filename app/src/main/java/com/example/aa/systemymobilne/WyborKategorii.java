package com.example.aa.systemymobilne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WyborKategorii extends AppCompatActivity {

    RelativeLayout kategoriaHistoria, kategoriaBologia , kategoriaInformatyka , kategoriaLosowo ;

    TextView imie , najlepszyHistoria , najlepszyBiologia , najlepszyInformatyka , najlepszyLosowy;

    DanePomoc danePomoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_kategorii);
        danePomoc = new DanePomoc(this);
        znajdzWidok();
        ustawNajlepszeWyniki();
        setListener();
    }


    private void znajdzWidok()
    {
        kategoriaHistoria = (RelativeLayout) findViewById(R.id.activity_wybor_kategorii_Historia);
        kategoriaBologia = (RelativeLayout) findViewById(R.id.activity_wybor_kategorii_Biologia);
        kategoriaInformatyka = (RelativeLayout) findViewById(R.id.activity_wybor_kategorii_Informatyka);
        kategoriaLosowo = (RelativeLayout) findViewById(R.id.activity_wybor_kategorii_Losowo);

    }

    public void ustawNajlepszeWyniki()
    {
        najlepszyHistoria = (TextView) findViewById(R.id.activity_wybor_kategorii_HistoriaNajlepszyWynik);
        najlepszyHistoria.setText("" + danePomoc.zwrocIloscPunktow("NAJLEPSZY_WYNIK_HISTORIA",0));

        najlepszyBiologia = (TextView) findViewById(R.id.activity_wybor_kategorii_BiologiaNajlepszyWynik);
        najlepszyBiologia.setText("" + danePomoc.zwrocIloscPunktow("NAJLEPSZY_WYNIK_HISTORIA",0));

        najlepszyInformatyka = (TextView) findViewById(R.id.activity_wybor_kategorii_InformatykaNajlepszyWynik);
        najlepszyInformatyka.setText("" + danePomoc.zwrocIloscPunktow("NAJLEPSZY_WYNIK_HISTORIA",0));

        najlepszyLosowy = (TextView) findViewById(R.id.activity_wybor_kategorii_LosowoNajlepszyWynik);
        najlepszyLosowy.setText("" + danePomoc.zwrocIloscPunktow("NAJLEPSZY_WYNIK_HISTORIA",0));

    }

    private void setListener() {
        kategoriaHistoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WyborKategorii.this, KategoriaHistoria.class));
            }
        });

        kategoriaBologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WyborKategorii.this, KategoriaBiologia.class));
            }
        });

        kategoriaInformatyka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WyborKategorii.this, KategoriaInformatyka.class));
            }
        });

        kategoriaLosowo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WyborKategorii.this, KategoriaLosowo.class));
            }
        });
    }
}
