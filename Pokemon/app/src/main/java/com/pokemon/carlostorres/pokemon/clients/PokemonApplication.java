package com.pokemon.carlostorres.pokemon.clients;

import android.app.Application;

import com.pokemon.carlostorres.pokemon.services.ManagersModule;
import com.pokemon.carlostorres.pokemon.services.ServicesModule;


/**
 * This is the Pokemon Application where Dependency Injection is set up
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public class PokemonApplication extends Application {

    DiComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerDiComponent.builder().servicesModule(new ServicesModule(this))
                .managersModule(new ManagersModule(this)).build();
    }

    public DiComponent getComponent() {
        return component;
    }

}


