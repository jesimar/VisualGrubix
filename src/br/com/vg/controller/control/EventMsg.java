
package br.com.vg.controller.control;

import br.com.vg.controller.structure.Node;
import static br.com.vg.controller.structure.iConstants.*;
import br.com.vg.util.iCallBack;
import br.com.vg.view.paint.PaintSendPacket;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * Classe responsável pelos eventos estáticos ou, que se movem e tem animação
 * de envio de pacotes, no simulador.
 * @author Jesimar Arantes
 */
public final class EventMsg extends EventGeneric{

    //-----------------------------ATRIBUTOS------------------------------------   

    /**
     * Quantidade de pacotes enviados na rede até este instante.
     */
    private int amountPacket;

    /**
     * Nó de origem do evento atual.
     */
    private Node sourceNode;

    private PaintSendPacket paintSendPk = new PaintSendPacket();

    /**
     * Lista de destinos no envio de pacotes do evento atual.
     */
    private LinkedList<Node> destinationNode = new LinkedList<Node>();

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe EventGeneric.
     * @param sourceNode - nó de origem da animação.
     * @param destinationNode - lista de nós destino no envio de pacotes por evento
     * @param time - tempo atual.
     * @param amountPacket - quantidade de pacote até este instante.
     */
    public EventMsg(Node sourceNode, LinkedList<Node> destinationNode,
            double time, int amountPacket){
        super(time);
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.amountPacket = amountPacket;
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //===============================OTHER======================================

    /**
     * Roda o evento de envio de mensagem do simulador.    
     * @param repaint - callback para desenhar na tela.
     * @param speedAnimation - velocidade da animação.      
     * @throws Exception - Exceção Lançada.
     */
    @Override
    public void runMsg(iCallBack repaint, float speedAnimation) throws Exception {
        try {
            for (byte i = 0; i < SIZE_CYCLES_ANIM; ++i){
                cyclesEvent = i;
                repaint.repaint();
                Thread.sleep((int)(10 * speedAnimation));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw new Exception("\nError Event Animation: \n" + ex);
        }
    }

     /**
     * Pinta a animação do envio dos pacotes.
     * @param g2 - Graphics2D para pintar na tela a animação de envio de pacotes.
     * @param formPacket - valor indicando qual o formato de pacotes para
     * para fazer a animação (Onda, Pacote Síncrono, Pacote Assíncrono).
     * @param color - cor do desenho do envio dos pacotes.
     */
    public void animation(Graphics2D g2, byte formPacket, Color color){

        if(formPacket == PACKET_SYNCHRONOUS){
            paintSendPk.animPacketSynchronous(g2, color, sourceNode,
                    cyclesEvent, destinationNode, 
                    Handler.getInstance().getDataSimulation().getRadiusNode());
        }
        else if (formPacket == PACKET_ASYNCHRONOUS){
            paintSendPk.animPacketAsynchronous(g2, color, sourceNode,
                    cyclesEvent, destinationNode,
                    Handler.getInstance().getDataSimulation().getRadiusNode());
        }
        else if (formPacket == PACKET_WAVE){
            paintSendPk.animWave(g2, color, sourceNode, cyclesEvent);
        }
        else if (formPacket == PACKET_WAVE2){
            paintSendPk.animWave2(g2, sourceNode, cyclesEvent);
        }
        else if (formPacket == PACKET_WAVE3){
            paintSendPk.animWave3(g2, sourceNode, cyclesEvent);
        }
    }

    /**
     * Captura a quantidade de pacotes enviados na rede até este instante.
     * @return quantidade de pacotes enviados na rede até este instante.
     */
    public int getAmountPacket(){
        return amountPacket;
    }
}
