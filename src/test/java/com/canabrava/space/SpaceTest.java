package com.canabrava.space;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest
{
    private final static double DELTA = 0.0001;
    @Test
    public void shouldInstance()
    {
        Space instance = Space.getInstance();
        assertNotNull(instance.getClass());
    }

    @Test
    public void shouldStoreElements()
    {
        final float[] sum = {0};
        SpaceElement element = new SpaceElement() {

            @Override
            public void setPosition(float x, float y, float z) {
                sum[0] = x+y+z;
            }

            @Override
            public void setAngle(float degrees) {

            }
        };
        float expectedSum = 6;

        Space.getInstance().clear();
        Space.getInstance().attach(element);
        Space.getInstance().getElementAt(0).setPosition(1,2,3);

        assertEquals(sum[0], expectedSum, DELTA);

    }
}
