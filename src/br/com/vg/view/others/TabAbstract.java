
package br.com.vg.view.others;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Márcio Arantes
 */
public abstract class TabAbstract extends JPanel{
    
    private static final FlowLayout layout = new FlowLayout(FlowLayout.LEADING);

    public TabAbstract() {
        super(layout);
    }
   
    public JPanel AddPanel(){
        JPanel panel = new JPanel(layout){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
            }
        };
        panel.setBackground(Color.WHITE);
        this.add(panel);
        return panel;
    }

    public void addMenuItem(Object ...objs){
        byte n = 0;
        JButton btm = null;
        JPanel panel = null;
        for(final Object o : objs){
            if(o instanceof KeyStroke){
                //menuItem.setAccelerator((KeyStroke) o);
                btm.setMnemonic(((KeyStroke) o).getKeyCode());
            }else if(o instanceof Boolean){
                btm.setEnabled((Boolean) o);
            }else if(o instanceof String){
                final String name = (String) o;
                if(name.matches("[\\-]*")){
                    panel = AddPanel();
                }else{
                    final byte index = n;
                    btm = new JButton(name);
                    btm.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try{
                                ActionPerformed(e, name, index);
                            }catch(Exception ex){
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, ex.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    if(panel==null){
                        panel = AddPanel();
                    }
                    panel.add(btm);
                    ++n;
                }
            }else{
                JOptionPane.showMessageDialog(null, "\nIn FMenu.addMenuItem type " +
                        "not defined\n", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void addRadioButtonMenuItem(LookAndFeelInfo[] LookAndFeelds) {
        String S[] = new String[LookAndFeelds.length];
        for(byte i=0; i<S.length; ++i){
            S[i] = LookAndFeelds[i].getName();
        }
        addRadioButtonMenuItem(S);
    }

    public void addRadioButtonMenuItem(String ...names) {
        ButtonGroup buttonGroup = new ButtonGroup();
        byte n = 0;
        JPanel panel = null;
        for(final String name : names){
            if(name.matches("[\\-]*")){
                panel = AddPanel();
            }else{
                final byte index = n;
                JRadioButton rb = new JRadioButton(name, n==0);
                buttonGroup.add(rb);
                rb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try{
                            ActionPerformed(e, name, index);
                        }catch(Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                if(panel==null){
                    panel = AddPanel();
                }
                panel.add(rb);
            }
            ++n;
        }
    }

    public void addLabelInformation(Object ...objs){
        JLabel label = null;
        JPanel panel = null;
        for(final Object o : objs){
            if(o instanceof String){
                final String name = (String) o;
                if(name.matches("[\\-]*")){
                    panel = AddPanel();
                }else{
                    label = new JLabel();
                    label.setName(name);
                    if(panel==null){
                        panel = AddPanel();
                    }
                    panel.add(label);
                }
            }else if(o instanceof Boolean){
                label.setVisible((Boolean) o);
            }else{
                JOptionPane.showMessageDialog(null, "\nIn FMenu.addLabelInformation"
                        + " type not defined\n", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updataLabel(String name, String text){
        for(Component comp : this.getComponents()){
            if(comp instanceof JPanel){
                JPanel panel = (JPanel) comp;
                for(Component c : panel.getComponents()){
                    if(c instanceof JLabel){
                        JLabel label = (JLabel) c;                        
                        if(label.getName().equalsIgnoreCase(name)){
                            label.setText(text);                            
                        }
                    }
                }
            }
        }
    }

    public void isVisibleLabel(String name, boolean visible){
        for(Component comp : this.getComponents()){
            if(comp instanceof JPanel){
                JPanel panel = (JPanel) comp;
                for(Component c : panel.getComponents()){
                    if(c instanceof JLabel){
                        JLabel label = (JLabel) c;
                        if(label.getName().equalsIgnoreCase(name)){
                            label.setVisible(visible);
                        }
                    }
                }
            }
        }
    }

    public void Enabled(String ...names) {
        for(String s : names){
            setEnabled(s, true);
        }
    }

    public void Disabled(String ...names) {
        for(String s : names){
            setEnabled(s, false);
        }
    }

    public void setEnabled(String name, boolean b) {
        for(Component comp : this.getComponents()){
            if(comp instanceof JPanel){
                JPanel panel = (JPanel) comp;
                for(Component c : panel.getComponents()){                    
                    if(c instanceof JButton){
                        JButton m = (JButton) c;
                        if(m.getText().equalsIgnoreCase(name)){
                            m.setEnabled(b);                            
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        for(Component comp : this.getComponents()){
            if(comp instanceof JPanel){
                JPanel panel = (JPanel) comp;
                byte n = 0;
                for(Component c : panel.getComponents()){                    
                    if(c instanceof JButton){
                        JButton m = (JButton) c;
                        m.setPreferredSize(new Dimension(180, 23));
                    }else if(c instanceof JRadioButton){
                        JRadioButton m = (JRadioButton) c;
                        m.setPreferredSize(new Dimension(180, 23));
                    }else if(c instanceof JLabel){
                        JLabel m = (JLabel) c;
                        m.setPreferredSize(new Dimension(280, 23));
                    }
                    ++n;
                }
                panel.setPreferredSize(new Dimension(6+(180+6)*(n/2 + n%2),
                        6+23+6+23+6));
            }
        }
    }

    public abstract void ActionPerformed(ActionEvent e, String item, byte index)
            throws Exception;
}
