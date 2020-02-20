package com.github.jscancella.buttons;

import java.io.IOException;
import java.nio.file.Path;

import com.github.jscancella.domain.BagBuilder;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class UpdateBagButton extends Button{

  public UpdateBagButton(TreeView<Path> dataFiles, TreeView<Path> tagFiles, TableView<String> metadataTable, final Stage stage) {
    super("Update Bag");
    
    this.setOnAction(action -> {
      final BagBuilder bb = new BagBuilder();
      
      //TODO get the save to location
//      bb.bagLocation(rootDir);
      
      //for each payload file
//      bb.addPayloadFile(payload, relative);
      
      //for each tag file that isn't a manifest
//      bb.addTagFile(tag);
      
      //for each metadata
//      bb.addMetadata(key, value);
      
      //for each manifest
//      bb.addAlgorithm(bagitAlgorithmName);
      
//      bb.fileEncoding(bag.getFileEncoding());
      
      //if there is a fetch file...
//      bb.addItemToFetch(fetchItem);
      
      //Main.bag = 
          try{
            bb.write();
          } catch(IOException e){
            // TODO let user know what went wrong
          }
    });
  }
}
