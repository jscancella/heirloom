package com.github.jscancella;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * The purpose of this class is to setup the look and feel of the application.
 * It is also responsible for wiring up the logic and using the bagging library
 */
public class MainFrame extends JFrame {
  private static final long serialVersionUID = 1L;

  public MainFrame() {
    super("Heirloom");
    
    this.setExtendedState(JFrame.MAXIMIZED_BOTH); // open full sized
    this.setSize(800, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.add(new MainPane(), BorderLayout.CENTER);
    this.add(new ToolBars(), BorderLayout.NORTH);

    this.setVisible(true);
  }
}
