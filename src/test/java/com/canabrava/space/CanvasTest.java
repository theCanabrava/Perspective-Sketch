package com.canabrava.space;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CanvasTest
{
    private final static double DELTA = 0.0001;

    private VanishingPoint generatePoint(HorizonLine line)
    {
        PerspectivePoint point = new PerspectivePoint(SpaceFactory.getToolset(), 0);
        VanishingPoint vanishingPoint = new VanishingPoint(point, line);
        SpaceFactory.getToolset().getObserver().setPosition(0, 1, 0);
        SpaceFactory.getToolset().getGrid().setAngle(90);
        return vanishingPoint;
    }

    @Test
    public void shouldCalculateLinesAtCenter()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        VanishingPoint point = generatePoint(horizon);
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);
        float expectedX = 0;
        float expectedY = 0;

        canvas.addPoint(point);
        List<PerspectiveLine> lines = canvas.getPerspectiveLines();

        checkLinesAtPoint(lines, expectedX, expectedY);
    }

    @Test
    public void shouldSpinLines()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        VanishingPoint point = generatePoint(horizon);
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);
        float expectedX = (float) (Math.tan(Math.toRadians(10)));
        float expectedY = 0;

        canvas.addPoint(point);
        SpaceFactory.getToolset().getGrid().setAngle(80);
        List<PerspectiveLine> lines = canvas.getPerspectiveLines();

        checkLinesAtPoint(lines, expectedX, expectedY);
    }

    @Test
    public void shouldMoveLinesHeight()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        VanishingPoint point = generatePoint(horizon);
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);
        float expectedX = 0;
        float expectedY = (float) 0.5;

        canvas.addPoint(point);
        SpaceFactory.getToolset().getObserver().setPosition(0, 1, (float) 0.5);
        List<PerspectiveLine> lines = canvas.getPerspectiveLines();

        checkLinesAtPoint(lines, expectedX, expectedY);
    }

    public void checkLinesAtPoint(List<PerspectiveLine> lines, float expectedX, float expectedY)
    {
        for(int i = 0; i<lines.size(); i++)
        {
            float angle = (float) (i*Math.PI/lines.size());
            float actualX = lines.get(i).getOrigin()[0];
            float actualY = lines.get(i).getOrigin()[1];
            float directionX = lines.get(i).getDirection()[0];
            float directionY = lines.get(i).getDirection()[1];

            assertEquals(expectedX, actualX, DELTA);
            assertEquals(expectedY, actualY, DELTA);
            assertEquals(Math.cos(angle), directionX, DELTA);
            assertEquals(Math.sin(angle), directionY, DELTA);
        }
    }

    @Test
    public void shouldCalculateLinesLeftOfCanvas()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        VanishingPoint point = generatePoint(horizon);
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);

        canvas.addPoint(point);
        SpaceFactory.getToolset().getGrid().setAngle(150);
        List<PerspectiveLine> lines = canvas.getPerspectiveLines();

        checkInterception(lines, point);
    }


    @Test
    public void shouldCalculateLinesRightOfCanvas()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        VanishingPoint point = generatePoint(horizon);
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);

        canvas.addPoint(point);
        SpaceFactory.getToolset().getGrid().setAngle(75);
        List<PerspectiveLine> lines = canvas.getPerspectiveLines();

        checkInterception(lines, point);
    }

    @Test
    public void shouldCalculatePerpendicularLines()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        VanishingPoint point = generatePoint(horizon);
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);

        canvas.addPoint(point);
        SpaceFactory.getToolset().getGrid().setAngle(0);
        List<PerspectiveLine> lines = canvas.getPerspectiveLines();

        checkInterception(lines, point);
    }

    private void checkInterception(List<PerspectiveLine> lines, VanishingPoint point)
    {
        float originX = point.getPosition()[0];
        float originY = point.getPosition()[1];

        for(PerspectiveLine line: lines)
        {
            float cornerX = line.getOrigin()[0];
            float cornerY = line.getOrigin()[1];
            float m = (line.getDirection()[1]/line.getDirection()[0]);
            float n = cornerY - m*cornerX;
            float actualY = m*originX + n;
            assertEquals(originY, actualY, DELTA);
        }
    }

    @Test
    public void shouldNotifyCanvas()
    {
        SpaceFactory.clearToolset();
        HorizonLine horizon = new HorizonLine(SpaceFactory.getToolset());
        Canvas canvas = new Canvas(SpaceFactory.getToolset().getViewPlane(), horizon, 1);
        VanishingPoint point = generatePoint(horizon);
        final int[] timesNotified = {0};
        CanvasListener listener = new CanvasListener() {
            @Override
            public void onCanvasUpdated() {
                timesNotified[0]++;
            }
        };
        int expectedNotifications = 1;

        canvas.addPoint(point);
        canvas.subscribeListener(listener);
        SpaceFactory.getToolset().getGrid().setAngle(0);

        Assert.assertEquals(expectedNotifications, timesNotified[0]);
    }

}
