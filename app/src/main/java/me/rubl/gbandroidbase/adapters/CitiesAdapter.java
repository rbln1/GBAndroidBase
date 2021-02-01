package me.rubl.gbandroidbase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.rubl.gbandroidbase.R;
import me.rubl.gbandroidbase.listeners.OnItemClickCityRecyclerListener;
import me.rubl.gbandroidbase.model.core.City;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder>{

    private ArrayList<City> citiesList;
    private final OnItemClickCityRecyclerListener clickListener;

    public CitiesAdapter(ArrayList<City> citiesList,
                         OnItemClickCityRecyclerListener clickListener) {
        this.citiesList = citiesList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(citiesList.get(position).getName());
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
            tvName = itemView.findViewById(R.id.title);
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