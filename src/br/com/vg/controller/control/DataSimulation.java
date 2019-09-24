
package br.com.vg.controller.control;

import br.com.vg.controller.structure.Move;
import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.NodeState;
import br.com.vg.controller.structure.Position;
import br.com.vg.view.paint.PaintGraphConnectivity;
import br.com.vg.view.paint.PaintNode;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.JComboBox;

/**
 * Classe onde fica armazenado todas os dados da simulação carregadas
 * do arquivo XML.
 * @author Jesimar Arantes
 */
public class DataSimulation {
           
    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Raio de comunicação dos sensores da rede.
     */
    private float radiusCommunication;
    
    /**
     * Dimensão X do ambiente onde os sensores estão localizados.
     */
    private int dimensionX;

    /**
     * Dimensão Y do ambiente onde os sensores estão localizados.
     */
    private int dimensionY;

    /**
     * Tempo maximo da simulação.
     */
    private double timeSimulationMax;

    /**
     * Quantidade de nós na rede de sensores sem fio.
     */
    private int ammountNodes;

    /**
     * Tamanho da lista de Animação. Ou seja, indica quantos pacotes são enviados 
     * na rede.
     */
    private int sizeListAnim;

    /**
     * Área da aplicação.
     */
    private int area;

    /**
     * Densidade de nós da Aplicação.
     */
    private float densidade;

    /**
     * Conectividade dos sensores da aplicação, em relação ao raio de comunicação.
     */
    private float conectivity;

    /**
     * Número médio de vizinhos de um nó sensor, em relação ao raio de comunicação.
     */
    private float averangeNeig;

    /**
     * Nome da aplicação (nome do arquivo aberto).
     */
    private String nameApp;

    private String descriptionApp;

    /**
     * Grafo de comunicação da rede de sensores sem fio.
     */
    private PaintGraphConnectivity graph;

    /**
     * Campos do mapping carregados da lista de NodeState.
     */
    private String fieldsMapping[];

    /**
     * Vetor de NodeHandler contendo todos os nós da rede.
     */
    private LinkedList<Node> listNodes = new LinkedList<Node>();

     /**
     * Lista de EventGeneric contendo todos eventos da rede.
     */
    private LinkedList<EventGeneric> listEvents = new LinkedList<EventGeneric>();   

    /**
     * Lista de Move contendo todas as movimentações que ocorreram na simulação.
     */
    private LinkedList<Move> listMoves = new LinkedList<Move>();

    /**
     * Lista de Nós que se movem na simulação.
     */
    private LinkedList<Node> listNodeMove = new LinkedList<Node>();

    /**
     * Lista contendo os tempos que os nós se movem. Esta lista contém somente
     * os tempos que mudam, na lista de moves.
     */
    private LinkedList<Double> listTimeMove = new LinkedList<Double>();   

    /**
     * Lista de NodeState contendo todos os nodestates da simulação.
     */
    private LinkedList<NodeState> listNodeStates = new LinkedList<NodeState>();

    /**
     * Lista de JComboBox que controla o mapping da simulação.
     */
    private LinkedList<JComboBox> listComboBoxMapping = new LinkedList<JComboBox>(); 

    /**
     * Lista de Posição dos nós moveis na rede. Usado para construir uma trilha
     * destes nós na rede.
     */
    private LinkedList<Position> listPositionTrain[];
    
    //----------------------------
    
    
    /**
     * Diametro do nó sensor (Para ser desenhado na tela).
     */
    private int diameterNode = 25;

    private int radiusNode = diameterNode/2;

    /**
     * Marca o indice da figura de mote selecionado, pelo combobox da interface
     * gráfica.
     */
    private byte indexMoteFigure = 0;

    /**
     * Marca o indice da figura do UAV selecionado, pelo combobox da interface
     * gráfica.
     */
    private byte indexUavFigure = 0;

    /**
     * Marca o indice da figura de intruso selecionado, pelo combobox da
     * interface gráfica.
     */
    private byte indexIntruderFigure = 0;
    
    private PaintNode paintNode = new PaintNode();

