package com.github.jscancella.buttons;

import java.nio.file.Path;

import com.github.jscancella.Main;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CreateNewBagButton extends Button{
  
  public CreateNewBagButton(final Main main, final TreeView<Path> dataFiles, final TreeView<Path> tagFiles, final TableView<String> metadataTable) {
    super("Create New Bag");
    
    this.setOnAction(action -> {
      main.resetBag();
      dataFiles.setRoot(new TreeItem<Path>());
      tagFiles.setRoot(new TreeItem<Path>());
      metadataTable.setItems(FXCollections.observableArrayList());
    });
  }
}
