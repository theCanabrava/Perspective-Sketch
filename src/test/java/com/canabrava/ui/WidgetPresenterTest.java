package com.canabrava.ui;

import com.canabrava.space.PositionController;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WidgetPresenterTest
{
    private final static double DELTA = 0.0001;
    private final PositionController controller = new PositionController();
    private WidgetPresenter presenter;
    static class MockView implements WidgetViewContract
    {
        float position = -1;
        float rotation = -1;
        List<Float> points;

        @Override
        public void setObserver(float position) { this.position = position; }

        @Override
        public void setGrid(float rotation) { this.rotation = rotation; }

        @Override
        public void setPerspective(List<Float> points) { this.points = points; }
    }

    @Test
    public void presenterShouldSetView()
    {
        presenter = new WidgetPresenter(controller);
        MockView view = new MockView();
        float expectedPosition = 1;
        float expectedRotation = (float) 0.01;
        float expectedSize = 2;

        presenter.setView(view);

        Assert.assertEquals(expectedPosition, view.position, DELTA);
        Assert.assertEquals(expectedRotation, view.rotation, DELTA);
        Assert.assertEquals(expectedSize, view.points.size(), DELTA);
    }

    @Test
    public void shouldRotatePoints()
    {
        presenter = new WidgetPresenter(controller);
        MockView view = new MockView();
        presenter.setView(view);
        float expectedDistance = 1;

        presenter.onGridSpun(45);

        Assert.assertEquals(expectedDistance, view.points.get(0), DELTA);
        Assert.assertEquals(-expectedDistance, view.points.get(1), DELTA);
    }

    @Test
    public void shouldDistancePoints()
    {
        presenter = new WidgetPresenter(controller);
        MockView view = new MockView();
        presenter.setView(view);
        float expectedDistance = 2;

        presenter.onGridSpun(45);
        presenter.onObserverMoved(2);

        Assert.assertEquals(expectedDistance, view.points.get(0), DELTA);
        Assert.assertEquals(-expectedDistance, view.points.get(1), DELTA);
    }

}
