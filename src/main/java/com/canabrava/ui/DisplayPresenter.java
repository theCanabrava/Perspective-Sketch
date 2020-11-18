package com.canabrava.ui;

import com.canabrava.space.CanvasListener;
import com.canabrava.space.DisplayController;
import com.canabrava.space.PositionController;

public class DisplayPresenter implements CanvasListener
{
    PositionController positionController;
    DisplayController displayController;
    float horizonHeight = 0;

    DisplayViewContract view;

    public DisplayPresenter(PositionController positionController, DisplayController displayController)
    {
        this.positionController = positionController;
        this.displayController = displayController;
        positionController.setObserverHeight(horizonHeight);
        displayController.subscribeCanvasListener(this);
    }

    public void setView(DisplayViewContract view)
    {
        this.view = view;
        this.view.setHeight(horizonHeight);
        this.view.setCanvasSize(displayController.getCanvasSize());
        this.view.updatePerspectiveLines(displayController.getPerspectiveLines());
    }

    public void onHorizonMoved(float height)
    {
        horizonHeight = height;
        positionController.setObserverHeight(height);
    }

    @Override
    public void onCanvasUpdated()
    {
        if(view != null) view.updatePerspectiveLines(displayController.getPerspectiveLines());
    }
}
