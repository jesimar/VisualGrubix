
package br.com.vg.view.window;

import br.com.vg.controller.control.Handler;
import br.com.vg.controller.resources.ResourcesGraphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe responsável por mostrar na tela o "Sobre" do software.
 * @author Jesimar Arantes
 */
public final class About extends JFrame{

    //-------------------------------ATRIBUTOS----------------------------------

    /**
     * Painel onde os recursos da aba about ficam alocados.
     */
    private JPanel panelAbout = new JPanel();

    /**
     * Painel onde os recursos da aba authors ficam alocados.
     */
    private JPanel panelAuthors = new JPanel();

    /**
     * Painel onde os recursos da aba license ficam alocados.
     */
    private JPanel panelLicense = new JPanel();

    /**
     * Componente onde fica alocado as abas.
     */
    private JTabbedPane jTabbedPane = new JTabbedPane();

    /**
     * Aba about onde fica os dados dos software em si.
     */
    private Abas abaAbout;

    /**
     * Aba authors onde fica os dados dos desenvolvedores.
     */
    private Abas abaAuthors;

    /**
     * Aba license onde fica os dados da licensa do software.
     */
    private Abas abaLicense;
    
    private ResourcesGraphics resources;

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Contrutor da Classe aqui são alocados os recursos desta classe.
     * @param title - título da janela
     */
    public About(String title){
        abaAbout = new Abas(panelAbout);
        abaAbout.addPanelsAbout();
        abaAuthors = new Abas(panelAuthors);
        abaAuthors.addPanelsAuthors();
        abaLicense = new Abas(panelLicense);
        abaLicense.addPanelsLicense();
        
        resources = Handler.getInstance().getResources();
        
        this.createWindow(title, 500, 520);
        this.createLogo("Visual GrubiX", new ImageIcon(resources.getImage(
                ResourcesGraphics.IMAGE_LOGO)));
        this.createAbas();
        this.createButtonClose();        
    }

    /**
     * Contrutor da Classe aqui são alocados os recursos desta classe.
     * @param title - título da janela
     * @param dimensionX - dimensão x da janela.
     * @param dimensionY - dimensão y da janela.
     */
    public About(String title, int dimensionX, int dimensionY){
        abaAbout = new Abas(panelAbout);
        abaAbout.addPanelsAbout();
        abaAbout.setLayout(new FlowLayout(FlowLayout.CENTER));
        abaAuthors = new Abas(panelAuthors);
        abaAuthors.addPanelsAuthors();
        abaAuthors.setLayout(new FlowLayout(FlowLayout.CENTER));
        abaLicense = new Abas(panelLicense);
        abaLicense.addPanelsLicense();
        abaLicense.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        resources = Handler.getInstance().getResources();
        
        this.createWindow(title, dimensionX, dimensionY);
        this.createLogo("Visual GrubiX", new ImageIcon(resources.getImage(
                ResourcesGraphics.IMAGE_LOGO)));
        this.createAbas();
        this.createButtonClose();        
    }

    //--------------------------MÉTODOS PRIVADOS--------------------------------

    //=================================OTHER====================================

