package com.canabrava.space;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObserverTest
{

    private final static double DELTA = 0.0001;

    @Test
    public void shouldAttachItselfToSpace()
    {
        Space.getInstance().clear();
        Observer observer = new Observer(0, 0, 0);
        assertEquals(observer, Space.getInstance().getElementAt(0));
    }

    @Test
    public void shouldChangePositions()
    {
        Observer observer = new Observer(0, 0, 0);
        float expectedX = 1;
        float expectedY = 2;
        float expectedZ = 3;

        observer.setPosition(1,2,3);
        float[] observerPosition = observer.getPosition();
        float actualX = observerPosition[0];
        float actualY = observerPosition[1];
        float actualZ = observerPosition[2];

        assertEquals(expectedX, actualX, DELTA);
        assertEquals(expectedY, actualY, DELTA);
        assertEquals(expectedZ, actualZ, DELTA);
    }

    @Test
    public void shouldSubscribePositionListener()
    {
        Observer observer = new Observer(0,0,0);
        final float[] positionSum = {0};
        PositionListener listener = new PositionListener() {
            @Override
            public void onElementPositionChanged(float x, float y, float z) {
                positionSum[0] = x+y+z;
            }
        };
        float expectedSum = 6;

        observer.subscribe(listener);
        observer.setPosition(1,2,3);

        assertEquals(expectedSum, positionSum[0], DELTA);
    }
}
