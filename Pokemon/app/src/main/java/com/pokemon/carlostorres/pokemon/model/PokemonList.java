package com.pokemon.carlostorres.pokemon.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonList {
    @SerializedName("count")
    private Integer count;
    @SerializedName("previous")
    private String previous;
    @SerializedName("results")
    private List<PokemonItem> results;
    @SerializedName("next")
    private String next;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PokemonItem> getResults() {
        return results;
    }

    public void setResults(List<PokemonItem> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
