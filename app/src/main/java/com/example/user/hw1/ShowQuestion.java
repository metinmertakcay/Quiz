package com.example.user.hw1;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.user.hw1.AllQuestions.EXTRA_POSITION;
import static com.example.user.hw1.AllQuestions.EXTRA_QUESTION;
import static com.example.user.hw1.AllQuestions.SEND_SELECTED_RADIOBUTTON;

/**
 * Created by User on 30.03.2018.
 */

public class ShowQuestion extends AppCompatActivity {

    public static final String CEVAP = "CEVAP";
    private TextView soru;
    private RadioGroup radioGroupChoser;
    private Button back, submit;
    private RadioButton radioButton;
    private QuestionDetail questionDetail;
    private int position;
    private int selectedButton;
    private RadioButton a_radio, b_radio, c_radio, d_radio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showquestion);

        initialize();

        Intent intent = getIntent();
        questionDetail = (QuestionDetail) intent.getSerializableExtra(EXTRA_QUESTION);
        position = intent.getIntExtra(EXTRA_POSITION, -1);
        selectedButton = intent.getIntExtra(SEND_SELECTED_RADIOBUTTON, -1);
        if(selectedButton != -1)
        {
            radioButton = (RadioButton)radioGroupChoser.getChildAt(selectedButton);
            radioButton.setChecked(true);
        }

        fillAllPlace();
        clickBackButton();
        clickSubmitButton();
    }

    public void clickBackButton()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(CEVAP,"-");
                intent.putExtra(EXTRA_POSITION, position);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public void clickSubmitButton()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupChoser.getCheckedRadioButtonId();
                if(selectedId != -1) {
                    radioButton = (RadioButton) findViewById(selectedId);
                    String cevap = findAnswer(radioButton);

                    Intent intent = new Intent();
                    intent.putExtra(CEVAP, cevap);
                    intent.putExtra(EXTRA_POSITION, position);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Toast.makeText(ShowQuestion.this,R.string.SelectWarning,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String findAnswer(RadioButton radioButton)
    {
        if(radioButton == a_radio)
        {
            return "A";
        }
        else if(radioButton == b_radio)
        {
            return "B";
        }
        else if(radioButton == c_radio)
        {
            return "C";
        }
        else
        {
            return "D";
        }
    }

    public void fillAllPlace()
    {
        soru.setText(questionDetail.getSoru());
        String text = a_radio.getText() + ")  " + questionDetail.getA();
        a_radio.setText(text);

        text = b_radio.getText() + ")  " + questionDetail.getB();
        b_radio.setText(text);

        text = c_radio.getText() + ")  " + questionDetail.getC();
        c_radio.setText(text);

        text = d_radio.getText() + ")  " + questionDetail.getD();
        d_radio.setText(text);

    }

    public void initialize()
    {
        back = (Button)findViewById(R.id.back_button);
        submit = (Button)findViewById(R.id.submit_button);
        soru = (TextView)findViewById(R.id.soru);
        a_radio = (RadioButton)findViewById(R.id.a_radio);
        b_radio = (RadioButton)findViewById(R.id.b_radio);
        c_radio = (RadioButton)findViewById(R.id.c_radio);
        d_radio = (RadioButton)findViewById(R.id.d_radio);
        radioGroupChoser = (RadioGroup)findViewById(R.id.radioGroupChoser);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(CEVAP,"-");
        intent.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK,intent);
        finish();
    }
}