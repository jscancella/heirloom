package com.github.jscancella.buttons;

import java.io.IOException;

import com.github.jscancella.Main_Old;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ValidateBagButton extends Button{
  private static final boolean IGNORE_HIDDEN_FILES = true;

  public ValidateBagButton(final Main_Old main, final CheckBox isBagValidCheckbox, final CheckBox isBagCompleteCheckbox) {
    super("Validate Bag");
    
    this.setOnAction(action -> {
      boolean isValid;
      try{
        //TODO show progress bar or something
        isValid = main.getBag().isValid(IGNORE_HIDDEN_FILES);
        isBagValidCheckbox.setSelected(isValid);
        if(isValid) {
          isBagCompleteCheckbox.setSelected(true);
        }
        else {
          //can be complete but not valid
          isBagCompleteCheckbox.setSelected(main.getBag().isComplete(IGNORE_HIDDEN_FILES));
        }
      } catch(IOException e){
        main.InformUserAboutError(e);
      }
    });
  }
}
