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

public class CancelledAdapter extends RecyclerView.Adapter<CancelledAdapter.CancelledHolder>{
    private Context context;
    private ArrayList<UserInfoModel> list;

    public CancelledAdapter(Context context, ArrayList<UserInfoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CancelledHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancelled_orders,parent,false);
        return new CancelledHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelledHolder holder, int position) {

        UserInfoModel data= list.get(position);
//        Glide.with(context)
//                .load(data.getImage())
//                .placeholder(R.drawable.ic_baseline_person_pin_24)
//                .into(holder.imgUser);
        holder.userName.setText(data.getUserName());
        holder.phoneNumber.setText(data.getPhonenumber());
        holder.providerLocation.setText(data.getCity());
        holder.userLocation.setText(data.getUserAddress());


    }





    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CancelledHolder extends RecyclerView.ViewHolder{
        private ImageView imgUser;
        private TextView userName,phoneNumber,providerLocation,userLocation;


        public CancelledHolder(@NonNull View itemView) {
            super(itemView);


            imgUser=itemView.findViewById(R.id.userProfileImageCan);

            userName=itemView.findViewById(R.id.userNameCan);
            phoneNumber=itemView.findViewById(R.id.txtPhoneNumberCan);
            providerLocation=itemView.findViewById(R.id.txtCurrentLocationCan);
            userLocation=itemView.findViewById(R.id.txtUserLocationCan);

        }
    }
}
