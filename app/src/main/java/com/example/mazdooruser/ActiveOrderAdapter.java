package com.example.mazdooruser;

import static com.example.mazdooruser.Utils.showSnackBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.ActiveHolder>{
private Context context;
    private ArrayList<UserInfoModel> list;
    private Activity activity;
    BottomSheetDialog    bottomSheetDialog;
    public ActiveOrderAdapter(Context context, ArrayList<UserInfoModel> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
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
        holder.userName.setText(data.getName() + " , " +data.getServiceType());
        holder.phoneNumber.setText(data.getPhonenumber());
        holder.providerLocation.setText(data.getUserAddress());
        holder.userLocation.setText(data.getCity());

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

        holder.txtRate.setOnClickListener(view -> {


            showBottomDialog(data);
        });
        
        holder.imgPhone.setOnClickListener(view ->{
            
            
            callProvider(data.getPhonenumber());
        });
    }

    private void callProvider(String phonenumber) {

        Uri u = Uri.parse("tel:" + phonenumber);

        // Create the intent and set the data for the
        // intent as the phone number.
        Intent i = new Intent(Intent.ACTION_DIAL, u);

        try
        {
            // Launch the Phone app's dialer with a phone
            // number to dial a call.
            context.startActivity(i);
        }
        catch (SecurityException s)
        {
            // show() method display the toast with
            // exception message.
            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void showBottomDialog(UserInfoModel data) {
        bottomSheetDialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(context)
                .inflate(R.layout.rate_driver, (LinearLayout) activity.findViewById(R.id.rateDriverLayout));


        bottomSheetDialog.setContentView(bottomSheetView);
        try {
            bottomSheetDialog.show();
        } catch (WindowManager.BadTokenException e) {
            //use a log message
        }

        TextView txtName= bottomSheetView.findViewById(R.id.txtProviderNameRate);
        TextView txtVehicleType= bottomSheetView.findViewById(R.id.txtTypeRate);
        RatingBar rateBar= bottomSheetView.findViewById(R.id.rateBar);
        AppCompatButton btnRateDriver= bottomSheetView.findViewById(R.id.btnRateDriver);


        txtName.setText(data.getName());

        txtVehicleType.setText(data.getServiceType());


        btnRateDriver.setOnClickListener(view -> {


            if (rateBar.getRating()<=0){
                showSnackBar(activity,"provide rating");
            }else{

                rateDriver(rateBar.getRating(),data);
            }
        });
    }
        private void rateDriver(float rating, UserInfoModel rides) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ;

        String pushId = UUID.randomUUID().toString();
        databaseReference.child("Ratings").child(rides.getUserId()).child( FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(new Rate(String.valueOf(rating), FirebaseAuth.getInstance().getCurrentUser().getUid(), rides.getName(), pushId))
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        Toast.makeText(context, "Thanks for rating", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();

                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
    private ImageView imgUser,btnCancelOrder,imgPhone;
    private TextView userName,phoneNumber,providerLocation,userLocation,txtRate;
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
            txtRate=itemView.findViewById(R.id.txtRateBtn);
            imgPhone=itemView.findViewById(R.id.imgPhone);
        }
    }
}
