package com.canabrava.space;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PositionControllerTest
{
    private final static double DELTA = 0.0001;

    @Test
    public void controllerRotatesGrid()
    {
        SpaceFactory.clearToolset();
        PositionController controller = new PositionController();
        float expectedDistance = 1;

        List<Float> positions = controller.setGridRotation(45);

        assertEquals(positions.get(0), expectedDistance, DELTA);
        assertEquals(positions.get(1), -expectedDistance, DELTA);
    }

    @Test
    public void controllerMovesObserver()
    {
        SpaceFactory.clearToolset();
        PositionController controller = new PositionController();
        float expectedDistance = 2;

        controller.setGridRotation(45);
        List<Float> positions = controller.setObserverDistance(2);

        assertEquals(positions.get(0), expectedDistance, DELTA);
        assertEquals(positions.get(1), -expectedDistance, DELTA);
    }

    @Test
    public void controllerLiftsObserver()
    {
        SpaceFactory.clearToolset();
        PositionController controller = new PositionController();
        HorizonLine line = SpaceFactory.getHorizonLine();
        float expectedHeight = 2;

        controller.setObserverHeight(2);

        assertEquals(line.getHeight(), expectedHeight, DELTA);
    }
}
