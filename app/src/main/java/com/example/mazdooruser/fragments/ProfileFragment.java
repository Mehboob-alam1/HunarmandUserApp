package com.example.mazdooruser.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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
import com.example.mazdooruser.BookingTabsAdapter;
import com.example.mazdooruser.LoginActivity;
import com.example.mazdooruser.R;
import com.example.mazdooruser.Rate;
import com.example.mazdooruser.UserInfoModel;
import com.example.mazdooruser.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    BookingTabsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        adapter = new BookingTabsAdapter(requireActivity());

        binding.tabViewpager.setAdapter(adapter);


        binding.bookingTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.tabViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.tabViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.bookingTabLayout.getTabAt(position).select();


            }
        });



        return binding.getRoot();
    }


//
//    private void rateDriver(float rating, UserInfoModel rides) {
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//        ;
//
//        String pushId = UUID.randomUUID().toString();
//        databaseReference.child("Ratings").child(rides.getUserId()).child( FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .setValue(new Rate(String.valueOf(rating), FirebaseAuth.getInstance().getCurrentUser().getUid(), rides.getName(), pushId))
//                .addOnCompleteListener(task -> {
//                    if (task.isComplete() && task.isSuccessful()) {
//                        Toast.makeText(requireContext(), "Thanks for rating", Toast.LENGTH_SHORT).show();
//                        bottomSheetDialog.dismiss();
//
//                    }
//                }).addOnFailureListener(e -> {
//                    Toast.makeText(requireContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                });
//
//
//    }
//    private void fetchOrder(String uid) {
//
//
//        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Orders");
//
//        databaseReference.child(uid)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()){
//binding.txtNoData.setVisibility(View.GONE);
//binding.layoutData.setVisibility(View.VISIBLE);
//
//                       data=      snapshot.getValue(UserInfoModel.class);
//
//                            binding.txtnameofuser.setText(data.getName() + " " + data.getServiceType());
//                            binding.txtphonenumber.setText(data.getPhonenumber());
//                            binding.txtAddress.setText(data.getCity());
//                            binding.btnphone.setOnClickListener(view -> {
//                                Intent intent = new Intent(Intent.ACTION_DIAL);
//                                intent.setData(Uri.parse("tel:" +data.getPhonenumber()));
//                                requireActivity().startActivity(intent);
//                            });
//                        }else{
//                            binding.txtNoData.setVisibility(View.VISIBLE);
//                            binding.layoutData.setVisibility(View.GONE);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        binding.txtNoData.setVisibility(View.VISIBLE);
//                        binding.layoutData.setVisibility(View.GONE);
//
//                    }
//                });
//    }


}
