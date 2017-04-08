package com.pokemon.carlostorres.pokemon.presentation;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.clients.DiComponent;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
import com.pokemon.carlostorres.pokemon.model.PokemonList;
import com.pokemon.carlostorres.pokemon.model.TipoNotificacion;
import com.pokemon.carlostorres.pokemon.services.IPokemonService;
import com.pokemon.carlostorres.pokemon.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PokemonListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final int PAGE_SIZE = 20;

    @Inject
    IPokemonService pokemonService;

    @BindView(R.id.recyclerView_pokemons)
    RecyclerView recyclerViewPokemons;

    @BindView(R.id.refresh_list)
    SwipeRefreshLayout swipeRefreshLayout;

    PokemonList listaPokemons;
    List<PokemonItem> pokeItems = new ArrayList<>();
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int orderType = 0;
    boolean mIsLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        loadPokemons();
        setTitle("Pokemon List");
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mLayoutManager = new LinearLayoutManager(this);
        }else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        AppUtils.initSwipeRefreshLayout(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerViewPokemons.setLayoutManager(mLayoutManager);
        recyclerViewPokemons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

                if (!mIsLoading) {
                    if ((mLayoutManager.getChildCount() + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        orderType += 20;
                        new CargaInicialAsyncTask().execute(orderType);
                    }
                }
            }
        });
    }

    @Override
    protected void injectComponent(DiComponent diComponent) {
        diComponent.inject(this);
    }

    private void loadPokemons() {
        new CargaInicialAsyncTask().execute(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            new CargaInicialAsyncTask().execute(orderType);
        }
        checkOnline();
    }

    @Override
    public void onRefresh() {
        loadPokemons();
        checkOnline();
    }

    /**
     * Esta clase realiza la carga inicial de manera asíncrona
     */
    private class CargaInicialAsyncTask extends ProgressAsyncTask<Integer, Void, Void> {

        /**
         * Constructor para el progress async task
         */
        public CargaInicialAsyncTask() {
            super(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mIsLoading = true;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            listaPokemons = pokemonService.getPokemonsList(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (listaPokemons == null) {
                AppUtils.crearToast(PokemonListActivity.this,
                        "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                        TipoNotificacion.ALERTA).show();
                super.onPostExecute(aVoid);
                return;
            }
            pokeItems.clear();
            pokeItems.addAll(listaPokemons.getResults());

            mAdapter = new ListAdapter(PokemonListActivity.this, pokeItems, pokemonService);
            recyclerViewPokemons.setAdapter(mAdapter);
            mIsLoading = false;
            mLayoutManager.scrollToPosition(orderType - 6);
//            orderType = pokeItems.size();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            super.onPostExecute(aVoid);
        }
    }
}
