package com.example.proyectoui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.adaptador.NotificationAdapter;
import com.example.proyectoui.adaptador.RecyclerAdapter;
import com.example.proyectoui.modelo.HousesList;
import com.example.proyectoui.modelo.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class FragmentNotification extends Fragment {
    private FirebaseAuth mAuth;
    private RecyclerView rvNotify;
    private NotificationAdapter adapter;
    private List<Notification> notifications;

    public FragmentNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        rvNotify = view.findViewById(R.id.recyclerNotificaction);
        initvalues();
        return view;
    }

    @Override
    public void onResume() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvNotify.setLayoutManager(manager);
        DatabaseHelper bd = new DatabaseHelper(getContext());
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            notifications = bd.ShowNotification(user.getEmail());
            adapter = new NotificationAdapter(notifications);
            rvNotify.setAdapter(adapter);
        }
        super.onResume();
    }

    private void initvalues(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvNotify.setLayoutManager(manager);
        DatabaseHelper bd = new DatabaseHelper(getContext());
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            notifications = bd.ShowNotification(user.getEmail());
            adapter = new NotificationAdapter(notifications);
            rvNotify.setAdapter(adapter);
        }

    }


}