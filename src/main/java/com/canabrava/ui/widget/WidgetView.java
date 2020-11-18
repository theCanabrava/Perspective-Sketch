package com.canabrava.ui.widget;
import com.canabrava.ui.WidgetPresenter;
import com.canabrava.ui.WidgetViewContract;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.List;

public class WidgetView extends Scene implements WidgetViewContract
{
    private WidgetPresenter presenter;
    private PerspectiveBox box;
    private PlaneLine line;
    private ObserverSlider slider;

    public WidgetView(Group parent, double width, double height)
    {
        super(parent, width, height);
        instanceBox(width, height);
        instanceLine(parent, width, height);
        instanceSlider(width, height);
        parent.getChildren().addAll(box, line, slider);
    }

    private void instanceBox(double width, double height)
    {
        box = new PerspectiveBox(30);
        box.setRotationListener(rotation -> presenter.onGridSpun((float) rotation));
        box.setX(width/2 - 15);
        box.setY((height-width)/2 - 15);
    }

    private void instanceLine(Group parent, double width, double height)
    {
        line = new PlaneLine(parent, 25);
        line.setStart(0, height-width);
        line.setEnd(width);
    }

    private void instanceSlider(double width, double height)
    {
        slider = new ObserverSlider(25);
        slider.setCenter(width/2, height-width);
        slider.setListener(position -> presenter.onObserverMoved((float) position));
    }

    public void setPresenter(WidgetPresenter presenter)
    {
        this.presenter = presenter;
        this.presenter.setView(this);
    }

    @Override
    public void setObserver(float position) { slider.setPosition(position); }

    @Override
    public void setGrid(float rotation) { box.setRotation(rotation); }

    @Override
    public void setPerspective(List<Float> points) { line.updatePoints(points); }
}
