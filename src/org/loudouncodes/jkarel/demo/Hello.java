package org.loudouncodes.jkarel.demo;

import org.loudouncodes.jkarel.*;

public class Hello {
  
  public static void main(String[] args) {
    
    System.out.println("Hello World!");
    
    Arena.openWorld("/org/loudouncodes/jkarel/demo/hello.map");
		Arena.getWorldFrame().setSize(800, 230);
    
  }
  
}