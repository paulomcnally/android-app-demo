package com.chocoyo.labs.app.demo.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chocoyo.labs.app.demo.DatabaseUtil;
import com.chocoyo.labs.app.demo.MyWelcomeActivity;
import com.chocoyo.labs.app.demo.R;
import com.chocoyo.labs.app.demo.models.CardModel;
import com.chocoyo.labs.app.demo.models.ErrorMessage;
import com.chocoyo.labs.app.demo.models.ErrorModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stephentuso.welcome.WelcomeHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private FirebaseDatabase mDatabase;
    private OkHttpClient client;


    @BindView(R.id.open)
    Button buttonOpen;

    WelcomeHelper welcomeScreen;

    private Button create;
    private EditText cardNumber;
    private DatabaseReference myRef;

    private SwipeRefreshLayout swipeContainer;


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

        client = new OkHttpClient();
        //myRef.setValue("Hello, World!");

        // initialize
        initCreateNumber();

        // set button text
        buttonOpen.setText(getString(R.string.app_name));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    new AsyncHTTPExample().execute("00759794", "b", "c");
                } catch (Exception e) {
                    Log.i(TAG, e.getMessage());
                }
            }
        });


    }

    @OnClick(R.id.open)
    public void open(View view) {
        openJuan();
    }

    private void openJuan() {
        Intent intent = new Intent(
                getApplicationContext(),
                JuanActivity.class);
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

    private class AsyncHTTPExample extends AsyncTask<String, Void, JSONObject> {
        protected void onPreExecute() {

        }

        protected JSONObject doInBackground(String... strings) {
            JSONObject responseObject = null;

            Request request = new Request.Builder()
                    .url(getString(R.string.api_url) + strings[0])
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                try {
                    responseObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseObject;
        }


        protected void onPostExecute(JSONObject result) {
            swipeContainer.setRefreshing(false);

            assert result != null;
            if (result.has("error")) {
                try {
                    JSONObject error = result.getJSONObject("error");
                    String message = error.getString("message");
                    int code = error.getInt("code");

                    ErrorModel errorModel = new ErrorModel();
                    ErrorMessage errorMessage = new ErrorMessage();
                    errorMessage.setMessage(message);
                    errorMessage.setCode(code);

                    errorModel.setErrorMessage(errorMessage);

                    Log.i(TAG, errorModel.getErrorMessage().getMessage());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String account = result.getString("account");
                    String balance = result.getString("balance");
                    String source = result.getString("source");

                    CardModel cardModel = new CardModel();
                    cardModel.setAccount(account);
                    cardModel.setBalance(balance);
                    cardModel.setSource(source);

                    Log.i(TAG, cardModel.getBalance());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




        }
    }
}
