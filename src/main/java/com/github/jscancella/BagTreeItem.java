package com.github.jscancella;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.control.TreeItem;

public class BagTreeItem extends TreeItem<Path> {
  final private Path path;
  public BagTreeItem(final Path path) {
    super(path);
    this.path = path;
    this.setValue(path.getFileName());
    
    if(Files.isDirectory(path)) {
      try{
        Files.list(path).forEach(child -> this.getChildren().add(new BagTreeItem(child)));
      } catch(IOException e){
        // TODO Auto-generated catch block
      }
    }
  }
  
  public Path getFullPath(){
    return path;
  }
}
