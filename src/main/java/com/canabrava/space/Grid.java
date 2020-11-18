package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

class Grid implements SpaceElement
{
    private float angle;
    private final List<AngleListener> perspectivePoints = new ArrayList<>();

    Grid(float angle)
    {
        this.angle = angle;
        Space.getInstance().attach(this);
    }

    @Override
    public void setPosition(float x, float y, float z) { }

    @Override
    public void setAngle(float degrees)
    {
        this.angle = degrees;
        for(AngleListener point: perspectivePoints) point.onAnglePositionChanged(this.angle);
    }

    public void subscribe(AngleListener point) { perspectivePoints.add(point); }

    public float getAngle() { return this.angle; }
}
