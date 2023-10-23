require '../../out/jkarel-1.0.0.jar'

java_import 'org.loudouncodes.jkarel.Arena'
java_import 'org.loudouncodes.jkarel.Robot'
java_import 'org.loudouncodes.jkarel.Direction'
java_import 'org.loudouncodes.jkarel.BeeperStack'
java_import 'java.awt.Color'

Arena.open_default_world

karel = Robot.new

4.times do 
  karel.move
  karel.turn_left
end

Arena.add_north_wall(5,5)
Arena.add_south_wall(5,5)
Arena.add_east_wall(5,5)
Arena.add_west_wall(5,5)
Arena.add_beepers(5,5,5)

rodney = Robot.new(1, 2, Direction::EAST, BeeperStack::INFINITY)

rodney.putBeeper
rodney.move
rodney.setColor(Color::BLUE);
rodney.putBeeper
rodney.move;
rodney.setColor(Color::GREEN);
rodney.putBeeper();
rodney.move;
rodney.setColor(Color::RED);
rodney.putBeeper();
rodney.move;
