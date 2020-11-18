package com.canabrava.ui;

import java.util.List;

public interface WidgetViewContract
{
    void setObserver(float position);
    void setGrid(float rotation);
    void setPerspective(List<Float> points);
}
