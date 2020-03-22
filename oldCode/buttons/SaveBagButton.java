package com.github.jscancella.buttons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;

import com.github.jscancella.Main_Old;
import com.github.jscancella.domain.BagBuilder;
import com.github.jscancella.trees.TreePathItem;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SaveBagButton extends Button{

  public SaveBagButton(final Main_Old main, final TreeView<TreePathItem> dataFiles, final TreeView<TreePathItem> tagFiles, final TableView<SimpleImmutableEntry<String, String>> metadataTable, final Stage stage) {
    super("Save Bag");
    
    this.setOnAction(action -> {
      final File bagitRootDir = new DirectoryChooser().showDialog(stage);
      final BagBuilder bb = new BagBuilder();
      
      //TODO
      bb.bagLocation(bagitRootDir.toPath());
      
      //for each payload file
//      bb.addPayloadFile(payload, relative);
      
      //for each tag file that isn't a manifest
//      bb.addTagFile(tag);
      
      //for each metadata
//      bb.addMetadata(key, value);
      
      //for each manifest
//      bb.addAlgorithm(bagitAlgorithmName);
      
//      bb.fileEncoding(bag.getFileEncoding());
      tagFiles.getChildrenUnmodifiable().stream().filter(node -> ((Path)node).getFileName().equals(null));
      
      //if there is a fetch file...
//      bb.addItemToFetch(fetchItem);
      
        try{
          main.setBag(bb.write());
        } catch(IOException e){
          main.InformUserAboutError(e);
        }
    });
  }
}
