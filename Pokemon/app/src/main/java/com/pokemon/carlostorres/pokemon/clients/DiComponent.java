package com.pokemon.carlostorres.pokemon.clients;

import android.content.Context;

import com.pokemon.carlostorres.pokemon.presentation.PokemonListActivity;
import com.pokemon.carlostorres.pokemon.presentation.PokemonDetailActivity;
import com.pokemon.carlostorres.pokemon.services.ManagersModule;
import com.pokemon.carlostorres.pokemon.services.ServicesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dependency Injection component where all Activities, fragments and adapters are injected
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
@Singleton
@Component(modules = {ManagersModule.class, ServicesModule.class})
public interface DiComponent {

    /**
     * Context injection
     *
     * @return Injected context
     */
    Context context();

    // Main List Activity
    void inject(PokemonListActivity activity);

    // Detail Activity
    void inject(PokemonDetailActivity activity);
}
