package com.example.daulet.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.daulet.lab3.models.Hero;
import com.example.daulet.lab3.recycleViewAdapters.HerosAdapter;

/**
 * Created by daulet on 10/13/17.
 */

public class HeroByIdActivity extends AppCompatActivity {
    private Hero currentHero;
    private View v;
    private HerosAdapter adapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Intent i;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_by_id);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        v = getWindow().getDecorView().getRootView();
        i = getIntent();
        currentHero = (Hero) i.getParcelableExtra("hero-item");

        TextView newsTitle = (TextView)findViewById(R.id.title);
        TextView newsBody = (TextView)findViewById(R.id.body);
        TextView newsDate = (TextView)findViewById(R.id.date);
        newsTitle.setText(currentHero.getName());
        newsBody.setText(currentHero.getBody());
        newsDate.setText(currentHero.getDate());

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Heroes");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
