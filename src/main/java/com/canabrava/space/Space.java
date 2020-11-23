package com.canabrava.space;

import java.util.ArrayList;
import java.util.List;

class Space
{
    private static Space shared;

    static Space getInstance()
    {
        if(shared == null) shared = new Space();
        return shared;
    }

    private final List<SpaceElement> elements = new ArrayList<>();

    public void attach(SpaceElement element) { elements.add(element); }
    public SpaceElement getElementAt(int position) { return elements.get(position); }
    public void clear() { elements.clear(); }
}
