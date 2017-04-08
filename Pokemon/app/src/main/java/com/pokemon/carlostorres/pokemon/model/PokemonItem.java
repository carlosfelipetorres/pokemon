package com.pokemon.carlostorres.pokemon.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public class PokemonItem {

    @DatabaseField(generatedId = true)
    private Integer id;
    @SerializedName("url")
    @DatabaseField(canBeNull = true)
    private String url;
    @SerializedName("name")
    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField()
    private boolean catched;

    public PokemonItem(){}

    public PokemonItem(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCatched() {
        return catched;
    }

    public void setCatched(boolean catched) {
        this.catched = catched;
    }
}
