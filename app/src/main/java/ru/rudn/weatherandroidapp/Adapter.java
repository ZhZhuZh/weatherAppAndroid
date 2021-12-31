package ru.rudn.weatherandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    ArrayList<String> name;
    ArrayList<Double> temp;
    Context context;
    LayoutInflater layoutInflater;

    public Adapter(ArrayList<String> name, ArrayList<Double> temp, Context context) {
        this.name = name;
        this.temp = temp;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.line, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.temp.setText(temp.get(position) + " C");
        holder.name.setText(name.get(position));
    }

    public void Add(String name, Double t){
        this.name.add(name);
        temp.add(t);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView name, temp;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.city);
            temp = itemView.findViewById(R.id.t);
        }
    }
}
