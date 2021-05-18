package com.example.proyectoui.adaptador;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoui.AccountFragment;
import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.DetailActivity;
import com.example.proyectoui.FragmentNotification;
import com.example.proyectoui.R;
import com.example.proyectoui.modelo.Notification;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerHolder> {
    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view, parent, false);
        return new NotificationAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.RecyclerHolder holder, int position) {
        Notification Notification = notifications.get(position);
        holder.notTitle.setText(Notification.getTopic());
        holder.notDescription.setText(Notification.getDescriptionNot());
        DatabaseHelper BD = new DatabaseHelper(holder.deleteNot.getContext());
        holder.deleteNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BD.DeleteNotify(Notification.getId_not());
                notifications.remove(position);
                NotificationAdapter.this.notifyItemRemoved(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        private TextView notTitle;
        private TextView notDescription;
        private ImageButton deleteNot;

        public RecyclerHolder(@NonNull View itemView){
            super(itemView);
            notTitle = itemView.findViewById(R.id.NotTopic);
            notDescription = itemView.findViewById(R.id.NotDesc);
            deleteNot = itemView.findViewById(R.id.deleteNot);
        }
    }
}
