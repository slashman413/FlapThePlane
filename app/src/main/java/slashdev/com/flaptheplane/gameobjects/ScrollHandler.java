package slashdev.com.flaptheplane.gameobjects;

import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.gameworld.GameWorld;

public class ScrollHandler
{
    public static final int PIPE_GAP = 49;
    public static final int SCROLL_SPEED = -59;
    private Grass backGrass;
    private Grass frontGrass;
    private GameWorld gameWorld;
    private Pipe pipe1;
    private Pipe pipe2;
    private Pipe pipe3;

    public ScrollHandler(GameWorld paramGameWorld, float paramFloat)
    {
        this.gameWorld = paramGameWorld;
        this.frontGrass = new Grass(0.0F, paramFloat, 143, 11, -59.0F);
        this.backGrass = new Grass(this.frontGrass.getTailX(), paramFloat, 143, 11, -59.0F);
        this.pipe1 = new Pipe(210.0F, 0.0F, 22, 60, -59.0F, paramFloat);
        this.pipe2 = new Pipe(49.0F + this.pipe1.getTailX(), 0.0F, 22, 70, -59.0F, paramFloat);
        this.pipe3 = new Pipe(49.0F + this.pipe2.getTailX(), 0.0F, 22, 60, -59.0F, paramFloat);
    }

    private void addScore(int paramInt)
    {
        this.gameWorld.addScore(paramInt);
    }

    public boolean collides(Boss paramBoss)
    {
        int i = 1;
        if ((!this.pipe1.isScored()) && (this.pipe1.getX() + this.pipe1.getWidth() / 2 < paramBoss.getX() + paramBoss.getWidth()))
        {
            addScore(i);
            this.pipe1.setScored(i>0?true:false);
            AssetLoader.playScoredSound();
        }
        else if ((!this.pipe2.isScored()) && (this.pipe2.getX() + this.pipe2.getWidth() / 2 < paramBoss.getX() + paramBoss.getWidth()))
        {
            addScore(i);
            this.pipe2.setScored(i>0?true:false);
            AssetLoader.playScoredSound();
        }
        else if ((!this.pipe3.isScored()) && (this.pipe3.getX() + this.pipe3.getWidth() / 2 < paramBoss.getX() + paramBoss.getWidth()))
        {
            addScore(i);
            this.pipe3.setScored(i>0?true:false);
            AssetLoader.playScoredSound();
        }
        if ((!this.pipe1.collides(paramBoss)) && (!this.pipe2.collides(paramBoss)) && (!this.pipe3.collides(paramBoss))) {
            return false;
        }

        return true;
    }

    public Grass getBackGrass()
    {
        return this.backGrass;
    }

    public Grass getFrontGrass()
    {
        return this.frontGrass;
    }

    public Pipe getPipe1()
    {
        return this.pipe1;
    }

    public Pipe getPipe2()
    {
        return this.pipe2;
    }

    public Pipe getPipe3()
    {
        return this.pipe3;
    }

    public void onRestart()
    {
        this.frontGrass.onRestart(0.0F, -59.0F);
        this.backGrass.onRestart(this.frontGrass.getTailX(), -59.0F);
        this.pipe1.onRestart(210.0F, -59.0F);
        this.pipe2.onRestart(49.0F + this.pipe1.getTailX(), -59.0F);
        this.pipe3.onRestart(49.0F + this.pipe2.getTailX(), -59.0F);
    }

    public void stop()
    {
        this.frontGrass.stop();
        this.backGrass.stop();
        this.pipe1.stop();
        this.pipe2.stop();
        this.pipe3.stop();
    }

    public void update(float paramFloat)
    {
        this.frontGrass.update(paramFloat);
        this.backGrass.update(paramFloat);
        this.pipe1.update(paramFloat);
        this.pipe2.update(paramFloat);
        this.pipe3.update(paramFloat);

        if (this.frontGrass.isScrolledLeft()) {
            this.frontGrass.reset(this.backGrass.getTailX());
        }
        if (this.backGrass.isScrolledLeft()) {
            this.backGrass.reset(this.frontGrass.getTailX());
        }
        if (this.pipe1.isScrolledLeft())
        {
            this.pipe1.reset(49.0F + this.pipe3.getTailX());
        }
        while (!this.backGrass.isScrolledLeft())
        {
            if (this.pipe2.isScrolledLeft())
            {
                this.pipe2.reset(49.0F + this.pipe1.getTailX());
                break;
            }
            if (!this.pipe3.isScrolledLeft()) {
                break;
            }
            this.pipe3.reset(49.0F + this.pipe2.getTailX());
            break;
        }
    }

    public void updateReady(float paramFloat)
    {
        this.frontGrass.update(paramFloat);
        this.backGrass.update(paramFloat);
        if (this.frontGrass.isScrolledLeft()) {
            this.frontGrass.reset(this.backGrass.getTailX());
        }
        if (this.backGrass.isScrolledLeft()) {
            this.backGrass.reset(this.frontGrass.getTailX());
        }
    }
}
