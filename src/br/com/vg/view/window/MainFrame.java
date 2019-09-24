
package br.com.vg.view.window;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.control.Handler;
import br.com.vg.controller.control.Simulation;
import br.com.vg.controller.resources.ResourcesGraphics;
import br.com.vg.controller.xml.ReaderLogXmlDOM;
import br.com.vg.controller.xml.ReaderLogXmlSAX;
import br.com.vg.util.iCallBack;
import br.com.vg.view.others.ButtonsAbstract;
import br.com.vg.view.others.TabAbstract;
import br.com.vg.view.paint.DrawSpaceViewer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe onde é gerenciado os recursos da interface grafica do visualizador.
 * @author Márcio Arantes e Jesimar Arantes.
 */
public final class MainFrame extends JFrame{

    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Parte responsável por gerenciar o tema do software.
     */
    private final LookAndFeelInfo[] LookAndFeels = UIManager
            .getInstalledLookAndFeels();

    /**
     * Local onde são alocados as abas e os botões de opções das abas.
     */
    private final JTabbedPane tabbedPane;

    /**
     * Aba arquivo, onde são colocados os recursos relacionados a abrir,
     * otimizar e fechar o software.
     */
    private final TabAbstract tabFile;

    /**
     * Aba Layout (lockAndFeel, onde são colocados os recursos para mudar o
     * tema da interface grafica.
     */
    private final TabAbstract tabLayout;

    /**
     * Aba ajuda, onde são colocados os recursos da ajuda do software.
     */
    private final TabAbstract tabHelp;
    
    /**
     * Aba onde mostrará os dados da simulação.
     */
    private final AbaConfiguration abaConfiguration;
    
    /**
     * Aba onde ficam as opções de controle do mapping.
     */
    private final AbaMapping abaMapping;
    
    /**
     * Aba onde mostrará os dados da simulação.
     */
    private final AbaData abaData;

    /**
     * Aba onde mostrará os dados da simulação.
     */
    private final AbaDescription abaDescription; 
    
    /**
     * Painel onde ficam as opções, mapping dados, etc do software.
     */
    private final JTabbedPane abasConfiguration = new JTabbedPane();
    
    /**
     * Objeto usado para abrir os arquivos XML.
     */
    private final JFileChooser fileChooserXML; 
    
    /**
     * Objeto usado para abrir os arquivos XML.
     */
    private final JFileChooser fileChooserIMG;  

    /**
     * Area de desenho do software, onde fica a rede sem fio.
     */
    private final DrawSpaceViewer draw;

    /**
     * Parte responsável por gerenciar a animação da simulação.
     */
    private Simulation simulation; 
    
    /**
     * Atributo que controla o idioma do software.
     */
    private ResourceBundle idioma;
    
    /**
     * Comprimento do repositório de abas onde fica as opções, mapping, data e etc.
     */
    private int compX = 300;     

    /**
     * Contador que conta a quantidade de print screens tirada para não sobrepor
     * uma determinda imagem alterando assim o nome da imagem.
     */
    private int indexImg = 0;            
    
    /**
     * Barra de progresso do software.
     */
    private final JProgressBar progressBar;

    /**
     * Barra de tempo onde é mostrado o progresso da simulação baseada nos
     * indices dos eventos.
     */
    private final JSlider timeBar;            

    /**
     * Painel de botões que controlam a simulação (Play, Stop, Back, etc).
     */
    private final ButtonsAbstract buttonsSimulation;   

    /**
     * Parte responsável por armazenar os dados da simulação.
     */
    private DataSimulation dataSimulation = null;
    
    private int width = 1280;
    
    private int height = 740;
    
    private ResourcesGraphics resources = Handler.getInstance().getResources();
    
    //----------------------------CONSTRUTORES----------------------------------

