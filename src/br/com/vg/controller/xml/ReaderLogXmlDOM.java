
package br.com.vg.controller.xml;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.control.EventGeneric;
import br.com.vg.controller.control.EventMove;
import br.com.vg.controller.control.EventMsg;
import br.com.vg.controller.structure.Move;
import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.NodeState;
import br.com.vg.controller.structure.State;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JProgressBar;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Classe que efetua a leitura do arquivo XML que contem as informações da
 * simulação.
 * @author Jesimar Arantes
 */
public final class ReaderLogXmlDOM {
    
    private Double timeMove = 0.0;

    /**
     * Construtor padrão da classe ReaderLogXmlDOM.
     */
    public ReaderLogXmlDOM(){        
    }
    
    //---------------------------MÉTODOS PÚBLICOS-------------------------------

    //================================OTHER=====================================

    /**
     * Lê o arquivo XML e carrega as informações da rede contida no XML na classe
     * ConfigNetwork se o carregamento ocorrer com sucesso returna true
     * caso contrario retorna false.
     * @param file - arquivo xml a ser lido.
     * @param data - objeto onde serão carregados os dados da simulação.
     * @param progressBar - barra de progresso indicando o quanto foi carregado
     * @throws Exception - Excessão lançada.
     * do arquivo xml.
     */
    public void readXML(final File file, DataSimulation data,
            JProgressBar progressBar) throws Exception{
        try {            
            SAXBuilder builder = new SAXBuilder();
            Document doc;
            
            doc = builder.build(file.getPath());
            Element element = doc.getRootElement();           
            
            readFieldConfiguration(data, element);
            
            LinkedList<State> listStates = new LinkedList<State>();
            
            readFieldSimulation(data, element, progressBar, listStates);
            
            createListEvents(data, listStates);
            progressBar.setValue(55);
            
            calcDataSimulation(file, data, progressBar);
            
            data.setSizeListAnim(data.getListEvents().size());            
            createListEventsMoves(data);

            progressBar.setValue(100);
        } catch (JDOMException ex) {
            throw new Exception("\nError check the format of file: \n" + ex);
        } catch (IOException ex) {
            throw new Exception("\nError check the format of file: \n" + ex);
        } catch (OutOfMemoryError ex){
            throw new Exception("\nFile consume lots of memory \n"
                    + "Out of memory error: very large file \n"
                    + "Increase the amount of memory the JVM" + ex);
        } catch (Exception ex){
            throw new Exception("\nError reader log xml: \n" + ex);
        }
    }

    //---------------------------MÉTODOS PRIVADOS-------------------------------

    //================================OTHER=====================================

    /**
     * Faz a leitura da parte de configuração da rede contida no xml.
     */
    private void readFieldConfiguration(DataSimulation data,
            Element element) throws Exception{
        try{
            Element elemConfig = element.getChild("configuration");

            String description = elemConfig.getChild("description")
                    .getAttributeValue("write");

            int dimensionX = (int)Float.parseFloat(elemConfig.getChild("field")
                    .getChildText("x"));

            int dimensionY = (int)Float.parseFloat(elemConfig.getChild("field")
                    .getChildText("y"));

            double simulationTime = Double.parseDouble(elemConfig
                    .getChildText("simulationtime"));

            float communRadius = Float.parseFloat(elemConfig
                    .getChildText("communicationradius"));

            int ammountNodes = Integer.parseInt(elemConfig.getChild("nodes")
                    .getChildText("count"));

            int vetId[] = new int[ammountNodes];
            float vetX[] = new float[ammountNodes];
            float vetY[] = new float[ammountNodes];
            String vetNodeType[] = new String[ammountNodes];
            boolean vetIsMobile[] = new boolean[ammountNodes];
            
            List<Element> listPositions = elemConfig.getChild("positions").getChildren();
            int i = 0;

            for (Element pos: listPositions){
                vetId[i] = Integer.parseInt(pos.getChildText("id"));
                vetX[i] = 10 * Float.parseFloat(pos.getChildText("x"));
                vetY[i] = 10 * Float.parseFloat(pos.getChildText("y"));
                vetNodeType[i] = pos.getChild("info").getAttributeValue("nodetype");
                vetIsMobile[i] = pos.getChildText("ismobile")
                        .equalsIgnoreCase("true") ? true : false;
                ++i;
            }

            storeConfiguration(data, dimensionX, dimensionY, simulationTime,
                    ammountNodes, vetId, vetX, vetY, vetNodeType,
                    vetIsMobile, description, communRadius);
        }catch (Exception ex){
            throw new Exception("\nProblem configuration: \n" + ex);
        }
    }

