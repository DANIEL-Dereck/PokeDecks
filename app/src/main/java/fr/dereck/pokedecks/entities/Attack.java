package fr.dereck.pokedecks.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attack implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("text")
    private String text;

    public Attack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
