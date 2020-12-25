package me.rubl.gbandroidbase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder>{

    ArrayList<String> citiesList;

    public CitiesAdapter(ArrayList<String> citiesList) {
        this.citiesList = citiesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(citiesList.get(position));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(android.R.id.text1);
        }
    }

    public void filterList(ArrayList<String> filteredList) {
        citiesList = filteredList;
        notifyDataSetChanged();
    }
}