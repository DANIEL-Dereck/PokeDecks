package fr.dereck.pokedecks.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.dereck.pokedecks.R;

public class StatsViewHolder extends RecyclerView.ViewHolder {
    private TextView stats_value;

    public StatsViewHolder(@NonNull View itemView) {
        super(itemView);

        this.stats_value = itemView.findViewById(R.id.stats_value);
    }

    public TextView getStats_value() {
        return stats_value;
    }
}
