package com.chocoyo.labs.app.demo.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoyo.labs.adapters.progress.AdapterProgress;
import com.chocoyo.labs.app.demo.DatabaseUtil;
import com.chocoyo.labs.app.demo.R;
import com.chocoyo.labs.app.demo.models.JuanModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import butterknife.BindView;

public class JuanActivity extends Activity {
    private RecyclerView mRecyclerView;

    private FirebaseDatabase mDatabase;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juan);

        mDatabase = DatabaseUtil.getDatabase();

        users = mDatabase.getReference().child("users");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new AdapterProgress());

        /*

        writeNewUser("Maria", "juan@perezz.com");
        writeNewUser("Juan", "juan@perezz.com");
        writeNewUser("Pedro", "juan@perezz.com");
        writeNewUser("Diana", "juan@perezz.com");
        writeNewUser("kennet", "juan@perezz.com");

        readData();

*/




    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<JuanModel, CarViewHolder> adapter = new FirebaseRecyclerAdapter<JuanModel, CarViewHolder>(
                JuanModel.class,
                R.layout.item_juan,
                CarViewHolder.class,
                users) {
            @Override
            protected void populateViewHolder(CarViewHolder viewHolder, final JuanModel juanModel, final int position) {

                // set key to send to edit activity
                final String key = this.getRef(position).getKey();

                viewHolder.username.setText(juanModel.getUsername());
                viewHolder.email.setText(juanModel.getEmail());

/*

                // open activity to edit
                viewHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AddCarActivity.class);
                        intent.putExtra(AddCarActivity.EXTRA_KEY, key);
                        startActivity(intent);
                    }
                });
                */
            }
        };

        mRecyclerView.setAdapter(adapter);
    }


    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView email;
        public CarViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.username);
            email = (TextView) view.findViewById(R.id.email);
        }
    }




/*
    private void readData() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ...

                JuanModel juanModel = dataSnapshot.getValue(JuanModel.class);
                Log.i("JuanEmailLog", juanModel.getEmail());

                Uri uri = Uri.parse(juanModel.getEmail());
                SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.image);
                draweeView.setImageURI(uri);

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

        String uniqueId = UUID.randomUUID().toString();

        users.child(uniqueId).setValue(juanModel);
    }
*/
}
