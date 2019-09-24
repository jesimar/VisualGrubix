
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Responsável por fazer a leitura de arquivo XML em tempo real sem jogar tudo
 * na memória (Utilizada principalmente para abrir arquivos muito grandes).
 * @author Jesimar Arantes
 */
public class ReaderLogXmlSAX extends DefaultHandler{

    /**
     * o galho atual
     */
    private StringBuffer galhoAtual = new StringBuffer(200);

    /**
     * o valor da tag atual
     */
    private StringBuffer valorAtual = new StringBuffer(100);

    private String readyString;

    private String readyField;

    private File file;
    private DataSimulation data;
    private JProgressBar progressBar;
    private int linhas = 0;

    private int nodeId;
    private float nodeX;
    private float nodeY;
    private String nodeType;

    private double time = 0.0;
    private int idEvent;
    private int receiverId;
    private int senderId;
    private int internReceiverId;
    private String senderLayer;

    private LinkedList<State> listState = new LinkedList<State>();   

    private Attributes atrib; 
    private double timeMove = 0.0;
    
    /**
     * Construtor da calsse XMLHandler.   
     * @param file - arquivo xml a ser lido.
     * @param data - objeto onde serão carregados os dados da simulação.     
     */
    public ReaderLogXmlSAX(File file, DataSimulation data, JProgressBar progressBar){
        this.file = file;
        this.data = data;
        this.progressBar = progressBar;
    }        
    
