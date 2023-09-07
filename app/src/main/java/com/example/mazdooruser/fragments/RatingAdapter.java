package com.example.mazdooruser.fragments;

import android.content.Context;
import android.icu.number.CompactNotation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mazdooruser.R;
import com.example.mazdooruser.Rate;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder>{
private Context context;
private ArrayList<Rate> list;


    public RatingAdapter(Context context, ArrayList<Rate> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_rating,parent,false);
        return new RatingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingHolder holder, int position) {

   Rate rate=     list.get(position);

   holder.txtRating.setText(rate.getRating());
   holder.txtUserGivenRating.setText(rate.getUserName() +  " "  +rate.getUserGivenRating());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RatingHolder extends RecyclerView.ViewHolder {
        TextView txtUserGivenRating, txtRating;

        public RatingHolder(@NonNull View itemView) {
            super(itemView);
    txtUserGivenRating=itemView.findViewById(R.id.txtRateGivenBy);
            txtRating=itemView.findViewById(R.id.txtRate);

        }
    }
}
