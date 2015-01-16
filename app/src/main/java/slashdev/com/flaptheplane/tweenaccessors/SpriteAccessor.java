package slashdev.com.flaptheplane.tweenaccessors;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor
        implements TweenAccessor<Sprite>
{
    public static final int ALPHA = 1;

    public int getValues(Sprite paramSprite, int paramInt, float[] paramArrayOfFloat)
    {
        switch (paramInt)
        {
            default:
                return 0;
        }
//        paramArrayOfFloat[0] = paramSprite.getColor().a;
//        return 1;
    }

    public void setValues(Sprite paramSprite, int paramInt, float[] paramArrayOfFloat)
    {
        switch (paramInt)
        {
            default:
                return;
        }
//        paramSprite.setColor(1.0F, 1.0F, 1.0F, paramArrayOfFloat[0]);
    }
}

