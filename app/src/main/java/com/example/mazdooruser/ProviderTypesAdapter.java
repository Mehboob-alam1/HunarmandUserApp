package com.example.mazdooruser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProviderTypesAdapter extends RecyclerView.Adapter<ProviderTypesAdapter.MyviewHolder> {
    ArrayList<ModelProviderType> list;
    Context context;
    DatabaseReference databaseReference;
    String uId;

    public ProviderTypesAdapter(ArrayList<ModelProviderType> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_providers, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        ModelProviderType data = list.get(position);

        holder.providerImage.setImageResource(data.getImage());
        holder.providerType.setText(data.getType());
        databaseReference = FirebaseDatabase.getInstance().getReference("userInfo");
        databaseReference.child(holder.providerType.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        UserInfoModel data = snap.getValue(UserInfoModel.class);

                     uId= data.getUserId();
                    }

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("type", holder.providerType.getText().toString());
                intent.putExtra("uID", uId);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView providerImage;
        TextView providerType;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            providerImage = itemView.findViewById(R.id.imgProvider);
            providerType = itemView.findViewById(R.id.txtProvider);
        }
    }
}
