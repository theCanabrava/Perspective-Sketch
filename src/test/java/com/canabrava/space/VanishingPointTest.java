package com.canabrava.space;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VanishingPointTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void shouldInstanceAtOrigin()
    {
        SpaceFactory.clearToolset();
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 90);
        float expectedX = 0;
        float expectedZ = 0;

        VanishingPoint vanishingPoint = new VanishingPoint(point, line);
        float[] position = vanishingPoint.getPosition();
        float actualX = position[0];
        float actualZ = position[1];

        assertEquals(expectedX, actualX, DELTA);
        assertEquals(expectedZ, actualZ, DELTA);
    }

    @Test
    public void shouldUpdateHeight()
    {
        SpaceFactory.clearToolset();
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);
        VanishingPoint vanishingPoint = new VanishingPoint(point, line);
        float expectedX = 0;
        float expectedZ = 1;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 1);
        float[] position = vanishingPoint.getPosition();
        float actualX = position[0];
        float actualZ = position[1];

        assertEquals(expectedX, actualX, DELTA);
        assertEquals(expectedZ, actualZ, DELTA);
    }

    @Test
    public void shouldUpdateDistance()
    {
        SpaceFactory.clearToolset();
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);
        VanishingPoint vanishingPoint = new VanishingPoint(point, line);
        float expectedX = 1;
        float expectedZ = 0;

        SpaceFactory.getToolset().getObserver().setPosition(0, 1, 0);
        SpaceFactory.getToolset().getGrid().setAngle(45);
        float[] position = vanishingPoint.getPosition();
        float actualX = position[0];
        float actualZ = position[1];

        assertEquals(expectedX, actualX, DELTA);
        assertEquals(expectedZ, actualZ, DELTA);
    }
}
