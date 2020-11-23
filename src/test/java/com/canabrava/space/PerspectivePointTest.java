package com.canabrava.space;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PerspectivePointTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void shouldInstanceAtPositionZero()
    {
        SpaceFactory.clearToolset();
        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 90);
        float expectedPosition = 0;

        assertEquals(expectedPosition, point.getPosition(), DELTA);
    }

    @Test
    public void shouldInstanceAtRightPosition()
    {
        SpaceFactory.clearToolset();
        float expectedPosition = 1;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 1);
        SpaceFactory.getToolset().getGrid().setAngle(45);

        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);

        assertEquals(expectedPosition, point.getPosition(), DELTA);
    }

    @Test
    public void shouldInstanceAtOffsetPosition()
    {
        SpaceFactory.clearToolset();
        float expectedPosition = -1;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 1);
        SpaceFactory.getToolset().getGrid().setAngle(45);

        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 90);

        assertEquals(expectedPosition, point.getPosition(), DELTA);
    }

    @Test
    public void shouldUpdateWithObserver()
    {
        SpaceFactory.clearToolset();
        float expectedPosition = 2;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 1);
        SpaceFactory.getToolset().getGrid().setAngle(45);

        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);
        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 2);

        assertEquals(expectedPosition, point.getPosition(), DELTA);
    }

    @Test
    public void shouldTwistWithGrid()
    {
        SpaceFactory.clearToolset();
        float expectedPosition = (float) 0.5;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 1);
        SpaceFactory.getToolset().getGrid().setAngle(45);

        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);
        SpaceFactory.getToolset().getGrid().setAngle((float) 63.4349488);

        assertEquals(expectedPosition, point.getPosition(), DELTA);
    }

    @Test
    public void shouldTwistToOppositeEndWithGrid()
    {
        SpaceFactory.clearToolset();
        float expectedPosition = (float) -1;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 1);
        SpaceFactory.getToolset().getGrid().setAngle(45);

        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);
        SpaceFactory.getToolset().getGrid().setAngle((float) 135);

        assertEquals(expectedPosition, point.getPosition(), DELTA);
    }
}
