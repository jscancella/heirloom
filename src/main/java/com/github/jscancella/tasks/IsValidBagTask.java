package com.github.jscancella.tasks;

import com.github.jscancella.domain.Bag;

import javafx.concurrent.Task;

public class IsValidBagTask extends Task<Boolean> {
  private final Bag bag;
  private final boolean ignoreHiddenFiles;
  
  public IsValidBagTask(final Bag bag, final boolean ignoreHiddenFiles){
    this.bag = bag;
    this.ignoreHiddenFiles = ignoreHiddenFiles;
  }

  @Override
  protected Boolean call() throws Exception{
    return bag.isValid(ignoreHiddenFiles);
  }

}
