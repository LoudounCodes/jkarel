package org.loudouncodes.jkarel.demo;

import org.loudouncodes.jkarel.*;

/**
 * main entry point for the 'hello world' Karel demo.
 * this should run if you run the jar file built with
 * the project's ant build system.
 *
 * This creates an Arena, makes it large enough to
 * display the message, sets teh speed to 'ludicrus',
 * instantiates an AlphaBot, and makes it say 'Hello World'.
 */
public class Hello {
  
  /**
    * Runs the demo program.
    *
    * @param args not used.
    */
  public static void main(String[] args) {
    Arena.openWorld("/org/loudouncodes/jkarel/demo/hello.map");
  Arena.getArenaFrame().setSize(1024, 250);
    Arena.setPace(Pacing.LUDICRUS);


  AlphaBot bot = new AlphaBot();
    bot.say("Hello World!"); 
  }

}