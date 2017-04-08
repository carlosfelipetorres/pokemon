package com.pokemon.carlostorres.pokemon.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.clients.DiComponent;
import com.pokemon.carlostorres.pokemon.clients.PokemonApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This is the base activity which offers common configuration and methods for other activities
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * no internet
     **/
    @BindView(R.id.nointernet)
    @Nullable
    protected TextView mNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectComponent(((PokemonApplication) getApplication()).getComponent());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        checkOnline();
    }

    /**
     * Injection component. This should be done if there are fields to be injected
     *
     * @param diComponent Dependency injection
     */
    protected abstract void injectComponent(DiComponent diComponent);

    /**
     * This method gets the Activity (i.e. this)
     *
     * @return The current Activity
     */
    protected Activity getActivity() {
        return this;
    }

    /**
     * This method shows a dialog to the user confirming to exit the activity without saving the
     * progress
     *
     * @param context    Current context
     * @param titleRes   Title to be shown. Could be null
     * @param msgRes     Message to be shown. Could be null
     * @param confirmRes Confirmation string for confirmation button. Cannot be null
     */
    protected void confirmExit(Context context, @StringRes Integer titleRes,
                               @StringRes Integer msgRes, @StringRes int confirmRes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        if (titleRes != null) {
            builder.setTitle(titleRes);
        }
        if (msgRes != null) {
            builder.setMessage(msgRes);
        }
        builder.setCancelable(true).setNegativeButton(R.string.cancel, null)
                .setPositiveButton(confirmRes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Checks internet status
     *
     * @return is conected or not
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Check online
     */
    public void checkOnline() {
        mNoInternet.setVisibility(!isOnline() ? View.VISIBLE : View.GONE);
    }

}
