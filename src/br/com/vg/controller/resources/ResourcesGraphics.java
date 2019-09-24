
package br.com.vg.controller.resources;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Classe responsavel por carregar as imagens utilizadas no simulador.
 * @author Jesimar Arantes
 */
public final class ResourcesGraphics implements iResourcesConstants{

    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Campo responsavel por conter a URL das imagens.
     */
    private URL imgUrl[];

    /**
     * Campo responsavel por conter a URL das imagens dos nós.
     */
    private URL imgUrlNodes[];

    /**
     * Campo responsavel por conter a URL das imagens dos intrusos.
     */
    private URL imgUrlIntruder[];

    /**
     * Campo responsavel por conter a URL das imagens dos uavs.
     */
    private URL imgUrlUavs[];

    /**
     * Campo responsavel por conter a URL das imagens.
     */
    private URL imgUrlIcons32x32[];

    /**
     * Campo onde são armazenados as imagens propriamente ditas.
     */
    private ImageIcon imgs[];

    /**
     * Campo onde são armazenados as imagens propriamente ditas dos nós.
     */
    private ImageIcon imgsNodes[];

    /**
     * Campo onde são armazenados as imagens propriamente ditas dos intrusos.
     */
    private ImageIcon imgsIntruder[];

    /**
     * Campo onde são armazenados as imagens propriamente ditas dos uavs.
     */
    private ImageIcon imgsUavs[];

    /**
     * Campo onde são armazenados as imagens propriamente ditas.
     */
    private ImageIcon imgsIcons32x32[];

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe ResourcesGraphics.
     * @throws Exception - Exceção Lançada.
     */
    public ResourcesGraphics() throws Exception{
        try{            
            loadImagesURL();
            loadImagesImageIcon();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new Exception("\nError loader images \n" + ex);
        }
    }

    //--------------------------MÉTODOS PRIVADOS--------------------------------

    //================================OTHER=====================================

    /**
     * Médodo responsável por carregar o vetor com as URLs das imagens.
     */
    private void loadImagesURL(){

        String path = "/br/com/vg/resources/images/";

        imgUrl = new URL[]{
            this.getClass().getResource(path + "background/imageApresentation.png"),
            this.getClass().getResource(path + "background/logo.png"), 
            this.getClass().getResource(path + "background/apresentation.png"),             
            this.getClass().getResource(path + "background/apresentation-ing.png"),
        };

        imgUrlNodes = new URL[]{
            this.getClass().getResource(path + "nodes/mote0.png"),
            this.getClass().getResource(path + "nodes/mote1.png"),
            this.getClass().getResource(path + "nodes/mote2.png"),
            this.getClass().getResource(path + "nodes/mote3.png"),
            this.getClass().getResource(path + "nodes/taxi0.png"),
            this.getClass().getResource(path + "nodes/taxi1.png"),
        };

        imgUrlIntruder = new URL[]{
            this.getClass().getResource(path + "nodes/intruder0.png"),
            this.getClass().getResource(path + "nodes/intruder1.png"),
            this.getClass().getResource(path + "nodes/intruder2.png"),
            this.getClass().getResource(path + "nodes/intruder3.png"),
            this.getClass().getResource(path + "nodes/intruder4.png"),
            this.getClass().getResource(path + "nodes/intruder5.png"),
        };

        imgUrlUavs = new URL[]{
            this.getClass().getResource(path + "nodes/uav0.png"),
            this.getClass().getResource(path + "nodes/uav1.png"),
            this.getClass().getResource(path + "nodes/uav2.png"),
            this.getClass().getResource(path + "nodes/uav3.png"),
            this.getClass().getResource(path + "nodes/uav4.png"),
            this.getClass().getResource(path + "nodes/uav5.png"),
        };

        imgUrlIcons32x32 = new URL[]{
            this.getClass().getResource(path + "icons/btn_play.png"),
            this.getClass().getResource(path + "icons/btn_pause.png"),
            this.getClass().getResource(path + "icons/btn_return.png"),
            this.getClass().getResource(path + "icons/btn_advance.png"),
            this.getClass().getResource(path + "icons/btn_back.png"),                        
            this.getClass().getResource(path + "icons/btn_time_zero.png"),
            this.getClass().getResource(path + "icons/btn_time_low.png"),
            this.getClass().getResource(path + "icons/btn_time_high.png"),
            this.getClass().getResource(path + "icons/btn_stop.png"),
            this.getClass().getResource(path + "icons/btn_print_screen.png"),
            this.getClass().getResource(path + "icons/btn_zoom1.png"),
            this.getClass().getResource(path + "icons/btn_zoom2.png"),
            this.getClass().getResource(path + "icons/btn_reset_system.png"),
            this.getClass().getResource(path + "icons/btn_open.png"),
        };

    }

    /**
     * Método responsável por carregar as imagens para o vetor de imagens
     * utilizadas no simulador.
     */
    private void loadImagesImageIcon() {
        imgs = new ImageIcon[imgUrl.length];
        
        for (byte i = 0; i < imgUrl.length; ++i){
            imgs[i] = new ImageIcon(imgUrl[i]);
        }
        
        imgsNodes = new ImageIcon[imgUrlNodes.length];

        for (byte i = 0; i < imgUrlNodes.length; ++i){
            imgsNodes[i] = new ImageIcon(imgUrlNodes[i]);
        }

        imgsIntruder = new ImageIcon[imgUrlIntruder.length];

        for (byte i = 0; i < imgUrlIntruder.length; ++i){
            imgsIntruder[i] = new ImageIcon(imgUrlIntruder[i]);
        }

        imgsUavs = new ImageIcon[imgUrlUavs.length];

        for (byte i = 0; i < imgUrlUavs.length; ++i){
            imgsUavs[i] = new ImageIcon(imgUrlUavs[i]);
        }

        imgsIcons32x32 = new ImageIcon[imgUrlIcons32x32.length];

        for (byte i = 0; i < imgUrlIcons32x32.length; ++i){
            imgsIcons32x32[i] = new ImageIcon(imgUrlIcons32x32[i]);
        }        
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //=================================GET======================================

    /**
     * Metodo que retorna a imagem procurada.
     * @param i - indice da imagem.
     * @return Imagem correspondente ao indice indicado.
     */
    public Image getImage(int i){                
        return imgs[i].getImage();
    }

    /**
     * Metodo que retorna a imagem procurada do nó.
     * @param i - indice da imagem.
     * @return Imagem correspondente ao indice indicado.
     */
    public Image getImageNodes(int i){
        return imgsNodes[i].getImage();
    }

    /**
     * Metodo que retorna a imagem procurada do intruso.
     * @param i - indice da imagem.
     * @return Imagem correspondente ao indice indicado.
     */
    public Image getImageIntruder(int i){
        return imgsIntruder[i].getImage();
    }

    /**
     * Metodo que retorna a imagem procurada do uav.
     * @param i - indice da imagem.
     * @return Imagem correspondente ao indice indicado.
     */
    public Image getImageUavs(int i){
        return imgsUavs[i].getImage();
    }

    /**
     * Metodo que retorna a imagem procurada.
     * @param i - indice da imagem.
     * @return Imagem correspondente ao indice indicado.
     */
    public Image getImageIcons(int i){
        return imgsIcons32x32[i].getImage();
    }
}
