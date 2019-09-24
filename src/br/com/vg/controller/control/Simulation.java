
package br.com.vg.controller.control;

import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.NodeState;
import static br.com.vg.controller.structure.iConstants.*;
import br.com.vg.util.iCallBack;
import br.com.vg.view.paint.PaintNode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * Classe responsavel por conter todo o controle da simulação. Bem como animação,
 * Como serão coloridos os nos sensores sem fio entre outros controles.
 * @author Jesimar Arantes
 */
public final class Simulation extends Thread{

    //-------------------------------ATRIBUTOS----------------------------------
    
    /**
     * Quantidade de eventos da simulação. Este valor representa quantos eventos
     * esta simulação contém.
     */
    private int SIZE_SIMULATION;

    /**
     * Modo em que se encontra o simulador (pausado, avançando, retornando).
     */
    private byte mode;

    /**
     * Indice da evento que esta sendo executado no simulação.
     */
    private int indexEvent;

    /**
     * Conta a quantidade de pacotes enviados na simulação.
     */
    private int amountPacketSend;

    /**
     * Velocidade de animação dos pacotes e progresso da simulação.
     */
    private float speedAnimation;

    /**
     * Lista de eventos da simulação.
     */
    private LinkedList<EventGeneric> listEvent;    

    /**
     * Variavel que indica se as figuras dos nós estão acionadas ou não.
     */
    private boolean visibleFigure = false;

    /**
     * Variavel que indica se as figuras em escala dos nós estão acionadas ou não.
     */
    private boolean visibleFigureScale = false;

    /**
     * Pinta na tela o id de todos os nós sensores ou não caso esteja
     * desabilitada.
     */
    private boolean paintIdNode = false;

    /**
     * Indica a forma atual de envio de pacotes que está selecionada.
     */
    private byte formPacket = PACKET_ASYNCHRONOUS;

    /**
     * Indica a forma atual de colorir as setas de comunicação dos sensores.
     */
    private byte graphConnectivity = GRAPH_CONNECTIVITY_DISABLE;

    /**
     * Armazena os dados da simulação.
     */
    private DataSimulation data;
    
    private PaintNode paintNode;

    /**
     * Repinta a tela (draw).
     */
    private iCallBack repaint;

    private double timeSim = 0.0f;

    //--------------------------------CONSTRUTOR--------------------------------

    /**
     * Construtor da classe.
     * @param repaint - interface que repinta a tela (draw).     
     */
    public Simulation(iCallBack repaint){
        this.repaint = repaint;
        this.repaint.update(indexEvent, speedAnimation, 0.0);
    }
    
    //----------------------------MÉTODOS PÚBLICOS------------------------------

    //=================================OTHER====================================

    public void valuesDefault(){
        this.graphConnectivity = GRAPH_CONNECTIVITY_DISABLE;        
        this.formPacket = PACKET_SYNCHRONOUS;
        this.paintIdNode = false;
        this.visibleFigure = false;
        this.visibleFigureScale = false;        
    }
    
    /**
     * Inicializa a simulação com os valores default.
     * @param dataSimulation - dados da simulação.
     */
    public void initSimulation(DataSimulation dataSimulation){
        synchronized(this){            
            this.data = dataSimulation;
            speedAnimation = 10;
            timeSim = 0.0f;
            mode = PAUSE;
            indexEvent = 0;
            amountPacketSend = 0;
            listEvent = dataSimulation.getListEvents();
            SIZE_SIMULATION = dataSimulation.getListEvents().size();
            paintNode = Handler.getInstance().getDataSimulation().getPaintNode();
        }
    }
    
    public void resetSimulation(){
        timeSim = 0.0f;
        mode = PAUSE;
        indexEvent = 0;
        amountPacketSend = 0;
    }

