package com.github.jscancella.buttons;

import java.io.IOException;

import com.github.jscancella.domain.Bag;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ValidateBagButton extends Button{
  private static final boolean IGNORE_HIDDEN_FILES = true;

  public ValidateBagButton(final Bag bag, final CheckBox isBagValidCheckbox, final CheckBox isBagCompleteCheckbox) {
    super("Validate Bag");
    
    this.setOnAction(action -> {
      boolean isValid;
      try{
        isValid = bag.isValid(IGNORE_HIDDEN_FILES);
        isBagValidCheckbox.setSelected(isValid);
         //TODO figure out isBagCompleteCheckbox
      } catch(IOException e1){
        //TODO tell user about error
      }
    });
  }
}
