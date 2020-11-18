package com.canabrava.ui;

import com.canabrava.space.DisplayController;
import com.canabrava.space.PerspectiveLine;
import com.canabrava.space.PositionController;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DisplayPresenterTest
{
    private final static double DELTA = 0.0001;
    private final PositionController positionController = new PositionController();
    private final DisplayController displayController = new DisplayController();
    private DisplayPresenter presenter;
    static class MockView implements DisplayViewContract
    {
        public float height = -1;
        public float canvasHeight = -1;
        public float canvasWidth = -1;
        public List<PerspectiveLine> lines;

        @Override
        public void setHeight(float height) { this.height = height; }

        @Override
        public void setCanvasSize(List<Float> window)
        {
            canvasWidth = window.get(0);
            canvasHeight = window.get(1);
        }

        @Override
        public void updatePerspectiveLines(List<PerspectiveLine> lines) { this.lines = lines; }
    }

    @Test
    public void presenterShouldSetView()
    {
        presenter = new DisplayPresenter(positionController, displayController);
        MockView view = new MockView();
        float expectedHeight = 0;

        presenter.setView(view);

        Assert.assertEquals(expectedHeight, view.height, DELTA);
    }

    @Test
    public void presenterShouldSetViewWindow()
    {
        presenter = new DisplayPresenter(positionController, displayController);
        MockView view = new MockView();
        float expectedSize = 1;

        presenter.setView(view);

        Assert.assertEquals(expectedSize, view.canvasHeight, DELTA);
        Assert.assertEquals(expectedSize, view.canvasWidth, DELTA);
    }

    @Test
    public void presenterShouldUpdateHorizonHeight()
    {

        presenter = new DisplayPresenter(positionController, displayController);
        MockView view = new MockView();
        float expectedHeight = 1;

        presenter.setView(view);
        presenter.onHorizonMoved(1);
        float actualHeight = displayController.getHorizonLineHeight();

        Assert.assertEquals(expectedHeight, actualHeight, DELTA);
    }

    @Test
    public void presenterShouldUpdateViewLines()
    {
        presenter = new DisplayPresenter(positionController, displayController);
        MockView view = new MockView();
        int expectedAmount = 22;

        presenter.setView(view);
        positionController.setObserverDistance(2);
        positionController.setGridRotation(45);

        Assert.assertEquals(expectedAmount, view.lines.size());
    }
}