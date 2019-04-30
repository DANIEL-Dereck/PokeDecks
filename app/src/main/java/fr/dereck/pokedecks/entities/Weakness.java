package fr.dereck.pokedecks.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weakness implements Serializable {
    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private String value;

    public Weakness() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.type + "x" + this.value;
    }
}
