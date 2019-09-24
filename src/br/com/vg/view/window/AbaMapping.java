/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vg.view.window;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.view.others.MappingAbstract;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

/**
 *
 * @author jesimar
 */
public class AbaMapping extends JPanel{
        
    private final MappingAbstract abaMapping;        
    
    public AbaMapping(int compX, int height){                
        
        abaMapping = new MappingAbstract() {
            @Override
            public void ActionPerformed(ActionEvent e, String item, byte index,
                    byte value) throws Exception {
                menuAbaMapping(e, item, index, value);
            }
        };        

        abaMapping.setLayout(new FlowLayout(FlowLayout.CENTER));
        abaMapping.setBackground(Color.lightGray);
        abaMapping.setPreferredSize(new Dimension(compX, height - 250));
        
        this.add(abaMapping);
    }            
    
    public void valuesDefault(DataSimulation data){
        abaMapping.clearComponents();
        abaMapping.addStringComboBoxVector(
            data.getFieldsMapping()
        );
        abaMapping.setComboBoxMapping(data);
    }
    
    public void valuesDefault(){
        abaMapping.clearComponents();
    }
    
    private void menuAbaMapping(ActionEvent e, String item, byte index, byte value){
        
    }
    
}
