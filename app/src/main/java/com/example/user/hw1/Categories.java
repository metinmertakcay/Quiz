package com.example.user.hw1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 29.03.2018.
 */

public class Categories extends AppCompatActivity {

    public static final String TYPE = "TYPE";
    private Button animal, city, sport, history, general;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initialize();
        clickAnimalCategory();
        clickCityCategory();
        clickSportCategory();
        clickHistoryCategory();
        clickGeneralCategory();
    }

    public void clickAnimalCategory()
    {
        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,AllQuestions.class);
                intent.putExtra(TYPE,0);
                finish();
                startActivity(intent);
            }
        });
    }

    public void clickCityCategory()
    {
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,AllQuestions.class);
                intent.putExtra(TYPE,1);
                finish();
                startActivity(intent);
            }
        });
    }

    public void clickSportCategory()
    {
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,AllQuestions.class);
                intent.putExtra(TYPE,2);
                finish();
                startActivity(intent);
            }
        });
    }

    public void clickHistoryCategory()
    {
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,AllQuestions.class);
                intent.putExtra(TYPE,3);
                finish();
                startActivity(intent);
            }
        });
    }

    public void clickGeneralCategory()
    {
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Categories.this,AllQuestions.class);
                intent.putExtra(TYPE,4);
                finish();
                startActivity(intent);
            }
        });
    }

    public void initialize()
    {
        animal = (Button)findViewById(R.id.animal);
        city = (Button)findViewById(R.id.city);
        sport = (Button)findViewById(R.id.sports);
        history = (Button)findViewById(R.id.history);
        general = (Button)findViewById(R.id.general);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Categories.this,AlertDialog.THEME_TRADITIONAL);
        alert.setMessage(R.string.AskGoLogin);
        alert.setCancelable(false);

        alert.setPositiveButton(R.string.Yes,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int x)
            {
                Intent intent = new Intent(Categories.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        alert.setNegativeButton(R.string.No,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int x)
            {

            }
        });
        alert.show();
    }
}