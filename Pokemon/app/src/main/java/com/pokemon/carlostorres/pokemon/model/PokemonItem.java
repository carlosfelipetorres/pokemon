package com.pokemon.carlostorres.pokemon.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public class PokemonItem {

    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;

    private boolean catched;

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
