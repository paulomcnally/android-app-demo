package com.chocoyo.labs.app.demo.ui.activities;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.chocoyo.labs.app.demo.DatabaseUtil;
import com.chocoyo.labs.app.demo.R;
import com.chocoyo.labs.app.demo.models.JuanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class JuanActivity extends Activity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juan);

        mDatabase = DatabaseUtil.getDatabase();

        users = mDatabase.getReference().child("users");

        writeNewUser("Juan", "juan@perezz.com");

        readData();
    }

    private void readData() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ...

                JuanModel juanModel = dataSnapshot.getValue(JuanModel.class);
                Log.i("JuanEmailLog", juanModel.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        users.child("963dec57-40ff-470e-90dc-db2a8a16a56f").addValueEventListener(valueEventListener);
    }

    private void writeNewUser(String username, String email) {
        JuanModel juanModel = new JuanModel();
        juanModel.setUsername(username);
        juanModel.setEmail(email);

       // String uniqueId = UUID.randomUUID().toString();

        users.child("963dec57-40ff-470e-90dc-db2a8a16a56f").setValue(juanModel);
    }

}
