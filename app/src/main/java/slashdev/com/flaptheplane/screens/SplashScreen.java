package slashdev.com.flaptheplane.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import slashdev.com.flaptheplane.MainGame;
import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.tweenaccessors.SpriteAccessor;

public class SplashScreen
        implements Screen
{
    private SpriteBatch batcher;
    private MainGame game;
    private TweenManager manager;
    private Sprite sprite;
    private float screenWidth;
    private float screenHeight;

    public SplashScreen(MainGame paramGame)
    {
        this.game = paramGame;
    }

    private void setupTween()
    {
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        this.manager = new TweenManager();
        TweenCallback local1 = new TweenCallback()
        {
            public void onEvent(int paramAnonymousInt, BaseTween<?> paramAnonymousBaseTween)
            {
                SplashScreen.this.game.setScreen(SplashScreen.this.game.gameScreen);
            }
        };
        ((Tween)((Tween)((Tween) Tween.to(this.sprite, 1, 0.8F).target(1.0F).ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4F)).setCallback(local1)).setCallbackTriggers(8)).start(this.manager);
    }

    public void dispose() {}

    public void hide() {}

    public void pause() {}

    public void render(float paramFloat)
    {
        this.manager.update(paramFloat);
        Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batcher.begin();
        this.batcher.draw(this.sprite, screenWidth / 2.0F - this.sprite.getWidth() / 2.0F, screenHeight / 2.0F - this.sprite.getHeight() / 2.0F);
//        this.sprite.draw(this.batcher);
        this.batcher.end();
    }

    public void resize(int paramInt1, int paramInt2) {}

    public void resume() {}

    public void show()
    {
        this.sprite = new Sprite(AssetLoader.logo);
        this.sprite.setColor(1.0F, 1.0F, 1.0F, 0.0F);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        float f3 = screenWidth * 0.7F / this.sprite.getWidth();
        this.sprite.setSize(f3 * this.sprite.getWidth(), f3 * this.sprite.getHeight());
        this.sprite.setPosition(screenWidth / 2.0F - this.sprite.getWidth() / 2.0F, screenHeight / 2.0F - this.sprite.getHeight() / 2.0F);
        setupTween();
        this.batcher = new SpriteBatch();
    }
}

