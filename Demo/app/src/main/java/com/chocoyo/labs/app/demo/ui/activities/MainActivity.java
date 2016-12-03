package com.chocoyo.labs.app.demo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chocoyo.labs.app.demo.DatabaseUtil;
import com.chocoyo.labs.app.demo.MyWelcomeActivity;
import com.chocoyo.labs.app.demo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stephentuso.welcome.WelcomeHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;

    @BindView(R.id.open) Button buttonOpen;

    WelcomeHelper welcomeScreen;

    private Button create;
    private EditText cardNumber;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // welcome message
        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        // database
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = DatabaseUtil.getDatabase();
        myRef = mDatabase.getReference("message");

        //myRef.setValue("Hello, World!");

        // initialize
        initCreateNumber();

        // set button text
        buttonOpen.setText(getString(R.string.app_name));

    }

    @OnClick(R.id.open)
    public void open(View view) {
        openJuan();
    }
    private void openJuan() {
        Intent intent = new Intent(
                getApplicationContext(),
                AuthExampleActivity.class);
        startActivity(intent);
    }

    private void initCreateNumber() {
        create = (Button) findViewById(R.id.create);
        cardNumber = (EditText) findViewById(R.id.card_number);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (cardNumber.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(),
                           "Loco, no has puesto tu numero",
                           Toast.LENGTH_LONG).show();
               } else {
                   myRef.setValue(cardNumber.getText().toString());
               }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }
}
