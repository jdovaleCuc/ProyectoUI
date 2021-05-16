package com.example.proyectoui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoui.DataBase.DatabaseHelper;
import com.example.proyectoui.adaptador.RecyclerAdapter;
import com.example.proyectoui.modelo.HousesList;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private RecyclerView rvHouses;
    private RecyclerAdapter adapter;
    private List<HousesList> houses;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvHouses = view.findViewById(R.id.recyclerHouse);
        initvalues();
        return view;

    }

    @Override
    public void onResume() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvHouses.setLayoutManager(manager);
        DatabaseHelper bd = new DatabaseHelper(getContext());
        houses = bd.Show();
        adapter = new RecyclerAdapter(houses);
        rvHouses.setAdapter(adapter);
        super.onResume();
    }

    private void initvalues(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvHouses.setLayoutManager(manager);
        DatabaseHelper bd = new DatabaseHelper(getContext());
        houses = bd.Show();
        adapter = new RecyclerAdapter(houses);
        rvHouses.setAdapter(adapter);
    }

}