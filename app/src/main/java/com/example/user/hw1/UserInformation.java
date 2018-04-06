package com.example.user.hw1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.user.hw1.MainActivity.EXTRA_PERSON_INFORMATION;

/**
 * Created by User on 28.03.2018.
 */

public class UserInformation extends AppCompatActivity {

    private static final String EXPRESSION = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    private Pattern pattern;
    private Matcher matcher;
    private TextView name, surname;
    private EditText phone, email;
    private Button submit, test;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinformation);

        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra(EXTRA_PERSON_INFORMATION);

        initialize();
        fillPersonInformation(person);
        clickSubmit(person);
        clickTest(person);
    }

    public void fillPersonInformation(Person person)
    {
        name.setText(person.getName());
        surname.setText(person.getSurname());
        phone.setText(person.getPhone());
        email.setText(person.getEmail());
    }

    public void clickSubmit(final Person person)
    {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.getText().toString().equals(person.getPhone()) && email.getText().toString().equals(person.getEmail()))
                {
                    Toast.makeText(UserInformation.this,R.string.notChangeError,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(checkValidEmail(email.getText().toString()))
                    {
                        databaseHelper = new DatabaseHelper(UserInformation.this);
                        String userId = databaseHelper.getId(person.getEmail());
                        databaseHelper.updateData(userId, phone.getText().toString(),email.getText().toString());
                        person.setPhone(phone.getText().toString());
                        person.setEmail(email.getText().toString());

                        Toast.makeText(UserInformation.this,R.string.Change,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        phone.setText(person.getPhone());
                        email.setText(person.getEmail());
                    }
                }
            }
        });
    }

    public boolean checkValidEmail(String email)
    {
        Pattern pattern = Pattern.compile(EXPRESSION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        Toast.makeText(UserInformation.this,R.string.ValidEmailWarning,Toast.LENGTH_SHORT).show();
        return false;
    }

    public void clickTest(final Person person)
    {
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(person.getPhone().equals(phone.getText().toString()) && person.getEmail().equals(email.getText().toString()))
                {
                    Intent intent = new Intent(UserInformation.this,Categories.class);
                    finish();
                    startActivity(intent);
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(UserInformation.this,AlertDialog.THEME_TRADITIONAL);
                    alert.setMessage(R.string.ChangeWarning);
                    alert.setCancelable(false);

                    alert.setPositiveButton(R.string.Continue,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface,int x)
                        {
                            Intent intent = new Intent(UserInformation.this,Categories.class);
                            finish();
                            startActivity(intent);
                        }
                    });

                    alert.setNegativeButton(R.string.Back,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface,int x)
                        {

                        }
                    });
                    alert.show();
                }
            }
        });
    }

    public void initialize()
    {
        name = (TextView)findViewById(R.id.name);
        surname = (TextView)findViewById(R.id.surname);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone_number);
        submit = (Button)findViewById(R.id.submit);
        test = (Button)findViewById(R.id.test);
    }

    @Override
    public void onBackPressed() {

    }
}