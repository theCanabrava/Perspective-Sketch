package com.canabrava.ui.widget;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class PlaneLine extends Line
{
    private final Group parent;
    private final double scale;
    private double center;
    private final List<Circle> perspectivePoints = new ArrayList<>();

    public PlaneLine(Group parent, double scale)
    {
        this.scale = scale;
        this.parent = parent;
    }

    public void setStart(double x, double y)
    {
        setStartX(x);
        setStartY(y);
        setEndY(y);
        calculateCenter();
    }

    public void setEnd(double x)
    {
        setEndX(x);
        calculateCenter();
    }

    private void calculateCenter() { center = (getStartX() + getEndX())/2; }

    public void updatePoints(List<Float> points)
    {
        if(perspectivePoints.size() < points.size()) addPoints(points);
        else if (perspectivePoints.size() > points.size()) removePoints(points);
        for(int i=0; i<points.size(); i++) updatePoint(perspectivePoints.get(i), points.get(i));
    }

    private void addPoints(List<Float> points)
    {
        for(int i=perspectivePoints.size(); i<points.size(); i++)
        {
            Circle circle = new Circle();
            circle.setCenterY(this.getStartY());
            circle.setRadius(5);
            parent.getChildren().add(circle);
            perspectivePoints.add(circle);
        }
    }

    private void removePoints(List<Float> points)
    {
        for(int i=perspectivePoints.size()-1; i>=points.size(); i--)
        {
            parent.getChildren().remove(perspectivePoints.get(i));
            perspectivePoints.remove(i);
        }
    }

    private void updatePoint(Circle point, double position)
    {
        double scaledPosition = (position*scale) + center;
        point.setCenterX(scaledPosition);
    }
}
