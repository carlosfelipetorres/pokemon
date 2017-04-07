package com.pokemon.carlostorres.pokemon.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.github.johnpersano.supertoasts.SuperToast;
import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.clients.DiComponent;
import com.pokemon.carlostorres.pokemon.model.PokemonList;
import com.pokemon.carlostorres.pokemon.model.TipoNotificacion;
import com.pokemon.carlostorres.pokemon.services.IPokemonService;
import com.pokemon.carlostorres.pokemon.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;

public class MainListActivity extends BaseActivity {

    @Inject
    IPokemonService pokemonService;

    PokemonList listaPokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        loadPokemons();
    }

    @Override
    protected void injectComponent(DiComponent diComponent) {
        diComponent.inject(this);
    }

    private void loadPokemons() {
        new CargaInicialAsyncTask().execute();
    }

    /**
     * Esta clase realiza la carga inicial de manera asíncrona
     */
    private class CargaInicialAsyncTask extends ProgressAsyncTask<Void, Void, Void> {

        /**
         * Constructor para el progress async task
         */
        public CargaInicialAsyncTask() {
            super(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listaPokemons = pokemonService.getPokemonsList(0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (listaPokemons == null) {
                AppUtils.crearToast(MainListActivity.this, "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                        TipoNotificacion.ALERTA).show();
                super.onPostExecute(aVoid);
                return;
            }
            Intent intent = new Intent(getActivity(), PokemonDetailActivity.class);
            startActivity(intent);

            super.onPostExecute(aVoid);
        }
    }
}
