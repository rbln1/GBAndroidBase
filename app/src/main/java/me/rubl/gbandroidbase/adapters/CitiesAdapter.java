package me.rubl.gbandroidbase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.rubl.gbandroidbase.listeners.OnItemClickCityRecyclerListener;
import me.rubl.gbandroidbase.entities.City;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder>{

    private final Context context;
    private ArrayList<City> citiesList;
    private final OnItemClickCityRecyclerListener clickListener;

    public CitiesAdapter(Context context, ArrayList<City> citiesList, OnItemClickCityRecyclerListener clickListener) {
        this.citiesList = citiesList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(context.getResources().getString(citiesList.get(position).getNameResourceId()));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvName = itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onItemClick(citiesList.get(position));
        }
    }

    public void filterList(ArrayList<City> filteredList) {
        citiesList = filteredList;
        notifyDataSetChanged();
    }
}