package com.canabrava.space;

public class VanishingPoint implements PositionListener
{
    private final PerspectivePoint origin;
    private final HorizonLine axis;
    private final int sections = 10;
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
        float z = axis.getHeight();
        return new float[] {x, z};
    }

    public int getSections() { return sections; }

    public float getPhase() { return phase; }

    public void setCanvas(PositionListener canvas)
    {
        this.canvas = canvas;
    }

    @Override
    public void onElementPositionChanged(float x, float y, float z)
    {
        if(canvas != null) canvas.onElementPositionChanged(0, 0,0);
    }
}
