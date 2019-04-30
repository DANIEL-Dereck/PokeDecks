package fr.dereck.pokedecks.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.dereck.pokedecks.R;

public class PokemonCardViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv_item_pokemon_card_image;
    private TextView iv_item_pokemon_card_name;
    private LinearLayout ll_item_pokemon_card;

    public PokemonCardViewHolder(@NonNull View itemView) {
        super(itemView);

        this.iv_item_pokemon_card_image = itemView.findViewById(R.id.iv_item_pokemon_card_image);
        this.iv_item_pokemon_card_name = itemView.findViewById(R.id.iv_item_pokemon_card_name);
        this.ll_item_pokemon_card = itemView.findViewById(R.id.ll_item_pokemon_card);
    }

    public ImageView getIv_item_pokemon_card_image() {
        return iv_item_pokemon_card_image;
    }

    public TextView getIv_item_pokemon_card_name() {
        return iv_item_pokemon_card_name;
    }

    public LinearLayout getLl_item_pokemon_card() {
        return ll_item_pokemon_card;
    }
}
