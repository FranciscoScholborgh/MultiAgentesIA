/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataFormat.converter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author frank
 */
public class ByteConverter {
    
    public static byte[] convertImageFileToByte (File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".")+1);
        BufferedImage bufferedIMG;
        try {
            bufferedIMG = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedIMG, extension, baos);
            baos.flush();
            byte[] imgAsBytes = baos.toByteArray();
            baos.close();
            return imgAsBytes;
        } catch (IOException ex) {
            Logger.getLogger(ByteConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }   
    }
}
