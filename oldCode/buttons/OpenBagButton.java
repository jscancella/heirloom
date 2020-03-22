package com.github.jscancella.buttons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;

import com.github.jscancella.Main_Old;
import com.github.jscancella.domain.Bag;
import com.github.jscancella.trees.TreePathItem;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OpenBagButton extends Button{

  public OpenBagButton(final Main_Old main, TreeView<TreePathItem> dataFiles, TreeView<TreePathItem> tagFiles, TableView<SimpleImmutableEntry<String, String>> metadataTable, final Stage stage) {
    super("Open Bag");
    
    this.setOnAction(action -> {
      final File bagitRootDir = new DirectoryChooser().showDialog(stage);
      if(bagitRootDir != null) {
        try{
          final Bag bag = Bag.read(bagitRootDir.toPath());
          main.setBag(bag);
          //TODO need to add bag stuff to rest of scene
          dataFiles.setRoot(new TreeItem<Path>(bag.getDataDir()));
          dataFiles.getRoot().setExpanded(true);
          
          tagFiles.setRoot(new TreeItem<Path>(bag.getRootDir()));
          tagFiles.getRoot().getChildren().filtered(item -> bag.getDataDir().equals(item.getValue())); //remove the data dir from the treeview
          
          metadataTable.setItems(FXCollections.observableArrayList(bag.getMetadata().getList()));
        } catch(IOException e){
          main.InformUserAboutError(e);
        }
      }
    });
  }
}
