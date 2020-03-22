package com.github.jscancella.buttons;

import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;

import com.github.jscancella.Main_Old;
import com.github.jscancella.trees.TreePathItem;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CreateNewBagButton extends Button{
  
  public CreateNewBagButton(final Main_Old main, final TreeView<TreePathItem> dataFiles, final TreeView<TreePathItem> tagFiles, final TableView<SimpleImmutableEntry<String, String>> metadataTable) {
    super("Create New Bag");
    
    this.setOnAction(action -> {
      main.resetBag();
      dataFiles.setRoot(new TreeItem<Path>());
      tagFiles.setRoot(new TreeItem<Path>());
      metadataTable.setItems(FXCollections.observableArrayList());
    });
  }
}
