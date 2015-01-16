package slashdev.com.flaptheplane.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import slashdev.com.flaptheplane.gamehelpers.AssetLoader;
import slashdev.com.flaptheplane.gamehelpers.GamePreferences;

public class Boss
{
    private Vector2 acceleration;
    private Circle boundingCircle;
    private float height;
    private boolean isAlive;
    private float originalY;
    private Vector2 position;
    private float rotation;
    private Vector2 velocity;
    private int width;

    public Boss(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
    {
        this.width = paramInt1;
        this.height = paramInt2;
        this.originalY = paramFloat2;
        this.position = new Vector2(paramFloat1, paramFloat2);
        this.velocity = new Vector2(0.0F, 0.0F);
        this.acceleration = new Vector2(0.0F, 460.0F);
        this.boundingCircle = new Circle();
        this.isAlive = true;
    }

    public void decelerate()
    {
        this.acceleration.y = 0.0F;
    }

    public void die()
    {
        this.isAlive = false;
        this.velocity.y = 0.0F;
    }

    public Circle getBoundingCircle()
    {
        return this.boundingCircle;
    }

    public float getHeight()
    {
        return this.height;
    }

    public float getRotation()
    {
        return this.rotation;
    }

    public float getWidth()
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

    public boolean isAlive()
    {
        return this.isAlive;
    }

    public boolean isFalling()
    {
        return this.velocity.y > 110.0F;
    }

    public void onClick()
    {
        if (this.isAlive)
        {
            AssetLoader.playFlapSound();
            this.velocity.y = -120.0F;
        }
    }

    public void onRestart(int paramInt)
    {
        this.rotation = 0.0F;
        this.position.y = paramInt;
        this.velocity.x = 0.0F;
        this.velocity.y = 0.0F;
        this.acceleration.x = 0.0F;
        this.acceleration.y = 460.0F;
        this.isAlive = true;
    }

    public boolean shouldntFlap()
    {
        return (this.velocity.y > 70.0F) || (!this.isAlive);
    }

    public void update(float paramFloat)
    {
        this.velocity.add(this.acceleration.cpy().scl(paramFloat));
        if (this.velocity.y > 200.0F) {
            this.velocity.y = 200.0F;
        }
        if (this.position.y < -13.0F)
        {
            this.position.y = -13.0F;
            this.velocity.y = 0.0F;
        }
        this.position.add(this.velocity.cpy().scl(paramFloat));
        this.boundingCircle.set(9.0F + this.position.x, 6.0F + this.position.y, 6.5F);
        if (this.velocity.y < 0.0F)
        {
            this.rotation -= 600.0F * paramFloat;
            if (this.rotation < -20.0F) {
                this.rotation = -20.0F;
            }
        }
        if ((isFalling()) || (!this.isAlive))
        {
            this.rotation += 480.0F * paramFloat;
            if (this.rotation > 90.0F) {
                this.rotation = 90.0F;
            }
        }
    }

    public void updateReady(float paramFloat)
    {
        this.position.y = (2.0F * (float)Math.sin(7.0F * paramFloat) + this.originalY);
    }
}
