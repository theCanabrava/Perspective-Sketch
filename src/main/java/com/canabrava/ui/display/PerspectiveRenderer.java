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
        double[] path = getPathParametersOfVector(vector);
        double[] letters = getQuadraticParameters(path);
        double[] roots = solveQuadratic(letters);
        double[] unscaledPoints = getLineEnds(path, roots);
        double[] scaledPoint = scaleLine(unscaledPoints);
        drawLine(line, scaledPoint);
    }

    private double[] getPathParametersOfVector(PerspectiveLine vector)
    {
        double xo = vector.getOrigin()[0];
        double yo = vector.getOrigin()[1];
        double xv = vector.getDirection()[0];
        double yv = vector.getDirection()[1];
        double r = 1.414;

        return new double[] {xo, yo, xv, yv, r};
    }

    private double[] getQuadraticParameters(double[] path)
    {
        double a = path[2]*path[2] + path[3]*path[3];
        double b = 2*path[0]*path[2] + 2*path[1]*path[3];
        double c = path[0]*path[0] + path[1]*path[1] - path[4]*path[4];
        return new double[] {a, b, c};
    }

    private double[] solveQuadratic(double[] letters)
    {
        double innerRoot = Math.sqrt(letters[1]*letters[1]-4*letters[0]*letters[2]);
        double root1 = (-letters[1] + innerRoot)/(2*letters[0]);
        double root2 = (-letters[1] - innerRoot)/(2*letters[0]);
        return new double[] {root1, root2};
    }

    private double[] getLineEnds(double[] path, double[] roots)
    {
        double unscaledStartX = path[0] + path[2]*roots[0];
        double unscaledEndX = path[0] + path[2]*roots[1];
        double unscaledStartY = path[1] + path[3]*roots[0];
        double unscaledEndY = path[1] + path[3]*roots[1];
        return new double[] {unscaledStartX, unscaledEndX, unscaledStartY, unscaledEndY};
    }

    private double[] scaleLine(double[] unscaledPoints)
    {
        double startX = (unscaledPoints[0]*scale) + centerX;
        double endX = (unscaledPoints[1]*scale) + centerX;
        double startY = -(unscaledPoints[2]*scale) + centerY;
        double endY = -(unscaledPoints[3]*scale) + centerY;
        return new double[] {startX, endX, startY, endY};
    }

    private void drawLine(Line line, double[] scaledPoint)
    {
        line.setStartX(scaledPoint[0]);
        line.setEndX(scaledPoint[1]);
        line.setStartY(scaledPoint[2]);
        line.setEndY(scaledPoint[3]);
    }
}