package com.canabrava.ui;

import com.canabrava.space.PositionController;

import java.util.List;

public class WidgetPresenter
{
    PositionController controller;
    private float observerDistance = 1;
    private float gridSpin = 0;
    private List<Float> perspectivePoints;
    private WidgetViewContract view;

    public WidgetPresenter(PositionController controller)
    {
        this.controller = controller;
        controller.setGridRotation(gridSpin);
        perspectivePoints = controller.setObserverDistance(observerDistance);
    }

    public void setView(WidgetViewContract widgetView)
    {
        view = widgetView;
        view.setObserver(observerDistance);
        view.setGrid(gridSpin);
        view.setPerspective(perspectivePoints);
    }

    public void onObserverMoved(float position)
    {
        observerDistance = position;
        perspectivePoints = controller.setObserverDistance(observerDistance);
        if(view != null) view.setPerspective(perspectivePoints);
    }

    public void onGridSpun(float degree)
    {
        gridSpin = degree;
        perspectivePoints = controller.setGridRotation(gridSpin);
        if(view != null) view.setPerspective(perspectivePoints);
    }
}
