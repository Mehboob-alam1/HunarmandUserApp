package com.example.mazdooruser.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mazdooruser.ModelProviderType;
import com.example.mazdooruser.ProviderTypesAdapter;
import com.example.mazdooruser.R;
import com.example.mazdooruser.databinding.FragmentServicesBinding;

import java.util.ArrayList;


public class ServicesFragment extends Fragment {

    FragmentServicesBinding binding;
    ArrayList<ModelProviderType> list;
    ProviderTypesAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentServicesBinding.inflate(inflater,container,false);
        binding.btntalktoUsService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+92 3129700730"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        list=new ArrayList<>();
        list.clear();
        list.add(new ModelProviderType("Carpenter",R.drawable.carpenter));
        list.add(new ModelProviderType("Electrician",R.drawable.electrician));
        list.add(new ModelProviderType("Handiman",R.drawable.handiman));
        list.add(new ModelProviderType("Painter",R.drawable.painter));
        list.add(new ModelProviderType("Sweeper",R.drawable.sweeper));
        list.add(new ModelProviderType("Vehicle Mechanic",R.drawable.vehicle_mechanic));
        list.add(new ModelProviderType("Launderer",R.drawable.laundry));
        list.add(new ModelProviderType("Ac Technician",R.drawable.technician));
        list.add(new ModelProviderType("Mason",R.drawable.mason));
        list.add(new ModelProviderType("Plumber",R.drawable.plumber));
        list.add(new ModelProviderType("Interior Designer",R.drawable.interior_designer));
        adapter=new ProviderTypesAdapter(list,getContext());
        binding.recyclerContent.setAdapter(adapter);

        binding.recyclerContent.setLayoutManager(new GridLayoutManager(getContext(), 3));




        return binding.getRoot();
    }
}