package com.canabrava.space;

public class PerspectivePoint implements PositionListener, AngleListener
{
    private final SpaceToolset toolset;
    private float x;
    private final float angleOffset;
    private PositionListener vanishingPoint;

    PerspectivePoint(SpaceToolset toolset, float angleOffset)
    {
        this.angleOffset = angleOffset;
        this.toolset = toolset;
        toolset.getObserver().subscribe(this);
        toolset.getGrid().subscribe(this);
        calculatePosition();
    }

    private void calculatePosition()
    {
        double degree = 90 - (toolset.getGrid().getAngle() + angleOffset);
        double radians = Math.toRadians(degree);
        double tangent = Math.tan(radians);
        double distance = toolset.getObserver().getPosition()[2];
        x = (float) (distance*tangent);
        if(vanishingPoint != null) vanishingPoint.onElementPositionChanged(0, 0, 0);
    }

    @Override
    public void onAnglePositionChanged(float degree) { calculatePosition(); }

    @Override
    public void onElementPositionChanged(float x, float y, float z) { calculatePosition(); }

    float getPosition() { return x; }

    public void setVanishingPoint(PositionListener vanishingPoint) {
        this.vanishingPoint = vanishingPoint;
    }

    public float getAngle() { return (toolset.getGrid().getAngle() + angleOffset); }
}
