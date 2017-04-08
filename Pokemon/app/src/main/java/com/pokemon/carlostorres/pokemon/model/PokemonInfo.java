package com.pokemon.carlostorres.pokemon.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class PokemonInfo {

    @DatabaseField(generatedId = true)
    private Integer idLocal;
    @SerializedName("id")
    @DatabaseField(canBeNull = false)
    private Integer id;
    @SerializedName("name")
    @DatabaseField(canBeNull = false)
    private String name;
    @SerializedName("weight")
    @DatabaseField(canBeNull = false)
    private Integer weight;
    @SerializedName("sprites")
    @DatabaseField(dataType= DataType.SERIALIZABLE, canBeNull = true)
    private Sprites sprites;
    @SerializedName("height")
    @DatabaseField(canBeNull = false)
    private Integer height;
    @SerializedName("base_experience")
    @DatabaseField(canBeNull = false)
    private Integer baseExperience;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }
}
