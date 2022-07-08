package com.example.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Notification activity;
    private List<Booking> mList;

    public MyAdapter(Notification activity,List<Booking> mList){
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.date.setText(mList.get(position).getDate());
        holder.time.setText(mList.get(position).getTime());
        holder.doctor.setText(mList.get(position).getDoctor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
       TextView date,time, doctor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_text);
            time = itemView.findViewById(R.id.time_text);
            doctor = itemView.findViewById(R.id.doctor_text);
        }
    }
}
