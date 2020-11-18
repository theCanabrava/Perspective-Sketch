package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

class ViewPlane implements SpaceElement
{
    @Override
    public void setPosition(float x, float y, float z) { }
    @Override
    public void setAngle(float degrees) { }

    private float windowCenter = 0;
    private float windowWidth = 1;
    private final List<WindowListener> canvasWindow = new ArrayList<>();

    ViewPlane() { Space.getInstance().attach(this); }

    public void setWindow(float center, float width)
    {
        windowCenter = center;
        windowWidth = width;
        for(WindowListener canvas: canvasWindow) canvas.onWindowPositionChanged(windowCenter, windowWidth);
    }

    public void subscribe(WindowListener canvas) { canvasWindow.add(canvas); }

    public float[] getWindow() { return new float[] {windowCenter, windowWidth}; }
}
