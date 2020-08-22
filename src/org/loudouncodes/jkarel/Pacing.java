package org.loudouncodes.jkarel;

import java.util.Scanner;


public enum Pacing {
  
  STEP {
    @Override
    void tick() {
      Scanner s = new Scanner(System.in);
      s.nextLine();
    }
  },
    
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
    
  LUDICRUS {
    @Override
    void tick() {
  		try {
  			Thread.sleep(50);
  		}
  		catch (InterruptedException e) {
  			e.printStackTrace();
  		}
    }
  };
  
  
  abstract void tick();
  
}