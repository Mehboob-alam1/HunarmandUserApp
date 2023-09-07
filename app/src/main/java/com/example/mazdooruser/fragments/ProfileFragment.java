package com.example.mazdooruser.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mazdooruser.LoginActivity;
import com.example.mazdooruser.R;
import com.example.mazdooruser.Rate;
import com.example.mazdooruser.UserInfoModel;
import com.example.mazdooruser.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ProfileFragment extends Fragment {


    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    FragmentProfileBinding binding;
    UserInfoModel data;
    BottomSheetDialog bottomSheetDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

//        binding.txtInfo = findViewById(R.id.txtInfo);
//        btnLogOut = findViewById(R.id.btnLogOut);

        shp = getContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);


//        profileImage = itemView.findViewById(R.id.imageofUser);
//        phoneImage = itemView.findViewById(R.id.btnphone);
//        name = itemView.findViewById(R.id.txtnameofuser);
//        phoneNumber = itemView.findViewById(R.id.txtphonenumber);
//        address = itemView.findViewById(R.id.txtAddress);
//

        fetchOrder(FirebaseAuth.getInstance().getCurrentUser().getUid());

        binding.btnRate.setOnClickListener(view -> {

            showDialog(data);


        });

        return binding.getRoot();
    }
    private void showDialog(UserInfoModel rideModel) {


        bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(R.layout.rate_driver, (LinearLayout) bottomSheetDialog.findViewById(R.id.rateDriverLayout));

        bottomSheetDialog.setCancelable(false);

        bottomSheetDialog.setContentView(bottomSheetView);
        try {
            bottomSheetDialog.show();
        } catch (WindowManager.BadTokenException e) {
            //use a log message
        }

        ;
        TextView txtDriverName = bottomSheetView.findViewById(R.id.txtProviderNameRate);
        TextView txtVehicleType = bottomSheetView.findViewById(R.id.txtTypeRate);



        txtDriverName.setText(rideModel.getName());
        txtVehicleType.setText(rideModel.getServiceType());



        AppCompatButton btnRate = bottomSheetView.findViewById(R.id.btnRateDriver);

        RatingBar ratingBar = bottomSheetView.findViewById(R.id.rateBar);


        btnRate.setOnClickListener(v -> {


            if (ratingBar.getRating() <= 0.0) {
                Toast.makeText(requireContext(), "Please select a rating", Toast.LENGTH_SHORT).show();
            } else {

                rateDriver(ratingBar.getRating(), rideModel);
            }
        });
    }

    private void rateDriver(float rating, UserInfoModel rides) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ;

        String pushId = UUID.randomUUID().toString();
        databaseReference.child("Ratings").child(rides.getUserId()).child( FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(new Rate(String.valueOf(rating), FirebaseAuth.getInstance().getCurrentUser().getUid(), rides.getName(), pushId))
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Thanks for rating", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();

                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });


    }
    private void fetchOrder(String uid) {


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Orders");

        databaseReference.child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){


                       data=      snapshot.getValue(UserInfoModel.class);

                            binding.txtnameofuser.setText(data.getName() + " " + data.getServiceType());
                            binding.txtphonenumber.setText(data.getPhonenumber());
                            binding.txtAddress.setText(data.getCity());
                            binding.btnphone.setOnClickListener(view -> {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" +data.getPhonenumber()));
                                requireActivity().startActivity(intent);
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}
