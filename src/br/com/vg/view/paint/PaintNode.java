
package br.com.vg.view.paint;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.control.Handler;
import br.com.vg.controller.resources.ResourcesGraphics;
import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.iConstants;
import static br.com.vg.controller.structure.iConstants.*;
import java.awt.*;
import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 * Classe responsável por pintar os nós na tela do simulador.
 * @author Jesimar Arantes
 */
public final class PaintNode {

    //--------------------------------ATRIBUTOS---------------------------------

    private int diameterNode = 25;
    private int radiusNode = diameterNode/2;
    private ResourcesGraphics resources = Handler.getInstance().getResources();
    
    /**
     * Define as possiveis cores para os nós da rede de sensores.
     */
    private Color[] intColor = { new Color(255, 0, 0),
            new Color(128, 255, 128), new Color(148, 0, 211),
            new Color(0, 255, 0), new Color(0, 128, 0),
            new Color(0, 128, 128), new Color(0, 0, 255),
            new Color(0, 0, 128), new Color(128, 128, 255),
            new Color(128, 0, 64), new Color(255, 0, 255),
            new Color(0, 255, 255)};

    //---------------------------MÉTODOS PÚBLICOS-------------------------------

    //=================================GET======================================   
    
     /**
     * Captura uma cor dado um valor numérico do tipo inteiro.
     * @param value - valor que será associado a um cor.
     * @return cor correspondente ao valor passado.
     */
    public Color getColor(int value){
        return intColor[value % 12];
    }

    /**
     * Captura uma cor dado um valor numérico do tipo float.
     * @param value - valor que será associado a um cor.
     * @return cor correspondente ao valor passado.
     */
    public Color getColor(float value){
        return new Color(1, 1 - value, 1 - value);
    }   
    
    public void setDiameterNode(int diameterNode) {
        this.diameterNode = diameterNode;
        this.radiusNode = diameterNode/2;        
    }

    //================================OTHER=====================================

    /**
     * Pinta um nó da rede com sua cor default (orange).
     * @param g2 - Graphics2D.
     * @param node - nó da rede a ser pintado de laraja.
     */
    public void paintNodeDefault(Graphics2D g2, Node node){
        
        g2.setColor(node.getNone());
        g2.fillOval((int)node.getCoordX() - diameterNode / 2,
                (int)node.getCoordY() - diameterNode / 2,
                diameterNode, diameterNode);

        g2.setColor(Color.black);

        g2.drawOval((int)node.getCoordX() - diameterNode / 2,
                (int)node.getCoordY() - diameterNode / 2,
                diameterNode, diameterNode);
    }

