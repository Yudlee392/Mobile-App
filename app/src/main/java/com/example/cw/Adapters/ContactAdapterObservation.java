package com.example.cw.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cw.Activities.MainActivity;
import com.example.cw.Activities.UpdateHike;
import com.example.cw.Activities.UpdateObservation;
import com.example.cw.Activities.ViewHike;

import com.example.cw.Models.Observation;
import com.example.cw.R;

import java.util.List;

public class ContactAdapterObservation extends RecyclerView.Adapter<ContactAdapterObservation.ContactViewHolder> {
    private List<Observation> observations;

    private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void onDeleteClick(Observation observation);

    }

    public ContactAdapterObservation(List<Observation> observations, OnDeleteClickListener onDeleteClickListener) {
        this.observations = observations;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dataobservation, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Observation observation = observations.get(position);
        holder.observation.setText(observation.observation);

        holder.btnDeleteObservation.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(observations.get(position));
            }
        });
        holder.btnMoreObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateObservation.class);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView observation;
        Button btnDeleteObservation,btnMoreObservation;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            observation = itemView.findViewById(R.id.observation);
            btnDeleteObservation = itemView.findViewById(R.id.btnDeleteObservation);
            btnMoreObservation = itemView.findViewById(R.id.btnMoreObservation);
        }
    }
}
