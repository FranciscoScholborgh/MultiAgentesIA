/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataFormat.converter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author frank
 */
public class ImageConverter {
    
    public static Image convertByteArrayToImage (byte[] imgAsBytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imgAsBytes);
            BufferedImage bufferedImage = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            return image;
        } catch (IOException ex) {
            Logger.getLogger(ImageConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
