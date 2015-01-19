package slashdev.com.flaptheplane.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.basegameutils.ActionResolver;
import com.google.basegameutils.GameHelper;

import slashdev.com.flaptheplane.MainGame;
import slashdev.com.flaptheplane.gamehelpers.GamePreferences;

public class GameScreenActivity extends AndroidApplication implements
        GameHelper.GameHelperListener, ActionResolver {

    private View gameView;
    private GameHelper gameHelper;
    private InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration localAndroidApplicationConfiguration = new AndroidApplicationConfiguration();
        localAndroidApplicationConfiguration.useGLSurfaceView20API18 = false;
        RelativeLayout localRelativeLayout = new RelativeLayout(this);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().clearFlags(2048);

        // Create game view
        View localView = initializeForView(new MainGame(this), localAndroidApplicationConfiguration);
        localRelativeLayout.addView(localView);
        gameView = localView;

        // Create Admob view
        AdView localAdView = new AdView(this);
        localAdView.setAdSize(AdSize.BANNER);
        localAdView.setBackgroundColor(0);
        localAdView.setAdUnitId("ca-app-pub-9136240319828955/1257947227");
        localAdView.loadAd(new AdRequest.Builder().build());

        // Create the interstitial.
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9136240319828955/3363475626");

        // Begin loading your interstitial.
        interstitialAd.loadAd(new AdRequest.Builder().build());

        RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
        localLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        localLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        localRelativeLayout.addView(localAdView, localLayoutParams);
        setContentView(localRelativeLayout);

        // Create the Google Api Client with access to Plus and Games
        if (gameHelper == null) {
            gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
        }
        gameHelper.setup(this);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        gameHelper.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null) {
            gameView.requestFocus();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        gameHelper.onActivityResult(requestCode, resultCode, intent);
    }

    public void displayInterstitialAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!interstitialAd.isLoaded()) {
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }
                interstitialAd.show();
            }
        });
    }

    @Override
    public boolean getSignedInGPGS() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void loginGPGS() {
        try {
            runOnUiThread(new Runnable(){
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void submitScoreGPGS(int score) {
        if (gameHelper.isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkI1c2-i6MKEAIQAQ", score);
        } else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {
        if (gameHelper.isSignedIn()) {
            Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
        } else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
    }

    @Override
    public void getLeaderboardGPGS() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkI1c2-i6MKEAIQAQ"), 100);
        } else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
    }

    @Override
    public void getAchievementsGPGS() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
        } else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
    }
}
