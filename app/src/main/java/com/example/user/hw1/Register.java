package com.example.user.hw1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 28.03.2018.
 */

public class Register extends AppCompatActivity {

    private static final String EXPRESSION = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    private EditText name, surname, phoneNumber, email, password, password_again;
    private Button submit, back;
    private Pattern pattern;
    private Matcher matcher;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initialize();
        clickSubmit();
        clickBack();
    }

    public void clickBack()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void clickSubmit()
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAllPlace())
                {
                    databaseHelper = new DatabaseHelper(Register.this);
                    databaseHelper.insertData(Register.this,name.getText().toString(),surname.getText().toString(),phoneNumber.getText().toString(),
                            email.getText().toString(),password.getText().toString());
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public boolean checkAllPlace()
    {
        if(TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(surname.getText().toString()) || TextUtils.isEmpty(phoneNumber.getText().toString()) ||
                TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(password_again.getText().toString()))
        {
            Toast.makeText(Register.this,R.string.FillAllPlacesError,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(password.getText().toString().equals(password_again.getText().toString())))
        {
            Toast.makeText(Register.this,R.string.MissMatchPasswordError,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(checkValidEmail(email.getText().toString()))
        {
            if(checkIsTakenEmail(email.getText().toString()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkValidEmail(String email)
    {
        Pattern pattern = Pattern.compile(EXPRESSION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        Toast.makeText(Register.this,R.string.ValidEmailWarning,Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean checkIsTakenEmail(String email)
    {
        databaseHelper = new DatabaseHelper(Register.this);
        Cursor cursor = databaseHelper.getEmailList();
        cursor.moveToFirst();
        int k = 0;
        while(k < cursor.getCount())
        {
            if(cursor.getString(0).equals(email))
            {
                Toast.makeText(Register.this,R.string.UsingEmailWarning,Toast.LENGTH_SHORT).show();
                return false;
            }
            k++;
        }
        return true;
    }

    public void initialize()
    {
        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        password_again = (EditText)findViewById(R.id.passwordAgain);
        submit = (Button)findViewById(R.id.submit);
        back = (Button)findViewById(R.id.back);
    }
}