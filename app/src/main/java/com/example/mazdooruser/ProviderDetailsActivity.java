package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mazdooruser.databinding.ActivityProviderDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProviderDetailsActivity extends AppCompatActivity {
    ActivityProviderDetailsBinding binding;
    String city, desc, image, name, phone, servType;
    String fcmToken;
    String userId;
    Type type;
    Gson gson;
String pushId;
private User user;
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
        userId = getIntent().getStringExtra("userId");

pushId= UUID.randomUUID().toString();
        binding.txtAbout.setText("About " + name);
        Picasso.get().load(image).placeholder(R.drawable.provider).into(binding.img);
        binding.contactDetails.setText("Name  " + name + "\n" + "City  " + city + "\n" + "Phone number  " + phone + "\n" + "Service Type  " + servType);
        binding.desc.setText(desc);
        binding.btnHire.setOnClickListener(view -> {
            DatabaseReference databaseReference1 =  FirebaseDatabase.getInstance().getReference("userInfo");
            databaseReference1.child(servType).child(userId).child(pushId).child("token")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){

                                fcmToken=   snapshot.getValue(String.class);
                                UserInfoModel data = new UserInfoModel(image,name,phone,city,desc,"","",servType,servType,userId,true,pushId,false);

                                setOrderUser(data);
                            }else {
                                Toast.makeText(ProviderDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ProviderDetailsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.txtAbout.setText("About " + name);
        Picasso.get().load(image).placeholder(R.drawable.provider).into(binding.img);
        binding.contactDetails.setText("Name  " + name + "\n" + "City  " + city + "\n" + "Phone number  " + phone + "\n" + "Service Type  " + servType);
        binding.desc.setText(desc);

    }

    private void setOrderUser(UserInfoModel data) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("UserOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                .setValue(data)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                       fetchData();
                        finish();

                    }else{
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {


                    Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });



    }
    private void fetchData() {
DatabaseReference mRef=FirebaseDatabase.getInstance().getReference();
        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            user = snapshot.getValue(User.class);

                            onSendNotification(user.getFirst_name() +" " +user.getSur_name(),"Booked you",fcmToken);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void onSendNotification(String name, String send_you_and_interest, String token) {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", name);
            jsonObject.put("body", send_you_and_interest);


            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("notification", jsonObject);
            jsonObject1.put("to", token);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Toast.makeText(ProviderDetailsActivity.this, "You booked a service", Toast.LENGTH_SHORT).show();
                            Log.d("Notification", "sent notification");
                        }

                    }, error -> {

                Log.d("Notification", "sent not notification");
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "key=AAAA5Gn9gtc:APA91bE3WZOpu_g_2O5ycFvpy-XVgm5V7ipT_tnz_9h1SQBfX9MklbxzyH_SEgsgI8S_F10zXgqDurXjh-Pj5hpL4mU7jnGl35wx6QTpB37kbXTH5_izK1L5nspZcNFDllHyqTatEn_w";
                    map.put("Content-type", "application/json");
                    map.put("Authorization", key);


                    return map;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}