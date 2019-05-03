package fr.dereck.pokedecks.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import fr.dereck.pokedecks.R;
import fr.dereck.pokedecks.entities.Resistance;
import fr.dereck.pokedecks.viewHolders.StatsViewHolder;

public class ResistanceAdapter extends RecyclerView.Adapter<StatsViewHolder> {
    private List<Resistance> items;

    public List<Resistance> getItems() {
        return items;
    }

    public void addItems(List<Resistance> items) {
        this.items.addAll(items);
    }

    public void setItems(List<Resistance> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public ResistanceAdapter(List<Resistance> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        StatsViewHolder statsViewHolder =
                new StatsViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.stats_item, viewGroup, false));

        return statsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder statsViewHolder, int i) {
        final Resistance selectedItem = this.items.get(i);

        statsViewHolder.getStats_value().setText(selectedItem.toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
