package com.pokemon.carlostorres.pokemon.presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.clients.DiComponent;
import com.pokemon.carlostorres.pokemon.model.PokemonInfo;
import com.pokemon.carlostorres.pokemon.model.PokemonItem;
import com.pokemon.carlostorres.pokemon.model.TipoNotificacion;
import com.pokemon.carlostorres.pokemon.services.IPokemonService;
import com.pokemon.carlostorres.pokemon.utils.AppUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import butterknife.BindView;

public class PokemonDetailActivity extends BaseActivity implements SensorEventListener {

    @Inject
    IPokemonService pokemonService;

    @BindView(R.id.cv)
    CardView cardViewImage;

    @BindView(R.id.photo)
    ImageView imageViewPhoto;

    @BindView(R.id.title)
    TextView textViewTitle;

    @BindView(R.id.textView_id)
    TextView textViewId;

    @BindView(R.id.textView_gender)
    TextView textViewGender;

    @BindView(R.id.textView_type)
    TextView textViewType;

    @BindView(R.id.textView_weight)
    TextView textViewWeight;

    @BindView(R.id.textView_shake_it)
    TextView textViewShakeIt;

    private SensorManager sensorManager;
    private PokemonInfo pokemon;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        new CargaInicialAsyncTask().execute(id);
        textViewShakeIt.setText("Shake it for catch'em!");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        configureImageLoader();

        mp = MediaPlayer.create(this, R.raw.one_ring_from_sleigh_bell);
    }

    private void configureImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void injectComponent(DiComponent diComponent) {
        diComponent.inject(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot =
                (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        if (accelationSquareRoot > 6.0) {
            setChatchedPokemonView();
            mp.start();
            if (pokemonService.setCatchedPokemon(new PokemonItem(pokemon.getId(), pokemon.getName()))) {
                AppUtils.crearToast(PokemonDetailActivity.this, "Has atrapado a " + pokemon.getName(),
                        SuperToast.Duration.MEDIUM, TipoNotificacion.EXITOSA).show();
            }
        }
    }

    private void setChatchedPokemonView() {
        ImageLoader.getInstance().displayImage(pokemon.getSprites().getFrontShiny(), imageViewPhoto);
        imageViewPhoto.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textViewShakeIt.setText("GOTCHA!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

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
        }

        @Override
        protected Void doInBackground(Integer... params) {
            pokemon = pokemonService.getPokemon(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (pokemon == null) {
                AppUtils.crearToast(PokemonDetailActivity.this,
                        "Hubo un problema con la conexion a internet", SuperToast.Duration.MEDIUM,
                        TipoNotificacion.ALERTA).show();
                super.onPostExecute(aVoid);
                return;
            }
            textViewId.setText(getString(R.string.id, String.valueOf(pokemon.getId())));
            textViewTitle.setText(pokemon.getName().toUpperCase());
            textViewWeight.setText(getString(R.string.weight, String.valueOf(pokemon.getWeight())));
            textViewGender.setText(getString(R.string.heigh, String.valueOf(pokemon.getHeight())));
            textViewType.setText(getString(R.string.exp, String.valueOf(pokemon.getBaseExperience())));

            ImageLoader.getInstance().displayImage(pokemon.getSprites().getFrontDefault(), imageViewPhoto);

            PokemonItem item = pokemonService.getPokemonItem(pokemon.getName());
            if(item != null && item.isCatched()) {
                setChatchedPokemonView();
            }
            super.onPostExecute(aVoid);
        }
    }
}
