package com.github.jscancella;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class Main extends Application {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) throws Exception{
    URL fxmlUrl = Main.class.getResource("heirloomUI.fxml");
    
    Parent root = FXMLLoader.load(fxmlUrl);
    Scene scene = new Scene(root);
    
    stage.setTitle("Heirloom"); //TODO add in version?
    stage.setScene(scene);
    stage.show();
    
    //add "data" node to tree
    final Path data = Paths.get("C:\\Users\\John\\work\\personal\\bagging\\src\\test\\resources\\bags\\v0_97\\bag\\data");
    TreeView<Path> dataFilesTree = (TreeView<Path>) scene.lookup("#dataFilesTree");
    dataFilesTree.setRoot(new BagTreeItem(data));
  }
  
  public static void InformUserAboutError(final String title, final String content, final Exception e) {
    //TODO log to debug.log
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle(title);
    dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonData.CANCEL_CLOSE));
    dialog.setContentText(content);
    dialog.showAndWait();
  }
}
