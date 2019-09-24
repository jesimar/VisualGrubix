
package br.com.vg.view.others;

import br.com.vg.controller.control.Handler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Jesimar Arantes
 */
public abstract class OptionsAbstract extends JPanel{

    private static final FlowLayout layout = new FlowLayout(FlowLayout.LEADING);
    
    private ResourceBundle idioma = Handler.getInstance().getIdioma();

    //-------------------------------CONSTRUTOR---------------------------------

    /**
     * Contrutor da classe.
     */
    public OptionsAbstract() {
        super(layout);
    }

    /**
     * Adiciona checkbox neste painel.
     * Formato: Lista de
     *              String (nome),
     *              boolean (isHabilitado)
     * Onde todos os objetos são opcionais mas você deve passar ou um nome
     * o restante é opcional.
     * @param objs qualquer quantidade de parametros desenjadas.
     */
    public void addCheckBox(Object ...objs){
        try{
            byte n = 0;
            JCheckBox checkBox = null;
            for(final Object o : objs){
                if(o instanceof String){
                    checkBox = createCheckBox((String) o, n);
                    checkBox.setPreferredSize(new Dimension(280, 25));
                    checkBox.setBackground(new Color(0, 0, 0, 0));
                    this.add(checkBox);
                    ++n;
                }else if(o instanceof Boolean){
                    checkBox.setSelected((Boolean) o);
                }else{
                    throw new Exception("\nIn OptionsAbstract type not defined\n");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JCheckBox createCheckBox(final String name, final byte index)
            throws Exception{

        final JCheckBox checkBox;
        if (name != null){
            checkBox = new JCheckBox(name);
        }else{
            throw new Exception("\nError create checkbox\n");
        }
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    ActionPerformed(e, name, checkBox.isSelected());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return checkBox;
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
    public void addComboBox(Object ...objs){
        try{
            byte n = 0;
            JComboBox comboBox = null;
            for(final Object o : objs){
                if(o instanceof String){
                    comboBox = createComboBox((String) o, n);
                    comboBox.setPreferredSize(new Dimension(280, 25));
                    this.add(comboBox);
                    ++n;
                }else if(o instanceof Boolean){
                    comboBox.setEnabled((Boolean) o);
                }else{
                    throw new Exception("In OptionsAbstract type not defined");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
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
            throw new Exception("Error create ComboBox");
        }
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    ActionPerformed(e, name, index, (byte)comboBox.getSelectedIndex());
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return comboBox;
    }

    /**
     * Adiciona um campo de mudança de valor inteiro.
     * @param objs
     *          Formato: lista de.
     *          String nome (label),
     *          Integer value (valor padrão para começar o campo jSpinner),
     *          boolean enabled (valor indicando se esta habilitado ou não),
     */
    public void addJSpinner(Object ...objs){
        try{
            byte n = 0;
            JSpinner jSpinner = null;
            String name = null;
            JLabel label = null;
            for(final Object o : objs){
                if(o instanceof String){
                    name = (String)o;
                    label = new JLabel(name);
                    label.setPreferredSize(new Dimension(170, 25));
                    this.add(label);
                }else if (o instanceof Integer){
                    jSpinner = new JSpinner();
                    //Gambi aqui limite não dependente do tamanho do terrono.
                    jSpinner.setPreferredSize(new Dimension(100, 25));
                    jSpinner.setValue((Integer)o);
                    this.add(jSpinner);
                    final byte index = n;
                    final JSpinner sp = jSpinner;
                    final String str = name;
                    jSpinner.addChangeListener(new ChangeListener() {
                        public void stateChanged(ChangeEvent e) {
                            try{
                                ChangeEvent(str, index, sp);
                            }catch(Exception ex){
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, ex.getMessage(),
                                        "Erro", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    });
                    ++n;
                }else if (o instanceof Boolean){
                    jSpinner.setEnabled((Boolean)o);
                    label.setEnabled((Boolean)o);
                }else{
                    throw new Exception("In OptionsAbstract type not defined");
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setEnabledJCheckBox(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JCheckBox){
                ((JCheckBox) comp).setEnabled(value);
            }
        }
    }

    public void setEnabledSuitableFigureScale(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JCheckBox){
                JCheckBox compAux = (JCheckBox) comp;
                if (compAux.getText().equalsIgnoreCase(idioma.getString("figures_scale"))){
                    compAux.setEnabled(value);
                }
            }
        }
    }

    public void setEnabledMovimentLines(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JCheckBox){
                JCheckBox compAux = (JCheckBox) comp;
                if (compAux.getText().equalsIgnoreCase(idioma.getString("mov_lines"))){
                    compAux.setEnabled(value);
                }
            }
        }
    }

    public void setEnabledCheckBoxFigure(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JCheckBox){
                JCheckBox compAux = (JCheckBox) comp;
                if (compAux.getText().equalsIgnoreCase(idioma.getString("figures"))){
                    compAux.setEnabled(value);
                }
            }
        }
    }

    public void setValueDefault(){
        for(Component comp : this.getComponents()){
            if(comp instanceof JComboBox){
                ((JComboBox) comp).setSelectedIndex(0);
            }else if(comp instanceof JCheckBox){
                ((JCheckBox) comp).setSelected(false);
            }else if(comp instanceof JSpinner){
                ((JSpinner) comp).setValue(25);
            }
        }
    }

    public void setEnabledJComboBox(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JComboBox){
                ((JComboBox) comp).setEnabled(value);
            }
        }
    }

    public void setEnabledJComboBoxFigure(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JComboBox){
                JComboBox compAux = (JComboBox) comp;
                if (compAux.getItemAt(0).toString().substring(0, 6)
                        .equalsIgnoreCase("Figure")){
                    compAux.setEnabled(value);
                }
            }
        }
    }

    public void setEnabledJSpinner(boolean value){
        for(Component comp : this.getComponents()){
            if(comp instanceof JSpinner){
                ((JSpinner) comp).setEnabled(value);
            }
            if(comp instanceof JLabel){
                ((JLabel) comp).setEnabled(value);
            }
        }
    }

    public abstract void ActionPerformed(ActionEvent e, String item, byte index,
            byte value) throws Exception;

    public abstract void ActionPerformed(ActionEvent e, String item, boolean state)
            throws Exception;

    public abstract void ChangeEvent(String item, byte index, JSpinner spinner)
            throws Exception;
}
