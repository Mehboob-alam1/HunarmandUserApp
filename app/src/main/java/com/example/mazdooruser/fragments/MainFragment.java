package com.example.mazdooruser.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mazdooruser.R;
import com.example.mazdooruser.SliderAdapter;
import com.example.mazdooruser.databinding.FragmentMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    DatabaseReference databaseReference;
    ArrayList<Slider> list;
    private SliderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMainBinding.inflate(inflater,container,false);
        databaseReference= FirebaseDatabase.getInstance().getReference();
list= new ArrayList<>();

        fetchBanners();
        binding.btntalktoUs.setOnClickListener(view -> {
            String contact = "+92 3129700730"; // use country code with your phone number

            String url = "https://api.whatsapp.com/send?phone=" + contact;
            try {
                PackageManager pm = requireContext().getPackageManager();
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                requireContext().startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                requireContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

        });




        return binding.getRoot();
    }

    private void setSlider() {
        binding.imageSliderMain.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSliderMain.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSliderMain.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSliderMain.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSliderMain.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSliderMain.setScrollTimeInSec(4); //set scroll delay in seconds :
        binding.imageSliderMain.startAutoCycle();

    }

    private void fetchBanners() {

        databaseReference.child("Banners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String imageUrl = snapshot1.child("imageUrl").getValue(String.class);
                        String imageLink = snapshot1.child("imageLink").getValue(String.class);
                        String pushId = snapshot1.child("pushId").getValue(String.class);

//                        Toast.makeText(StartScreen.this, ""+data, Toast.LENGTH_SHORT).show();
                        list.add(new Slider(imageLink, imageUrl, pushId));
                    }

                    adapter = new SliderAdapter(list,getContext());
                    binding.imageSliderMain.setSliderAdapter(adapter);
                    setSlider();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}