    /**
     * Construtor da classe.
     * @throws InterruptedException - Exceção lançada.
     */
    public MainFrame() throws InterruptedException {       
        
        idioma = Handler.getInstance().getIdioma();
        
        this.setTitle(idioma.getString("titulo"));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        //=========================Criação da Simulação=========================

        simulation = new Simulation(new iCallBack() {
            public void repaint() {
                draw.repaint();
            }

            public void update(int index, double speed, double time) {
                if (timeBar != null){                  
                    timeBar.setValue(index+1);                    
                }
                String strTime = String.format(idioma.getString("time") + 
                        ": %.2f / %.2f", time, (dataSimulation != null ? 
                        dataSimulation.getListEvents().getLast().getTime() : 0.0));
                
                String strIndexEvent = String.format(idioma.getString("index_event") +
                        ": %d / %d", index+1, (dataSimulation != null ?
                        dataSimulation.getSizeIndexEvent() : 0));

                if (tabFile != null){
                    tabFile.updataLabel("time", strTime);
                    tabFile.updataLabel("event", strIndexEvent);                    
                    tabFile.updataLabel("speed", 
                            String.format(idioma.getString("speed") + ": %s",
                            (speed != 0.0f ? 100.0/speed + "" : "Maximo")));
                    tabFile.isVisibleLabel("time", true);
                    tabFile.isVisibleLabel("event", true);                    
                    tabFile.isVisibleLabel("speed", true);
                }
                if (abaData != null){
                    abaData.viewData(dataSimulation, simulation, index, time);
                }
            }
        });
        simulation.start();
        simulation.suspend();        

        //======================Criação do Painel Principal=====================

        tabbedPane = new JTabbedPane(){
            @Override
            public void setPreferredSize(Dimension preferredSize) {
                super.setPreferredSize(preferredSize);
                for(Component comp : this.getComponents()){
                    if(comp instanceof TabAbstract){
                        JPanel panel = (TabAbstract) comp;
                        panel.setPreferredSize(new Dimension(preferredSize.width,
                                preferredSize.height));
                    }
                }
            }
        };

        //==========================Criação do menu File========================
        
        tabFile = new TabAbstract() {
            @Override
            public void ActionPerformed(ActionEvent e, String item, byte index)
                    throws Exception {
                menuFileEvent(e, item, index);
            }
        };
        tabFile.addMenuItem(
            idioma.getString("abrir") + " (Alt+O)",  
            KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_MASK),
            "Open Time Real",  KeyStroke.getKeyStroke(KeyEvent.VK_T,
                InputEvent.CTRL_MASK),
            idioma.getString("close_sim"), KeyStroke.getKeyStroke(KeyEvent.VK_C,
                InputEvent.CTRL_MASK), false,
            "------------",
            idioma.getString("exit_program") + " (Alt+E)", 
            KeyStroke.getKeyStroke(KeyEvent.VK_E,
                InputEvent.CTRL_MASK)
        );
        tabbedPane.addTab(idioma.getString("arquivo"), tabFile);
        
        tabFile.addLabelInformation(            
            "time", false,
            "event", false,
            "---------",
            "coord", false,
            "speed", false
        );

        //====================Criação do Menu Look And Feel=====================
        
        tabLayout = new TabAbstract(){
            @Override
            public void ActionPerformed(ActionEvent e, String item, byte index)
                    throws Exception {
                setLookAndFeel(LookAndFeels[index].getClassName());
            }
        };
        tabLayout.addRadioButtonMenuItem(LookAndFeels);
        tabbedPane.addTab("Look and Feel", tabLayout);

        //=======================Criação do Menu Help===========================

        tabHelp = new TabAbstract(){
            @Override
            public void ActionPerformed(ActionEvent e, String item, byte index)
                    throws Exception {
                menuHelpEvent(e, item, index);
            }
        };
        tabHelp.addMenuItem(
            "Manual Visual GrubiX", KeyStroke.getKeyStroke(KeyEvent.VK_M,
                InputEvent.CTRL_MASK),
            "Manual GrubiX", KeyStroke.getKeyStroke(KeyEvent.VK_G,
                InputEvent.CTRL_MASK),
            "Web Site (Alt+W)", KeyStroke.getKeyStroke(KeyEvent.VK_W,
                InputEvent.CTRL_MASK),
            "------------",
            idioma.getString("sobre") + " (Alt+A)", KeyStroke.getKeyStroke(KeyEvent.VK_A,
                InputEvent.CTRL_MASK)
        );
        tabbedPane.addTab(idioma.getString("ajuda"), tabHelp);

