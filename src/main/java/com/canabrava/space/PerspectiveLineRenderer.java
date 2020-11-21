package com.canabrava.space;

import java.util.List;

public interface PerspectiveLineRenderer
{
    public void render(List<VanishingPoint> points);
    public List<PerspectiveLine> getLines();
}
