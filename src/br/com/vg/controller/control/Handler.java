/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vg.controller.control;

import br.com.vg.controller.resources.ResourcesGraphics;
import br.com.vg.view.window.ChooseLanguage;
import br.com.vg.view.window.iConstants;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 * Classe que controla os ponteiros das estruturas.
 * @author Jesimar Arantes
 */
public class Handler {    

    /**
     * Instancia da classe.
     */
    private static Handler instance = null;
    
    /**
     * Atributo que controla o idioma do software.
     */
    private ResourceBundle idioma;
    
    private DataSimulation dataSimulation;
    
    private ResourcesGraphics resources;

    /**
     * Construtor padrão da classe.
     */
    public Handler() {
    }   
    
    /**
     * Metodo principal do visualizador.
     * Carrega o software.
     * @param args the command line arguments
     */
    public static void main(String[] args){
        getInstance().loadResourcesGraphics();                    
        //new ChooseLanguage();    
    }
    
    private void loadResourcesGraphics(){
        Executors.newSingleThreadExecutor().execute(new Runnable(){
            public void run(){
                try{
                    resources = new ResourcesGraphics();
                    new ChooseLanguage(iConstants.PORTUGUES, false);    
                } catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: \n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }        

    /**
     * Captura a instancia desta classe.
     * @return instancia desta classe.
     */
    public static Handler getInstance(){
        if(instance == null){
            instance = new Handler();
        }
        return instance;
    }
    
    public ResourcesGraphics getResources(){
        return resources;
    }
    
    public void setIdioma(ResourceBundle idioma){
        this.idioma = idioma;
    }
    
    public ResourceBundle getIdioma(){
        return idioma;
    }

    public DataSimulation getDataSimulation() {
        return dataSimulation;
    }

    public void setDataSimulation(DataSimulation dataSimulation) {
        this.dataSimulation = dataSimulation;
    }        
}
