package com.pokemon.carlostorres.pokemon.services;


import com.pokemon.carlostorres.pokemon.model.PokemonList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Api pokemons
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos F. Torres J.</a>
 */
public interface IPokemonApi {

    /**
     * Gets the list of pokemons
     *
     * @return List of pokemons
     */
    @GET("pokemon")
    Call<PokemonList> getPokemonList(@Query("offset") int offset);

}
