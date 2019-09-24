/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vg.view.window;

import br.com.vg.controller.control.Handler;
import br.com.vg.controller.resources.ResourcesGraphics;
import br.com.vg.util.UtilWindow;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import javax.swing.*;

/**
 * Classe responsável pela escolha de qual ideoma o programa será carregado.
 * @author Jesimar Arantes
 */
public class ChooseLanguage extends JFrame{
    
    private String strLanguage = null;
    private JPanel panel;
    private JButton btnOk;
    private ResourcesGraphics resources;
    
    public ChooseLanguage(){        
        this.setLocation(900, 220);
        this.setSize(300, 200);
        this.setVisible(true);
        this.setTitle("Language");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                
        
        final ChooseLanguage language = this;
        
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(275, 170));                
        panel.add(UtilWindow.addLabel("Choose Language:", new Dimension(270, 25)));
        
        String vetorLanguage[] = {"English", "Portuguese"};
        final JComboBox comboBoxLanguage = new JComboBox(vetorLanguage);        
        comboBoxLanguage.setPreferredSize(new Dimension(180, 25));
        panel.add(comboBoxLanguage);
        
        panel.add(UtilWindow.addLabel("", new Dimension(280, 35)));
        
        btnOk = new JButton("Ok");
        btnOk.setPreferredSize(new Dimension(120, 25));
        btnOk.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent arg0) {                
                if (comboBoxLanguage.getSelectedIndex() == 0){
                    strLanguage = "en_EN";        
                }else if (comboBoxLanguage.getSelectedIndex() == 1){
                    strLanguage = "pt_PT";
                }     
                language.dispose();
                loadMainFrame(strLanguage, false);                
            }
        });
        panel.add(btnOk);        
        this.add(panel);                
        panel.updateUI();
    }    
    
    public ChooseLanguage(int TYPE_LANGUAGE, boolean isShowWindowPres){
        if (TYPE_LANGUAGE == iConstants.INGLES){
            strLanguage = "en_EN";       
        }else if (TYPE_LANGUAGE == iConstants.PORTUGUES){
            strLanguage = "pt_PT";
        }             
        loadMainFrame(strLanguage, isShowWindowPres);                
    }
    
    private void loadMainFrame(String strLanguage, boolean isShowWindowPres){
        try{            
            String language[] = strLanguage.split("_");
            Locale.setDefault(new Locale(language[0], language[1]));
            Locale locale = Locale.getDefault();
            Handler.getInstance().setIdioma(ResourceBundle.getBundle(
                    "br.com.vg.resources.language.idioma", locale));            
            if (isShowWindowPres){
               loadPresentationViewer();                    
            }

            MainFrame main = new MainFrame();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nError loading program: \n" + ex,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }  
    
    private void loadPresentationViewer(){
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            public void run(){
                resources = Handler.getInstance().getResources();
                if (resources != null){
                        new PresentationViewer(new ImageIcon(resources.getImage(
                                ResourcesGraphics.IMAGE_FUNDO)));
                }
            }
        });
    }
}