    /**
     * Construtor da classe.
     */
    public DataSimulation() {
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //================================OTHER=====================================        

    /**
     * Calcula a lista de nós que se movem. A lista listNodeMove é uma lista
     * onde cada elemento é um nó que se move.
     */
    public void calcListNodeMove(){
        boolean isPresent;
        for (Move move: listMoves){
            isPresent = false;
            for (Node no: listNodeMove){
                if (move.getNode().getId() == no.getId()){
                    isPresent = true;
                }
            }
            if (!isPresent){
                listNodeMove.add(move.getNode());
            }
        }        
        //Calcula e seta todos os nós da rede que são movéis.
        for (Node no: listNodeMove){
            LinkedList<Move> listTempMove = new LinkedList<Move>();
            for (Move move: listMoves){
                if (no.getId() == move.getNode().getId()){
                    listTempMove.add(move);
                }
            }
            no.setVectorMove(listTempMove.toArray(new Move[listTempMove.size()]));
        }

        listMoves.clear();
    }

    /**
     * Define os campos da aba mapping baseados nos nomes dos NodeStates.
     */
    public void calcFieldsMapping(){
        LinkedList<String> listTemp = new LinkedList<String>();
        for (NodeState nodeState: listNodeStates){
            if (!listTemp.contains(nodeState.getName())){
                listTemp.add(nodeState.getName());
            }
        }
        fieldsMapping = new String[listTemp.size()];
        fieldsMapping = listTemp.toArray(new String[listTemp.size()]);
    }

    /**
     * 
     */   
    public void calcVetorMappingNode(){
        LinkedList<NodeState> tempNodeState[][] = new
                LinkedList[listNodes.size()][fieldsMapping.length];

        for (int i = 0; i < listNodes.size(); i++){
            for (int j = 0; j < fieldsMapping.length; j++){
                tempNodeState[i][j] = new LinkedList<NodeState>();
            }
        }
        for (NodeState nodeState: listNodeStates){
                int field = 0;
                for (int i = 0; i < fieldsMapping.length; i++){
                    if (fieldsMapping[i].equals(nodeState.getName())){
                        field = i;
                    }
                }
                int idNode = nodeState.getNode().getId() - 1;
                tempNodeState[idNode][field].add(nodeState);
        }
        for (Node node : listNodes){
            node.setMappingNodeState(tempNodeState[node.getId() - 1]);
        }
        listNodeStates.clear();
    }

    /**
     * Calcula o grafo de conectividade da rede de sensores.
     */
    public void calcGraphConnectivity(){
        graph = new PaintGraphConnectivity(this);
    }

    /**
     * Calcula alguns dados da simulação. Como densidade, conectividade e número
     * de vizinhos em media.
     * @param nameAplication - nome do aplicação aberta
     */
    public void calcData(String nameAplication){
        nameApp = nameAplication;
        area = dimensionX * dimensionY;
        densidade = ammountNodes / (float)area;
        averangeNeig = (float)(densidade * Math.PI * (radiusCommunication/10.0) *
                (radiusCommunication/10.0));
        conectivity = (float)(Math.pow((1 - Math.pow(
                Math.E, -averangeNeig)), ammountNodes));
    }
    
    public void calcListPositionsTrain(){
        listPositionTrain = new LinkedList[listNodeMove.size()];
        for (int i = 0; i < listPositionTrain.length; ++i){
            listPositionTrain[i] = new LinkedList<Position>();
        }
    }

    /**
     * Pinta o grafo de conectividade da rede de sensores.
     * @param g2 - Graphics2D.
     * @param visibleArrows - visibilidade das setas direcionais de conectividade.
     */
    public void paintGraphConnectivity(Graphics2D g2, boolean visibleArrows){
        graph.paintGraph(g2, this, visibleArrows);
    }

    //=================================ADD======================================

    /**
     * Adiciona um elemento NodeState na lista de NodeState.
     * @param nodeState - um elemento estado do nó (pintar) da rede.
     */
    public void addElementNodeState(NodeState nodeState){
        listNodeStates.add(nodeState);
    }

    /**
     * Adiciona um elemento Move na lista de Move.
     * @param move - um elemento movimento de um nó da rede.
     */
    public void addElementMove(Move move) {
        listMoves.add(move);
    }

    /**
     * Adiciona um evento na rede.
     * @param event - evento da simulação.
     */
    public void addEvent(EventGeneric event){
        listEvents.add(event);
    }

    /**
     * Adiciona um evento na sua respectiva posição na lista de eventos.
     * @param event - evento a ser adicionado.
     * @param pos - posição do evento na lista de eventos.
     */
    public void addEventPosition(EventGeneric event, int pos){
        listEvents.add(pos, event);
    }

    /**
     * Adiciona um nó na lista de nós da rede de sensores.
     * @param node - nó a ser adicionado.
     */
    public void addNode(Node node){
        listNodes.add(node);
    }

    public void addPositionTrain(int i, Position position){
        listPositionTrain[i].add(position);
    }
    
    public void addTimeMove(Double time){
        listTimeMove.add(time);
    }

    public void clearPositionTrain(){
        for (int i = 0; i < listPositionTrain.length; i++){
            if (listPositionTrain[i].size() > 0){
                listPositionTrain[i].clear();
            }
        }
    }
    
    //=================================SET======================================
    
    /**
     * Seta a quantidade de nós da rede de sensores.
     * @param ammountNodes - quantidade de nós da rede.
     */
    public void setAmmountNodes(int ammountNodes) {
        this.ammountNodes = ammountNodes;
    }

    /**
     * Seta a largura X da área de cobertura da rede de sensores.
     * @param dimensionX - comprimento da área da rede.
     */
    public void setDimensionX(int dimensionX) {
        this.dimensionX = dimensionX;
    }

    /**
     * Seta a altura Y da área de cobertura da rede de sensores.
     * @param dimensionY - largura da área da rede.
     */
    public void setDimensionY(int dimensionY) {
        this.dimensionY = dimensionY;
    }

    /**
     * Seta o tempo máximo da simulação.
     * @param timeSimulationMax - tempo maximo da simulação
     */
    public void setTimeSimulationMax(double timeSimulationMax){
        this.timeSimulationMax = timeSimulationMax;
    }

    /**
     * Seta a lista de combo box do mapping.
     * @param listComboBoxMapping - lista de combo box do mapping.
     */
    public void setListComboBoxMapping(LinkedList<JComboBox> listComboBoxMapping){
        this.listComboBoxMapping = listComboBoxMapping;
    }

    /**
     * Seta o raio de comunição dos sensores da rede.
     * @param radiusCommunication - raio de comunicação.
     */
    public void setRadiusCommunication(float radiusCommunication) {
        this.radiusCommunication = radiusCommunication;
    }

    /**Seta a o tamanho da lista de animação (indicando quantos pacotes são
     * enviados na rede.
     * @param sizeListAnim - valor indicando o tamanho da lista de animação.
     */
    public void setSizeListAnim(int sizeListAnim){
        this.sizeListAnim = sizeListAnim;
    }

    public void setDescriptionApp(String descriptionApp) {
        this.descriptionApp = descriptionApp;
    }

    //=================================GET======================================

    /**dataSimulation
     * Captura a quantidade de nós da rede de sensores.
     * @return quantidade de nós da rede.
     */
    public int getAmmountNodes() {
        return ammountNodes;
    }

    /**
     * Captura a largura X da área de cobertura da rede de sensores.
     * @return largura da rede de sensores.
     */
    public int getDimensionX() {
        return dimensionX;
    }

    /**
     * Captura a altura Y da área de cobertura da rede de sensores.
     * @return altura da rede de sensores.
     */
    public int getDimensionY() {
        return dimensionY;
    }

    /**
     * Captura o tempo total de simulação da rede de sensores sem fio.
     * @return tempo total de simulação.
     */
    public double getSimulationTimeMax(){
        return timeSimulationMax;
    }

    /**
     * Captura o tamanho total da lista de eventos.
     * @return tamanho total da lista de eventos.
     */
    public int getSizeIndexEvent(){
        return listEvents.size();
    }

    /**
     * Captura o raio de comunicação da rede.
     * @return raio de comunicação
     */
    public float getRadiusCommunication() {
        return radiusCommunication;
    }

    public String getDescriptionApp() {
        return descriptionApp;
    }   

    /**
     * Captura o tamanho da lista de animação de pacotes na rede.
     * @return tamanho da lista de pocotes de animação enviados na rede.
     */
    public int getSizeListAnim(){
        return sizeListAnim;
    }

    /**
     * Captura a área da aplicação.
     * Área = DimensãoX * DimensãoY
     * @return área.
     */
    public int getArea() {
        return area;
    }

    /**
     * Captura o numero de vizinhos em media.
     * NumeroVizinhosMedia = Densidade * PI * RaioComunicação ^ 2
     * @return numero de vizinhos em media.
     */
    public float getAverangeNeig() {
        return averangeNeig;
    }

    /**
     * Captura a conectividade da rede.
     * Conectividade = (1 - E ^ (- NumeroVizinhosMedia)) ^ NumeroNós
     * @return conectividade.
     */
    public float getConectivity() {
        return conectivity;
    }

    /**
     * Captura a densidade da rede.
     * Densidade = NumeroNós / Área
     * @return densidade.
     */
    public float getDensidade() {
        return densidade;
    }

    public String getNameApp(){
        return nameApp;
    }

    /**
     * Captura o nó sensor de id informado.
     * @param id - do sensor procurado.
     * @return nó corresponde ao id passado.
     */
    public Node getNode(int id){
        for (Node no : listNodes){
            if (no.getId() == id){
                return no;
            }
        }
        return null;
    }   

    /**
     * Captura o vetor de Nós da rede.
     * @return lista de nós da rede.
     */
    public LinkedList<Node> getListNodes(){
        return listNodes;
    }

    /**
     * Captura a lista de eventos.
     * @return lista de eventos.
     */
    public LinkedList<EventGeneric> getListEvents(){
        return listEvents;
    }
    
    /**
     * Captura a lista de nós que se movem.
     * @return lista de nós que se movem.
     */
    public LinkedList<Node> getListNodeMove(){
        return listNodeMove;
    }

    public int getAmountNodeMove(){
        return listNodeMove.size();
    }
    
    /**
     * Captura os campos utilizados no mapping da simulação.
     * @return fieldsMapping - campos do mapping.
     */
    public String[] getFieldsMapping(){
        return  fieldsMapping;
    }

    public int getSizeFieldsMapping(){
        return fieldsMapping.length;
    }

    /**
     * Captura a lista de combo box que seta as configurações do mapping.
     * @return lista de combo box do mapping.
     */
    public LinkedList<JComboBox> getListComboBoxMapping(){
        return listComboBoxMapping;
    }

    public LinkedList<Double> getListTimeMove(){
        return listTimeMove;
    }

    public LinkedList<Position>[] getPositionTrain(){
        return listPositionTrain;
    }
    
    //-----------------------------------
    
    /**
     * Seta todos os valores das configurações de paint como default.
     */
    public void valuesDefault(){
        diameterNode = 25;
        radiusNode = diameterNode/2;
        paintNode.setDiameterNode(diameterNode);
        indexMoteFigure = 0;
        indexUavFigure = 0;
        indexIntruderFigure = 0;
    }

    public int getDiameterNode() {
        return diameterNode;
    }

    public void setDiameterNode(int diameterNode) {
        this.diameterNode = diameterNode;
        this.radiusNode = diameterNode/2;
        paintNode.setDiameterNode(diameterNode);
    }

    public int getRadiusNode() {
        return radiusNode;
    }

    public byte getIndexIntruderFigure() {
        return indexIntruderFigure;
    }

    public void setIndexIntruderFigure(byte indexIntruderFigure) {
        this.indexIntruderFigure = indexIntruderFigure;
    }

    public byte getIndexMoteFigure() {
        return indexMoteFigure;
    }

    public void setIndexMoteFigure(byte indexMoteFigure) {
        this.indexMoteFigure = indexMoteFigure;
    }

    public byte getIndexUavFigure() {
        return indexUavFigure;
    }

    public void setIndexUavFigure(byte indexUavFigure) {
        this.indexUavFigure = indexUavFigure;
    }
    
    public PaintNode getPaintNode(){
        return paintNode;
    }
}
