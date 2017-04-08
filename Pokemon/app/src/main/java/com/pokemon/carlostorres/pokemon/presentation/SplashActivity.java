package com.pokemon.carlostorres.pokemon.presentation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.pokemon.carlostorres.pokemon.R;
import com.pokemon.carlostorres.pokemon.clients.DiComponent;
import com.pokemon.carlostorres.pokemon.utils.AnimationUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Main activity where splash is loaded
 *
 * @author <a href="mailto:carlosfelipetorres75@gmail.com">Carlos Torres</a>
 */
public class SplashActivity extends BaseActivity {

    /** Constant for animation delay **/
    private static final int ANIM_DELAY = 500;

    /** Constant for animations duration **/
    private static final int ANIM_DURATION = 1000;

    /** Delay for the splash view **/
    private static final int SPLASH_DELAY = 2500;

    /** Logo Image View **/
    @BindView(R.id.logo_iv)
    ImageView mLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    /**
     * Injection component. This should be done if there are fields to be injected
     *
     * @param diComponent
     *         Dependency injection
     */
    @Override
    protected void injectComponent(DiComponent diComponent) {
        diComponent.inject(this);
    }

    /**
     * Initializes the application
     */
    private void init() {
        mLogoIv.setAlpha(0.0f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateLogo();
            }
        }, ANIM_DELAY);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectActivity();
            }
        }, SPLASH_DELAY);
    }

    /**
     * This method redirects to the right Activity depending if the session is active or not
     */
    private void redirectActivity() {
        Intent intent = new Intent(this, PokemonListActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * This method animate the logo. Fades and resizes the Logo view
     */
    private void animateLogo() {
        ObjectAnimator scaleXAnimation = AnimationUtils
                .createObjectAnimator(mLogoIv, "scaleX", 0.0F, 1.0F, ANIM_DURATION);
        ObjectAnimator scaleYAnimation = AnimationUtils
                .createObjectAnimator(mLogoIv, "scaleY", 0.0F, 1.0F, ANIM_DURATION);
        ObjectAnimator alphaAnimation = AnimationUtils
                .createObjectAnimator(mLogoIv, "alpha", 0.0F, 1.0F, ANIM_DURATION);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.start();
    }

}

