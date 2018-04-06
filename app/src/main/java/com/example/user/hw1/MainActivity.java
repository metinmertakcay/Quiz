package com.example.user.hw1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON_INFORMATION = "EXTRA_PERSON_INFORMATION";
    private EditText email, password;
    private Button clear_button, login_button;
    private TextView registration;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        clickClear();
        clickLogin();
        clickRegistration();
    }

    public void clickClear()
    {
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setText("");
                password.setText("");
            }
        });
    }

    public void clickLogin()
    {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText().toString()) || (TextUtils.isEmpty(password.getText().toString())))
                {
                    Toast.makeText(MainActivity.this,R.string.FillAllPlacesError,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseHelper = new DatabaseHelper(MainActivity.this);
                    Cursor cursor = databaseHelper.getUser(email.getText().toString(),password.getText().toString());
                    if(cursor.getCount() == 0)
                    {
                        Toast.makeText(MainActivity.this,R.string.WrongEmailOrPassword,Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }
                    else
                    {
                        cursor.moveToFirst();
                        Person person = new Person(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                        cursor.close();

                        Intent intent = new Intent(MainActivity.this, UserInformation.class);
                        intent.putExtra(EXTRA_PERSON_INFORMATION, person);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void clickRegistration()
    {
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }

    public void initialize()
    {
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        clear_button = (Button)findViewById(R.id.clear_button);
        login_button = (Button)findViewById(R.id.login_button);
        registration = (TextView)findViewById(R.id.registration);
        registration.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}