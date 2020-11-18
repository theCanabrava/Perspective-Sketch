package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

public class PositionController
{
    private SpaceToolset toolset;
    private List<PerspectivePoint> points;

    public PositionController()
    {
        toolset = SpaceFactory.getToolset();
        points = SpaceFactory.getPerspectivePoints();
    }

    public List<Float> setObserverDistance(float distance)
    {
        float[] currentPosition = toolset.getObserver().getPosition();
        toolset.getObserver().setPosition(currentPosition[0], Math.abs(distance), currentPosition[2]);
        return getPointsPosition();
    }

    public void setObserverHeight(float height)
    {
        float[] currentPosition = toolset.getObserver().getPosition();
        toolset.getObserver().setPosition(currentPosition[0], currentPosition[1], height);
    }

    public List<Float> setGridRotation(float degree)
    {
        if(degree > 90) degree = 90;
        toolset.getGrid().setAngle(degree);
        return getPointsPosition();
    }

    private List<Float> getPointsPosition()
    {
        List<Float> pointPositions = new ArrayList<>();
        for(PerspectivePoint point: points)
        {
            pointPositions.add(point.getPosition());
        }
        return pointPositions;
    }
}
