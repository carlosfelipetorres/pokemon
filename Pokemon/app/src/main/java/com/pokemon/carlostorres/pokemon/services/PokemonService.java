package com.pokemon.carlostorres.pokemon.services;

import android.content.Context;

import com.github.johnpersano.supertoasts.SuperToast;
import com.pokemon.carlostorres.pokemon.clients.ClientePokemonSystem;
import com.pokemon.carlostorres.pokemon.clients.IClientePokemonSystem;
import com.pokemon.carlostorres.pokemon.model.PokemonList;
import com.pokemon.carlostorres.pokemon.model.TipoNotificacion;
import com.pokemon.carlostorres.pokemon.utils.AppUtils;

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

    public PokemonService(Context context) {
        this.mContext = context;
    }

    @Override
    public PokemonList getPokemonsList(int offset) {
        IPokemonApi mPokemonApi = getPokemonApi();

        Call<PokemonList> call = mPokemonApi.getPokemonList(offset);
        Response<PokemonList> response = mCliente.execute(call);

//        if (!isSuccessful(response)) {
//            List<String> categoriesPersisted = getAllCategories();
//            return categoriesPersisted;
//        }
        if (response.body() == null) {
            AppUtils.crearToast(mContext, "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                    TipoNotificacion.ALERTA).show();
        }

        PokemonList categories = response.body();
//        List<String> listing = response.body()getData().getElementos();
//        for (String rc : listing) {
//            categories.add(rc);
//        }
//
//        if (getAllCategories().isEmpty()) {
//            for (RedditCategory rc : categories) {
//                createOrUpdateCategory(rc);
//            }
//        }

        return categories;
    }

    /**
     * Obtiene el API de las visitas del cliente
     *
     * @return La interfaz del API de vencimisntos de producto
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
