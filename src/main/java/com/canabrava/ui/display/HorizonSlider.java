package com.canabrava.ui.display;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class HorizonSlider extends Circle
{
    public interface SliderListener { void onHorizonSlided(double position); }
    private SliderListener listener;
    private final Line horizon;
    private final double scale;
    private final double center;

    public HorizonSlider(Group parent, double scale, double center)
    {
        this.scale = scale;
        this.center = center;

        this.setCenterX(25);
        this.setRadius(5);

        horizon = new Line();
        horizon.setStartX(0);
        horizon.setEndX(parent.getScene().getWidth());
        drawElements(center);
        parent.getChildren().add(horizon);

        this.setOnMouseDragged(e ->
        {
            double height = setHeight(e.getSceneY());
            if(listener != null) listener.onHorizonSlided(height);
        });
    }

    public void setListener(SliderListener listener) { this.listener = listener; }

    public void setHorizon(double heigth)
    {
        double scaledHeight = (center - scale*heigth);
        drawElements(scaledHeight);
    }

    private double setHeight(double dragEnd)
    {
        if(dragEnd > center + scale) dragEnd = center + scale;
        else if (dragEnd < center - scale) dragEnd = center - scale;
        drawElements(dragEnd);
        return (center-dragEnd)/scale;
    }

    private void drawElements(double heigth)
    {
        horizon.setStartY(heigth);
        horizon.setEndY(heigth);
        this.setCenterY(heigth);
    }

}
