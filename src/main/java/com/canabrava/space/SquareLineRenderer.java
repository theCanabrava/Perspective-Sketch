package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

public class SquareLineRenderer implements PerspectiveLineRenderer
{
    private final List<PerspectiveLine> perspectiveLines = new ArrayList<>();
    private final float size;
    private final float[] origin;

    SquareLineRenderer(float size, float[] origin)
    {
        this.size = size;
        this.origin = origin;
    }

    @Override
    public List<PerspectiveLine> getLines() { return perspectiveLines; }

    @Override
    public void render(List<VanishingPoint> points)
    {
        perspectiveLines.clear();
        if(points.size()>1)
        {
            addLines(points.get(0), points.get(1));
            addLines(points.get(1), points.get(0));
        }
    }

    private void addLines(VanishingPoint from, VanishingPoint to)
    {
        for(int i=0; i < from.getSections(); i++)
        {
            float[] corner = calculateCorner(from, i);
            float projectionX = calculateProjection(from, corner);
            float[] lineOrigin = calculateLineOrigin(from, projectionX);
            float[] lineDirection = calculateLineDirection(to, lineOrigin);
            perspectiveLines.add(new PerspectiveLine(lineOrigin, lineDirection));
        }
    }

    private float[] calculateCorner(VanishingPoint from, int i)
    {
        float length = i*size + from.getPhase();
        float cornerX = (float) (origin[0] + length*Math.cos(Math.toRadians(from.getAngle())));
        float cornerY = (float) (from.getPosition()[1] + length*Math.sin(Math.toRadians(from.getAngle())));
        return new float[] {cornerX, cornerY};
    }

    private float calculateProjection(VanishingPoint from, float[] corner)
    {
        float[] functionParameters = calculateFunction(origin, corner);
        if(Float.isInfinite(functionParameters[0])) return origin[0];
        return (from.getPosition()[1]-functionParameters[1])/functionParameters[0];
    }

    private float[] calculateLineOrigin(VanishingPoint from, float projectionX)
    {
        float[] functionParameters = calculateFunction(origin, from.getPosition());
        float lineY = functionParameters[0]*projectionX + functionParameters[1];
        return new float[] {projectionX, lineY};
    }

    private float[] calculateLineDirection(VanishingPoint to, float[] linePoint)
    {
        float directionX = to.getPosition()[0] - linePoint[0];
        float directionY = to.getPosition()[1] - linePoint[1];
        return new float[] {directionX, directionY};
    }


    private float[] calculateFunction(float[] from, float[] to)
    {
        float m = ((from[1] - to[1])/(from[0] - to[0]));
        float n = from[1] - m*from[0];
        return new float[] {m, n};
    }
}
