package com.pokemon.carlostorres.pokemon.managers;

import com.pokemon.carlostorres.pokemon.model.PokemonInfo;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
import com.pokemon.carlostorres.pokemon.persistence.CrudManager;

import java.sql.SQLException;

/**
 * Implementation of the apps' queries
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public class PokemonInfoManager extends CrudManager<PokemonInfo, Integer> {

    /**
     * This is the main constructor of the CrudManager
     *
     * @param helper
     *         The DBHelper
     *
     * @throws SQLException
     *         If there's an error creating the Entity's DAO
     */
    public PokemonInfoManager(DatabaseHelper helper) throws SQLException {
        super(helper, PokemonInfo.class);
    }
}
