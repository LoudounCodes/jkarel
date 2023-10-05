package org.loudouncodes.jkarel;

import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
  * As we build out this library, we will need a class that will:
  *   - Take an instance of an ImageIcon, by default in the 'NORTH' orientation
  *   - Replace a 'default' color with the color provided (so robots
  *     can have colors)
  *   - rotate the icon to make EAST, SOUTH, and WEST versions
  *   - return an HashMap of these icons.
  */
 
public class ImageUtils {

  public static HashMap<Direction, BufferedImage> setupImages(ImageIcon icon, Color c) {
    
    HashMap<Direction, BufferedImage> images = new HashMap<Direction, BufferedImage>();
    
    BufferedImage north = replaceColor(icon, c);
    BufferedImage east = rotateRight(north);
    BufferedImage south = rotateRight(east);
    BufferedImage west = rotateRight(south);
    
    images.put(Direction.NORTH, north);
    images.put(Direction.EAST, east);
    images.put(Direction.SOUTH, south);
    images.put(Direction.WEST, west);
    
    return images;
  }
  
  private static BufferedImage replaceColor(ImageIcon icon, Color newColor) {
    
    Color oldColor = new Color(255,255,0);
    
    BufferedImage img = new BufferedImage(icon.getIconWidth(),
                                          icon.getIconHeight(),
                                          BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = img.createGraphics();
    icon.paintIcon(null, g2d, 0, 0);
    g2d.dispose();

    // Loop through each pixel
    for (int y = 0; y < img.getHeight(); y++) {
        for (int x = 0; x < img.getWidth(); x++) {
            int pixel = img.getRGB(x, y);
            if (pixel == oldColor.getRGB()) {
                img.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    return img;
  }
  
  private static BufferedImage rotateRight(BufferedImage img) {
    
        // Create an AffineTransform for a 90-degree rotation
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(90), img.getWidth() / 2.0, img.getHeight() / 2.0);

        // Create an AffineTransformOp to apply the rotation
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        // Apply the rotation and return the result
        BufferedImage rotatedImg = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
        op.filter(img, rotatedImg);

        return rotatedImg;
  }
}