package com.example.mazdooruser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.ActiveHolder>{
private Context context;
    private ArrayList<UserInfoModel> list;

    public ActiveOrderAdapter(Context context, ArrayList<UserInfoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ActiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.active_orders,parent,false);
        return new ActiveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveHolder holder, int position) {

       UserInfoModel data= list.get(position);
        Glide.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.ic_baseline_person_pin_24)
                .into(holder.imgUser);
        holder.userName.setText(data.getUserName());
        holder.phoneNumber.setText(data.getPhonenumber());
        holder.providerLocation.setText(data.getCity());
        holder.userLocation.setText(data.getUserAddress());

        holder.btnCompleteOrder.setOnClickListener(v -> {

            setOrderComplete(data);
            list.remove(position); // Remove the item from the list
            notifyDataSetChanged();
        });
        holder.btnCancelOrder.setOnClickListener(v -> {
            deactivateOrder(data);
            list.remove(position); // Remove the item from the list
            notifyDataSetChanged(); //
        });
    }

    private void deactivateOrder(UserInfoModel data) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("ProviderOrder")
                .child(data.getUserId())
                .removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        databaseReference.child("ProviderCancelled")
                                        .child(data.getUserId())
                                                .child(data.getPushId())
                                                        .setValue(data);
                        databaseReference.child("UserOrders")
                                .child(data.getUserId2())
                                .child(data.getPushId())
                                .removeValue().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        Toast.makeText(context, "order cancelled", Toast.LENGTH_SHORT).show();
                                        databaseReference.child("UserCancelled")
                                                .child(data.getUserId2())
                                                .child(data.getPushId())
                                                .setValue(data);
                                    }
                                });
                    }
                });
    }

    private void setOrderComplete(UserInfoModel data) {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("ProviderOrder")
                .child(data.getUserId())
                .removeValue();
        databaseReference.child("ProviderCompleted")
                .child(data.getUserId())
                .child(data.getPushId())
                .setValue(data)
                .addOnCompleteListener(task -> {

                    if (task.isComplete()){

                        databaseReference.child("UserCompleted")
                                .child(data.getUserId2())
                                .child(data.getPushId())
                                .setValue(data)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        Toast.makeText(context, "Order completed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActiveHolder extends RecyclerView.ViewHolder{
    private ImageView imgUser,btnCancelOrder;
    private TextView userName,phoneNumber,providerLocation,userLocation;
    private AppCompatButton btnCompleteOrder;

        public ActiveHolder(@NonNull View itemView) {
            super(itemView);


            imgUser=itemView.findViewById(R.id.userProfileImage);
            btnCancelOrder=itemView.findViewById(R.id.imgCancelOrder);
            btnCancelOrder=itemView.findViewById(R.id.imgCancelOrder);
            userName=itemView.findViewById(R.id.userName);
            phoneNumber=itemView.findViewById(R.id.txtPhoneNumber);
            providerLocation=itemView.findViewById(R.id.txtCurrentLocation);
            userLocation=itemView.findViewById(R.id.txtUserLocation);
            btnCompleteOrder=itemView.findViewById(R.id.btnCompleteOrder);
        }
    }
}
