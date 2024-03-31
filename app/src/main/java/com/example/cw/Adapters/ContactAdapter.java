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
import com.example.cw.Activities.ViewHike;
import com.example.cw.Models.Hike;
import com.example.cw.Models.Observation;
import com.example.cw.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Hike> hikes;

    private OnDeleteClickListener onDeleteClickListener;


    public interface OnDeleteClickListener {
        void onDeleteClick(Hike hike);

    }

    public ContactAdapter(List<Hike> hikes, OnDeleteClickListener onDeleteClickListener) {
        this.hikes = hikes;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.datacard, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.hikeName.setText(hike.name);

        holder.btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(hikes.get(position));
            }
        });
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateHike.class);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hikes.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName;
        Button btnDelete,btnMore;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.hikeName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnMore = itemView.findViewById(R.id.btnMore);
        }
    }
}
