package com.github.jscancella.trees;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.control.TreeItem;

public class TreePathItem extends TreeItem<Path> {
  public TreePathItem(final Path root) {
    super(root);
  }
  
  public TreePathItem() {
    super();
  }
  
  public void walk() {
    try{
      
      Files.list(this.getValue()).forEach(childPath -> {
          TreePathItem child = new TreePathItem(childPath);
          this.getChildren().add(child);
          child.walk();
        }        
      );
    } catch(IOException e){
      // TODO Auto-generated catch block
    }
  }
}