    /**
     * Cria o frame da janela sobre.
     */
    private void createWindow(String title, int dimensionX, int dimensionY){
        this.setTitle(title);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setSize(new Dimension(dimensionX, dimensionY));
        this.setLocation(300, 120);
        this.setVisible(true);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);        
    }

    /**
     * Mostra o logo do menu ajuda.
     */
    private void createLogo(String name, ImageIcon icon){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(this.getWidth() - 24,
                this.getHeight() - 400));
        this.add(panel);
        JLabel logo = new JLabel(icon);
        panel.add(logo);
        JLabel nome = new JLabel(name);
        nome.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        panel.add(nome);
    }

    /**
     * Cria as abas na tela do visualizador.
     */
    private void createAbas(){
        jTabbedPane.addTab("About", panelAbout);
        jTabbedPane.addTab("Authors", panelAuthors);
        jTabbedPane.addTab("License", panelLicense);
        this.add(jTabbedPane);
    }

    /**
     * Cria o botão close.
     */
    private void createButtonClose(){
        final JButton btn = new JButton("Close");
        btn.setSize(100, 25);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        this.add(btn);
    }   

    //----------------------------CLASSE INTERNA--------------------------------

    /**
     * Classe interna responsável por mostrar os conteudos do software, autores
     * e licensa.
     */
    private class Abas extends JPanel{

        //----------------------------ATRIBUTOS---------------------------------

        private JPanel panelAbas;
        //private JPanel panel;

        //----------------------------CONSTRUTOR--------------------------------

        /**
         * Construtor da classe Abas.
         */
        public Abas(JPanel panelAbas){
            this.panelAbas = panelAbas;
        }
        
        //--------------------------MÉTODOS PÚBLICOS----------------------------

        //================================OTHER=================================

        /**
         * Adiciona o painel about onde fica dados do software.
         */
        public void addPanelsAbout(){
            //panel = new JPanel();
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelAbas.add(this);
            JTextArea textAreaField = new JTextArea("Visual Grubix: Viewer " +
                    "Simulator Grubix:\n\n" +
                    "       Version: 1.0\n" +
                    "       Platforms: Linux e Windows");
            textAreaField.setFont(new Font(Font.SERIF, Font.BOLD, 12));
            textAreaField.setColumns(33);
            textAreaField.setRows(10);
            textAreaField.setBackground(new Color(0, 0, 0, 0));
            this.add(textAreaField);
        }
        
        /**
         * Adiciona o painel authors onde fica dados dos desenvolvedores.
         */
        public void addPanelsAuthors(){
            //panel = new JPanel();
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelAbas.add(this);
            JTextArea textAreaField = new JTextArea(
                    "Developer:\n" +
                    "    Jesimar da Silva Arantes\n" +
                    "       email: jesimar.arantes@gmail.com\n" +
                    "       Student of Computer Science \n" +
                    "       Federal University of Lavras\n\n" +
                    "Thanks:\n" +
                    "    Márcio da Silva Arantes\n" +
                    "        email: marcio@comp.ufla.br\n" +
                    "        Student of Computer Science \n" +
                    "        Federal University of Lavras\n\n" +
                    "    Tales Heimfarth\n" +
                    "        email: emaildotales@googlemail.com\n" +
                    "        Professor at the Federal University of Lavras\n" +
                    "        Federal University of Lavras");


            textAreaField.setFont(new Font(Font.SERIF, Font.BOLD, 12));
            textAreaField.setColumns(33);            
            textAreaField.setBackground(new Color(0, 0, 0, 0));
            this.add(textAreaField);
        }
        
        /**
         * Adiciona o painel onde fica os dados da licensa do software.
         */
        public void addPanelsLicense(){            
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelAbas.add(this);
            JTextArea textAreaField = new JTextArea("License:\n\n" +
                "The Visual GrubiX is free software; you can redistribute " +
                "it and/or modify it under the terms of the GNU General Public " +
                "License as published by the Free Software Foundation; either " +
                "version 2 of the License, or (at your option) any later version. " +
                "The Visual GrubiX is distributed in the hope that it will be " +
                "useful, but WITHOUT ANY WARRANTY; without even the implied " +
                "warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. " +
                "See the GNU General Public License for more details.");
            textAreaField.setFont(new Font(Font.SERIF, Font.ITALIC, 12));
            textAreaField.setColumns(33);            
            textAreaField.setBackground(new Color(0, 0, 0, 0));
            textAreaField.setLineWrap(true);
            this.add(textAreaField);
        }
    }
    
    /**
     * Colocar no about:
     * versão, ambiente (multiplataforma), linguagem, desenvolvedor, 
     * orientador, agradecimentos, site.
     */
}
