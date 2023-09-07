package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mazdooruser.databinding.ActivityRatingsBinding;
import com.example.mazdooruser.fragments.RatingAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity {
ActivityRatingsBinding binding;
    String city, desc, image, name, phone, servType,userId;
    private ArrayList<Rate> list;
    private RatingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityRatingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        city = getIntent().getStringExtra("city");
        desc = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        servType = getIntent().getStringExtra("servtype");
        userId = getIntent().getStringExtra("userId");

list= new ArrayList<>();

        Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        fetchRating(userId);

    }

    private void fetchRating(String userId) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Ratings")
                .child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){



                    for (DataSnapshot snap:snapshot.getChildren()){

                    Rate rate =   snap.getValue(Rate.class);
                    list.add(rate);
                    }

                    adapter= new RatingAdapter(RatingsActivity.this,list);
                    binding.recylcerRating.setAdapter(adapter);
                    binding.recylcerRating.setLayoutManager(new LinearLayoutManager(RatingsActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}