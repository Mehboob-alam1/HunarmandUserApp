package com.example.mazdooruser.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mazdooruser.CompletedAdapter;
import com.example.mazdooruser.UserInfoModel;
import com.example.mazdooruser.databinding.FragmentCompletedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CompletedFragment extends Fragment {

    private FragmentCompletedBinding binding;
    private ArrayList<UserInfoModel> list;
    private CompletedAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompletedBinding.inflate(inflater, container, false);
//        return inflater.inflate(R.layout.fragment_completed, container, false);
list= new ArrayList<>();
        fetchCompleted();
        return binding.getRoot();


    }


    private void fetchCompleted() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserCompleted");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.noData.setVisibility(View.GONE);



                    for (DataSnapshot snap : snapshot.getChildren()) {

                        UserInfoModel rides = snap.getValue(UserInfoModel.class);
                        list.add(rides);
                    }




                    adapter = new CompletedAdapter(requireContext(), list);
                    binding.recyclerComp.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    binding.recyclerComp.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    binding.noData.setVisibility(View.VISIBLE);
                    binding.recyclerComp.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //binding.noData.getRoot().setVisibility(View.VISIBLE);
                binding.noData.setVisibility(View.VISIBLE);
                binding.recyclerComp.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        if (adapter != null) {
            binding.recyclerComp.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            binding.recyclerComp.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }
}