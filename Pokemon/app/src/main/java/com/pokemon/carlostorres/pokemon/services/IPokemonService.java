package com.pokemon.carlostorres.pokemon.services;


import com.pokemon.carlostorres.pokemon.model.PokemonInfo;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
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

    /**
     * Obtiene la informacion de un pokemon
     *
     * @return pokemon
     */
    PokemonInfo getPokemon(int id);

    /**
     * Obtiene la informacion de un pokemon
     *
     * @return pokemon
     */
    PokemonInfo getPokemonByName(String name);

    /**
     * Captura un pokemon
     *
     * @param catchedPokemon
     *         pokemon
     */
    boolean setCatchedPokemon(PokemonItem catchedPokemon);

    /**
     * Obtener un item pokemon
     *
     * @param name
     *        of pokemon
     */
    PokemonItem getPokemonItem(String name);
}
