
package br.com.vg.view.paint;

import br.com.vg.controller.structure.Node;
import static br.com.vg.controller.structure.iConstants.SIZE_CYCLES_ANIM;
import br.com.vg.util.Util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * Classe responsável por pintar na tela a forma de envio dos pacotes.
 * @author Jesimar Arantes
 */
public final class PaintSendPacket {

    /**
     * Construtor padrão.
     */
    public PaintSendPacket(){
        
    }

    //-----------------------------MÉTODOS PÚBLICOS-----------------------------

    //=================================OUTHER===================================

    /**
     * Executa a animação do envio do pacote em formato de pequenos pacotes
     * individuais sincronizados (forma tradicional).
     * @param g2 - Graphics2D responsável por pintar na tela o envio dos pacotes.
     * @param color - cor dos pacotes enviados.
     * @param source - nó de origem do envio de pacotes.
     * @param i - indice para animar os pacotes.     
     * @param destinationNode - nós destino a serem enviados os pacotes.
     */
    public void animPacketSynchronous(Graphics2D g2, Color color, 
            Node source, byte i, LinkedList<Node> destinationNode, int radiusNode){

        int SIZE = radiusNode;
        g2.setColor(color);
        double angle;
        int x;
        int y;
        float dist;
        for (Node node: destinationNode){

            angle = Math.atan2(node.getCoordY() -  source.getCoordY(),
                    node.getCoordX() - source.getCoordX());

            x = (int)(source.getCoordX() + (i * source.getRadiusCommunication()
                    / (SIZE_CYCLES_ANIM - 1) * Math.cos(angle)));
            y = (int)(source.getCoordY() + (i * source.getRadiusCommunication()
                    / (SIZE_CYCLES_ANIM - 1) * Math.sin(angle)));

            dist = Util.calcDistEuclidiana(node.getCoordX(),
                    node.getCoordY(), source.getCoordX(), source.getCoordY());

            if (i * source.getRadiusCommunication() / (SIZE_CYCLES_ANIM - 1) < dist){
                g2.fillRect(x - SIZE/2, y - SIZE/2,
                        SIZE, SIZE);
            }else{
                g2.fillRect((int)node.getCoordX() - SIZE/2,
                        (int)node.getCoordY() - SIZE/2,
                        SIZE, SIZE);
            }
        }
    }

    /**
     * Executa a animação do envio do pacote em formato de pequenos pacotes
     * individuais sem serem sincronizados.
     * @param g2 - Graphics2D responsável por pintar na tela o envio dos pacotes.
     * @param color - cor dos pacotes enviados.
     * @param source - nó de origem do envio de pacotes.
     * @param i - indice para animar os pacotes.    
     * @param destinationNode - nós destino a serem enviados os pacotes.
     */
    public void animPacketAsynchronous(Graphics2D g2, Color color,
            Node source, byte i, LinkedList<Node> destinationNode, int radiusNode){

        int SIZE = radiusNode;
        g2.setColor(color);
        for (Node node: destinationNode){
            g2.fillRect((
                    (int)(source.getCoordX() * (SIZE_CYCLES_ANIM - 1 - i) +
                    node.getCoordX() * i) / (SIZE_CYCLES_ANIM - 1) - SIZE/2),
                    (int)((source.getCoordY() * (SIZE_CYCLES_ANIM - 1 - i) +
                    node.getCoordY() * i) / (SIZE_CYCLES_ANIM - 1) - SIZE/2),
                    SIZE, SIZE
                );
        }
    }

    /**
     * Executa a animação do envio do pacote em formato de onda.
     * @param g2 - Graphics2D responsável por pintar na tela o envio dos pacotes.
     * @param color - cor dos pacotes enviados.
     * @param source - nó de origem do envio de pacotes.
     * @param i - indice para animar os pacotes.     
     */
    public void animWave(Graphics2D g2, Color color, Node source, byte i){

        int coordX = (int) (source.getCoordX() -
                i * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int coordY = (int) (source.getCoordY() -
                i * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int w = (int) (i * 2 * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int h = (int) (i * 2 * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);

        g2.setColor(color);
        g2.drawOval(coordX, coordY, w, h);
    }

    /**
     * Executa a animação do envio do pacote em formato de onda preenchido o centro.
     * @param g2 - Graphics2D responsável por pintar na tela o envio dos pacotes.
     * @param source - nó de origem do envio de pacotes.
     * @param i - indice para animar os pacotes.     
     */
    public void animWave2(Graphics2D g2, Node source, byte i){

        int coordX = (int) (source.getCoordX() -
                i * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int coordY = (int) (source.getCoordY() -
                i * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int w = (int) (i * 2 * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int h = (int) (i * 2 * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);

        g2.setColor(new Color(0, 0, 255, 125));
        g2.fillOval(coordX, coordY, w, h);
    }

    /**
     * Executa a animação do envio do pacote em formato de onda preenchido o
     * centro diminuindo a intensidade do sinal.
     * @param g2 - Graphics2D responsável por pintar na tela o envio dos pacotes.
     * @param source - nó de origem do envio de pacotes.
     * @param i - indice para animar os pacotes.     
     */
    public void animWave3(Graphics2D g2, Node source, byte i){

        int coordX = (int) (source.getCoordX() -
                i * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int coordY = (int) (source.getCoordY() -
                i * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int w = (int) (i * 2 * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);
        int h = (int) (i * 2 * source.getRadiusCommunication() / SIZE_CYCLES_ANIM);

        g2.setColor(new Color(0, 0, 255, 255 - 255*i/30));
        g2.fillOval(coordX, coordY, w, h);
    }
}
