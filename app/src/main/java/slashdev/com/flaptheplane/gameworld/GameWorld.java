package slashdev.com.flaptheplane.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.gameobjects.Boss;
import slashdev.com.flaptheplane.gameobjects.ScrollHandler;


public class GameWorld
{
    private Boss boss;
    private GameState currentState = GameState.MENU;
    private Rectangle ground;
    private int midPointY;
    private GameRenderer renderer;
    private float runTime = 0.0F;
    private int score = 0;
    private ScrollHandler scroller;

    public GameWorld(int paramInt)
    {
        this.midPointY = paramInt;
        this.boss = new Boss(33.0F, paramInt - 5, 17, 12);
        this.scroller = new ScrollHandler(this, paramInt + 66);
        this.ground = new Rectangle(0.0F, paramInt + 66, 137.0F, 11.0F);
    }

    private void updateReady(float paramFloat)
    {
        this.boss.updateReady(this.runTime);
        this.scroller.updateReady(paramFloat);
    }

    public void addScore(int paramInt)
    {
        this.score = (paramInt + this.score);
    }

    public Boss getBoss()
    {
        return this.boss;
    }

    public int getMidPointY()
    {
        return this.midPointY;
    }

    public int getScore()
    {
        return this.score;
    }

    public CharSequence getScoreCharSequence()
    {
        return Integer.toString(this.score);
    }

    public ScrollHandler getScroller()
    {
        return this.scroller;
    }

    public boolean isGameOver()
    {
        return this.currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore()
    {
        return this.currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu()
    {
        return this.currentState == GameState.MENU;
    }

    public boolean isReady()
    {
        return this.currentState == GameState.READY;
    }

    public boolean isRunning()
    {
        return this.currentState == GameState.RUNNING;
    }

    public void menu()
    {
        this.currentState = GameState.MENU;
        this.boss.onRestart(-5 + this.midPointY);
    }

    public void ready()
    {
        this.currentState = GameState.READY;
        this.renderer.prepareTransition(0, 0, 0, 1.0F);
    }

    public void restart()
    {
        this.score = 0;
        this.boss.onRestart(-5 + this.midPointY);
        this.scroller.onRestart();
        ready();
    }

    public void setRenderer(GameRenderer paramGameRenderer)
    {
        this.renderer = paramGameRenderer;
    }

    public void start()
    {
        this.currentState = GameState.RUNNING;
    }

    public void update(float paramFloat)
    {
        this.runTime = (paramFloat + this.runTime);
        switch (this.currentState)
        {
            case READY:
                updateReady(paramFloat);
                break;
            case RUNNING:
                updateRunning(paramFloat);
                break;
            case GAMEOVER:
            case HIGHSCORE:
                updateReady(paramFloat);
                break;
            default:
                break;
        }
    }

    public void updateRunning(float paramFloat)
    {
        if (paramFloat > 0.15F) {
            paramFloat = 0.15F;
        }
        this.boss.update(paramFloat);
        this.scroller.update(paramFloat);
        if ((this.scroller.collides(this.boss)) && (this.boss.isAlive()))
        {
            this.scroller.stop();
            this.boss.die();
            AssetLoader.deadSound.play();
            this.renderer.prepareTransition(255, 255, 255, 0.3F);
            AssetLoader.playFallSound();
        }
        if (Intersector.overlaps(this.boss.getBoundingCircle(), this.ground))
        {
            if (this.boss.isAlive())
            {
                AssetLoader.deadSound.play();
                this.renderer.prepareTransition(255, 255, 255, 0.3F);
                this.boss.die();
            }
            this.scroller.stop();
            this.boss.decelerate();
            this.currentState = GameState.GAMEOVER;
            if (this.score > AssetLoader.getHighScore())
            {
                AssetLoader.setHighScore(this.score);
                this.currentState = GameState.HIGHSCORE;
            }
        }
    }

    public static enum GameState
    {
        HIGHSCORE,  GAMEOVER,  RUNNING,  READY,  MENU;
    }
}
