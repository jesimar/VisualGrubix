
package br.com.vg.view.others;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author Jesimar Arantes
 */
public abstract class ButtonsAbstract extends JPanel{

    private static final FlowLayout layout = new FlowLayout(FlowLayout.LEADING);

    //-------------------------------CONSTRUTOR---------------------------------

    /**
     * Contrutor da classe.
     */
    public ButtonsAbstract() {
        super(layout);
    }

    /**
     * Adiciona botões neste painel.
     * Formato: Lista de
     *              String (nome),
     *              Icon (icone),
     *              KeyStroke (atalho),
     *              boolean (isHabilitado)
     * Onde todos os objetos são opcionais mas você deve passar ou um nome ou
     * um icone, o restante eh opcional.
     * @param objs qualquer quantidade de parametros desenjadas.
     */
    public void addButtons(Object ...objs){
        try{
            byte n = 0;
            JButton btn = null;
            String nome = null;
            for(final Object o : objs){
                if(o instanceof String){
                    if (nome != null){
                        btn = createButton(nome, null, n);
                        this.add(btn);
                        ++n;
                    }
                    nome = (String) o;
                    btn = null;
                }else if (o instanceof Icon){
                    btn = createButton(nome, (Icon) o, n);
                    this.add(btn);
                    ++n;
                    nome = null;
                }else if(o instanceof KeyStroke){
                    if (btn == null){
                        btn = createButton(nome, null, n);
                        this.add(btn);
                        ++n;
                    }
                    btn.setMnemonic(((KeyStroke) o).getKeyCode());
                }else if(o instanceof Boolean){
                    if (btn == null){
                        btn = createButton(nome, null, n);
                        this.add(btn);
                        ++n;
                    }
                    btn.setEnabled((Boolean) o);
                }else{
                    throw new Exception("\nIn ButtonsAbstract type not defined\n");
                }
            }
            if (btn == null){
                btn = createButton(nome, null, n);
                this.add(btn);
                ++n;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createButton(final String name, final Icon icon, 
            final byte index) throws Exception{
        String s[];
        if (name.contains("|")){
            s = name.split("[|]");
        }else{
            s = new String[]{
                name, null
            };
        }
        final String nome = s[0];
        String text = s[1];

        JButton btn;
        if (name != null && icon != null){
            //Detalhe não estou usando o nome deveria ser:  JButton(nome, icon);
            btn = new JButton(icon);
            btn.setName(nome);
        }else if (name != null){
            btn = new JButton(nome);
        }else if (icon != null){
            btn = new JButton(icon);
        }else{
            throw new Exception("\nError create button\n");
        }
        btn.setPreferredSize(new Dimension(50, 40));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    ActionPerformed(e, nome, index);
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btn.setToolTipText(text);
        return btn;
    }

    public void setEnabled(String ...names) {
        for(String s : names){
            enabled(s, true);
        }
    }

    public void setDisabled(String ...names) {
        for(String s : names){
            for(Component comp : this.getComponents()){
                if(comp instanceof JButton){
                    JButton m = (JButton) comp;
                    if(m.getName().equalsIgnoreCase(s)){
                        m.setEnabled(false);
                    }
                }
            }
        }
    }

    public void setDisabled() {
        for(Component comp : this.getComponents()){
            if(comp instanceof JButton){
                JButton m = (JButton) comp;
                m.setEnabled(false);
            }
        }
    }

    private void enabled(String name, boolean b) {
        for(Component comp : this.getComponents()){
            if(comp instanceof JButton){
                JButton m = (JButton) comp;
                if(m.getText().equalsIgnoreCase(name)){
                    m.setEnabled(b);
                }
            }
        }
    }

    public void setEnabledJButtons(boolean value) {
        for(Component comp : this.getComponents()){
            if(comp instanceof JButton){
                ((JButton) comp).setEnabled(value);
            }
        }
    }

    public abstract void ActionPerformed(ActionEvent e, String item, byte index)
            throws Exception;
}
