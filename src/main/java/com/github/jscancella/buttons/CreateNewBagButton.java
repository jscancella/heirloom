package com.github.jscancella.buttons;

import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CreateNewBagButton extends Button{
  
  public CreateNewBagButton(TreeView<Path> dataFiles, TreeView<Path> tagFiles, TableView<String> metadataTable) {
    super("Create New Bag");
    
    this.setOnAction(action -> {
//      Main.bag = null; //TODO
      dataFiles.setRoot(new TreeItem<Path>());
      tagFiles.setRoot(new TreeItem<Path>());
      metadataTable.setItems(FXCollections.observableArrayList());
    });
  }
}
