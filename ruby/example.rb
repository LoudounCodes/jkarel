require '../out/jkarel-1.0.0.jar'
java_import 'org.loudouncodes.jkarel.Arena'
java_import 'org.loudouncodes.jkarel.Robot'

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