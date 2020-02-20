package com.github.jscancella.buttons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.jscancella.domain.Bag;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OpenBagButton extends Button{

  public OpenBagButton(Bag bag, TreeView<Path> dataFiles, TreeView<Path> tagFiles, TableView<String> metadataTable, final Stage stage) {
    super("Open Bag");
    
    this.setOnAction(action -> {
      final File bagitRootDir = new DirectoryChooser().showDialog(stage);
      if(bagitRootDir != null) {
        try{
//          bag = 
              Bag.read(bagitRootDir.toPath());
          //TODO need to add bag stuff to rest of scene
          dataFiles.setRoot(new TreeItem<Path>(bag.getDataDir()));
          dataFiles.getRoot().setExpanded(true);
          
          tagFiles.setRoot(new TreeItem<Path>(bag.getRootDir()));
          tagFiles.getRoot().getChildren().filtered(item -> bag.getDataDir().equals(item.getValue())); //remove the data dir from the treeview
          
          //TODO convert to correct types
          final List<String> flattenList = bag.getMetadata().getList().stream()
            .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
          metadataTable.setItems(FXCollections.observableArrayList(flattenList));
        } catch(IOException e){
          //TODO tell user about error
        }
      }
    });
  }
}
