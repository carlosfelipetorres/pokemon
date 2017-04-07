package com.pokemon.carlostorres.pokemon.presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.clients.DiComponent;
import com.pokemon.carlostorres.pokemon.model.PokemonList;
import com.pokemon.carlostorres.pokemon.model.TipoNotificacion;
import com.pokemon.carlostorres.pokemon.services.IPokemonService;
import com.pokemon.carlostorres.pokemon.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class PokemonListActivity extends BaseActivity {

    @Inject
    IPokemonService pokemonService;

    PokemonList listaPokemons;

    @BindView(R.id.recyclerView_pokemons)
    RecyclerView recyclerViewPokemons;

    /** Adapter **/
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        loadPokemons();
        setTitle("List");
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            recyclerViewPokemons.setLayoutManager(new LinearLayoutManager(this));
        }else {
            recyclerViewPokemons.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    @Override
    protected void injectComponent(DiComponent diComponent) {
        diComponent.inject(this);
    }

    private void loadPokemons() {
        new CargaInicialAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        checkOnline();
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
                AppUtils.crearToast(PokemonListActivity.this, "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                        TipoNotificacion.ALERTA).show();
                super.onPostExecute(aVoid);
                return;
            }
            mAdapter = new ListAdapter(PokemonListActivity.this, listaPokemons.getResults(), pokemonService);
            recyclerViewPokemons.setAdapter(mAdapter);
//            Intent intent = new Intent(getActivity(), PokemonDetailActivity.class);
//            startActivity(intent);

            super.onPostExecute(aVoid);
        }
    }
}
