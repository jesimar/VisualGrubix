
package br.com.vg.view.others;

import br.com.vg.controller.control.DataSimulation;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Classe responsável pela aba mapping e seu conteúdo.
 * @author Jesimar Arantes
 */
public abstract class MappingAbstract extends JPanel{

    //-------------------------------CONSTRUTOR---------------------------------

    /**
     * Contrutor da classe.
     */
    public MappingAbstract() {
    }

    /**
     * Adiciona combobox neste painel.
     * Formato: Lista de
     *              String (campo1 | campo2 | campo3 | ... | campoN),
     *              boolean (isHabilitado)
     * Onde todos os objetos são opcionais mas você deve passar ou um nome
     * o restante é opcional.
     * Obs: os campos dentro do mesmo combobox devem ser separados por um pipe "|"
     * @param objs qualquer quantidade de parametros desenjadas.
     */
    public void addStringComboBox(Object ...objs){
        try{
            byte n = 0;
            JLabel str;
            JComboBox comboBox = null;
            for(final Object o : objs){
                if(o instanceof String){
                    str = new JLabel((String) o);
                    str.setPreferredSize(new Dimension(190, 25));
                    this.add(str);
                    comboBox = createComboBox("None |Color |BorderColor |Label", n);
                    comboBox.setPreferredSize(new Dimension(90, 25));
                    this.add(comboBox);
                    ++n;
                }else if(o instanceof Boolean){
                    comboBox.setEnabled((Boolean) o);
                }else{
                    throw new Exception("\nIn MappingAbstract type not defined\n");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Adiciona combobox neste painel.
     * Formato: Lista de
     *              String (campo1 | campo2 | campo3 | ... | campoN),
     *              boolean (isHabilitado)
     * Onde todos os objetos são opcionais mas você deve passar ou um nome
     * o restante é opcional.
     * Obs: os campos dentro do mesmo combobox devem ser separados por um pipe "|"
     * @param objs qualquer quantidade de parametros desenjadas.
     */
    public void addStringComboBoxVector(Object objs[]){
        try{
            byte n = 0;
            JLabel str;
            JComboBox comboBox = null;
            for(final Object o : objs){
                if(o instanceof String){
                    str = new JLabel((String) o);
                    str.setPreferredSize(new Dimension(190, 25));
                    this.add(str);
                    comboBox = createComboBox("None |Color |BorderColor |Label", n);
                    comboBox.setPreferredSize(new Dimension(90, 25));
                    this.add(comboBox);
                    ++n;
                }else if(o instanceof Boolean){
                    comboBox.setEnabled((Boolean) o);
                }else{
                    throw new Exception("\nIn MappingAbstract type not defined\n");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JComboBox createComboBox(final String name, final byte index)
            throws Exception{

        String s[];
        if (name.contains("|")){
            s = name.split("[|]");
        }else{
            s = new String[]{
                name, null
            };
        }

        final JComboBox comboBox;
        if (name != null){
            comboBox = new JComboBox();
            comboBox.setModel(new javax.swing.DefaultComboBoxModel(s));
        }else{
            throw new Exception("\nError create ComboBox\n");
        }
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    ActionPerformed(e, name, index, (byte)comboBox.getSelectedIndex());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return comboBox;
    }

    public void setEnabledJComboBox(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JComboBox){
                ((JComboBox) comp).setEnabled(value);
            }
        }
    }

    public void setComboBoxMapping(DataSimulation dataSimulation){
        LinkedList<JComboBox> listComboBox = new LinkedList<JComboBox>();
        for(Component comp : this.getComponents()){
            if(comp instanceof JComboBox){
                listComboBox.add((JComboBox) comp);
            }
        }
        dataSimulation.setListComboBoxMapping(listComboBox);
    }

    public void clearComponents(){
        for(Component comp : this.getComponents()){
            this.remove(comp);
        }
    }

    public abstract void ActionPerformed(ActionEvent e, String item, byte index,
            byte value) throws Exception;

}
