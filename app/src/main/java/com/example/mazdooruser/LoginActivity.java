package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mazdooruser.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog dialog;
    String email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setTitle("Login");
        dialog.setMessage("Please wait.....");
        dialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        binding.txtSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SetupProfileActivity.class));
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etUserNameIn.getText().toString().isEmpty()) {
                    binding.etUserNameIn.setError("Email is required");
                    binding.etUserNameIn.requestFocus();
                } else if (binding.etPasswordIn.getText().toString().isEmpty()) {
                    binding.etPasswordIn.setError("Password is required");
                    binding.etPasswordIn.requestFocus();
                } else {
                    dialog.show();
                    email = binding.etUserNameIn.getText().toString();
                    password = binding.etPasswordIn.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        dialog.dismiss();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "No user registered, register yourself and try again",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

    }

    private void updateUI(FirebaseUser user) {

        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}