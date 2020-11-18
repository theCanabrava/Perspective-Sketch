package com.canabrava.space;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HorizonLineTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void shouldInstanceWithSameHeightAsObserver()
    {
        SpaceFactory.clearToolset();
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        float expectedHeight = SpaceFactory.getToolset().getObserver().getPosition()[0];

        assertEquals(expectedHeight, line.getHeight(), DELTA);
    }

    @Test
    public void shouldInstanceWithSameCenterAsPlane()
    {
        SpaceFactory.clearToolset();
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        float expectedCenter = SpaceFactory.getToolset().getViewPlane().getWindow()[0];

        assertEquals(expectedCenter, line.getCenter(), DELTA);
    }

    @Test
    public void shouldSyncHeightWithObserver()
    {
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        float expectedHeight = 100;

        SpaceFactory.getToolset().getObserver().setPosition(0, 0, 100);

        assertEquals(expectedHeight, line.getHeight(), DELTA);
    }

    @Test
    public void shouldSyncCenterWithViewPlane()
    {
        HorizonLine line = new HorizonLine(SpaceFactory.getToolset());
        float expectedCenter = 10;

        SpaceFactory.getToolset().getViewPlane().setWindow(10, 100);

        assertEquals(expectedCenter, line.getCenter(), DELTA);
    }

}
