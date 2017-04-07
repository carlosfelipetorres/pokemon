package com.pokemon.carlostorres.pokemon.services;


import com.pokemon.carlostorres.pokemon.model.PokemonList;

import java.util.List;

import rx.Observable;

/**
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public interface IPokemonService {

    /**
     * Obtiene la informacion de pokemons
     *
     * @return Observable con lista
     */
    PokemonList getPokemonsList(int offset);


}
