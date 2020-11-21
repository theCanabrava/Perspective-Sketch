package com.canabrava.ui.display;

import com.canabrava.space.PerspectiveLine;
import com.canabrava.ui.DisplayPresenter;
import com.canabrava.ui.DisplayViewContract;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class DisplayView extends Scene implements DisplayViewContract
{
    private double scale;
    private double centerX;
    private double centerY;
    private final PerspectiveRenderer renderer;
    private final HorizonSlider slider;
    private final Rectangle canvas;
    private DisplayPresenter presenter;

    public DisplayView(Group parent, double x, double y)
    {
        super(parent, x, y);
        calculateDimensions(x, y);
        canvas = instanceCanvas();
        slider = new HorizonSlider(parent, scale, centerY);
        slider.setListener(height -> presenter.onHorizonMoved((float) height));
        parent.getChildren().addAll(canvas, slider);
        renderer = new PerspectiveRenderer(parent, scale, centerX, centerY);
    }

    public void setPresenter(DisplayPresenter presenter)
    {
        this.presenter = presenter;
        this.presenter.setView(this);
    }

    private Rectangle instanceCanvas()
    {
        Rectangle canvas = new Rectangle();
        canvas.setX(centerX - scale);
        canvas.setY(centerY - scale);
        canvas.setWidth(2*scale);
        canvas.setHeight(2*scale);
        canvas.setFill(Color.TRANSPARENT);
        canvas.setStroke(Color.BLACK);
        return canvas;
    }

    private void calculateDimensions(double x, double y)
    {
        centerY = y/2;
        centerX = x/2;
        scale = Math.min(centerX, centerY) - 50;
    }

    @Override
    public void setHeight(float height) { slider.setHorizon(height); }

    @Override
    public void setCanvasSize(List<Float> window) { canvas.setWidth(window.get(0)*scale*2); }

    @Override
    public void updatePerspectiveLines(List<PerspectiveLine> lines) { renderer.updateLines(lines); }
}