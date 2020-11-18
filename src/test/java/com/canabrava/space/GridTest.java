package com.canabrava.space;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GridTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void shouldAttachItselfToSpace()
    {
        Space.getInstance().clear();
        Grid grid = new Grid(0);
        assertEquals(grid, Space.getInstance().getElementAt(0));
    }

    @Test
    public void shouldChangeAngle()
    {
        Grid grid = new Grid(0);
        float expectedAngle = 90;

        grid.setAngle(90);
        float actualAngle = grid.getAngle();

        assertEquals(expectedAngle, actualAngle, DELTA);
    }

    @Test
    public void shouldSubscribePerspectivePoint()
    {
        Grid grid = new Grid(0);
        final float[] phasedAngle = {90};
        AngleListener listener = new AngleListener() {
            @Override
            public void onAnglePositionChanged(float degree) {
                phasedAngle[0] += degree;
            }
        };
        float expectedAngle = 180;

        grid.subscribe(listener);
        grid.setAngle(90);

        assertEquals(expectedAngle, phasedAngle[0], DELTA);
    }

    @Test
    public void shouldSubscribeMultiplePerspectivePoint()
    {
        Grid grid = new Grid(0);
        final float[] phasedAngle = {90, 90};
        AngleListener positiveListener = new AngleListener() {
            @Override
            public void onAnglePositionChanged(float degree) {
                phasedAngle[0] += degree;
            }
        };
        AngleListener negativeListener = new AngleListener() {
            @Override
            public void onAnglePositionChanged(float degree) {
                phasedAngle[1] -= degree;
            }
        };
        float expectedPositiveAngle = 180;
        float expectedNegativeAngle = 0;

        grid.subscribe(positiveListener);
        grid.subscribe(negativeListener);
        grid.setAngle(90);

        assertEquals(expectedPositiveAngle, phasedAngle[0], DELTA);
        assertEquals(expectedNegativeAngle, phasedAngle[1], DELTA);
    }
}
