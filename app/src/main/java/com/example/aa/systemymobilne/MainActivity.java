package com.example.aa.systemymobilne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout start, zarzadzajPytaniami, wyjdz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        znajdzWidok();
        setListener ();
    }

    private void znajdzWidok()
    {
        start = (RelativeLayout) findViewById(R.id.activity_main_start);
        zarzadzajPytaniami = (RelativeLayout) findViewById(R.id.activity_main_zarzadzajPytaniami);
        wyjdz = (RelativeLayout) findViewById(R.id.activity_main_wyjscie);
    }

    private void setListener()
    {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WyborKategorii.class));
            }
        });

        zarzadzajPytaniami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ZarzadzajPytaniami.class));
            }
        });

//        wyjdz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.exit(0);
//            }
//        });
    }


}
