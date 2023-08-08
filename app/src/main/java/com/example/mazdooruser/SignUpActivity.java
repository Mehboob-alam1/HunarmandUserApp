package com.example.mazdooruser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mazdooruser.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ProgressDialog dialog;
    String email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setTitle("Register");
        dialog.setMessage("Please wait.....");
        dialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etPasswordUp.getText().toString().isEmpty()) {
                    binding.etPasswordUp.setError("Password Required");
                    binding.etPasswordUp.requestFocus();
                } else if (binding.etUserNameUp.getText().toString().isEmpty()) {
                    binding.etUserNameUp.setError("Email required");
                    binding.etUserNameUp.requestFocus();
                } else {
                    dialog.show();
                    email = binding.etUserNameUp.getText().toString();
                    password = binding.etPasswordUp.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        dialog.dismiss();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(SignUpActivity.this, "Registered Successfully now login ", Toast.LENGTH_SHORT).show();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        dialog.dismiss();
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });

                }


            }
        });
    }

    private void updateUI(FirebaseUser user) {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
//            reload();
        }
    }
}