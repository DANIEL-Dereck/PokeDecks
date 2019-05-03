package fr.dereck.pokedecks.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PokemonCard implements Serializable {
    public static final String API_CARDS = "cards";

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("imageUrlHiRes")
    private String imageUrlHiRes;
    @SerializedName("hp")
    private String lifepoints;
    @SerializedName("types")
    private List<String> types;
    @SerializedName("attacks")
    private List<Attack> attacks;
    @SerializedName("resistances")
    private List<Resistance> resistances;
    @SerializedName("weaknesses")
    private List<Weakness> weaknesses;

    public PokemonCard() {
        this.types = new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.resistances = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrlHiRes(String imageUrlHiRes) {
        this.imageUrlHiRes = imageUrlHiRes;
    }

    public String getImageUrlHiRes() {
        return imageUrlHiRes;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLifepoints() {
        return lifepoints;
    }

    public void setLifepoints(String lifepoints) {
        this.lifepoints = lifepoints;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public List<Resistance> getResistances() {
        return resistances;
    }

    public void setResistances(List<Resistance> resistances) {
        this.resistances = resistances;
    }

    public List<Weakness> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<Weakness> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getTypesString() {
        String types = "";

        if (this.types != null) {
            for (String type : this.types) {
                types += type + "";
            }
        }

        return types;
    }
}