    /**
     * Pinta um nó da rede com sua própria cor.
     * @param g2 - Graphic2D.
     * @param node - nó da rede a ser pintado com sua própria cor.
     */
    public void paintNodeColor(Graphics2D g2, Node node){

        g2.setColor(node.getColor());
        g2.fillOval((int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode);

        g2.setColor(Color.black);

        g2.drawOval((int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode);
    }

    /**
     * Pinta um nó da rede com uma cor definida como parametro.
     * @param g2 - Graphics2D.
     * @param node - nó da rede a ser pintado.
     * @param color - cor a ser pintada o nó.
     */
    public void paintNodeColor(Graphics2D g2, Node node, Color color){
        g2.setColor(color);
        g2.fillOval((int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode);

        g2.setColor(Color.black);

        g2.drawOval((int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode);
    }

    /**
     * Pinta a borda do respectivo nó da rede.
     * @param g2 - Graphics2D.
     * @param node - nó da rede a ser pintado a borda.
     */
    public void paintNodeBoarder(Graphics2D g2, Node node){

        if (node.getColor() == node.getBoarderColor()){
            g2.setColor(node.getNone());
        }else{
            g2.setColor(node.getColor());
        }
        g2.fillOval((int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode);

        g2.setColor(node.getBoarderColor());

        g2.setStroke(new BasicStroke(9.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));

        g2.drawOval((int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode);

        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
    }

    /**
     * Pinta o label do respectivo nó da rede.
     * @param g2 - Graphics2D.
     * @param node - nó da rede a ser impresso o label do nó. Este label indica
     *               um valor numérico para a cor do nó.
     */
    public void paintLabel(Graphics2D g2, Node node){
        g2.setColor(Color.BLACK);
        Font fontPrevious = g2.getFont();
        g2.setFont(new Font(Font.SERIF, Font.PLAIN, (int)(0.85 * diameterNode)));
        g2.drawString(node.getLabel(), node.getCoordX() - radiusNode,
                node.getCoordY() + diameterNode);
        g2.setFont(fontPrevious);
    }

    /**
     * Pinta o id de todos os nós sensores.
     * @param g2 - Graphics2D.
     * @param nodes - lista de nós a serem colocados os ids na tela.
     * @param colorId - Cor a ser pintado o id.
     */
    public void paintAllIdNode(Graphics2D g2, LinkedList<Node> nodes,
            Color colorId){

        g2.setColor(colorId);
        Font font = g2.getFont();
        g2.setFont(new Font(Font.SERIF, Font.PLAIN, (int)(0.85 * diameterNode)));
        for (Node node: nodes){
            g2.drawString(String.valueOf(node.getId()),
                node.getCoordX() - radiusNode, node.getCoordY() - radiusNode
            );
        }
        g2.setFont(font);
    }

    /**
     * Pinta a imagem do node do nós da rede.
     * @param g2 - Graphics2D.
     * @param node - nó da rede a ser colocado a imagem.
     * @param imageType - imagem respectivo nó (Ex. Imagem de MOTE, UAV, INTRUER).
     */
    public void paintNodeFigure(Graphics2D g2, Node node, int imageType){
        g2.drawImage(getImageNode(imageType),
                (int)node.getCoordX() - radiusNode,
                (int)node.getCoordY() - radiusNode,
                diameterNode, diameterNode, null);
    }

    /**
     * Pinta a imagem do node do nós da rede em escala (UAVs maiores que nós
     * mote).
     * @param g2 - Graphics2D.
     * @param node - nó da rede a ser colocado a imagem.
     * @param imageType - imagem respectivo nó (Ex. Imagem de MOTE, UAV, INTRUER).
     */
    public void paintNodeFigureScale(Graphics2D g2, Node node,
            int imageType){

        if (imageType == iConstants.NODE_REGULAR){
            g2.drawImage(getImageNode(imageType),
                    (int)node.getCoordX() - radiusNode,
                    (int)node.getCoordY() - radiusNode,
                    diameterNode, diameterNode, null);
        }else if (imageType == iConstants.NODE_UAV){
            g2.drawImage(getImageNode(imageType),
                    (int)node.getCoordX() - 3 * radiusNode,
                    (int)node.getCoordY() - 3 * radiusNode,
                    3 * diameterNode, 3 * diameterNode, null);
        }else if (imageType  == iConstants.NODE_INTRUDER){
            g2.drawImage(getImageNode(imageType),
                    (int)node.getCoordX() - diameterNode,
                    (int)node.getCoordY() - diameterNode,
                    2 * diameterNode, 2 * diameterNode, null);
        }

    }

    /**
     * Captura o tipo da imagem do nó de acordo com a string passada.
     * @param imageType - string representando o tipo de imagem do nó.
     * @return Image do node.
     */
    public Image getImageNode(Integer imageType){
        ImageIcon image = null;
        DataSimulation data = Handler.getInstance().getDataSimulation();
                
        if (imageType != null){
            if (imageType == iConstants.NODE_REGULAR){
                image = new ImageIcon(resources.getImageNodes(
                    data.getIndexMoteFigure()));
            }else if (imageType == iConstants.NODE_UAV){
                image = new ImageIcon(resources.getImageUavs(
                    data.getIndexUavFigure()));
            }else if (imageType == iConstants.NODE_INTRUDER){
                image = new ImageIcon(resources.getImageIntruder(
                    data.getIndexIntruderFigure()));
            }
            return image.getImage();
        }else{
            return null;
        }
    }

    public void paintNode(Graphics2D g2, Node node, DataSimulation data){
        boolean isColor = false;
        boolean isBoarderColor = false;
        boolean isLabel = false;
        for (int i = 0; i < data.getSizeFieldsMapping(); i++){
            int field = data.getListComboBoxMapping().get(i).getSelectedIndex();
            if (field == COLOR){
                isColor = true;
            }else if (field == BOARDER_COLOR){
                isBoarderColor = true;
            }else if (field == LABEL){
                isLabel = true;
            }
        }
        if (isColor){
            paintNodeColor(g2, node);
        }
        if (isBoarderColor){
            paintNodeBoarder(g2, node);
        }
        if (isLabel){
            paintLabel(g2, node);
            if (!isColor && !isBoarderColor){
                paintNodeDefault(g2, node);
            }
        }
        if (!isColor && !isBoarderColor && !isLabel){
            paintNodeDefault(g2, node);
        }
    }
}
