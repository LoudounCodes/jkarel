package org.loudouncodes.jkarel.demo;

import org.loudouncodes.jkarel.*;

public class Hello {
  
  public static void main(String[] args) {
        
    Arena.openWorld("/org/loudouncodes/jkarel/demo/hello.map");
		Arena.getWorldFrame().setSize(1024, 250);
    
    Arena.setPace(Pacing.LUDICRUS);
    AlphaBot bot = new AlphaBot();
    bot.say("Hello World!"); 
  }
  
}