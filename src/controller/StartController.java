package controller;

import applications.SystemCopy;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;
import applications.*;
import model.Airspace;


import java.io.File;
import java.util.logging.Level;

public class StartController {

    public void startBtnClick(ActionEvent actionEvent) {


        Airspace airspace = new Airspace();
        Simulator simulator = new Simulator(airspace);
        simulator.start();
        Radar radar = new Radar(airspace);
        radar.start();
        SystemCopy sc = new SystemCopy();
        sc.start();

        try {
            Parent root = FXMLLoader.load(getClass().getResource(".." + File.separator + "view" + File.separator + "mainApplication.fxml"));
            Main.primaryStage1.setTitle("Air space");
            Main.primaryStage1.setScene(new Scene(root));
            Main.primaryStage1.show();
        } catch (Exception e) {
            LoggerService logger=LoggerService.getInstance();
            logger.log(Level.WARNING,e);
        }


    }
}
