package com.canabrava.space;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewPlaneTest
{

    private final static double DELTA = 0.0001;

    @Test
    public void shouldAttachItselfToSpace()
    {
        Space.getInstance().clear();
        ViewPlane plane = new ViewPlane();
        assertEquals(plane, Space.getInstance().getElementAt(0));
    }

    @Test
    public void shouldChangeWindow()
    {
        ViewPlane plane = new ViewPlane();
        float expectedCenter = 5;
        float expectedWidth = 500;

        plane.setWindow(5,500);
        float[] planeWindow = plane.getWindow();
        float actualCenter = planeWindow[0];
        float actualWidth = planeWindow[1];

        assertEquals(expectedCenter, actualCenter, DELTA);
        assertEquals(expectedWidth, actualWidth, DELTA);
    }

    @Test
    public void shouldSubscribeCanvas()
    {
        ViewPlane plane = new ViewPlane();
        final float[] windowProduct = {0};
        WindowListener listener = new WindowListener() {
            @Override
            public void onWindowPositionChanged(float center, float width) {
                windowProduct[0] = center*width;
            }
        };
        float expectedProduct = 2500;

        plane.subscribe(listener);
        plane.setWindow(5,500);

        assertEquals(expectedProduct, windowProduct[0], DELTA);
    }
}
