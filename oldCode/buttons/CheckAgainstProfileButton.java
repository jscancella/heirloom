package com.github.jscancella.buttons;

import java.io.InputStream;

import com.github.jscancella.Main_Old;
import com.github.jscancella.conformance.BagLinter;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class CheckAgainstProfileButton extends Button{

  public CheckAgainstProfileButton(final Main_Old main, final CheckBox isBagProfileCompliantCheckbox) {
    super("Check Against Profile");
    
    this.setOnAction(action -> {
      try{
        final InputStream jsonProfile = null; //TODO get profile URL to check against
        isBagProfileCompliantCheckbox.setSelected(BagLinter.checkAgainstProfile(jsonProfile, main.getBag()));
      }
      catch(Exception e) {
        main.InformUserAboutError(e);
        isBagProfileCompliantCheckbox.setSelected(false);
      }
    });
  }
}
