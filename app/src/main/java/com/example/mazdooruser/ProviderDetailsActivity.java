package com.example.mazdooruser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mazdooruser.databinding.ActivityProviderDetailsBinding;
import com.squareup.picasso.Picasso;

public class ProviderDetailsActivity extends AppCompatActivity {
    ActivityProviderDetailsBinding binding;
    String city, desc, image, name, phone, servType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProviderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        city = getIntent().getStringExtra("city");
        desc = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        servType = getIntent().getStringExtra("servtype");


    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.txtAbout.setText("About " + name);
        Picasso.get().load(image).placeholder(R.drawable.provider).into(binding.img);
        binding.contactDetails.setText("Name  " + name + "\n" + "City  " + city + "\n" + "Phone number  " + phone + "\n" + "Service Type  " + servType);
        binding.desc.setText(desc);

    }
}