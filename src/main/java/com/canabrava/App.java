package com.canabrava;

import com.canabrava.space.DisplayController;
import com.canabrava.space.PositionController;
import com.canabrava.ui.DisplayPresenter;
import com.canabrava.ui.WidgetPresenter;
import com.canabrava.ui.display.DisplayView;
import com.canabrava.ui.widget.WidgetView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args ) { launch(args); }

    @Override
    public void start(Stage displayStage) throws Exception
    {
        PositionController positionController = new PositionController();
        WidgetPresenter widgetPresenter = new WidgetPresenter(positionController);
        WidgetView widgetView = new WidgetView(new Group(), 200, 322);
        widgetView.setPresenter(widgetPresenter);

        Stage widgetStage = new Stage();
        widgetStage.setTitle("Perspective widget");
        widgetStage.setScene(widgetView);

        DisplayPresenter displayPresenter = new DisplayPresenter(positionController, new DisplayController());
        DisplayView displayView = new DisplayView(new Group(), 1000, 618);
        displayView.setPresenter(displayPresenter);

        displayStage.setTitle("Perspective view");
        displayStage.setScene(displayView);
        displayStage.show();
        widgetStage.show();

        widgetStage.setX(100);
        widgetStage.setY(100);
    }
}
