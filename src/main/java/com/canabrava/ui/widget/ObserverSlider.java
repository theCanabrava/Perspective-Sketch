package com.canabrava.ui.widget;

import javafx.scene.shape.Circle;

public class ObserverSlider extends Circle
{
    public interface SlideListener { void onObserverSlided(double distance);}
    private ObserverSlider.SlideListener listener;
    private double center;
    private final double scale; //Unidade: px/m

    public ObserverSlider(double scale)
    {
        this.scale = scale;
        this.center = this.getCenterY();
        this.setOnMouseDragged(e ->
        {
            double distance = setDistance(e.getSceneY());
            if(listener != null) listener.onObserverSlided(distance);
        });

        setRadius(5);
    }

    public void setCenter(double x, double y)
    {
        this.setCenterX(x);
        this.setCenterY(y);
        this.center = y;
    }

    public void setListener(SlideListener listener) { this.listener = listener; }

    private double setDistance(double dragEnd)
    {
        double pixelDistance = dragEnd - center;
        if(pixelDistance < 0) { pixelDistance = 0; }
        this.setCenterY(center + pixelDistance);
        return pixelDistance/scale;
    }

    public void setPosition(double y) { setCenterY(scale*y + center); }
}