package com.github.jscancella;

import java.util.AbstractMap.SimpleImmutableEntry;

import org.junit.jupiter.api.Test;

public class FOO {

  @Test
  public void test() {
    System.err.println(new SimpleImmutableEntry<String, String>("foo", "bar"));
  }
}
