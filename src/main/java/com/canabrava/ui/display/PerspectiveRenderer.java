package com.canabrava.ui.display;

import com.canabrava.space.PerspectiveLine;
import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class PerspectiveRenderer
{
    private final Group parent;
    private final double scale;
    private final double centerX;
    private final double centerY;
    private final List<Line> lines = new ArrayList<>();

    public PerspectiveRenderer(Group parent, double scale, double centerX, double centerY)
    {
        this.parent = parent;
        this.scale = scale;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void updateLines(List<PerspectiveLine> vectors)
    {
        if(lines.size() < vectors.size()) addLines(vectors);
        else if (lines.size() > vectors.size()) removeLines(vectors);
        for(int i=0; i<vectors.size(); i++) updateLine(lines.get(i), vectors.get(i));
    }

    private void addLines(List<PerspectiveLine> vectors)
    {
        for(int i=lines.size(); i<vectors.size(); i++)
        {
            Line line = new Line();
            line.setStartX(0);
            line.setEndX(parent.getScene().getWidth());
            parent.getChildren().add(line);
            lines.add(line);
        }
    }

    private void removeLines(List<PerspectiveLine> vectors)
    {
        for(int i=lines.size()-1; i>=vectors.size(); i--)
        {
            parent.getChildren().remove(lines.get(i));
            lines.remove(i);
        }
    }

    private void updateLine(Line line, PerspectiveLine vector)
    {
        double scaledX = (vector.getOrigin()[0]*scale) + centerX;
        double scaledY = -(vector.getOrigin()[1]*scale) + centerY;
        double startY = (-scaledX)*(-vector.getDirection()[1]/vector.getDirection()[0]) + scaledY;
        double endY = (parent.getScene().getWidth()-scaledX)*(-vector.getDirection()[1]/vector.getDirection()[0]) + scaledY;
        line.setStartY(startY);
        line.setEndY(endY);
    }
}