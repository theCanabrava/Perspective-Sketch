package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

class SpaceFactory
{
    private static SpaceToolset currentToolset;
    private static final List<PerspectivePoint> perspectivePoints = new ArrayList<>();
    private static HorizonLine horizonLine;
    private static Canvas canvas;

    public static SpaceToolset getToolset()
    {
        if(currentToolset == null) generateSpace();
        return currentToolset;
    };

    public static List<PerspectivePoint> getPerspectivePoints()
    {
        if(currentToolset == null) generateSpace();
        return perspectivePoints;
    }

    public static HorizonLine getHorizonLine()
    {
        if(currentToolset == null) generateSpace();
        return horizonLine;
    }

    public static Canvas getCanvas()
    {
        if(currentToolset == null) generateSpace();
        return canvas;
    }

    static void clearToolset() { currentToolset = null; }

    private static void generateSpace()
    {
        generateToolset();
        generateObserverSight();
        generateCanvas();
    }

    private static void generateToolset()
    {
        Observer observer= new Observer(0,0,1);
        Grid grid = new Grid(0);
        ViewPlane viewPlane = new ViewPlane();
        currentToolset = new SpaceToolset(observer, grid, viewPlane);
    }

    private static void generateObserverSight()
    {
        perspectivePoints.clear();
        perspectivePoints.add(new PerspectivePoint(currentToolset, 0));
        perspectivePoints.add(new PerspectivePoint(currentToolset, 90));
        horizonLine = new HorizonLine(currentToolset);
    }

    private static void generateCanvas()
    {
        canvas = new Canvas(currentToolset.getViewPlane(), horizonLine, 1);
        canvas.addPoint(new VanishingPoint(perspectivePoints.get(0), horizonLine));
        canvas.addPoint(new VanishingPoint(perspectivePoints.get(1), horizonLine));
    }
}
