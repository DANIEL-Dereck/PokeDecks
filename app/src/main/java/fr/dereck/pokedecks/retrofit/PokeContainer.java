package fr.dereck.pokedecks.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import fr.dereck.pokedecks.entities.PokemonCard;

public class PokeContainer {
    @SerializedName("cards")
    private List<PokemonCard> cards;

    public List<PokemonCard> getCards() {
        return cards;
    }

    public void setCards(List<PokemonCard> cards) {
        this.cards = cards;
    }
}