        //=====================Criação da Barra de Progresso====================

        progressBar = new JProgressBar(0, 100){
            @Override
            public void setValue(int n) {
                super.setValue(n);
                progressBar.repaint();
            }
        };
        progressBar.setValue(0);
        progressBar.setVisible(true);        
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);

        //=======================Criação da Barra de Tempo======================

        timeBar = new JSlider();
        timeBar.setVisible(false);
        timeBar.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                simulation.setIndexSimulation(timeBar.getValue() - 1);
            }
        });                                        

        //========================Criação da DrawSpace==========================

        draw = new DrawSpaceViewer(simulation){           
            @Override
            public void updateCoord(double x, double y){                
                String strCoordXY = String.format("Coord: (%5.2f ; %5.2f)",
                x / 10, y / 10);
                if (tabFile != null && dataSimulation != null){
                    tabFile.updataLabel("coord", strCoordXY);
                    tabFile.isVisibleLabel("coord", true);
                }
            }
        };
        draw.setLayout(new FlowLayout());

        //======Criação das Abas Opções, Mapping, Data e Description=====

        abaConfiguration = new AbaConfiguration(simulation, draw, compX, height);                           
        abasConfiguration.add(idioma.getString("options"), abaConfiguration);            

        abaMapping = new AbaMapping(compX, height);        
        abasConfiguration.add(idioma.getString("mapping"), abaMapping);       

        abaData = new AbaData(compX, height);        
        abasConfiguration.add(idioma.getString("data"), abaData);        

        abaDescription = new AbaDescription(compX, height);
        abasConfiguration.add(idioma.getString("description"), abaDescription);

        //============Criação do Menu Botões Gerenciamento Simulação============

        buttonsSimulation = new ButtonsAbstract() {
            @Override
            public void ActionPerformed(ActionEvent e, String item, byte index)
                    throws Exception {
                menuButtonEvent(e, item, index);
            }
        };
        resources = Handler.getInstance().getResources();
        buttonsSimulation.addButtons(
                idioma.getString("reset"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_STOP)),

                idioma.getString("return"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_BACK)),

                idioma.getString("back"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_RETURN)),

                idioma.getString("pause"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_PAUSE)),

                idioma.getString("play"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_PLAY)),

                idioma.getString("advanced"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_ADVANCE)),

                idioma.getString("rapid"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_TIME_ZERO)),

                idioma.getString("slow"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_TIME_LOW)),

                idioma.getString("great"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_TIME_HIGH)),

                idioma.getString("printscreen"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_PRINT_SCREEN)), 
                
                idioma.getString("zoommais"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_ZOOM1)),
                
                idioma.getString("zoommenos"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_ZOOM2)),
                
                idioma.getString("ajustar"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_RESET_SYSTEM)),
                
                idioma.getString("carregar_fundo"),
                new ImageIcon(resources.getImageIcons(
                ResourcesGraphics.ICON_OPEN))
            );
        buttonsSimulation.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsSimulation.setBackground(Color.lightGray);
        buttonsSimulation.setPreferredSize(new Dimension(width - 30, 50));
        buttonsSimulation.setDisabled();

        //==============Criação da Tela de Escolha de Arquivo XML===============

        fileChooserXML = new JFileChooser(".");
        fileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooserXML.setMultiSelectionEnabled(false);        
        fileChooserXML.setFileFilter(new FileNameExtensionFilter("Files .xml", "xml"));
        
        //==============Criação da Tela de Escolha de Arquivo IMG===============

        fileChooserIMG = new JFileChooser(".");
        fileChooserIMG.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooserIMG.setMultiSelectionEnabled(false);        
