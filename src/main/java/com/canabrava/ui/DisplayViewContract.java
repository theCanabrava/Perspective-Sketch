package com.canabrava.ui;

import com.canabrava.space.PerspectiveLine;

import java.util.List;

public interface DisplayViewContract
{
    void setHeight(float height);
    void setCanvasSize(List<Float> window);
    void updatePerspectiveLines(List<PerspectiveLine> lines);
}
