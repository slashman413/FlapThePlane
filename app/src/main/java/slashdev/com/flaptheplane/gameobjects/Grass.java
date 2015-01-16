package slashdev.com.flaptheplane.gameobjects;

public class Grass
        extends Scrollable
{
    public Grass(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float paramFloat3)
    {
        super(paramFloat1, paramFloat2, paramInt1, paramInt2, paramFloat3);
    }

    public void onRestart(float paramFloat1, float paramFloat2)
    {
        this.position.x = paramFloat1;
        this.velocity.x = paramFloat2;
    }
}
