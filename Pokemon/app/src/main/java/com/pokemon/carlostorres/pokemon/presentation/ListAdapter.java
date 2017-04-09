package com.pokemon.carlostorres.pokemon.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.model.PokemonInfo;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
import com.pokemon.carlostorres.pokemon.services.IPokemonService;
import com.pokemon.carlostorres.pokemon.utils.AnimationUtils;

import java.util.List;

import javax.inject.Inject;

import static com.pokemon.carlostorres.pokemon.presentation.PokemonDetailActivity.ID;

/**
 * Esta clase es utilizada para adaptar las visita de pokemons en un Recycler view
 *
 * @author <a href="mailto:carlos-torres@accionplus.com">Carlos Torres</a>
 */
public class ListAdapter extends RecyclerView.Adapter {
    /**
     * Contexto actual
     **/
    private Context mContext;

    /** ultima posicion del item **/
    private int lastPosition = -1;

    private LayoutInflater mInflater;

    @Inject
    IPokemonService pokemonService;

    private List<PokemonItem> pokemonItemList;

    /**
     * Constructor para el adaptador de informes devisitas
     *
     * @param context          Contexto actual
     * @param redditCategories Visitas
     */
    public ListAdapter(Context context, List<PokemonItem> redditCategories,
                       IPokemonService rappiTestService) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pokemonItemList = redditCategories;
        this.pokemonService = rappiTestService;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vistaCompetencia = mInflater.inflate(R.layout.pokemon_item, parent, false);
        return new ViewHolder(vistaCompetencia);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PokemonItem pokemon = pokemonItemList.get(position);
        final ViewHolder vh = (ViewHolder) holder;

        vh.titulo.setText(pokemon.getName());
        PokemonInfo info = pokemonService.getPokemonByName(pokemon.getName());
        if(pokemon.isCatched() && info != null){
            ImageLoader.getInstance().displayImage(info.getSprites().getFrontShiny(), vh.imagen);
            vh.cv.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            vh.imagen.setImageResource(R.drawable.siluet);
            vh.cv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        vh.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PokemonDetailActivity.class);
                intent.putExtra(ID, position + 1);
                AnimationUtils.configurarAnimacion(mContext, vh.cv, true, intent);
            }
        });

        setAnimation(vh.cv, position);

    }

    /**
     * Set the animation of the holder o the list
     *
     * @param viewToAnimate
     * @param position
     */
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.anim_scale_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return pokemonItemList.size();
    }

    /**
     * Set reddit list
     *
     * @param redditCategory
     */
    public void setPokemonItemList(List<PokemonItem> redditCategory) {
        this.pokemonItemList = redditCategory;
    }

    /**
     * Clase que contiene los elementos de la vista
     */
    private static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Nombre de la app
         **/
        public TextView titulo;
        /**
         * Descripcion de la app
         **/
        public TextView descripcion;
        /**
         * Imagen
         **/
        public ImageView imagen;
        /**
         * card view
         **/
        public CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.title);
            imagen = (ImageView) itemView.findViewById(R.id.photo);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}

