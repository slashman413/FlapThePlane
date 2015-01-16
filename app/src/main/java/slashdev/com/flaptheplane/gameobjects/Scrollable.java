package slashdev.com.flaptheplane.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable
{
    protected int height;
    protected boolean isScrolledLeft;
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;

    public Scrollable(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float paramFloat3)
    {
        this.position = new Vector2(paramFloat1, paramFloat2);
        this.velocity = new Vector2(paramFloat3, 0.0F);
        this.width = paramInt1;
        this.height = paramInt2;
        this.isScrolledLeft = false;
    }

    public int getHeight()
    {
        return this.height;
    }

    public float getTailX()
    {
        return this.position.x + this.width;
    }

    public int getWidth()
    {
        return this.width;
    }

    public float getX()
    {
        return this.position.x;
    }

    public float getY()
    {
        return this.position.y;
    }

    public boolean isScrolledLeft()
    {
        return this.isScrolledLeft;
    }

    public void reset(float paramFloat)
    {
        this.position.x = paramFloat;
        this.isScrolledLeft = false;
    }

    public void stop()
    {
        this.velocity.x = 0.0F;
    }

    public void update(float paramFloat)
    {
        this.position.add(this.velocity.cpy().scl(paramFloat));
        if (this.position.x + this.width < 0.0F) {
            this.isScrolledLeft = true;
        }
    }
}
