
package br.com.vg.controller.structure;

import java.awt.Color;
import java.util.LinkedList;

/**
 * Classe responsável por modelar o nó.
 * @author Jesimar Arantes
 */
public final class Node extends NodeGeneric{

    //-----------------------------ATRIBUTOS------------------------------------    

    /**
     * Cor que será pintado o nó sensor caso não esteja definida nenhuma cor.
     */
    private Color none = Color.orange;

    /**
     * Cor que será pintado o nó sensor.
     */
    private Color color = Color.orange;

    /**
     * Cor que será pintado a borda do nó sensor.
     */
    private Color boarderColor = Color.orange;

    /**
     * Label indicando um valor numérico na visualização da simulação.
     */
    private String label = "";

    /**
     * Valor indicando se o nó corrente é movel ou estático.
     */
    private boolean isMobile = false;
    
    /**
     * String indicando o tipo de imagem.
     *      UAV - indica que o nó é um UAV.
     *      REGULAR - indica que o nó é um nó sensor de terra.
     *      INTRUDER - indica que o nó é um intruso.
     */
    private String nodeType;

    private int nodeTypeInt;

    private Move[] vectorMove;

    private LinkedList<NodeState> mappingNodeState[];

    //-----------------------------CONSTRUTORES---------------------------------

    /**
     * Construtor da classe Node.
     * @param id - id do nó.
     * @param coordX - coordenada x do nó.
     * @param coordY - coordenada y do nó.
     * @param radiusSensor - raio de alcance do sensor do nó.
     * @param radiusComunication - raio de alcance de comunicação do nó.
     */
    public Node(int id, float coordX, float coordY, float radiusComunication){
        super(id, coordX, coordY, radiusComunication);
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //================================GET=======================================

    /**
     * Captura a cor do nó sensor default.
     * @return color - cor default do nó sensor.
     */
    public Color getNone() {
        return none;
    }

    /**
     * Captura a cor do nó sensor.
     * @return color - cor do nó sensor.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Captura a cor da borda do nó sensor.
     * @return color - cor da borda do nó sensor.
     */
    public Color getBoarderColor() {
        return boarderColor;
    }

    /**
     * Captura o label que é um valor numerico indicativo da cor do nó sensor.
     * @return label - label do nó sensor.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Captura a string indicando o tipo de imagem.
     * @return "UAV" - indica que o nó é um UAV.
     *         "REGULAR" - indica que o nó é um nó sensor de terra.
     *         "INTRUDER" - indica que o nó é um intruso.
     */
    public int getNodeType(){
        return nodeTypeInt;
    }

    public String getNodeTypeString(){
        return nodeType;
    }

    //================================SET=======================================

    /**
     * Seta a nova posição do nó sensor. (Para nós moveis).
     * @param newCoordX - nova coordenada do nó sensor.
     * @param newCoordY - nova coordenada do nó sensor.
     */
    public void setNewPosition(float newCoordX, float newCoordY){
        super.coordX = newCoordX;
        super.coordY = newCoordY;
    }

    /**
     * Seta a cor da borda do nó da rede.
     * @param boarderColor - cor da borda.
     */
    public void setBoarderColor(Color boarderColor) {
        this.boarderColor = boarderColor;
    }

    /**
     * Seta a cor do nó da rede.
     * @param color - cor do nó.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * String do label do nó da rede.
     * @param label - label do nó.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Seta se o nó corrente é movel ou estático.
     * @param value - true indica que o nó é movel.
     *              - false indica que o nó é estático.
     */
    public void setIsMobile(boolean value){
        this.isMobile = value;
    }
    
    /**
     * Seta o tipo da imagem a ser utilizada no icone do nó da rede.
     * @param nodeType - string indicando o tipo da imagem.
     *        "UAV" - indica que o nó é um UAV.
     *        "REGULAR" - indica que o nó é um nó sensor de terra.
     *        "INTRUDER" - indica que o nó é um intruso.
     */
    public void setNodeType(String nodeType){
        if (nodeType == null){
            nodeType = "REGULAR";
        }
        this.nodeType = nodeType;
        if (nodeType.equalsIgnoreCase("REGULAR")){
            this.nodeTypeInt = iConstants.NODE_REGULAR;
        }else if (nodeType.equalsIgnoreCase("UAV")){
            this.nodeTypeInt = iConstants.NODE_UAV;
        }else if (nodeType.equalsIgnoreCase("INTRUDER")){
            this.nodeTypeInt = iConstants.NODE_INTRUDER;
        }else{
            this.nodeType = "REGULAR";
            this.nodeTypeInt = iConstants.NODE_REGULAR;
        }
    }

    //=================================IS=======================================

    /**
     * Captura se o nó corrente é movel ou estático.
     * @return true - indicando nó movel.
     *         false - indicando nó estático.
     */
    public boolean isMobile(){
        return isMobile;
    }

   /**
     * Captura a coordenada X
     * @return Coordenada X.
     */
    public float getCoordX() {
        return super.coordX;
    }

    /**
     * Seta a coordenada X
     * @param coordX - coordenada X.
     */
    public void setCoordX(float coordX) {
        super.coordX = coordX;
    }

    /**
     * Captura a coordenada Y
     * @return Coordenada Y.
     */
    public float  getCoordY() {
        return super.coordY;
    }

    public Move[] getVectorMove(){
        return this.vectorMove;
    }

    /**
     * Seta a coordenada Y
     * @param coordY - coordenada Y.
     */
    public void setCoordY(float coordY) {
        super.coordY = coordY;
    }

    public void setVectorMove(Move[] listMove){
        this.vectorMove = listMove;
    }

    public LinkedList<NodeState>[] getMappingNodeState() {
        return mappingNodeState;
    }

    public void setMappingNodeState(LinkedList<NodeState>[] mappingNodeState) {
        this.mappingNodeState = mappingNodeState;
    }
}
