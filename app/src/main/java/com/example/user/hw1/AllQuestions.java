package com.example.user.hw1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.user.hw1.Categories.TYPE;
import static com.example.user.hw1.ShowQuestion.CEVAP;

/**
 * Created by User on 29.03.2018.
 */

public class AllQuestions extends AppCompatActivity{

    private QuestionList qList;
    private BufferedReader jsonReader;
    private List<QuestionDetail> questions;
    private Button back, result;
    private RecyclerView recyclerView;
    private QuestionAdapter mquestionAdapter;
    public static final int PASSLIST = 1001;
    public static final int ACTIVITY = 1000;
    public static final String EXTRA_LIST = "EXTRA_LIST";
    public static final String EXTRA_QUESTION = "QUESTION";
    public static final String SEND_SELECTED_RADIOBUTTON = "SEND_SELECTED_RADIOBUTTON";
    public static final String EXTRA_POSITION = "POSITION";
    public static final int BOS = 0;
    public static final int DOGRU = 1;
    public static final int YANLIS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allquestion);

        initialize();
        createQuestionList(qList);
        clickBackButton();
        clickSubmitButton();
        showQuestion();
        clickRecycleItem();
    }

    public void clickRecycleItem()
    {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                Intent intent = new Intent(AllQuestions.this,ShowQuestion.class);
                intent.putExtra(EXTRA_QUESTION, questions.get(position));
                intent.putExtra(EXTRA_POSITION, position);

                int selectedButton = chackSelectedItem(questions.get(position));
                intent.putExtra(SEND_SELECTED_RADIOBUTTON, selectedButton);
                startActivityForResult(intent,ACTIVITY);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public int chackSelectedItem(QuestionDetail questionDetail)
    {
        if((questionDetail.getStatus() != BOS) &&(questionDetail.getStatus() != -1))
        {
            if(questionDetail.getUserAnswer().equals("A"))
            {
                return 0;
            }
            else if(questionDetail.getUserAnswer().equals("B"))
            {
                return 1;
            }
            else if(questionDetail.getUserAnswer().equals("C"))
            {
                return 2;
            }
            else
            {
                return 3;
            }
        }
        return -1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String cevap = data.getStringExtra(CEVAP);
                    Integer position = data.getIntExtra(EXTRA_POSITION, -1);

                    if (position == -1) {
                        Toast.makeText(getApplicationContext(), R.string.UnexpectedlyTerminated, Toast.LENGTH_SHORT).show();
                    } else {
                        setAnswer(questions.get(position),cevap);
                        mquestionAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public void setAnswer(QuestionDetail questionDetail, String cevap)
    {
        if(questionDetail.getUserAnswer() == null || questionDetail.getUserAnswer().equals("-")) {
            questionDetail.setUserAnswer(cevap);
            if (cevap.equals("-")) {
                questionDetail.setStatus(BOS);
            } else {
                if (cevap.equals(questionDetail.getCevap())) {
                    questionDetail.setStatus(DOGRU);
                } else {
                    questionDetail.setStatus(YANLIS);
                }
            }
        }
        else
        {
            if(cevap.equals("-")) {}
            else
            {
                questionDetail.setUserAnswer(cevap);
                if (cevap.equals(questionDetail.getCevap())) {
                    questionDetail.setStatus(DOGRU);
                } else {
                    questionDetail.setStatus(YANLIS);
                }
            }
        }
    }

    public void showQuestion()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mquestionAdapter = new QuestionAdapter(questions);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(mquestionAdapter);
    }

    public void clickSubmitButton()
    {
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllQuestions.this,Result.class);
                intent.putExtra(EXTRA_LIST, (ArrayList<QuestionDetail>) questions);
                startActivityForResult(intent,PASSLIST);
            }
        });
    }

    @Override
    public void onBackPressed() {
        createAlertDialog();
    }

    public void createAlertDialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(AllQuestions.this,AlertDialog.THEME_TRADITIONAL);
        alert.setMessage(R.string.Ask);
        alert.setCancelable(false);

        alert.setPositiveButton(R.string.Yes,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface,int x)
            {
                Intent intent = new Intent(AllQuestions.this,Categories.class);
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

    public void clickBackButton()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog();
            }
        });
    }

    public void createQuestionList(QuestionList questionList)
    {
        for(int i=0;i<questionList.getQuestionDetail().size();i++){
            questions.add(questionList.getQuestionDetail().get(i));
        }
    }

    public void initialize()
    {
        jsonReader = checkIntent();
        qList = getQuestionUsingJson(jsonReader);
        questions = new ArrayList<QuestionDetail>();
        back = (Button)findViewById(R.id.back_button);
        result = (Button)findViewById(R.id.result_button);
    }

    public BufferedReader checkIntent()
    {
        BufferedReader jsonReader = null;
        Intent intent = getIntent();
        if((intent.getIntExtra(TYPE, 0) >= 0) && (intent.getIntExtra(TYPE, 0) <= 4))
        {
            int rawNumber = intent.getIntExtra(TYPE, 0);
            switch (rawNumber) {
                case 0: {
                    jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.animal)));
                    break;
                }
                case 1: {
                    jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.city)));
                    break;
                }
                case 2: {
                    jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.sport)));
                    break;
                }
                case 3: {
                    jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.history)));
                    break;
                }
                case 4: {
                    jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.general)));
                    break;
                }
            }
        }
        return jsonReader;
    }

    public QuestionList getQuestionUsingJson(BufferedReader jsonReader){
        Gson gson = new Gson();
        QuestionList questionList = gson.fromJson(jsonReader,QuestionList.class);
        if(jsonReader != null)
        {
            try {
                jsonReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return questionList;
    }
}