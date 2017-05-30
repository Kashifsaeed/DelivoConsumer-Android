package com.attribe.delivo.app.Extras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.attribe.delivo.app.R;
import com.google.firebase.database.*;

public class TestFirebase extends AppCompatActivity {

//    FirebaseDatabase mRootRef=FirebaseDatabase.getInstance();
//    DatabaseReference mRef=mRootRef.getReference("AgentsLocation");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("AgentsLocation");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_firebase);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
