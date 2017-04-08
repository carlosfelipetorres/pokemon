package com.pokemon.carlostorres.pokemon.services;

import android.content.Context;

import com.github.johnpersano.supertoasts.SuperToast;
import com.pokemon.carlostorres.pokemon.clients.ClientePokemonSystem;
import com.pokemon.carlostorres.pokemon.clients.IClientePokemonSystem;
import com.pokemon.carlostorres.pokemon.managers.PokemonInfoManager;
import com.pokemon.carlostorres.pokemon.managers.PokemonItemManager;
import com.pokemon.carlostorres.pokemon.model.PokemonInfo;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
import com.pokemon.carlostorres.pokemon.model.PokemonList;
import com.pokemon.carlostorres.pokemon.model.TipoNotificacion;
import com.pokemon.carlostorres.pokemon.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public class PokemonService implements IPokemonService {

    /**
     * Cliente servidor
     **/
    @Inject
    IClientePokemonSystem mCliente = new ClientePokemonSystem();

    /**
     * Singleton API visitas
     **/
    private IPokemonApi mPokemonApi;

    /**
     * Application context
     **/
    private Context mContext;

    private PokemonItemManager pokemonItemManager;
    private PokemonInfoManager pokemonInfoManager;

    public PokemonService(Context context, PokemonItemManager pokemonItemManager, PokemonInfoManager pokemonInfoManager) {
        this.mContext = context;
        this.pokemonItemManager = pokemonItemManager;
        this.pokemonInfoManager = pokemonInfoManager;
    }

    @Override
    public PokemonList getPokemonsList(int offset) {
        IPokemonApi mPokemonApi = getPokemonApi();

        Call<PokemonList> call = mPokemonApi.getPokemonList(offset);
        Response<PokemonList> response = mCliente.execute(call);

        if (!isSuccessful(response)) {
            List<PokemonItem> pokemonItems = pokemonItemManager.all();
            PokemonList persisted = new PokemonList();
            persisted.setResults(pokemonItems);
            return persisted;
        }
        if (response.body() == null) {
            AppUtils.crearToast(mContext, "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                    TipoNotificacion.ALERTA).show();
        }

        PokemonList list = response.body();
        for (PokemonItem pi : list.getResults()) {
            if (pokemonItemManager.findByAttr("name", pi.getName()).isEmpty()) {
                pokemonItemManager.createOrUpdate(pi);
            }
        }
        List<PokemonItem> pokemonItems = pokemonItemManager.all();
        PokemonList persisted = new PokemonList();
        persisted.setResults(pokemonItems);
        return persisted;
    }

    @Override
    public PokemonInfo getPokemon(int id) {
        IPokemonApi mPokemonApi = getPokemonApi();

        Call<PokemonInfo> call = mPokemonApi.getPokemonInfo(id);
        Response<PokemonInfo> response = mCliente.execute(call);

        if (!isSuccessful(response)) {
            List<PokemonInfo> infoList = pokemonInfoManager.findByAttr("id", id);
            PokemonInfo pokemon = null;
            if(!infoList.isEmpty()) {
                pokemon = infoList.get(0);
            }
            return pokemon;
        }
        if (response.body() == null) {
            AppUtils.crearToast(mContext, "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                    TipoNotificacion.ALERTA).show();
        }

        PokemonInfo pokemonInfo = response.body();

        if (pokemonInfoManager.findByAttr("name", pokemonInfo.getName()).isEmpty()) {
            pokemonInfoManager.createOrUpdate(pokemonInfo);
        }

        return pokemonInfo;
    }

    @Override
    public PokemonInfo getPokemonByName(String name) {
        List<PokemonInfo> infoList = pokemonInfoManager.findByAttr("name", name);
        if(!infoList.isEmpty()){
            return infoList.get(0);
        }
        return null;
    }

    @Override
    public boolean setCatchedPokemon(PokemonItem catchedPokemon) {
        catchedPokemon.setCatched(true);
        return pokemonItemManager.createOrUpdate(catchedPokemon);
    }

    @Override
    public PokemonItem getPokemonItem(String name) {
        List<PokemonItem> items = pokemonItemManager.findByAttr("name", name);
        if(!items.isEmpty()) {
            return items.get(0);
        }
        return null;
    }

    /**
     * Obtiene el API de pokemons
     *
     * @return La interfaz del API
     */
    private IPokemonApi getPokemonApi() {
        if (mPokemonApi == null) {
            mPokemonApi = mCliente.getApi(IPokemonApi.class);
        }
        return mPokemonApi;
    }

    /**
     * Este metodo verifica si la respuesta fue exitosa o no
     *
     * @param response La respuesta para ser verificada
     * @return True si fue exitoso. False de lo contrario.
     */
    public static boolean isSuccessful(Response response) {
        return response != null && response.isSuccessful();
    }
}
