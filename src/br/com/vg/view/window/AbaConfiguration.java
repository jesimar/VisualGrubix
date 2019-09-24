/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vg.view.window;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.control.Handler;
import br.com.vg.controller.control.Simulation;
import br.com.vg.view.others.OptionsAbstract;
import br.com.vg.view.paint.DrawSpaceViewer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 *
 * @author jesimar
 */
public class AbaConfiguration extends JPanel{
    
    private ResourceBundle idioma = Handler.getInstance().getIdioma();
        
    private final OptionsAbstract optionsConfig;
    
    private DrawSpaceViewer draw;
    
    private Simulation simulation;
        
    public AbaConfiguration(Simulation simulation, DrawSpaceViewer draw, 
            int compX, int height ){
        
        this.draw = draw;
        this.simulation = simulation;
        
        optionsConfig = new OptionsAbstract() {
            @Override
            public void ActionPerformed(ActionEvent e, String item, byte index,
                    byte value) throws Exception {
                menuOptionEvent(e, item, index, value);
            }
            @Override
            public void ActionPerformed(ActionEvent e, String item, boolean state)
                    throws Exception {
                menuOptionEvent(e, item, state);
            }

            @Override
            public void ChangeEvent(String item, byte index, JSpinner spinner)
                    throws Exception {
                menuOptionEvent(item, index, spinner);
            }
        };
        optionsConfig.addComboBox(
                idioma.getString("opt_1"),                
                idioma.getString("opt_3"),
                idioma.getString("opt_4"),
                idioma.getString("opt_5"),
                idioma.getString("opt_6")
            );

        optionsConfig.addCheckBox(
                "Anti-Aliasing",
                idioma.getString("node_id"),
                idioma.getString("draw_rule"),
                idioma.getString("figures"),
                idioma.getString("figures_scale"),
                idioma.getString("mov_lines")
            );
        optionsConfig.addJSpinner(
                idioma.getString("node_size") + ": ",
                25
            );

        optionsConfig.setLayout(new FlowLayout(FlowLayout.CENTER));
        optionsConfig.setBackground(Color.lightGray);
        optionsConfig.setPreferredSize(new Dimension(compX, height - 250));
        optionsConfig.setEnabledJCheckBox(false);
        optionsConfig.setEnabledJComboBox(false);
        optionsConfig.setEnabledJSpinner(false);
        this.add(optionsConfig);
    }
    
    public void configurationInit(){
        optionsConfig.setEnabledJCheckBox(true);
        optionsConfig.setEnabledJComboBox(true);
        optionsConfig.setEnabledJSpinner(true);
        optionsConfig.setEnabledJComboBoxFigure(false);
        optionsConfig.setEnabledSuitableFigureScale(false);        
    }
    
    public void valuesDefault(){
        optionsConfig.setValueDefault();
        optionsConfig.setEnabledJCheckBox(false);
        optionsConfig.setEnabledJComboBox(false);
        optionsConfig.setEnabledJSpinner(false);
    }
    
    /**
     * Evento de clique em alguma opção de configuração do menu options, que 
     * seja do tipo checkbox.
     */
    private void menuOptionEvent(ActionEvent e, String item, boolean state){
        if (item.equalsIgnoreCase("Anti-Aliasing")){
            draw.setAntiAliasing(state);            
        }else if (item.equalsIgnoreCase(idioma.getString("node_id"))){
            simulation.paintNodeId(state);
        }else if (item.equalsIgnoreCase(idioma.getString("draw_rule"))){
            draw.visibleRule(state);
        }else if (item.equalsIgnoreCase(idioma.getString("figures"))){
            optionsConfig.setEnabledJComboBoxFigure(state);
            optionsConfig.setEnabledSuitableFigureScale(state);
            simulation.setIsVisibleFigure(state);
        }else if (item.equalsIgnoreCase(idioma.getString("figures_scale"))){
            simulation.setIsVisibleFigureScale(state);
        }else if (item.equalsIgnoreCase(idioma.getString("mov_lines"))){
            draw.visibleMovimentLines(state);                
        }
        draw.repaint();
    }
    
    /**
     * Evento de clique em alguma opção de configuração do menu options, que
     * seja do tipo combobox.
     */
    private void menuOptionEvent(ActionEvent e, String item, byte index, byte value){
        if (index == 0){
            simulation.setGraphConnectivity(value);            
        }else if (index == 1){
            simulation.setFormPacket(value);            
        }else if (index == 2){
            DataSimulation data = Handler.getInstance().getDataSimulation();
            if (data != null){
                data.setIndexMoteFigure(value);
            }
        }else if (index == 3){
            DataSimulation data = Handler.getInstance().getDataSimulation();
            if (data != null){
                data.setIndexUavFigure(value);
            }            
        }else if (index == 4){
            DataSimulation data = Handler.getInstance().getDataSimulation();
            if (data != null){
                data.setIndexIntruderFigure(value);
            }            
        }
        draw.repaint();
    }
    
    /**
     * Evento que aumenta o tamanho dos nós da rede.
     */
    private void menuOptionEvent(String item, byte index, JSpinner spinner){
        if (item.equalsIgnoreCase(idioma.getString("node_size") + ": ")){
            if ((Integer)spinner.getValue() >= iConstants.SIZE_MIN_NODE &&
                    (Integer)spinner.getValue() < iConstants.SIZE_MAX_NODE){
                DataSimulation data = Handler.getInstance().getDataSimulation();
                if (data != null){
                    data.setDiameterNode((Integer)spinner.getValue());                    
                }
                draw.repaint();
            }else if ((Integer)spinner.getValue() < iConstants.SIZE_MIN_NODE){
                spinner.setValue(iConstants.SIZE_MIN_NODE);
            }else{
                spinner.setValue(iConstants.SIZE_MAX_NODE);
            }
        }
    }        
    
}
