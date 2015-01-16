package slashdev.com.flaptheplane.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import slashdev.com.flaptheplane.MainGame;
import slashdev.com.flaptheplane.inputhandlers.GameScreenInputHandler;
import slashdev.com.flaptheplane.gameworld.GameRenderer;
import slashdev.com.flaptheplane.gameworld.GameWorld;


public class GameScreen
        implements Screen
{
    private MainGame game;
    private GameRenderer renderer;
    private float runTime;
    private GameWorld world;
    private GameScreenInputHandler gameScreenInputHandler;

    public GameScreen(MainGame g)
    {
        this.game = g;
    }

    public void dispose() {}

    public void hide() {}

    public void pause() {}

    public void render(float paramFloat)
    {
        this.runTime = (paramFloat + this.runTime);
        this.world.update(paramFloat);
        if (this.world.isMenu()) {
            this.gameScreenInputHandler.act();
        }
        this.renderer.render(paramFloat, this.runTime);
    }

    public void resize(int paramInt1, int paramInt2) {}

    public void resume() {
        this.renderer.reloadAssets();
    }

    public void show() {
        float f1 = Gdx.graphics.getWidth();
        float f2 = Gdx.graphics.getHeight();
        float f3 = f2 / (f1 / 136.0F);
        int i = (int)(f3 / 2.0F);

        if (this.world == null) {
            this.world = new GameWorld(i);
        }
        if (this.gameScreenInputHandler == null) {
            this.gameScreenInputHandler = new GameScreenInputHandler(this.game, this.world, f1 / 136.0F, f2 / f3, i);
        }
        Gdx.input.setInputProcessor(this.gameScreenInputHandler);

        if (this.renderer == null) {
            this.renderer = new GameRenderer(this.world, (int)f3, i);
        }
        this.world.setRenderer(this.renderer);
    }
}

