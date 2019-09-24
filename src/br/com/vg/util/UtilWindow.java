/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vg.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Jesimar Arantes
 */
public class UtilWindow {

    /**
     * Adiciona um label.
     * @param str - mensagem do label.
     * @param dim - dimensão do label.
     * @return label criado.
     */
    public static JLabel addLabel(String str, Dimension dim){
        JLabel label = new JLabel(str);
        label.setPreferredSize(dim);
        return label;
    }

    public static ImageIcon getImageSmall(Image image, int compX, int compY){
        BufferedImage buff = new BufferedImage(compX, compY,
                BufferedImage.TYPE_4BYTE_ABGR);
        buff.getGraphics().drawImage(image, 0, 0, compX, compY, null);
        final Image img = buff.getScaledInstance(compX, compY,
                BufferedImage.SCALE_DEFAULT);
        return new ImageIcon(img);
    }
}
