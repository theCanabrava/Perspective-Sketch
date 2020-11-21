package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

class SameAngleLineRenderer implements PerspectiveLineRenderer
{
    private final List<PerspectiveLine> perspectiveLines = new ArrayList<>();
    private final float size;

    SameAngleLineRenderer(float size) { this.size = size; }

    @Override
    public List<PerspectiveLine> getLines() { return perspectiveLines; }

    @Override
    public void render(List<VanishingPoint> vanishingPoints)
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
        if(Math.abs(point.getPosition()[0]) > size) return false;
        else if (Math.abs(point.getPosition()[1]) > size) return false;
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
            getNextCornerPoint(vanishingPoint, cornerPoint, angleStep);
        }
    }

    private float[] getStartingPoint(VanishingPoint point)
    {
        int directionFromOrigin = 1;
        if(point.getPosition()[0] < 0) directionFromOrigin = -1;
        float x = size*directionFromOrigin;
        return new float[] {x, size};
    }

    private float getPhi(VanishingPoint point, float[] topPoint)
    {
        float[] bottomPoint = {topPoint[0], -size};
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

    private void getNextCornerPoint(VanishingPoint point, float[] pageCorner, float angleStep)
    {
        float theta = angleBetween(pageCorner, point.getPosition());
        float nextTheta = theta + angleStep;
        pageCorner[1] = (float) ((pageCorner[0] - point.getPosition()[0])*Math.tan(nextTheta)+point.getPosition()[1]);
    }
}
