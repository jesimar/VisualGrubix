
package br.com.vg.view.window;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.control.Handler;
import br.com.vg.controller.control.Simulation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe responsável por apresentar o conteúdo da aba data onde será
 * exibida os dados da simulação.
 * @author Jesimar Arantes
 */
public final class AbaData extends JPanel{    
    
    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Area onde sera exibido os dados da simulação.
     */
    private JTextArea textArea = new JTextArea();
    
    private ResourceBundle idioma = Handler.getInstance().getIdioma();
    
    //-------------------------------CONSTRUTOR---------------------------------

    /**
     * Construtor da classe.
     */
    public AbaData(int compX, int height){
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
     * @param simulation - instancia da simulação.
     * @param index - index do evento corrente da simulação.
     * @param time - tempo atual da simulação.
     */
    public void viewData(DataSimulation data, Simulation simulation, int index,
            double time){
        textArea.setText("");
        textArea.append(idioma.getString("sim_info") + ": \n"  );

        textArea.append("\n" + idioma.getString("app_nome") + ": " + data.getNameApp());
        textArea.append("\n" + idioma.getString("largura") + ": " + data.getDimensionX());
        textArea.append("\n" + idioma.getString("altura") + ": " + data.getDimensionY());

        textArea.append("\n" + idioma.getString("quant_nos") + ": " + data.getAmmountNodes());
        textArea.append("\n" + idioma.getString("quant_nos_estaticos") + ": " +
                (data.getAmmountNodes() - data.getAmountNodeMove()));
        textArea.append("\n" + idioma.getString("quant_nos_moveis") + ": " +
                data.getAmountNodeMove());

        textArea.append(String.format("\n" + idioma.getString("area") + 
                ": %d m2", data.getArea()));

        textArea.append(String.format("\n" + idioma.getString("densidade") + 
                ": %.8f nodes/m2", data.getDensidade()));

        textArea.append(String.format("\n" + idioma.getString("num_medio_vizinhos")  + 
                ": %.4f", data.getAverangeNeig()));

        textArea.append(String.format("\n" + idioma.getString("conectividade") + 
                ": %.4f", 100 * data.getConectivity()) + " %");

        if (data.getListEvents() != null && data.getListEvents().size() > 0){
            textArea.append(String.format("\n\n" + idioma.getString("tempo") + 
                ": %.3f / %.3f", time, data.getListEvents().getLast().getTime()));

            textArea.append(String.format("\n" + idioma.getString("indice_evento") + 
                ": %d / %d", index+1, data.getListEvents().size()));

            textArea.append("\n" + idioma.getString("num_pacotes_enviados") + 
                ": " + (data != null ? simulation.getAmountPacketSend() : 0) + 
                " / " + (data != null ? data.getSizeListAnim() : 0));
        }

    }  
}
