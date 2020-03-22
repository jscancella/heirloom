package com.github.jscancella;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) throws Exception{
    URL fxmlUrl = Main.class.getResource("heirloomUI.fxml");
    
    Parent root = FXMLLoader.load(fxmlUrl);
    Scene scene = new Scene(root, 800, 480);
    
    stage.setTitle("Heirloom"); //TODO add in version?
    stage.setScene(scene);
    stage.show();
  }
}
