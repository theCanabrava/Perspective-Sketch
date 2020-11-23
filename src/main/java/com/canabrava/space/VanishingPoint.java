package com.canabrava.space;

public class VanishingPoint implements PositionListener
{
    private final PerspectivePoint origin;
    private final HorizonLine axis;
    private final int sections = 20;
    private final float phase = 0;
    private PositionListener canvas;

    VanishingPoint(PerspectivePoint point, HorizonLine line)
    {
        origin = point;
        axis = line;
        origin.setVanishingPoint(this);
    }

    public float[] getPosition()
    {
        float x = origin.getPosition();
        float y = axis.getHeight();
        return new float[] {x, y};
    }

    public int getSections() { return sections; }

    public float getPhase() { return phase; }

    public void setCanvas(PositionListener canvas)
    {
        this.canvas = canvas;
    }

    public float getAngle() { return origin.getAngle(); }

    @Override
    public void onElementPositionChanged(float x, float y, float z)
    {
        if(canvas != null) canvas.onElementPositionChanged(0, 0,0);
    }
}
