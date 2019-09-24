
package br.com.vg.view.paint;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.iConstants;
import br.com.vg.util.Util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * Classe responsável por desenhar a conexão dos nós da rede de sensor em função
 * do raio de comunicação.
 * @author Jesimar Arantes
 */
public final class PaintGraphConnectivity {

    //-----------------------------ATRIBUTOS------------------------------------

     /**
     * Lista de nos destino. Dado um nó a lista armazena todos os destinos
     * daquele nó.
     */
    private LinkedList<Node> destinationNode[];

     /**
     * Lista de nos destino. Dado um nó a lista armazena todos os destinos
     * daquele nó.
     */
    private LinkedList<Node> destinationNodeMove [];

    //------------------------------CONSTRUTOR----------------------------------

    /**
     * Construtor da classe.
     */
    public PaintGraphConnectivity(){
        
    }

    /**
     * Construtor da classe.
     * @param data - dados da simulação.
     */    
    public PaintGraphConnectivity(DataSimulation data) {       
        this.destinationNode = new LinkedList[data.getListNodes().size()];
        this.destinationNodeMove = new LinkedList[data.getAmountNodeMove()];
        this.calcGraphConnectivity(data);
    }

    //---------------------------MÉTODOS PÚBLICOS-------------------------------

    //==================================OTHER===================================   

    /**
     * Método responsável por pintar as linhas de conexão dos nós.
     * @param g2 - Graphics2D
     * @param data - classe que contem os dados da simulação.
     * @param isVisibleArrows - indica que as setas são ou não visíveis.
     */
    public void paintGraph(Graphics2D g2, DataSimulation data,
            boolean isVisibleArrows){
        g2.setColor(new Color(40, 40, 40));
        int i = 0;
        int j = 0;
        for (Node sourceNode: data.getListNodes()){
            for (Node no: destinationNode[i]){
                g2.drawLine(
                        (int)sourceNode.getCoordX(),
                        (int)sourceNode.getCoordY(),
                        (int)no.getCoordX(),
                        (int)no.getCoordY()
                    );
                if (isVisibleArrows){
                    drawArrows(g2, sourceNode, no, data);
                }
            }

            if (sourceNode.isMobile()){
                if (sourceNode.getNodeType() != iConstants.NODE_INTRUDER){
                    destinationNodeMove[j].clear();
                    calcDestinationNodes(data, sourceNode, destinationNodeMove[j]);
                    for (Node no: destinationNodeMove[j]){
                        g2.drawLine(
                                (int)sourceNode.getCoordX(),
                                (int)sourceNode.getCoordY(),
                                (int)no.getCoordX(),
                                (int)no.getCoordY()
                        );
                        if (isVisibleArrows){
                            drawArrows(g2, sourceNode, no, data);
                        }
                    }
                    ++j;
                }
            }
            ++i;
        }
    }

    /**
     * Calcula os nós destino a partir de um nó de origem que não sejam moveis.
     * @param data - dados da simulação.
     * @param source - nó de origem onde será calculado os nós destino do
     * raio de comunicação.
     * @param nodeDestination - lista de nós destino onde será acrescentado os
     * mesmos.
     */
    public void calcDestinationNodes(DataSimulation data, Node source,
            LinkedList<Node> nodeDestination){

        float dist;
        for (Node node: data.getListNodes()){
            dist = Util.calcDistEuclidiana(source.getCoordX(), source.getCoordY(),
                    node.getCoordX(), node.getCoordY());

            if (dist <= source.getRadiusCommunication() && dist != 0.0){
                nodeDestination.add(node);
            }
        }
    }        

    //--------------------------MÉTODOS PRIVADOS--------------------------------

    //=================================OTHER====================================

    /**
     * Calcula os nós destino a para todas as origens e acha todos os destinos
     * daquela origem.
     */
    private void calcGraphConnectivity(DataSimulation data){
        for (int i = 0; i < data.getAmountNodeMove(); ++i){
            destinationNodeMove[i] = new LinkedList<Node>();
        }

        int j = 0;
        for (Node no : data.getListNodes()){
            destinationNode[j] = new LinkedList<Node>();
            if (!no.isMobile()){
                calcDestinationNodesMoveis(data, no, destinationNode[j]);
            }
            ++j;
        }
    }  
    
    private void calcDestinationNodesMoveis(DataSimulation data, Node source,
            LinkedList<Node> nodeDestination){

        float dist;
        for (Node node: data.getListNodes()){            
            dist = Util.calcDistEuclidiana(source.getCoordX(), source.getCoordY(),
                    node.getCoordX(), node.getCoordY());

            if (dist <= source.getRadiusCommunication() && dist != 0.0 && 
                    !node.isMobile()){
                nodeDestination.add(node);
            }
        }
    }

    /**
     * Pinta na tela as linhas de direção de comunicação da rede de sensores.
     */
    private void drawArrows(Graphics2D g2, Node sourceNode, Node no, 
            DataSimulation data){
        int diam = data.getDiameterNode();
        double angulo = Math.atan2(
                no.getCoordY() - sourceNode.getCoordY(),
                no.getCoordX() - sourceNode.getCoordX()
                );

        int xm = (int)((sourceNode.getCoordX() + no.getCoordX() -
                sourceNode.getCoordX()) - diam * Math.cos(angulo));

        int ym = (int)(sourceNode.getCoordY() + no.getCoordY() -
                sourceNode.getCoordY() - diam * Math.sin(angulo));

        int x[] = {
            (int)(xm + diam * Math.cos(Math.PI/2 + angulo)/2),
            (int)(xm - diam * Math.cos(Math.PI/2 + angulo)/2),
            (int)(sourceNode.getCoordX() + no.getCoordX() - 
                sourceNode.getCoordX() - diam * Math.cos(angulo))
        };

        int y[] = {
            (int)(ym + diam * Math.sin(Math.PI/2 + angulo)/2),
            (int)(ym - diam * Math.sin(Math.PI/2 + angulo)/2),
            (int)(sourceNode.getCoordY() + no.getCoordY() - 
                sourceNode.getCoordY() - diam * Math.sin(angulo))
        };

        g2.fillPolygon(x, y, 3);
    }        
}