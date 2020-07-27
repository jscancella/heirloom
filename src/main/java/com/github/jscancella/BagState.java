package com.github.jscancella;

public enum BagState {
  VALID_UNKNOWN("Valid? : UNKNOWN"),
  COMPLETE_UNKNOWN("Complete? : UNKNOWN"),
  PROFILE_COMPLIANT_UNKNOWN("Profile Compliant? : UNKNOWN"),
  VALID_YES("Valid? : YES"),
  COMPLETE_YES("Complete? : YES"),
  PROFILE_COMPLIANT_YES("Profile Compliant? : YES"),
  VALID_NO("Valid? : NO"),
  COMPLETE_NO("Complete? : NO"),
  PROFILE_COMPLIANT_NO("Profile Compliant? : NO")
  ;
  
  private final String label;
  
  private BagState(String label) {
    this.label = label;
  }
  
  public String getLabel() {
    return label;
  }
}
