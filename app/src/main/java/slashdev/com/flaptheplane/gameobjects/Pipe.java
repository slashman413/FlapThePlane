package slashdev.com.flaptheplane.gameobjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Pipe
        extends Scrollable
{
    public static final int SKULL_HEIGHT = 11;
    public static final int SKULL_WIDTH = 24;
    public static final int VERTICAL_GAP = 45;
    private Rectangle barDown = new Rectangle();
    private Rectangle barUp = new Rectangle();
    private float groundY;
    private boolean isScored = false;
    private Random r = new Random();
    private Rectangle skullDown = new Rectangle();
    private Rectangle skullUp = new Rectangle();

    public Pipe(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float paramFloat3, float paramFloat4)
    {
        super(paramFloat1, paramFloat2, paramInt1, paramInt2, paramFloat3);
        this.groundY = paramFloat4;
    }

    public boolean collides(Boss paramBoss)
    {
        return (this.position.x < paramBoss.getX() + paramBoss.getWidth()) && ((Intersector.overlaps(paramBoss.getBoundingCircle(), this.barUp)) || (Intersector.overlaps(paramBoss.getBoundingCircle(), this.barDown)) || (Intersector.overlaps(paramBoss.getBoundingCircle(), this.skullUp)) || (Intersector.overlaps(paramBoss.getBoundingCircle(), this.skullDown)));
    }

    public Rectangle getBarDown()
    {
        return this.barDown;
    }

    public Rectangle getBarUp()
    {
        return this.barUp;
    }

    public Rectangle getSkullDown()
    {
        return this.skullDown;
    }

    public Rectangle getSkullUp()
    {
        return this.skullUp;
    }

    public boolean isScored()
    {
        return this.isScored;
    }

    public void onRestart(float paramFloat1, float paramFloat2)
    {
        this.velocity.x = paramFloat2;
        reset(paramFloat1);
    }

    public void reset(float paramFloat)
    {
        super.reset(paramFloat);
        this.height = (15 + this.r.nextInt(90));
        this.isScored = false;
    }

    public void setScored(boolean paramBoolean)
    {
        this.isScored = paramBoolean;
    }

    public void update(float paramFloat)
    {
        super.update(paramFloat);
        this.barUp.set(this.position.x, this.position.y, this.width, this.height);
        this.barDown.set(this.position.x, 45.0F + (this.position.y + this.height), this.width, this.groundY - (45.0F + (this.position.y + this.height)));
        this.skullUp.set(this.position.x - (24 - this.width) / 2, this.position.y + this.height - 11.0F, 24.0F, 11.0F);
        this.skullDown.set(this.position.x - (24 - this.width) / 2, this.barDown.y, 24.0F, 11.0F);
    }
}
