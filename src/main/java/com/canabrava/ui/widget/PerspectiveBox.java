package com.canabrava.ui.widget;
import javafx.scene.shape.Rectangle;

public class PerspectiveBox extends Rectangle
{
    public interface RotationListener { void onBoxRotated(double angle);}
    private RotationListener listener;
    private final double side ;

    public PerspectiveBox(int side)
    {
        this.side = side;
        setHeight(this.side);
        setWidth(this.side);

        this.setOnMouseDragged(e ->
        {
            double angle = getAngle(e.getSceneX(), e.getSceneY());
            this.setRotate(-angle);
            if(listener != null) listener.onBoxRotated(angle);
        });
    }

    public void setRotationListener(RotationListener listener) { this.listener = listener; }

    private double getAngle(double x, double y)
    {
        double centerX = this.getX() + side / 2;
        double centerY = this.getY() + side / 2;
        double deltaX = x - centerX;
        double deltaY = -y + centerY;
        if (deltaX < 0.01) deltaX = 0.01;
        else if (deltaY < 0.01) deltaY = 0.01;
        return Math.toDegrees(Math.atan(deltaY / deltaX));
    }

    public void setRotation(double degrees) { this.setRotate(-degrees); }
}
