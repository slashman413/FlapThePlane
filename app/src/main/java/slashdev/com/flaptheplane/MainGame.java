package slashdev.com.flaptheplane;

import com.badlogic.gdx.Game;
import com.google.basegameutils.ActionResolver;

import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.screens.GameScreen;
import slashdev.com.flaptheplane.screens.SettingsScreen;
import slashdev.com.flaptheplane.screens.SplashScreen;


public class MainGame
        extends Game
{
    public GameScreen gameScreen;
    public SettingsScreen settingsScreen;

    private ActionResolver actionResolver;

    public MainGame(ActionResolver r) {
        this.actionResolver = r;
    }

    public void create()
    {
        AssetLoader.load();
        gameScreen = new GameScreen(this);
        settingsScreen = new SettingsScreen(this);
        setScreen(new SplashScreen(this));
    }

    public void dispose()
    {
        super.dispose();
        AssetLoader.dispose();
    }

    public ActionResolver getActionResolver() {
        return this.actionResolver;
    }
}