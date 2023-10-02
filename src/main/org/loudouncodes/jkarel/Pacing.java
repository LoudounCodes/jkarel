package org.loudouncodes.jkarel;

import java.util.Scanner;

/**
 * An enumeration that represents the different speed settings
 * for an Arena.
 *
 * Passing on the these defined Pacings to Arena's setPace()
 * method will make the robots on the arena move at that pace.
 */
public enum Pacing {
  
  /**
   * When this pacing is used, the Arena will wait for you
   * to hit any key on your keyboard before letting the
   * Robots advance to their next move.
   *
   * WARNING: This may be incompatible with programs that
   * try to use other kinds of keyboard input, as it grabs
   * hold of the keyboard waiting for a keypress.
   */
  STEP {
    @Override
    void tick() {
      Scanner s = new Scanner(System.in);
      s.nextLine();
    }
  },
    
  /**
   * When this pacing is used, there will be a 600 millisecond
   * delay between each step of the robots in the Arena.
   */
  SLOW {
    @Override
    void tick() {
      try {
        Thread.sleep(600);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  },
 
  /**
   * When this pacing is used, there will be a 400 millisecond
   * delay between each step of the robots in the Arena.
   */   
  MEDIUM { 
    @Override
    void tick() {
      try {
        Thread.sleep(400);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }, 

  /**
   * When this pacing is used, there will be a 200 millisecond
   * delay between each step of the robots in the Arena.
   */     
  FAST {
    @Override
    void tick() {
      try {
        Thread.sleep(200);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }, 
 
  /**
   * When this pacing is used, there will be a 5 millisecond
   * delay between each step of the robots in the Arena.
   */   
  LUDICRUS {
    @Override
    void tick() {
      try {
        Thread.sleep(5);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  };
  
  
  abstract void tick();
  
}