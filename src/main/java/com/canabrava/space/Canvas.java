package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

public class Canvas implements PositionListener, WindowListener
{
    private final float height;
    private final ViewPlane plane;
    private final HorizonLine horizon;
    private final List<VanishingPoint> vanishingPoints = new ArrayList<>();
    private final List<CanvasListener> listeners = new ArrayList<>();
    private final PerspectiveLineRenderer renderer;

    Canvas(ViewPlane viewPlane, HorizonLine line, float height)
    {
        plane = viewPlane;
        horizon = line;
        this.height = height;
        renderer = new SameAngleLineRenderer(height);
        plane.subscribe(this);
    }

    public void subscribeListener(CanvasListener listener) { listeners.add(listener); }

    public void notifyListeners()
    {
        for(CanvasListener listener: listeners)
        {
            listener.onCanvasUpdated();
        }
    }

    public void addPoint(VanishingPoint point)
    {
        point.setCanvas(this);
        vanishingPoints.add(point);
        calculatePerspectiveLines();
    }

    public float[] getSize()
    {
        float width = plane.getWindow()[1];
        return new float[] {width, height};
    }

    public float getHorizonHeight()
    {
        return horizon.getHeight();
    }

    public List<PerspectiveLine> getPerspectiveLines() { return renderer.getLines(); }

    private void calculatePerspectiveLines() { renderer.render(vanishingPoints); }

    @Override
    public void onElementPositionChanged(float x, float y, float z) { updateCanvas(); }

    @Override
    public void onWindowPositionChanged(float center, float width) { updateCanvas(); }

    private void updateCanvas()
    {
        calculatePerspectiveLines();
        notifyListeners();
    }
}