//        fileChooserIMG.setFileFilter(new FileNameExtensionFilter("Files .png", "xml"));
        
        //================Adicionar Componentes na Tela Principal===============

        this.add(tabbedPane);
        this.add(abasConfiguration);       
        this.add(draw);
        this.add(progressBar);
        this.add(timeBar);       
        this.add(buttonsSimulation);
        
        this.setSize(width, height);       
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                setSize(getWidth(), getHeight());
            }
        });   
        this.setVisible(true);
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //================================OTHER=====================================

    /**
     * Atualiza as dimensões da janela.
     * @param width - comprimento.
     * @param height - largura.
     */
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);        
        tabbedPane.setPreferredSize(new Dimension(width-30, 100));
        abasConfiguration.setPreferredSize(new Dimension(compX, height - 230));
        abaConfiguration.setPreferredSize(new Dimension(compX, height - 250));
        abaMapping.setPreferredSize(new Dimension(compX, height - 250));
        abaData.setPreferredSize(new Dimension(compX, height - 250));
        abaDescription.setPreferredSize(new Dimension(compX, height - 250));        
        draw.setPreferredSize(new Dimension(width - compX - 30, height - 230));
        buttonsSimulation.setPreferredSize(new Dimension(width - 30, 50));
        progressBar.setPreferredSize(new Dimension(width-30, 23));
        timeBar.setPreferredSize(new Dimension(width-30, 23));
    }

    //--------------------------MÉTODOS PRIVADOS--------------------------------

    //================================OTHER=====================================

    /**
     * Evento de clique em algum botão de menu file.
     */
    private void menuFileEvent(ActionEvent e, String item, int index)
            throws Exception {
        if(item.equalsIgnoreCase(idioma.getString("abrir")
            + " (Alt+O)")){
            openFile(iConstants.OPEN_XML_NORMAL);
        }else if (item.equalsIgnoreCase("Open Time Real")){
            openFile(iConstants.OPEN_XML_TIME_REAL);
        }else if(item.equalsIgnoreCase(idioma.getString("close_sim"))){
            close();
        }else if(item.equalsIgnoreCase(idioma.getString("exit_program") + " (Alt+E)")){
            exit();
        }else{
            throw new Exception("\nEvent from iten '" + item +
                    "' not is implemented yet\n");
        }
    }

    /**
     * Evento de clique em algum botão de menu help.
     */
    private void menuHelpEvent(ActionEvent e, String item, byte index)
            throws Exception {
        if (item.equalsIgnoreCase("Manual Visual GrubiX")) {
            manualVisualizador();
        }else if (item.equalsIgnoreCase("Manual GrubiX")) {
            manualGrubix();
        }else if(item.equalsIgnoreCase("Web Site (Alt+W)")){
            webSite();
        }else if(item.equalsIgnoreCase(idioma.getString("sobre")
            + " (Alt+A)")){
            about();
        }else{
            throw new Exception(idioma.getString("exc_1") + item + 
                    idioma.getString("exc_2"));
        }
    }

    /**
     * Evento de clique em algum botão que gerencia a simulação.
     */
    private void menuButtonEvent(ActionEvent e, String item, byte index){
        if (index == 0){
            simulation.resetSimulation();
            timeBar.setValue(0);
        }else if (index == 1){
            simulation.setIndexSimulationBack();
        }else if (index == 2){
            simulation.back();
        }else if (index == 3){
            simulation.pause();
        }else if (index == 4){
            simulation.play();            
        }else if (index == 5){
            simulation.setIndexSimulationAdvanced();
        }else if (index == 6){
            simulation.setSpeedAnimation(0.0f);
        }else if (index == 7){
            if (simulation.getSpeedAnimation() == 0){
                simulation.setSpeedAnimation(2.5f);
            }else if (simulation.getSpeedAnimation() < 50){
                simulation.setSpeedAnimation(simulation.getSpeedAnimation()*2);
            }
        }else if (index == 8){
            if (simulation.getSpeedAnimation() > 1){
                simulation.setSpeedAnimation(simulation.getSpeedAnimation()/2);
            }
        }else if (index == 9){
            BufferedImage img = new BufferedImage(this.getWidth() - compX - 30,
                    this.getHeight() - 230, BufferedImage.TYPE_3BYTE_BGR);
            draw.paintComponent(img.getGraphics());
            try {
                ImageIO.write(img, "png", new File("img" + indexImg + ".png"));
                ++indexImg;
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "\nError Print Screen: \n" + 
                        ex, "Error print screen", JOptionPane.ERROR_MESSAGE);
            }
        }else if (index == 10){
            draw.clickedZoomMais();
        }else if (index == 11){
            draw.clickedZoomMenos();
        }else if (index == 12){
            draw.ajustarWindow();
        }else if (index == 13){
            int result = fileChooserIMG.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                final File file = fileChooserIMG.getSelectedFile();                
                draw.setImgBackground(new ImageIcon(file.getPath()));
                draw.setImgBackground(true);
                draw.updateUI();
            }else{
                draw.setImgBackground(false);
                draw.updateUI();
            }            
        }
    }

    /**
     * Seta o novo tema passada como sendo o tema atual do software.
     */
    private void setLookAndFeel(String lookAndFeelds) {
        try {
            UIManager.setLookAndFeel(lookAndFeelds);
            SwingUtilities.updateComponentTreeUI(this);
            fileChooserXML.updateUI();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nErro: ClassNotFoundException\n",
                    "Update LookAndFeel", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nErro: InstantiationException\n",
                    "Update LookAndFeel", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nErro: IllegalAccessException\n",
                    "Update LookAndFeel", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nErro: UnsupportedLookAndFeelException\n",
                    "Update LookAndFeel", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abri a tela de abrir arquivos xml.
     */
    private void openFile(final int open) {                   
        int result = fileChooserXML.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooserXML.getSelectedFile();
            Executors.newSingleThreadExecutor().execute(new Runnable(){
                public void run(){
                    timeBar.setVisible(false);
                    progressBar.setValue(0);
                    progressBar.setVisible(true);
                    
                    /**
                    * Abri um arquivo xml de modo normal JDOM para exibição da 
                    * simulação.
                    */
                    if (open == iConstants.OPEN_XML_NORMAL){                       
                        openNormal(file);
                    }
                    /**
                    * Abri arquivo em time real (SAX) assim vai ser carregado 
                    * linha a linha. (Usado em arquivos grandes demais).
                    */
                    else if (open == iConstants.OPEN_XML_TIME_REAL){
                        openTimeReal(file);
                    }                                       
                                        
                    progressBar.setVisible(false); 
                    timeBar.setValue(0);
                    timeBar.setVisible(true);
                }
            });
        }
    }      
    
    private void openNormal(File file){
        try{           
            DataSimulation data = new DataSimulation();
            ReaderLogXmlDOM readDOM = new ReaderLogXmlDOM();
            readDOM.readXML(file, data, progressBar);            
            initSimulation(data);
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nError Open File: \n" +
                    ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    private void openTimeReal(File file){
        try{
            DataSimulation data = new DataSimulation();
            ReaderLogXmlSAX readSax = new ReaderLogXmlSAX(file, data, progressBar);
            readSax.readXML();                        
            initSimulation(data);
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nError Open File: \n" +
                ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initSimulation(DataSimulation data){
        desableConfigComponents(); 
        
        dataSimulation = data;
        Handler.getInstance().setDataSimulation(dataSimulation);     
        
        dataSimulation.valuesDefault();
                
        abaConfiguration.configurationInit();
        abaMapping.valuesDefault(dataSimulation);            
        abaDescription.viewDescription(dataSimulation.getDescriptionApp());
        buttonsSimulation.setEnabledJButtons(true);          

        tabFile.setEnabled(idioma.getString("close_sim"), true);
        tabFile.isVisibleLabel("coord", true);
        tabFile.isVisibleLabel("time", true);
        tabFile.isVisibleLabel("event", true);
        tabFile.isVisibleLabel("speed", true);           

        simulation.initSimulation(dataSimulation);
        draw.setDataSimulation(dataSimulation);
        draw.resetSystem();
        draw.setCentro(1200, 700);//???

        timeBar.setMinimum(0);
        timeBar.setMaximum(dataSimulation.getListEvents().size());            

        simulation.resume(); 
        
        if (abaData != null){
            abaData.viewData(dataSimulation, simulation, 0, 0);
        }
    }
    
    /**
     * Desabilita as configurações dos componentes da simulação.
     */
    private void desableConfigComponents(){        
        simulation.valuesDefault();        
        draw.valuesDefault();        
               
        abaConfiguration.valuesDefault();
        abaMapping.valuesDefault();
        abaData.valuesDefault();
        abaDescription.valuesDefault();
        
        timeBar.setVisible(false);
        progressBar.setValue(0);
        progressBar.setVisible(true);        
        buttonsSimulation.setEnabledJButtons(false);                
        
        tabFile.setEnabled(idioma.getString("close_sim"), false);
        tabFile.isVisibleLabel("coord", false);
        tabFile.isVisibleLabel("time", false);
        tabFile.isVisibleLabel("event", false);
        tabFile.isVisibleLabel("speed", false);
        
        simulation.suspend();
        dataSimulation = null;
        draw.setDataSimulation(dataSimulation);
    }
    
    public void setVisible(){
        this.setVisible(true);
    }

    /**
     * Fecha a execução de uma simulação.
     */
    private void close(){
        int close = JOptionPane.showConfirmDialog(null, 
                idioma.getString("ask_close_program"), 
                idioma.getString("close_sim"),
                JOptionPane.YES_NO_OPTION);
        if (close == 0){
            desableConfigComponents();
        }
    }

    /**
     * Sai do programa.
     */
    private void exit() {
        int exit = JOptionPane.showConfirmDialog(null, 
                idioma.getString("ask_exit_program"), 
                idioma.getString("exit_program"), 
                JOptionPane.YES_NO_OPTION);
        if (exit == 0){
            System.exit(0);
        }
    }

    /**
     * Abri a tela de sobre o visual grubix.
     */
    private void about() {
        new About(idioma.getString("about_vg"));
    }

    /**
     * Abri o manual do visualizador grubix.
     */
    private void manualVisualizador(){

        File fileManual = null;
        try {
            fileManual = new File("./manuais/ManualVisualizador.pdf");           
            Desktop.getDesktop().open(fileManual);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,  "\nError open file manual do "
                    + "visualizador grubix \n" + ex + "\nPath = " +
                    fileManual.getAbsolutePath(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abri o manual do grubix.
     */
    private void manualGrubix(){
        File fileManual = null;
        try {            
            fileManual = new File("./manuais/ManualGrubix.pdf");
            Desktop.getDesktop().open(fileManual);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,  "\nError open file manual "
                    + "do grubix\n" + ex + "\nPath = " +
                    fileManual.getAbsolutePath(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abri o site do grubi.
     */
    private void webSite() {
        try {
            Desktop desktop;
            //Primeiro verificamos se é possível a integração com o desktop
            if (!Desktop.isDesktopSupported()) {
                throw new IllegalStateException("\nDesktop resources not supported!\n");
            }
            desktop = Desktop.getDesktop();
            //Agora vemos se é possível disparar o browser default.
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                throw new IllegalStateException("\nNo default browser set!\n");
            }
            URI uri = new URI("http://pesquisa.dcc.ufla.br/grub/");
            //Dispara o browser default, que pode ser o Explorer, Firefox ou outro.
            desktop.browse(uri);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nError open site web: \n" +
                    ex.getMessage(), "Error Site: ", JOptionPane.ERROR_MESSAGE);
        }
    }
}
