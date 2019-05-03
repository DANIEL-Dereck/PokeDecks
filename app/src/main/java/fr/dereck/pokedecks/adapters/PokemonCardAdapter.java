package fr.dereck.pokedecks.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.dereck.pokedecks.R;
import fr.dereck.pokedecks.Utils.AppUtil;
import fr.dereck.pokedecks.activities.CardViewActivity;
import fr.dereck.pokedecks.entities.PokemonCard;
import fr.dereck.pokedecks.viewHolders.PokemonCardViewHolder;

public class PokemonCardAdapter extends RecyclerView.Adapter<PokemonCardViewHolder> {

    private List<PokemonCard> items;
    private Context ctx;

    public PokemonCardAdapter(Context ctx) {
        this.ctx = ctx;
        this.items = new ArrayList<>();
    }

    public void addItem(PokemonCard card) {
        this.items.add(card);
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.items.clear();
        this.notifyDataSetChanged();
    }

    public void addItems(List<PokemonCard> items) {
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

    public void setItems(List<PokemonCard> items) {
        this.items.clear();
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PokemonCardViewHolder pokemonCardViewHolder =
                new PokemonCardViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_pokemon_card, viewGroup, false));

        return pokemonCardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonCardViewHolder pokemonCardViewHolder, int i) {
        final PokemonCard selectedCard = this.items.get(i);

        pokemonCardViewHolder.getLl_item_pokemon_card().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PokemonCardAdapter.this.ctx, CardViewActivity.class);
                intent.putExtra(CardViewActivity.EXTRA_CARD, selectedCard);
                PokemonCardAdapter.this.ctx.startActivity(intent);
            }
        });

        pokemonCardViewHolder.getIv_item_pokemon_card_name().setText(selectedCard.getName());

        String url = AppUtil.NOIMG;
        if (selectedCard.getImageUrl() != null && !selectedCard.getImageUrl().isEmpty()) {
            url = selectedCard.getImageUrl();
        }

        pokemonCardViewHolder.getIv_item_pokemon_card_image().setImageURI(null);
        Picasso.get().load(url).placeholder(R.drawable.pokemon_icon).into(pokemonCardViewHolder.getIv_item_pokemon_card_image());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
