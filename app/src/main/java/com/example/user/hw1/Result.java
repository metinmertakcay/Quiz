package com.example.user.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.hw1.AllQuestions.DOGRU;
import static com.example.user.hw1.AllQuestions.EXTRA_LIST;
import static com.example.user.hw1.AllQuestions.PASSLIST;
import static com.example.user.hw1.AllQuestions.YANLIS;

/**
 * Created by User on 30.03.2018.
 */

public class Result extends AppCompatActivity {

    private Button back;
    private TextView right,empty,wrong,success;
    private int rightNumber = 0,emptyNumber = 0,wrongNumber = 0,successNumber;
    private List<QuestionDetail> questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initialize();
        questions = (ArrayList<QuestionDetail>)getIntent().getSerializableExtra(EXTRA_LIST);
        statistic();
        clickBackButton();
    }

    public void statistic()
    {
        for (int i=0;i<questions.size();i++)
        {
            if(questions.get(i).getStatus() == DOGRU)
            {
                rightNumber ++;
            }
            else if(questions.get(i).getStatus() == YANLIS)
            {
                wrongNumber ++;
            }
            else
            {
                emptyNumber ++;
            }
        }
        right.setText(rightNumber + "");
        empty.setText(emptyNumber + "");
        wrong.setText(wrongNumber + "");
        success.setText("%" + (rightNumber * 100) / 10);
    }

    public void clickBackButton()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(PASSLIST,intent);
                finish();
            }
        });
    }

    public void initialize()
    {
        back = (Button)findViewById(R.id.back);
        right = (TextView) findViewById(R.id.right);
        empty = (TextView) findViewById(R.id.empty);
        wrong = (TextView) findViewById(R.id.wrong);
        success = (TextView) findViewById(R.id.success);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
