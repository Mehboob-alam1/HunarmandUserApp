package com.example.mazdooruser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.viewHolder> {
    ArrayList<UserInfoModel> list;
    DatabaseReference databaseReference;
    Context context;
    String typeOfProvider;

    public ProviderAdapter(ArrayList<UserInfoModel> list, Context context, String typeOfProvider) {
        this.list = list;
        this.context = context;
        this.typeOfProvider = typeOfProvider;
    }

    @NonNull
    @Override

    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.carpenter_item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        UserInfoModel data = list.get(position);

        holder.address.setText(data.getCity());
        holder.name.setText(data.getName());

        holder.phoneNumber.setText(data.getPhonenumber());
        Picasso.get().load(data.getImage()).placeholder(R.drawable.provider).into(holder.profileImage);


        holder.moreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();

             String str=   gson.toJson(data);
                   Intent intent=new Intent(context,ProviderDetailsActivity.class);
                   intent.putExtra("city",data.getCity());
                   intent.putExtra("desc",data.getDescription());
                   intent.putExtra("image",data.getImage());
                   intent.putExtra("name",data.getName());
                   intent.putExtra("phone",data.getPhonenumber());
                   intent.putExtra("servtype",data.getServiceType());
                intent.putExtra("userId",data.getUserId());

                   context.startActivity(intent);

            }
        });
        holder.phoneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +data.getPhonenumber()));
               context. startActivity(intent);
            }
        });


        holder.btnCheckRatings.setOnClickListener(view -> {


             Intent intent = new Intent(context,RatingsActivity.class);
            intent.putExtra("city",data.getCity());
            intent.putExtra("desc",data.getDescription());
            intent.putExtra("image",data.getImage());
            intent.putExtra("name",data.getName());
            intent.putExtra("phone",data.getPhonenumber());
            intent.putExtra("servtype",data.getServiceType());
            intent.putExtra("userId",data.getUserId());
            context.startActivity(intent);
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String uri = "http://maps.google.com/maps?q=loc:" + data.getLatitude() + "," + data.getLongitude();
//               try {
//                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                   intent.setPackage("com.google.android.apps.maps");
//                   context.startActivity(intent);
//               }catch (Exception e){
//                   Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//               }


                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("www.google.com")
                        .appendPath("maps")
                        .appendPath("dir")
                        .appendPath("")
                        .appendQueryParameter("api", "1")
                        .appendQueryParameter("destination", data.getLatitude() + "," + data.getLongitude());
                String url = builder.build().toString();
                Log.d("Directions", url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
               context. startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage, phoneImage,moreImage;
        TextView name, phoneNumber, address;
        LinearLayout btnCheckRatings;



        public viewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.imageofUser);
            phoneImage = itemView.findViewById(R.id.btnphone);
            name = itemView.findViewById(R.id.txtnameofuser);
            phoneNumber = itemView.findViewById(R.id.txtphonenumber);
            address = itemView.findViewById(R.id.txtAddress);
            moreImage=itemView.findViewById(R.id.btnMore);
            btnCheckRatings=itemView.findViewById(R.id.btnCheckRatings);
        }
    }
}
