package slashdev.com.flaptheplane.tweenaccessors;

import aurelienribon.tweenengine.TweenAccessor;

public class ValueAccessor
        implements TweenAccessor<Value>
{
    public int getValues(Value paramValue, int paramInt, float[] paramArrayOfFloat)
    {
        paramArrayOfFloat[0] = paramValue.getValue();
        return 1;
    }

    public void setValues(Value paramValue, int paramInt, float[] paramArrayOfFloat)
    {
        paramValue.setValue(paramArrayOfFloat[0]);
    }
}

