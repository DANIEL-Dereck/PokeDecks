package fr.dereck.pokedecks.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import fr.dereck.pokedecks.R;
import fr.dereck.pokedecks.entities.Attack;
import fr.dereck.pokedecks.viewHolders.AttackViewHolder;

public class AttackAdapter extends RecyclerView.Adapter<AttackViewHolder> {
    private List<Attack> items;

    public List<Attack> getItems() {
        return items;
    }

    public void addItems(List<Attack> items) {
        this.items.addAll(items);
    }

    public void setItems(List<Attack> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public AttackAdapter(List<Attack> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AttackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AttackViewHolder attackViewHolder =
                new AttackViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.attack_item, viewGroup, false));

        return attackViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttackViewHolder attackViewHolder, int i) {
        final Attack selectedItem = this.items.get(i);

        attackViewHolder.getAtk_title().setText(selectedItem.getName());
        attackViewHolder.getAtk_desc().setText(selectedItem.getText());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