    /**
     * Armazena as informações da leitura da configuração do arquivo xml na
     * classe DataSimulation.
     */
    private void storeConfiguration(DataSimulation data, int dimX,
            int dimY, double simulationTime, int ammountNodes, int[] vetId,
            float[] vetX, float[] vetY, String[] vetNodeType,
            boolean[] vetIsMobile, String description, float communRadius){

        data.setDimensionX(dimX);
        data.setDimensionY(dimY);
        data.setTimeSimulationMax(simulationTime);
        data.setAmmountNodes(ammountNodes);
        data.setDescriptionApp(description);
        data.setRadiusCommunication(10 * communRadius);
        
        for (int i = 0; i < ammountNodes; ++i){
            Node node = new Node(vetId[i], (int)vetX[i], (int)vetY[i],
                    data.getRadiusCommunication());            
            node.setNodeType(vetNodeType[i]);            
            node.setIsMobile(vetIsMobile[i]);
            data.addNode(node);
        }
    }

    /**
     * Faz a leitura da parte simulationRun do arquivo xml.
     */
    private void readFieldSimulation(DataSimulation data, Element element, 
            JProgressBar progressBar, LinkedList<State> listStates) throws Exception {
        try{
            Element elemSimulationRun = element.getChild("simulationrun");
            
            List<Element> listChildrenSimRun = elemSimulationRun.getChildren();

            double time = 0.0;

            for (Element tagSimulationRun: listChildrenSimRun){
                if (tagSimulationRun.getName().equalsIgnoreCase("enqueue")){
                    if (tagSimulationRun.getChild("tolayer")
                            .getChildText("senderlayer")
                            .equalsIgnoreCase("Physical")){

                        time = Double.parseDouble(tagSimulationRun.getChildText("time"));
                        readAndStoreEnqueue(listStates, tagSimulationRun, time);
                        progressBar.setValue(
                                (int)(time * 100 / (2 * data.getSimulationTimeMax()))
                            );
                    }
                }
                else if(tagSimulationRun.getName().equalsIgnoreCase("nodestate")){
                    readAndStoreNodeState(data, tagSimulationRun, time);
                }
                else if(tagSimulationRun.getName().equalsIgnoreCase("move")){
                    readAndStoreMove(data, tagSimulationRun);
                }
            }
        }catch (Exception ex){
            throw new Exception("\nProblem simulationrun: \n" + ex);
        }
    }

    /**
     * Lê as informações do campo Enqueue Physical e armazena as informações na
     * classe DataSimulation.
     */
    private void readAndStoreEnqueue(LinkedList<State> listStates,
            Element tagSimulationRun, double time){

        int id = Integer.parseInt(tagSimulationRun.getChildText("id"));
        int receiverId = Integer.parseInt(tagSimulationRun
                .getChildText("receiverid"));
        int senderId = Integer.parseInt(tagSimulationRun
                .getChild("tolayer").getChildText("senderid"));
        int internReceiverId = Integer.parseInt(tagSimulationRun
                .getChild("tolayer").getChildText("internreceiverid"));

        State state = new State(id, receiverId, senderId, internReceiverId, time);
        listStates.add(state);
    }

    /**
     * Lê as informações do campo NodeState do xml e armazena na classe
     * DataSimulation.
     */
    private void readAndStoreNodeState(DataSimulation data, Element tagSimulationRun,
            double time){

        int id = Integer.parseInt(tagSimulationRun.getAttributeValue("id"));
        String name = tagSimulationRun.getAttributeValue("name");
        String type = tagSimulationRun.getAttributeValue("type");
        String value = tagSimulationRun.getAttributeValue("value");
        NodeState nodeState = new NodeState(data.getNode(id), time, name, type, value);
        data.addElementNodeState(nodeState);
    }

    /**
     * Lê as informações da campo Move do xml e armazena na classe DataSimulation.
     */
    private void readAndStoreMove(DataSimulation data, Element tagSimulationRun){
        
        int id = Integer.parseInt(tagSimulationRun.getAttributeValue("id"));
        float x = 10 * Float.parseFloat(tagSimulationRun.getAttributeValue("x"));
        float y = 10 * Float.parseFloat(tagSimulationRun.getAttributeValue("y"));
        double time = Double.parseDouble(tagSimulationRun.getAttributeValue("time"));
        Move move = new Move(data.getNode(id), time, x, y);
        data.addElementMove(move);
        
        if (timeMove != time){
            timeMove = time;
            data.addTimeMove(timeMove);
        }
    }
    