    /**
     * Lê o arquivo XML e carrega as informações da rede contida no XML na classe
     * ConfigNetwork se o carregamento ocorrer com sucesso returna true
     * caso contrario retorna false.     
     * @param file - arquivo xml a ser lido.
     * @param data - objeto onde serão carregados os dados da simulação.     
     * @throws Exception - Excessão lançada do arquivo xml.
     */
    public void readXML()throws Exception{
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();

            InputSource input = new InputSource(file.getPath());            
            parser.parse(input, this);
            
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("\nError check the format of file: \n" + ex);
        } catch (OutOfMemoryError ex){
            ex.printStackTrace();
            throw new Exception("\nFile consume lots of memory \n"
                    + "Out of memory error: very large file \n"
                    + "Increase the amount of memory the JVM" + ex);
        } catch (Exception ex){
            ex.printStackTrace();
            throw new Exception(ex);
        }
    }

    /**
     * Começa um documento novo
     */
    @Override
    public void startDocument() {
        
    }

    /**
     * Termina o documento
     */
    @Override
    public void endDocument() {
        try {            
            progressBar.setString("Aguarde... Criando Lista de Eventos...");
            createListEvents();            
            calcDataSimulation();            
            data.setSizeListAnim(data.getListEvents().size());
            progressBar.setString("Aguarde... Criando Lista Eventos Moveis...");
            createListEventsMoves();
            progressBar.setString("");
        } catch (Exception ex) {
            Logger.getLogger(ReaderLogXmlSAX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * comeca uma tag nova
     */
    @Override
    public void startElement(String uri, String localName, String tag,
        Attributes atributos){
        galhoAtual.append("/").append(tag); // seta o galho atual
        readyString = "<" + galhoAtual.substring(1)
                + (atributos.getLength() != 0 ? "/atributos" : "") + ">";
        
        valorAtual.delete(0, valorAtual.length()); // limpa a tag valor atual atual
        
        if (atributos.getLength() != 0){
            atrib = atributos;
        }
    }

    /**
     * recebe os dados de uma tag
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        valorAtual.append(ch, start, length);// adiciona ao valor atual
    }

    /**
     * termina uma tag
     */
    @Override
    public void endElement(String uri, String localName, String tag){
        readyField = valorAtual.toString().trim();// mostra o valor
        
        readFieldConfiguration();
        
        valorAtual.delete(0, valorAtual.length());// limpa a variavel valor atual

        // seta o galho atual
        galhoAtual.delete(galhoAtual.length() - tag.length() - 1, galhoAtual.length());
        
        atrib = null;
    }   
    
    //---------------------------MÉTODOS PRIVADOS-------------------------------

    //================================OTHER=====================================
    
     /**
     * Faz a leitura da parte de configuração da rede contida no xml.
     */
    private void readFieldConfiguration(){
        try{
            if (readyField.length() != 0){                               
                if(readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/field/x>")){
                    data.setDimensionX((int)Float.parseFloat(readyField));
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/field/y>")){
                    data.setDimensionY((int)Float.parseFloat(readyField));
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/simulationtime/atributos>")){
                    data.setTimeSimulationMax(Double.parseDouble(readyField));
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/communicationradius>")){
                    data.setRadiusCommunication(10 * Float.parseFloat(readyField));
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/nodes/count>")){
                    data.setAmmountNodes(Integer.parseInt(readyField));
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/positions/position/id>")){
                    nodeId = Integer.parseInt(readyField);
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/positions/position/x>")){
                    nodeX = 10 * Float.parseFloat(readyField);
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/positions/position/y>")){
                    nodeY = 10 * Float.parseFloat(readyField);                               
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/positions/position/ismobile>")){
                    Node node = new Node(nodeId, (int)nodeX, (int)nodeY,
                            data.getRadiusCommunication());                    
                    node.setNodeType(nodeType);
                    boolean isMobile = readyField.equalsIgnoreCase("true") ? true : false;
                    node.setIsMobile(isMobile);
                    data.addNode(node);
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/simulationrun/enqueue/time>")){
                     time = Double.parseDouble(readyField);    
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/simulationrun/enqueue/id>")){
                    idEvent = Integer.parseInt(readyField);    
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/simulationrun/enqueue/receiverid>")){
                    receiverId = Integer.parseInt(readyField);                        
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/simulationrun/enqueue/tolayer/senderid>")){
                    senderId = Integer.parseInt(readyField);                      
                }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/simulationrun/enqueue/tolayer/senderlayer>")){
                    senderLayer = readyField;    
                }
                else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/simulationrun/enqueue/tolayer/internreceiverid>")){
                    internReceiverId = Integer.parseInt(readyField); 
                    
                    if (senderLayer.equalsIgnoreCase("Physical")){
                        State state = new State(idEvent, receiverId, senderId,
                                internReceiverId, time);
                        listState.add(state);
                    }
                }
            }else if (readyString.equalsIgnoreCase
                        ("<simulatorlog/configuration/description/atributos>")){
                if (atrib != null){                    
                    data.setDescriptionApp(atrib.getValue(0));
                } 
            }else if (readyString.equalsIgnoreCase
                    ("<simulatorlog/configuration/positions/position/info/atributos>")){
                if (atrib != null){
                    nodeType = atrib.getValue(0);                    
                }  
            }else if(readyString.equalsIgnoreCase
                    ("<simulatorlog/simulationrun/nodestate/atributos>")){
                if (atrib != null){
                    NodeState nodeState = new NodeState(data.getNode(
                            Integer.parseInt(atrib.getValue(0))), time,
                            atrib.getValue(1), atrib.getValue(2), atrib.getValue(3));
                    data.addElementNodeState(nodeState);
                }
            }else if(readyString.equalsIgnoreCase
                    ("<simulatorlog/simulationrun/move/atributos>")){
                if (atrib != null){
                    Move move = new Move(data.getNode(
                            Integer.parseInt(atrib.getValue(0))),
                            Double.parseDouble(atrib.getValue(3)),
                            10 * Float.parseFloat(atrib.getValue(1)),
                            10 * Float.parseFloat(atrib.getValue(2)));
                    data.addElementMove(move);
                    
                    if (timeMove != Double.parseDouble(atrib.getValue(3))){
                        timeMove = Double.parseDouble(atrib.getValue(3));
                        data.addTimeMove(timeMove);
                    }
                    
                }
            }
            linhas++;
            progressBar.setString("Aguarde... Linhas: " + linhas);
        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "\nError Ready XML Field "
                    + "Configuration\n" + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createListEvents() throws Exception{
        try{
            State[] vetStates = listState.toArray(new State[listState.size()]);
            if (vetStates.length > 0){
                LinkedList<Integer> listInternReceiverId = new LinkedList<Integer>();
                int amountPacket = 0;
                for (int i = 0; i < vetStates.length - 1; ++i){
                    listInternReceiverId.add(vetStates[i].getInternReceiverId());
                    if (vetStates[i].getIdEvent() != vetStates[i+1].getIdEvent()){
                        ++amountPacket;
                        addEvent(listInternReceiverId, vetStates, i, amountPacket);
                    }
                }
                ++amountPacket;
                listInternReceiverId.add(vetStates[vetStates.length - 1].getInternReceiverId());
                addEvent(listInternReceiverId, vetStates, vetStates.length - 1, amountPacket);
            }
            listState.clear();
        }catch (Exception ex){
            throw new Exception("\nProblem create List Events: \n" + ex);
        }
    }

    /**
     * Adiciona um evento a lista de eventos na classe DataSimulation.
     */
    private void addEvent(LinkedList<Integer> listInternReceiverId, 
            State[] vetStates, int index, int amountPacket){

        Integer vetInternReceiverID[] = listInternReceiverId.
                toArray(new Integer[listInternReceiverId.size()]);

        LinkedList<Node> destinationNode = calcDestination(vetInternReceiverID, 
                vetStates[index].getReceiverId());

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
    private LinkedList<Node> calcDestination(Integer internReceiverId[], 
            int receiverId){
        
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
    private void createListEventsMoves(){
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
    private void calcDataSimulation() throws Exception{
        try{
            progressBar.setString("Aguarde... Calculando Lista Node Move...");
            data.calcListNodeMove();

            progressBar.setString("Aguarde... Calculando Campos Mapping...");
            data.calcFieldsMapping();            

            progressBar.setString("Aguarde... Calculando Vetor Mapping Node...");
            data.calcVetorMappingNode();            

            progressBar.setString("Aguarde... Criando Grafo de Conectividade...");
            data.calcGraphConnectivity();            

            progressBar.setString("Aguarde... Calculando Aba Dados...");
            data.calcData(file.getName());
            
            data.calcListPositionsTrain();

        }catch(Exception ex){
            throw new Exception("\nProblem Calc Data Simulation: \n" + ex);
        }
    }
}

