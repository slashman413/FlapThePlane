package slashdev.com.flaptheplane.android;

import slashdev.com.flaptheplane.R;
import slashdev.com.flaptheplane.gamehelpers.GamePreferences;
import slashdev.com.flaptheplane.screens.SplashScreen;
import slashdev.com.flaptheplane.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashScreenActivity extends AndroidApplication {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(null);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (GamePreferences.isTutorialShown()) {
                    Intent intent = new Intent(SplashScreenActivity.this, GameScreenActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    SplashScreenActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, TutorialActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    SplashScreenActivity.this.startActivity(intent);
                }
                handler.removeCallbacks(this);
                Gdx.app.exit();
            }
        }, AUTO_HIDE_DELAY_MILLIS);
    }
}
