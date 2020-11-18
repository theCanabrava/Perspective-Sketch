package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

public class Canvas implements PositionListener, WindowListener
{
    private final float height;
    private final ViewPlane plane;
    private final HorizonLine horizon;
    private final List<VanishingPoint> vanishingPoints = new ArrayList<>();
    private final List<PerspectiveLine> perspectiveLines = new ArrayList<>();
    private final List<CanvasListener> listeners = new ArrayList<>();

    Canvas(ViewPlane viewPlane, HorizonLine line, float height)
    {
        plane = viewPlane;
        horizon = line;
        this.height = height;
        plane.subscribe(this);
    }

    public void subscribeListener(CanvasListener listener)
    {
        listeners.add(listener);
    }

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

    public List<PerspectiveLine> getPerspectiveLines() { return perspectiveLines; }

    private void calculatePerspectiveLines()
    {
        perspectiveLines.clear();
        for(VanishingPoint point: vanishingPoints)
        {
            if(isPointInsideCanvas(point)) addLinesFromInside(point);
            else addLinesFromOutside(point);
        }
    }

    private boolean isPointInsideCanvas(VanishingPoint point)
    {
        if(Math.abs(point.getPosition()[0] - plane.getWindow()[0]) > plane.getWindow()[1]) return false;
        else if (Math.abs(point.getPosition()[1]) > height) return false;
        else return true;
    }

    private void addLinesFromInside(VanishingPoint point)
    {
        float angleStep = (float) (Math.PI/point.getSections());
        float angle = angleStep * point.getPhase();

        for(int i = 0; i<point.getSections(); i++)
        {
            float[] origin = new float[] {point.getPosition()[0], point.getPosition()[1]};
            float[] direction = new float[] {(float) Math.cos(angle), (float) Math.sin(angle)};
            angle += angleStep;
            perspectiveLines.add(new PerspectiveLine(origin, direction));
        }
    }

    private void addLinesFromOutside(VanishingPoint vanishingPoint)
    {
        float[] cornerPoint = getStartingPoint(vanishingPoint);
        float angleStep = getPhi(vanishingPoint, cornerPoint);
        for(int i = 0; i<vanishingPoint.getSections()+1; i++)
        {
            addLineBetween(vanishingPoint, cornerPoint);
            getNextCornerPoinr(vanishingPoint, cornerPoint, angleStep);
        }
    }

    private float[] getStartingPoint(VanishingPoint point)
    {
        int directionFromOrigin = 1;
        if(point.getPosition()[0] < plane.getWindow()[0]) directionFromOrigin = -1;
        float x = plane.getWindow()[0] + plane.getWindow()[1]*directionFromOrigin;
        return new float[] {x, height};
    }

    private float getPhi(VanishingPoint point, float[] topPoint)
    {
        float[] bottomPoint = {topPoint[0], -height};
        float topAngle = angleBetween(topPoint, point.getPosition());
        float bottomAngle = angleBetween(bottomPoint, point.getPosition());
        return (bottomAngle-topAngle)/point.getSections();
    }

    private float angleBetween(float[] pointA, float[] pointB)
    {
        return (float) Math.atan((pointA[1] - pointB[1])/(pointA[0] - pointB[0]));
    }

    private void addLineBetween(VanishingPoint point, float[] pageCorner)
    {
        int d = point.getPosition()[0] < pageCorner[0] ? 1:-1;
        float theta = angleBetween(pageCorner, point.getPosition());
        float[] origin = new float[] {pageCorner[0], pageCorner[1]};
        float[] direction = new float[] {(float) (d*Math.cos(theta)), (float) (d*Math.sin(theta))};
        perspectiveLines.add(new PerspectiveLine(origin, direction));
    }

    private void getNextCornerPoinr(VanishingPoint point, float[] pageCorner, float angleStep)
    {
        float theta = angleBetween(pageCorner, point.getPosition());
        float nextTheta = theta + angleStep;
        pageCorner[1] = (float) ((pageCorner[0] - point.getPosition()[0])*Math.tan(nextTheta)+point.getPosition()[1]);
    }

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
