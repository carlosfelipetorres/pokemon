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

import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
import com.pokemon.carlostorres.pokemon.services.IPokemonService;
import com.pokemon.carlostorres.pokemon.utils.AnimationUtils;

import java.util.List;

import javax.inject.Inject;

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

    /**
     * Inflater
     **/
    private LayoutInflater mInflater;

    /**
     * Reddit service
     **/
    @Inject
    IPokemonService pokemonService;

    /**
     * Las categorias a ser mostradas
     **/
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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vistaCompetencia = mInflater.inflate(R.layout.categories_row, parent, false);
        return new ViewHolder(vistaCompetencia);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PokemonItem category = pokemonItemList.get(position);
        final ViewHolder vh = (ViewHolder) holder;

        vh.titulo.setText(category.getName());
//        vh.descripcion.setText(category.getPublicDescription());
//        if ((category.getIconImg().isEmpty() || category.getIconImg() == null) && category.getHeaderImg() != null) {
//            Uri uri = Uri.parse(category.getHeaderImg());
//            ImageManageUtils.displayRoundImage(vh.imagen, uri.toString(), null);
//        } else {
//            Uri uri = Uri.parse(category.getIconImg());
//            ImageManageUtils.displayImage(vh.imagen, uri.toString(), null);
//        }
        vh.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PokemonItem rc = pokemonItemList.get(position);
//                pokemonService.saveCategory(rc);
                Intent intent = new Intent(mContext, PokemonDetailActivity.class);
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
            descripcion = (TextView) itemView.findViewById(R.id.description);
            imagen = (ImageView) itemView.findViewById(R.id.photo);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }
    }
}

