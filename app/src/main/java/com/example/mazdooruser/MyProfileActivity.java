package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mazdooruser.databinding.ActivityMyProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {
private ActivityMyProfileBinding binding;
private DatabaseReference mRef;
private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_my_profile);


        mRef = FirebaseDatabase.getInstance().getReference("Users");


        fetchData();


        binding.btnLogout.setOnClickListener(v -> {


            showAlertDialog();

        });


    }
    private void fetchData() {

        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            user = snapshot.getValue(User.class);

                            Glide.with(MyProfileActivity.this).load(user.getUser_image()).placeholder(R.drawable.ic_baseline_person_24)
                                    .into(binding.profileImage);

                            binding.txtUserName.setText(user.getFirst_name() + " " + user.getSur_name());
                            binding.txtPhoneNumber.setText(user.getPhone_number());
                            binding.txtGmail.setText(user.getEmail());
                            binding.txtAddress.setText(user.getAddress());


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }


    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.i, null);
        alertDialog.setView(customLayout);

        TextView btnYes = customLayout.findViewById(R.id.btnYes);
        TextView  btnNo = customLayout.findViewById(R.id.btnNo);

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        btnYes.setOnClickListener(v -> {

            signOut();
        });

        btnNo.setOnClickListener(v -> {
            alert.dismiss();
        });

    }

}