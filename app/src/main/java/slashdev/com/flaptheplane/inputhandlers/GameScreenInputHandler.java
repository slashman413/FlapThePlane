package slashdev.com.flaptheplane.inputhandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.List;

import slashdev.com.flaptheplane.MainGame;
import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.gameobjects.Boss;
import slashdev.com.flaptheplane.gameworld.GameWorld;

public class GameScreenInputHandler extends Stage
{
    private MainGame game;
    private List<Button> menuButtons;
    private Boss myBoss;
    private GameWorld myWorld;
    private TextButton moreButton;
    private TextButton settingsButton;
    private TextButton startButton;
    private float scaleFactorX;
    private float scaleFactorY;
    private int midPointY;

    public GameScreenInputHandler(MainGame game, GameWorld paramGameWorld, float paramFloat1, float paramFloat2, int midY)
    {
        this.game = game;
        this.myWorld = paramGameWorld;
        this.myBoss = paramGameWorld.getBoss();
        int i = paramGameWorld.getMidPointY();
        this.scaleFactorX = paramFloat1;
        this.scaleFactorY = paramFloat2;
        this.midPointY = midY;
        this.menuButtons = new ArrayList();

        // more button
        this.moreButton = new TextButton("More", AssetLoader.skin);
        TextButton.TextButtonStyle style = moreButton.getStyle();
        style.checked = style.up;
        style.down = style.up;
        this.moreButton.setWidth(240.0F);
        this.moreButton.setHeight(80.0F);
        this.moreButton.setStyle(style);
        this.moreButton.setPosition((Gdx.graphics.getWidth() - moreButton.getWidth()) / 2,  Gdx.graphics.getHeight() / 2 - 100);
        this.moreButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (GameScreenInputHandler.this.myWorld.isMenu()) {
                    Gdx.net.openURI("https://play.google.com/store/apps/developer?id=SlashDev+Massor");
                }

                return false;
            }
        });
        this.menuButtons.add(this.moreButton);

        // settings button
        this.settingsButton = new TextButton("Settings", AssetLoader.skin);
        this.settingsButton.setWidth(240.0F);
        this.settingsButton.setHeight(80.0F);
        this.settingsButton.setStyle(style);
        this.settingsButton.setPosition((Gdx.graphics.getWidth() - settingsButton.getWidth()) / 2,  Gdx.graphics.getHeight() / 2 - 200);
        this.settingsButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (GameScreenInputHandler.this.myWorld.isMenu()) {
                    GameScreenInputHandler.this.game.setScreen(GameScreenInputHandler.this.game.settingsScreen);
                }

                return false;
            }
        });
        this.menuButtons.add(this.settingsButton);

        // start button
        this.startButton = new TextButton("Start", AssetLoader.skin);
        this.startButton.setWidth(240.0F);
        this.startButton.setHeight(80.0F);
        this.startButton.setStyle(style);
        this.startButton.setPosition((Gdx.graphics.getWidth() - startButton.getWidth()) / 2, Gdx.graphics.getHeight() / 2 - 300);
        this.startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (GameScreenInputHandler.this.myWorld.isMenu()) {
                    GameScreenInputHandler.this.myWorld.restart();
                }

                return false;
            }
        });
        this.menuButtons.add(this.startButton);

        addActor(moreButton);
        addActor(settingsButton);
        addActor(startButton);
    }

    private int scaleX(int paramInt)
    {
        return (int)(paramInt / this.scaleFactorX);
    }

    private int scaleY(int paramInt)
    {
        return (int)(paramInt / this.scaleFactorY);
    }

    public List<Button> getMenuButtons()
    {
        return this.menuButtons;
    }

    public boolean keyDown(int paramInt)
    {
        if (paramInt == Input.Keys.BACK)
        {
            if (this.myWorld.isReady() || this.myWorld.isGameOver() || this.myWorld.isHighScore())
            {
                this.myWorld.menu();
            }
            else if (this.myWorld.isMenu())
            {
                Gdx.app.exit();
            }
        }
        return super.keyDown(paramInt);
    }

    public boolean keyTyped(char paramChar)
    {
        return false;
    }

    public boolean keyUp(int paramInt)
    {
        return false;
    }

    public boolean mouseMoved(int paramInt1, int paramInt2)
    {
        return false;
    }

    public boolean scrolled(int paramInt)
    {
        return false;
    }

    public boolean touchDown(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        int i = scaleX(paramInt1);
        int j = scaleY(paramInt2);

        if (this.myWorld.isReady())
        {
            this.myWorld.start();
            this.myBoss.onClick();
        }
        else if ((this.myWorld.isGameOver()) || (this.myWorld.isHighScore()))
        {
            this.game.getActionResolver().submitScoreGPGS(this.myWorld.getScore());
            this.game.getActionResolver().getLeaderboardGPGS();
            this.myWorld.restart();
        }
        else if (this.myWorld.isRunning())
        {
            this.myBoss.onClick();
        }
        return super.touchDown(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public boolean touchDragged(int paramInt1, int paramInt2, int paramInt3)
    {
        return false;
    }
}
