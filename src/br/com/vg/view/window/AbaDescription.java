
package br.com.vg.view.window;

import br.com.vg.controller.control.Handler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe responsável por apresentar o conteúdo da aba data onde será
 * exibida os dados da simulação.
 * @author Jesimar Arantes
 */
public final class AbaDescription extends JPanel{    
    
    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Area onde sera exibido os dados da simulação.
     */
    private JTextArea textArea = new JTextArea();   
    
    //-------------------------------CONSTRUTOR---------------------------------

    /**
     * Construtor da classe.
     */
    public AbaDescription(int compX, int height){
        JScrollPane scroll = new JScrollPane();        
        textArea.setBackground(Color.white);
        textArea.setEditable(false);
        textArea.setColumns(25);
        textArea.setRows(31);
        scroll.setViewportView(textArea);
        this.add(scroll);
        
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(compX, height - 250));
    }

    //---------------------------MÉTODOS PÚBLICOS-------------------------------    

    //==================================OTHER===================================       

    /**
     * Limpa as informações exibidas.
     */
    public void valuesDefault(){
        textArea.setText("");
    }

    /**
     * Mostra os dados da simulação na tela.
     * @param data - dados da simulação.
     */
    public void viewDescription(String description){
        textArea.setText("");
        textArea.append(Handler.getInstance().getIdioma().getString("desc_sim") + "\n\n");
        textArea.setWrapStyleWord(false);
        textArea.append(description);
    }  
}
