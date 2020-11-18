package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

public class DisplayController
{
    Canvas canvas;

    public DisplayController()
    {
        canvas = SpaceFactory.getCanvas();
    }

    public void subscribeCanvasListener(CanvasListener listener) { canvas.subscribeListener(listener); }

    public List<Float> getCanvasSize()
    {
        List<Float> dimensions = new ArrayList<>();
        float[] rawDimensions = canvas.getSize();
        dimensions.add(rawDimensions[0]);
        dimensions.add(rawDimensions[1]);
        return dimensions;
    }

    public float getHorizonLineHeight() { return canvas.getHorizonHeight(); }

    public List<PerspectiveLine> getPerspectiveLines() { return canvas.getPerspectiveLines(); }

}
