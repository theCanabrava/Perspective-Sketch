package com.canabrava.space;

public class SpaceToolset
{
    private final Space space = Space.getInstance();
    private final Observer observer;
    private final Grid grid;
    private final ViewPlane viewPlane;

    SpaceToolset(Observer observer, Grid grid, ViewPlane viewPlane)
    {
        this.observer = observer;
        this.grid = grid;
        this.viewPlane = viewPlane;
    }


    public Space getSpace() { return space; }

    public Observer getObserver() { return observer; }

    public Grid getGrid() { return grid; }

    public ViewPlane getViewPlane() { return viewPlane; }
}
