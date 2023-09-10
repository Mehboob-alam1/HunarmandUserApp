package com.example.mazdooruser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.CompleteHolder>{
    private Context context;
    private ArrayList<UserInfoModel> list;

    public CompletedAdapter(Context context, ArrayList<UserInfoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CompleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.complete_orders,parent,false);
        return new CompleteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteHolder holder, int position) {

        UserInfoModel data= list.get(position);
        Glide.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.ic_baseline_person_pin_24)
                .into(holder.imgUser);
        holder.userName.setText(data.getUserName());
        holder.phoneNumber.setText(data.getPhonenumber());
        holder.providerLocation.setText(data.getCity());
        holder.userLocation.setText(data.getUserAddress());


    }





    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CompleteHolder extends RecyclerView.ViewHolder{
        private ImageView imgUser;
        private TextView userName,phoneNumber,providerLocation,userLocation;


        public CompleteHolder(@NonNull View itemView) {
            super(itemView);


            imgUser=itemView.findViewById(R.id.userProfileImageComp);

            userName=itemView.findViewById(R.id.userNameComp);
            phoneNumber=itemView.findViewById(R.id.txtPhoneNumberComp);
            providerLocation=itemView.findViewById(R.id.txtCurrentLocationComp);
            userLocation=itemView.findViewById(R.id.txtUserLocationComp);

        }
    }
}
