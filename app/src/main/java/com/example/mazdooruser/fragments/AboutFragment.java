package com.example.mazdooruser.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mazdooruser.R;
import com.example.mazdooruser.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {
FragmentAboutBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAboutBinding.inflate(inflater,container,false);





        return binding.getRoot();
    }
}