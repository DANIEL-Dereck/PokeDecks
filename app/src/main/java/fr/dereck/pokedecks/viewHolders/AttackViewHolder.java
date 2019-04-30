package fr.dereck.pokedecks.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.dereck.pokedecks.R;

public class AttackViewHolder extends RecyclerView.ViewHolder {
    private TextView atk_title;
    private TextView atk_desc;

    public AttackViewHolder(@NonNull View itemView) {
        super(itemView);

        this.atk_title = itemView.findViewById(R.id.atk_title);
        this.atk_desc = itemView.findViewById(R.id.atk_desc);
    }

    public TextView getAtk_title() {
        return atk_title;
    }

    public TextView getAtk_desc() {
        return atk_desc;
    }
}
