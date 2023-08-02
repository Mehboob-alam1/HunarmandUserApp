package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mazdooruser.databinding.ActivityDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    String userType;
    DatabaseReference databaseReference;
    UserInfoModel model;
    ArrayList<UserInfoModel> list;
    RecyclerView recyclerView;
    ProviderAdapter adapter;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView = findViewById(R.id.recyclerItem);
        userType = getIntent().getStringExtra("type");
        uId = getIntent().getStringExtra("uID");
        binding.userOption.setText(userType);
        model = new UserInfoModel();
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("userInfo").child("VerPro");
//        databaseReference.child("VerPro").child(data.getServiceType()).child(data.getUserId()).setValue(data);

        databaseReference.child(userType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                    binding.recyclerItem.setVisibility(View.VISIBLE);
                    binding.noData.setVisibility(View.GONE);
                    list.clear();

                    for (DataSnapshot snap : snapshot.getChildren()) {



                        UserInfoModel data = snap.getValue(UserInfoModel.class);


                        if (data.isVerified()) {
                            list.add(data);


                        }else {
                            binding.recyclerItem.setVisibility(View.GONE);
                            binding.noData.setVisibility(View.VISIBLE);
                            Toast.makeText(DetailsActivity.this, "No data Exist", Toast.LENGTH_SHORT).show();

                        }
                    }


                    adapter = new ProviderAdapter(list, DetailsActivity.this, userType);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                    adapter.notifyDataSetChanged();

                } else {
                    binding.recyclerItem.setVisibility(View.GONE);
                    binding.noData.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailsActivity.this, "No data Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailsActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}