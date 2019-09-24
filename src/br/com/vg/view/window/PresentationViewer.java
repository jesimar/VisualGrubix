
package br.com.vg.view.window;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 * Classe responsavel por exibir a tela de apresentação do software.
 * @author Jesimar Arantes
 */
public final class PresentationViewer extends JFrame{

    //-------------------------------CONSTRUTORES-------------------------------

    /**
     * Construtor da classe PresentationViewer.
     * @param imageIcon - icone mostrado na tela de apresentaçao do software.
     */
    public PresentationViewer(ImageIcon imageIcon){
        this(imageIcon, true, 40, 300, 200);
    }

    /**
     * Construtor da classe PresentationViewer.
     * @param imageIcon - icone mostrado na tela de apresentaçao do software.
     * @param progBarVisible - barra de progresso é visivel ou não.
     */
    public PresentationViewer(ImageIcon imageIcon, boolean progBarVisible){
        this(imageIcon, progBarVisible, 40, 200, 200);
    }

    /**
     * Construtor da classe PresentationViewer.
     * @param imageIcon - icone mostrado na tela de apresentaçao do software.
     * @param timeSleep - tempo para exibição na abertura do software.
     */
    public PresentationViewer(ImageIcon imageIcon, int timeSleep){        
        this(imageIcon, true, timeSleep, 200, 200);
    }

    /**
     * Construtor da classe PresentationViewer.
     * @param imgIcon - icone mostrado na tela de apresentaçao do software.
     * @param progBarVisible - barra de progresso é visivel ou não.
     * @param timeSleep - tempo para exibição na abertura do software.
     * @param locX - Localização na tela X.
     * @param locY - Localização na tela Y.
     */
    public PresentationViewer(ImageIcon imgIcon, boolean progBarVisible,
            int timeSleep, int locX, int locY){
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 6));
        this.setUndecorated(true);
        this.setLocation(locX, locY);
        this.setVisible(true);
        int width = imgIcon.getIconWidth();
        int height = imgIcon.getIconHeight();
        this.setSize(width + 20, height + 50);
        this.setPreferredSize(new Dimension(width + 6, height + 6));
        final JLabel lab = new JLabel(imgIcon);
        this.add(lab);

        if (progBarVisible){
            JProgressBar progressBar = new JProgressBar(0, 100){
                @Override
                public void setValue(int n) {
                    super.setValue(n);                    
                }
            };
            progressBar.setVisible(true);
            progressBar.setValue(0);
            progressBar.setBorderPainted(true);
            progressBar.setStringPainted(true);
            progressBar.setPreferredSize(new Dimension(width - 20, 23));
            this.add(progressBar);
            for (byte i = 0; i <= 100; ++i){
                try {                        
                    progressBar.setValue(i);
                    progressBar.updateUI();
                    Thread.sleep(timeSleep);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null,
                            "\nError Window Presentation: \n" + ex,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }        
        this.dispose();      
    }
}
