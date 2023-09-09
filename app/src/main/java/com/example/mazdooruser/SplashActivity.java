package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
private DatabaseReference userRef;
private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userRef= FirebaseDatabase.getInstance().getReference("Users");
        if (FirebaseAuth.getInstance().getCurrentUser() !=null) {
            checkIsActive(FirebaseAuth.getInstance().getCurrentUser());
            dialog= new ProgressDialog(this);
            dialog.setMessage("Checking active status....");
            dialog.setTitle("Status");
            dialog.setCancelable(true);
            dialog.show();

            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    dialog.dismiss();



                }

            }, 5 * 1000);
        }else{
            updateUI();
        }



    }
    private void checkIsActive(FirebaseUser user) {
        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("block").exists()){
                    boolean isBlock= (snapshot.child("block").getValue(boolean.class));
                    if (isBlock){

                        Toast.makeText(SplashActivity.this, "You are blocked contact admin", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this,UserBlockedActivity.class));
                        finish();
                    }else{
                        updateUI();
                    }
                }else{
                    Toast.makeText(SplashActivity.this, "Something went wrong try checking your internet", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUI() {

        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        finish();
    }
}