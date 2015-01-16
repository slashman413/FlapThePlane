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
import com.google.android.gms.games.Games;
import com.google.basegameutils.ActionResolver;
import com.google.basegameutils.GameHelper;

import slashdev.com.flaptheplane.MainGame;

public class AndroidLauncher extends AndroidApplication implements
        GameHelper.GameHelperListener, ActionResolver {

    private GameHelper gameHelper;

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
        // Create Admob view
        AdView localAdView = new AdView(this);
        localAdView.setAdSize(AdSize.BANNER);
        localAdView.setBackgroundColor(0);
        localAdView.setAdUnitId("ca-app-pub-9136240319828955/6438353227");
        localAdView.loadAd(new AdRequest.Builder().build());

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
    protected void onResume() {


        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
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
        }
    }

    @Override
    public void submitScoreGPGS(int score) {
        Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkI1c2-i6MKEAIQAQ", score);
    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
    }

    @Override
    public void getLeaderboardGPGS() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkI1c2-i6MKEAIQAQ"), 100);
        }
        else if (!gameHelper.isConnecting()) {
            loginGPGS();
        }
    }

    @Override
    public void getAchievementsGPGS() {
        if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
        }
        else if (!gameHelper.isConnecting()) {
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
