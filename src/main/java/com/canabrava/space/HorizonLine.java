package com.canabrava.space;

public class HorizonLine implements PositionListener, WindowListener
{
    private float height;
    private float center;

    HorizonLine(SpaceToolset toolset)
    {
        height = toolset.getObserver().getPosition()[2];
        center = toolset.getViewPlane().getWindow()[0];
        toolset.getObserver().subscribe(this);
        toolset.getViewPlane().subscribe(this);
    }

    @Override
    public void onElementPositionChanged(float x, float y, float z) { height = z; }

    public float getHeight() { return height; }

    @Override
    public void onWindowPositionChanged(float center, float width) { this.center = center; }

    public float getCenter() { return center; }
}