    /**
     * Metodo que roda a simulação simulação.
     */
    @Override
    public void run(){
        while(true){
            try {
                while (mode == PAUSE){
                    Thread.sleep(200);
                }
                if (indexEvent <= SIZE_SIMULATION - 1 && indexEvent >= 0){
                    EventGeneric event = listEvent.get(indexEvent);
                    if (event instanceof EventMove){
                        event.runMove(data, repaint, speedAnimation, mode);
                    }else if (event instanceof EventMsg){
                        event.runMsg(repaint, speedAnimation);                        
                    }
                    timeSim = event.getTime();
                    this.repaint.update(indexEvent, speedAnimation, timeSim);
                    repaint.repaint();
                }
                if (mode == PLAY){
                    Thread.sleep((int)(50 * speedAnimation + 50));
                    ++indexEvent;
                    if (indexEvent > SIZE_SIMULATION - 1){
                        mode = PAUSE;
                        indexEvent--;
                    }
                }
                if (mode == BACK){
                    Thread.sleep((int)(50 * speedAnimation + 50));
                    --indexEvent;
                    if (indexEvent < 0){
                        mode = PAUSE;
                        indexEvent++;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "\nError Simulation Run: \n " + ex,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
        
    /**
     * Pinta na aba monitor os nós sensores, o grafo de comunicação, a área de
     * monitoramento dos sensores, e executa a animação da simulação.
     * @param g2 - Graphics2D.     
     */
    public void paint(Graphics2D g2){      
        if(listEvent==null){
            return;
        }
        if (graphConnectivity == GRAPH_CONNECTIVITY_ENABLE){            
            data.paintGraphConnectivity(g2, false);
        }else if (graphConnectivity == GRAPH_CONNECTIVITY_ORIENTED){            
            data.paintGraphConnectivity(g2, true);
        }
        if (paintIdNode){
            paintNode.paintAllIdNode(g2, data.getListNodes(), Color.blue);
        }
        paintNode(g2);
        if (timeSim != 0.0){
            setColor();
        }
        
        if (mode != PAUSE){
            if (listEvent.size() > 0){
                EventGeneric event = listEvent.get(indexEvent);
                if (event instanceof EventMsg){
                    EventMsg eventMsg = (EventMsg)event;
                    eventMsg.animation(g2, formPacket, Color.blue);
                }
            }
        }        
    }

    /**
     * Pausa a animação da simulação.
     */
    public void pause(){
        mode = PAUSE;
    }

    /**
     * Avança a animação da simulação.
     */
    public void play(){
        mode = PLAY;
    }

    /**
     * Volta a animação da simulação.
     */
    public void back(){
        mode = BACK;
    }

    //=================================GET======================================

    /**
     * Captura a velocidade da animação do envio dos pacotes.
     * @return speedAnimation - valor indicando a velocidade da simulação atual.
     */
    public float getSpeedAnimation(){
        return speedAnimation;
    }

    /**
     * Captura a quantidade de pacotes enviadas na simulação até o evento corrente.
     * @return quantidade de pacotes enviados na simulação.
     */
    public int getAmountPacketSend(){
        if (listEvent.get(indexEvent) instanceof EventMsg){
            EventMsg eventMsg = (EventMsg)(listEvent.get(indexEvent));
            amountPacketSend = eventMsg.getAmountPacket();
        }
        return amountPacketSend;
    }
    
    //=================================SET======================================

    /**
     * Seta a velocidade do animação de envio de pacotes.
     * @param speed - valor da velocidade de envio de pacotes.
     */
    public void setSpeedAnimation(float speed){
        speedAnimation = speed;
    }

    /**
     * Método chamado quando o usuário arrasta a barra de progresso da simulação.
     * Assim esta barra vai para o local onde o usuário solta a barra.
     * @param value - valor indicando o novo valor do indice.
     */
    public void setIndexSimulation(int value){
        if (value >= 0 && value <= SIZE_SIMULATION){
            indexEvent = value;            
        }else{
            indexEvent = 0;
        }
    }

    /**
     * Método chamado quando o usuário clica em avançar (Advanced). Assim o indice
     * do evento é avançado em 10 %, se for possível avançar é claro.
     */
    public void setIndexSimulationAdvanced(){
        if (indexEvent + SIZE_SIMULATION / 10 < SIZE_SIMULATION - 1){
            indexEvent += SIZE_SIMULATION / 10;
            data.clearPositionTrain();
        }
    }

    /**
     * Método chamado quando o usuário clica em retornar (Back). Assim o indice
     * do evento é retornado em 10 %, se for possível retornar é claro.
     */
    public void setIndexSimulationBack(){
        if (indexEvent - SIZE_SIMULATION / 10 > 0){
            indexEvent -= SIZE_SIMULATION / 10;
            data.clearPositionTrain();
        }
    }

    /**
     * Seta a visibilidade dos ids dos nós da rede.
     * @param paintIdNode - visibilidade habilitado ou não.
     */
    public void paintNodeId(boolean paintIdNode) {
        this.paintIdNode = paintIdNode;
    }

    /**
     * Seta a visibilidade das figuras dos nós da rede.
     * @param visibleFigure - visibilidade das figuras habilitadas ou não.
     */
    public void setIsVisibleFigure(boolean visibleFigure) {
        this.visibleFigure = visibleFigure;
    }

    /**
     * Seta a visibilidade das figuras em escala dos nós da rede.
     * @param visibleFigureScale - visibilidade das figuras em escala
     * habilitadas ou não.
     */
    public void setIsVisibleFigureScale(boolean visibleFigureScale) {
        this.visibleFigureScale = visibleFigureScale;
    }

    /**
     * Seta o grafo de comunicação da rede.
     * @param graphConnectivity - tipo de grafo de comunicação.
     */
    public void setGraphConnectivity(byte graphConnectivity) {
        this.graphConnectivity = graphConnectivity;
    }

    /**
     * Seta a forma de envio de pacotes na rede.
     * @param formPacket - tipo de forma de envio de pacote.
     */
    public void setFormPacket(byte formPacket) {
        this.formPacket = formPacket;
    }
    
    //----------------------------MÉTODOS PRIVADOS------------------------------

    //=================================OTHER====================================

    /**
     * Pinta os nós da rede com a cor setada no mapping ou com a figura
     * correspondente ao nó (MOTE, UAV, INTRUDER).
     * @param g2 - graphic2D que será utilizado para pintar os nós.
     */
    private void paintNode(Graphics2D g2){
        if (!visibleFigure){
            for (Node node: data.getListNodes()){
                paintNode.paintNode(g2, node, data);
            }
        }else{
            if (visibleFigureScale){
                for (Node node: data.getListNodes()){
                    paintNode.paintNodeFigureScale(g2, node, node.getNodeType());
                }
            }else{
                for (Node node: data.getListNodes()){
                    paintNode.paintNodeFigure(g2, node, node.getNodeType());
                }
            }
        }
    }

    /**
     * Seta a cor dos nós da rede de acordo com o mapping.
     * @param event - evento corrente da simulação.
     */
    private void setColor(){       
        if (visibleFigure){
            return;
        }
        for (Node node: data.getListNodes()){
            int index = 0;
            for (LinkedList<NodeState> listNS: node.getMappingNodeState()){
                byte type = (byte)data.getListComboBoxMapping().get(index)
                        .getSelectedIndex();
                if (type != NONE){
                    for (NodeState nodeState: listNS){
                        if (timeSim >= nodeState.getTime()){
                            if (type == COLOR){
                                if (nodeState.getValueInt() != -1){
                                    node.setColor(paintNode.getColor(
                                        nodeState.getValueInt()));
                                }else if (nodeState.getValueFloat() != -1.0f){
                                    node.setColor(paintNode.getColor(
                                        nodeState.getValueFloat()));
                                }
                            }else if (type == BOARDER_COLOR){
                                if (nodeState.getValueInt() != -1){
                                    node.setBoarderColor(paintNode
                                            .getColor(nodeState.getValueInt()));
                                }else if (nodeState.getValueFloat() != -1.0f){
                                    node.setBoarderColor(paintNode
                                            .getColor(nodeState.getValueFloat()));
                                }
                            }else{
                                if (nodeState.getValueInt() != -1){
                                    node.setLabel(String.valueOf(nodeState.getValueInt()));
                                }else if (nodeState.getValueFloat() != -1.0f){
                                    node.setLabel(String.valueOf(nodeState
                                            .getValueFloat()));
                                }
                            }
                        }
                    }
                }
                ++index;
            }
        }
    }
}
