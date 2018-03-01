package com.irozon.muralsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.irozon.mural.Mural;

public class MainActivity extends AppCompatActivity {

    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage = findViewById(R.id.ivImage);

        Mural.with(this).source("http://www.asc-csa.gc.ca/images/recherche/tiles/186feed0-c60e-4d52-a17e-5bf350e22991.jpg")
                .placeholder(R.color.colorAccent)
                //.resize(400, 300)
                .loadImage(ivImage);
    }
}
