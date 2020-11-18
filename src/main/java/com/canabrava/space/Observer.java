package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

class Observer implements SpaceElement
{
    private float x;
    private float y;
    private float z;
    private final List<PositionListener> lineOfSight = new ArrayList<>();

    Observer(float x, float y, float z)
    {
        Space.getInstance().attach(this);
        setPosition(x, y, z);
    }

    @Override
    public void setPosition(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        for(PositionListener element: lineOfSight) element.onElementPositionChanged(this.x,this.y,this.z);
    }

    @Override
    public void setAngle(float degrees) { }

    public void subscribe(PositionListener sightElement)
    {
        lineOfSight.add(sightElement);
    }

    public float[] getPosition()
    {
        return new float[] {x, y, z};
    }
}