    /**
     * Cria uma lista de eventos.
     */
    private void createListEvents(DataSimulation data, LinkedList<State> listState)
            throws Exception{        
        try{
            State[] vetStates = listState.toArray(new State[listState.size()]);
            if (vetStates.length > 0){
                LinkedList<Integer> listInternReceiverId = new LinkedList<Integer>();
                int amountPacket = 0;
                for (int i = 0; i < vetStates.length - 1; ++i){
                    listInternReceiverId.add(vetStates[i].getInternReceiverId());
                    if (vetStates[i].getIdEvent() != vetStates[i+1].getIdEvent()){
                        ++amountPacket;
                        addEvent(data, listInternReceiverId, vetStates, i, amountPacket);
                    }
                }
                ++amountPacket;
                listInternReceiverId.add(vetStates[vetStates.length - 1].getInternReceiverId());
                addEvent(data, listInternReceiverId, vetStates, 
                        vetStates.length - 1, amountPacket);
            }
            listState.clear();
        }catch (Exception ex){
            throw new Exception("\nProblem create List Events: \n" + ex);
        }
    }

    /**
     * Adiciona um evento a lista de eventos na classe DataSimulation.
     */
    private void addEvent(DataSimulation data, LinkedList<Integer>
            listInternReceiverId, State[] vetStates, int index, int amountPacket){

        Integer vetInternReceiverID[] = listInternReceiverId.
                toArray(new Integer[listInternReceiverId.size()]);        
        
        LinkedList<Node> destinationNode = calcDestination(data,
                vetInternReceiverID, vetStates[index].getReceiverId());

        Node nodeSource = data.getNode(vetStates[index].getIdSender());
        data.addEvent(new EventMsg(nodeSource, destinationNode,
                vetStates[index].getTime(), amountPacket));

        listInternReceiverId.clear();
    }

    /**
     * Calcula os nos destinos do nó origem dado o alcance do raio de comunicação
     * entre os nós.
     * @param internReceiverId - vetor de id dos nós sensores de destino (quando
     * for broadcasting).
     * @param receiverId - id do sensor de destino.
     * @return lista de nós destino.
     */
    private LinkedList<Node> calcDestination(DataSimulation data,
            Integer internReceiverId[], int receiverId){
        LinkedList<Node> destinationNode = new LinkedList<Node>();
        //BroadCast
        if (receiverId == -1){
            for (Integer idDestination: internReceiverId){
                destinationNode.add(data.getNode(idDestination));
            }
        }
        //UniCast
        else{
            destinationNode.add(data.getNode(receiverId));
        }
        return destinationNode;
    }

    /**
     * Cria uma lista de eventos colocando na lista os eventos de apenas
     * movimentos.
     */
    private void createListEventsMoves(DataSimulation data){
        boolean teste;
        int j;        
        EventMove eventMove;
        for (Double moveTime: data.getListTimeMove()){
            teste = false;
            j = 0;
            for (EventGeneric event: data.getListEvents()){
                if (moveTime < event.getTime()){
                    teste = true;                    
                    eventMove = new EventMove(moveTime);
                    data.addEventPosition((EventGeneric)eventMove, j);
                    break;
                }
                ++j;
            }
            if (!teste){                
                eventMove = new EventMove(moveTime);
                data.addEventPosition((EventGeneric)eventMove, j);
            }
        }
    }

    /**
     * Processa as informações dos dados da simulação.
     */
    private void calcDataSimulation(File file, DataSimulation data,
            JProgressBar progBar) throws Exception{
        try{
            
            data.calcListNodeMove();
            progBar.setValue(65);

            data.calcFieldsMapping();
            progBar.setValue(75);

            data.calcVetorMappingNode();
            progBar.setValue(80);

            data.calcGraphConnectivity();
            progBar.setValue(85);

            data.calcData(file.getName());
            progBar.setValue(90);

            data.calcListPositionsTrain();
            progBar.setValue(95);
            
        }catch(Exception ex){
            throw new Exception("\nProblem Calc Data Simulation: \n" + ex);
        }
    }    
}
