package com.canabrava.space;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SpaceFactoryTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void shouldInstanceSpaceToolset()
    {
        SpaceFactory.clearToolset();
        SpaceToolset toolset = SpaceFactory.getToolset();
        assertNotNull(toolset);
    }

    @Test
    public void toolsetShouldHaveObserverAtZero()
    {
        SpaceFactory.clearToolset();
        Observer observer = SpaceFactory.getToolset().getObserver();
        float expectedX = 0;
        float expectedY = 0;
        float expectedZ = 1;

        float[] positionVector = observer.getPosition();
        float actualX = positionVector[0];
        float actualY = positionVector[1];
        float actualZ = positionVector[2];

        assertEquals(expectedX, actualX, DELTA);
        assertEquals(expectedY, actualY, DELTA);
        assertEquals(expectedZ, actualZ, DELTA);
    }

    @Test
    public void toolsetShouldHaveGridAtZero()
    {
        SpaceFactory.clearToolset();
        Grid grid = SpaceFactory.getToolset().getGrid();
        float expectedAngle = 0;

        float actualAngle = grid.getAngle();

        assertEquals(expectedAngle, actualAngle, DELTA);
    }

    @Test
    public void toolsetShouldHaveWindowWithDefaultCanvas()
    {
        SpaceFactory.clearToolset();
        ViewPlane plane = SpaceFactory.getToolset().getViewPlane();
        float expectedCenter = 0;
        float expectedWidth = 1;

        float[] canvasVector = plane.getWindow();
        float actualCenter = canvasVector[0];
        float actualWidth = canvasVector[1];

        assertEquals(expectedCenter, actualCenter, DELTA);
        assertEquals(expectedWidth, actualWidth, DELTA);
    }

    @Test
    public void shouldInstancePerspectivePoints()
    {
        SpaceFactory.clearToolset();
        List<PerspectivePoint> points = SpaceFactory.getPerspectivePoints();
        int expectedSize = 2;

        assertEquals(expectedSize, points.size());
    }

    @Test
    public void shouldInstanceHorizonLines()
    {
        SpaceFactory.clearToolset();
        HorizonLine line = SpaceFactory.getHorizonLine();
        float expectedHeight = 0;

        assertEquals(expectedHeight, line.getHeight(), DELTA);
    }

    @Test
    public void shouldInstanceCanvas()
    {
        SpaceFactory.clearToolset();
        Canvas canvas = SpaceFactory.getCanvas();
        float expectedHeight = 1;
        float expectedWidth = 1;
        int expectedLines = 40;

        assertEquals(expectedWidth, canvas.getSize()[0], DELTA);
        assertEquals(expectedHeight, canvas.getSize()[1], DELTA);
        assertEquals(expectedLines, canvas.getPerspectiveLines().size());
    }
}
