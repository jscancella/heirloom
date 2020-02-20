package com.github.jscancella.buttons;

import com.github.jscancella.conformance.BagLinter;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class CheckAgainstProfileButton extends Button{

  public CheckAgainstProfileButton(final CheckBox isBagProfileCompliantCheckbox) {
    super("Check Against Profile");
    
    this.setOnAction(action -> {
      //TODO get profile URL to check against
      try{
//        isBagProfileCompliantCheckbox.setSelected(BagLinter.checkAgainstProfile(jsonProfile, bag));
      }
      catch(Exception e) {
        //TODO tell user about error
        isBagProfileCompliantCheckbox.setSelected(false);
      }
    });
  }
}
