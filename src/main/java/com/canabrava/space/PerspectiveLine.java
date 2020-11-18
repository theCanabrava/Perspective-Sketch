package com.canabrava.space;

public class PerspectiveLine
{
    float[] origin;
    float[] direction;

    PerspectiveLine(float[] o, float[] d)
    {
        origin = o;
        direction = d;
    }

    public float[] getOrigin() { return origin; }

    public float[] getDirection() { return direction; }
}
