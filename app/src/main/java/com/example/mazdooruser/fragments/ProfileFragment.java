package com.example.mazdooruser.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mazdooruser.LoginActivity;
import com.example.mazdooruser.R;
import com.example.mazdooruser.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {


    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

//        binding.txtInfo = findViewById(R.id.txtInfo);
//        btnLogOut = findViewById(R.id.btnLogOut);

        shp = getContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        CheckLogin();

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        return binding.getRoot();
    }

    public void CheckLogin() {
        if (shp == null)
            shp = getContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);


        String userName = shp.getString("name", "");

        if (userName != null && !userName.equals("")) {
            binding.txtInfo.setText("Welcome  " + userName);

        } else {
            Intent i = new Intent(getContext(), LoginActivity.class);
            getContext().startActivity(i);
            getActivity().finish();
        }
    }


    public void Logout() {
        try {
            if (shp == null)
                shp = getContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);

            shpEditor = shp.edit();
            shpEditor.putString("name", "");
            shpEditor.commit();

            Intent i = new Intent(getContext(), LoginActivity.class);
            getContext().startActivity(i);
            getActivity().finish();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
