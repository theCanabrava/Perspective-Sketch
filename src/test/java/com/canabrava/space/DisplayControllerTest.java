package com.canabrava.space;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DisplayControllerTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void controllerShouldGetCanvasSize()
    {
        SpaceFactory.clearToolset();
        DisplayController controller = new DisplayController();
        float expectedDimension = 1;

        List<Float> canvasDimensions = controller.getCanvasSize();

        for(float dimension: canvasDimensions) Assert.assertEquals(expectedDimension, dimension, DELTA);
    }

    @Test
    public void controllerShouldGetHorizonHeight()
    {
        SpaceFactory.clearToolset();
        DisplayController controller = new DisplayController();
        float expectedHeight = 0;

        float height = controller.getHorizonLineHeight();

        Assert.assertEquals(expectedHeight, height, DELTA);
    }

    @Test
    public void controllerShouldGetPerspectiveLines()
    {
        SpaceFactory.clearToolset();
        DisplayController controller = new DisplayController();
        float expectedAmount = 40;

        List<PerspectiveLine> lines = controller.getPerspectiveLines();
        float amount = lines.size();

        Assert.assertEquals(expectedAmount, amount, DELTA);
    }

}
