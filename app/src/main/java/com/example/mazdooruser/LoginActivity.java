package com.example.mazdooruser;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    EditText edtUserId, edtPassword;
    Button btnLogin;
    TextView txtInfo, notaUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserId = findViewById(R.id.edtUserid);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserId.setText("");
        edtPassword.setText("");
        btnLogin = findViewById(R.id.btnLogin);
        txtInfo = findViewById(R.id.txtInfo);
        notaUser = findViewById(R.id.notaUser);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        CheckLogin();
        notaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+92 34647"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(LoginActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUserId.getText().toString().equals("") || edtPassword.getText().toString().equals(""))
                    txtInfo.setText("Please insert userid and password");
                else
                    DoLogin(edtUserId.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    public void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

        String userName = shp.getString("name", "");

        if (userName != null && !userName.equals("")) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void DoLogin(String userid, String password) {
        try {
            if (password.equals("hunarmanduser")) {
                if (shp == null)
                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                shpEditor = shp.edit();
                shpEditor.putString("name", userid);
                shpEditor.commit();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else
                txtInfo.setText("Invalid Credentails");
        } catch (Exception ex) {
            txtInfo.setText(ex.getMessage().toString());
        }
    }

}