package com.example.proyectoui.adaptador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.proyectoui.DetailActivity;
import com.example.proyectoui.R;
import com.example.proyectoui.modelo.HousesList;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private List<HousesList> houses;

    public RecyclerAdapter(List<HousesList> houses) {
        this.houses = houses;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.houseitem_view, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        HousesList house = houses.get(position);
        byte[] img = house.getImgResource() ;
        Bitmap bt = BitmapFactory.decodeByteArray(img,0,img.length);
        holder.imgItem.setImageBitmap(bt);
        holder.HouseTitle.setText(house.getTitle());
        holder.HouseDescription.setText(house.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("HouseDetail",house);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder{
         private ImageView imgItem;
         private TextView HouseTitle;
         private TextView HouseDescription;

         public RecyclerHolder(@NonNull View itemView){
             super(itemView);
             imgItem = itemView.findViewById(R.id.imgitem);
             HouseTitle = itemView.findViewById(R.id.NotTopic);
             HouseDescription = itemView.findViewById(R.id.NotDesc);
         }
    }
}
