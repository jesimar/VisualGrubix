
package br.com.vg.view.paint;

import br.com.vg.controller.control.DataSimulation;
import br.com.vg.controller.control.Handler;
import br.com.vg.controller.control.Simulation;
import br.com.vg.controller.resources.ResourcesGraphics;
import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.Position;
import br.com.vg.controller.structure.iConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;

/**
 * Classe responsável por reimplementar a classe DrawSpace (abstrata).
 * @author Jesimar Arantes
 */
public class DrawSpaceViewer extends DrawSpace {

    //------------------------------ATRIBUTOS-----------------------------------      

    /**
     * Variável indica se o botão foi precionado ou não sobre um
     * determinado nó.
     */
    private boolean isPressedNode = false;

    /**
     * Atributo que indica se a régua esta visivel ou não.
     */
    private boolean visibleRuler;

    /**
     * Atributo que indica se o anti-aliasing esta ativado ou não.
     */
    private boolean antiAliasing;
    
    /**
     * Atributo que indica se o recurso linhas de movimento esta ativado ou não.
     */
    private boolean movimentLines;
    
    private boolean isImgBackground;
    
    private ImageIcon imgBackground;
    
    /**
     * Nó onde o mouse foi clicado. Será utilizado para exibir as
     * informações deste nó na tela.
     */
    private Node nodeStatus = null;

    /**
     * Atributo que contém os dados da simulação.
     */
    private DataSimulation dataSimulation;

    /**
     * Atributo que manipula a simulação.
     */
    private Simulation simulation;
   
    /**
     * Lista de nós destino da comunicação do nó selecionado pelo mouse.
     */
    private LinkedList<Node> nodeDestination = new LinkedList<Node>();

    private Rectangle2D rectArea;
    
    private ResourceBundle idioma = Handler.getInstance().getIdioma();  
    
    private ResourcesGraphics resources = Handler.getInstance().getResources();
    
    //------------------------------CONSTRUTOR----------------------------------

    /**
     * Construtor da classe.
     * @param simulation - instancia da simulação.
     */
    public DrawSpaceViewer(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Seta a visibilidade da régua.
     * @param visibleRuler - indica se a regua esta visivel ou não.
     */
    public void visibleRule(boolean visibleRuler){
        this.visibleRuler = visibleRuler;
    }
    
    /**
     * Seta se o anti aliasing esta habilitado ou não.
     * @param antiAliasing - habilitado ou não.
     */
    public void setAntiAliasing(boolean antiAliasing) {
        this.antiAliasing = antiAliasing;
    }
    
    public void setImgBackground(boolean isImgBackground) {
        this.isImgBackground = isImgBackground;
    }
    
    public void setImgBackground(ImageIcon imgBackground) {
        this.imgBackground = imgBackground;
    }

    /**
     * Seta os dados da simulação para exibição na tela.
     * @param data - dados da simulação.
     */
    public void setDataSimulation(DataSimulation data){
        synchronized(this){
            this.dataSimulation = data;
            if (dataSimulation != null){
                rectArea = new Rectangle(0, 0,
                        10 * dataSimulation.getDimensionX(),
                        10 * dataSimulation.getDimensionY());
            }
            repaint();
        }
    }

    /**
     * Habilita/desabilita as linhas de movimento do UAV ou do Intruso.
     * @param state
     */
    public void visibleMovimentLines(boolean state){
        this.movimentLines = state;
    }
    
     /**
     * Seta todos os valores das configurações de paint como default.
     */
    public void valuesDefault(){
        antiAliasing = false;
        movimentLines = false;
        visibleRuler = false;
        isPressedNode = false;
        isImgBackground = false;
        imgBackground = null;
        nodeDestination.clear();
        zoom = 0.2;
        repaint();
    }

    //------------------------------MÉTODOS PROTEGIDOS--------------------------

    /**
     * Inicializa (reseta) o zoom da vizualiação.
     */
    @Override
    protected void init() {
        zoom = 0.2;
    }

    /**
     * Parte responsavel por desenhar na tela tudo que se quiser antes
     * de se aplicar qualquer translação ou zoom no sistema.
     * @param g2 - Graphics2D para pintar na draw.
     */
    @Override
    protected void paintComponentAntes(Graphics2D g2) {
        super.paintComponentAntes(g2);

        if (dataSimulation == null){
            paintHelpInit(g2);
            return;
        }
        g2.setColor(Color.black);
        g2.drawRect(0, 0, width-1, height-1);

        if (visibleRuler) {
            paintRuler(g2);
        }
        if (isPressedNode) {
            paintStatusNodePressed(g2, visibleRuler);
        }
    }
        
    /**
     * Parte responsavel por desenhar na tela tudo que se quiser depois
     * de se aplicar qualquer translação ou zoom no sistema.
     * @param g2 - Graphics2D para pintar na draw.
     */
    @Override
    protected void paintComponentDepois(Graphics2D g2) {
        super.paintComponentDepois(g2);

        if (dataSimulation == null){
            return;
        }
        synchronized(this){
            if (antiAliasing) {
                g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            }
            g2.setColor(Color.lightGray);
            
            if (!isImgBackground){
                g2.setColor(Color.white);
                g2.fill(rectArea);
            }else{                
                g2.drawImage(imgBackground.getImage(), 0, 0, 
                        10 * dataSimulation.getDimensionX() - 1,
                        10 * dataSimulation.getDimensionY() - 1 , null); 
            }           
            
            simulation.paint(g2);

            if (isPressedNode) {
                dataSimulation.getPaintNode().paintNodeColor(g2, nodeStatus, Color.blue);
                paintNodePressed(g2);
            }                    

            if (movimentLines){
                paintLinesObjMoves(g2);
            }
        }
    }
    
    /**
     * Movimentação do mouse.
     * @param e - evento de movimento.
     * @param X - nova coordenada X.
     * @param Y - nova coordenada Y.
     */
    @Override
    protected void MouseMoved(MouseEvent e, double X, double Y) {       
        updateCoord(X, Y);
    }

    public void updateCoord(double x, double y){
        
    }

    /**
     * Mouse precionado.
     * @param e - evento de movimento.
     * @param X - coordenada do clique X.
     * @param Y - coordenada do clique Y.
     */
    @Override
    protected void MousePressed(MouseEvent e, double X, double Y) {
        isPressedNode = false;
        if(dataSimulation != null){
            float x;
            float y;
            float r;
            for (Node node : dataSimulation.getListNodes()) {
                x = node.getCoordX();
                y = node.getCoordY();
                r = dataSimulation.getRadiusNode();
                if ((X - x) * (X - x) + (Y - y) * (Y - y) <= r * r) {
                    isPressedNode = true;
                    nodeStatus = node;
                    nodeDestination.clear();
                }
            }
        }    
    }

    //-----------------------------MÉTODOS PRIVADOS-----------------------------

    private void paintHelpInit(Graphics2D g2){
        ImageIcon imgIcon = new ImageIcon(resources.getImage(
                ResourcesGraphics.IMAGE_FUNDO_PORT));
//        ImageIcon imgIcon = new ImageIcon(resources.getImage(
//                ResourcesGraphics.IMAGE_FUNDO_INGLES));  
        g2.drawImage(imgIcon.getImage(), 0, 0, width - 1, height - 1 , null);        
    }
    
    /**
     * Pinta em uma tabela os status do nó precionado pelo mouse.
     * Os seguintes status são: id, coordx, coordy, size sensor, size
     * communication.
     */
    private void paintStatusNodePressed(Graphics2D g2, boolean visibleRuler) {
        
        int coordX = 40;
        int coordY = 40;
        if (!visibleRuler) {
            coordX = 0;
            coordY = 0;
        }
        g2.setColor(new Color(0, 120, 200, 100));
        g2.fillRect(coordX, coordY, 200, 130);
        g2.setColor(Color.black);
        g2.drawRect(coordX, coordY, 200, 130);

        g2.drawRect(coordX + 127, coordY + 3, 47, 47);
        g2.drawImage(dataSimulation.getPaintNode().getImageNode(nodeStatus.getNodeType()),
                coordX + 130, coordY + 5, 42, 42, null);

        g2.drawString(idioma.getString("node_info"), coordX + 10, coordY + 20);
        g2.drawString(idioma.getString("id") + ": " + nodeStatus.getId(),
                coordX + 10, coordY + 40);
        g2.drawString(idioma.getString("coordx") + ": " + String.format("%5.1f",
                nodeStatus.getCoordX() / 10.0), coordX + 10, coordY + 60);
        g2.drawString(idioma.getString("coordx") + ": " + String.format("%5.1f",
                nodeStatus.getCoordY() / 10.0), coordX + 10, coordY + 80);        
        g2.drawString(idioma.getString("comm_range") + ": " + nodeStatus
                .getRadiusCommunication() / 10.0, coordX + 10, coordY + 100);
        g2.drawString(idioma.getString("node_type") + ": " + nodeStatus.getNodeTypeString(),
                coordX + 10, coordY + 120);
    }

    /**
     * Desenha informações na tela do nó precionado como o alcance do nó sensor
     * selecionado e raio de comunicação do nó.
     */
    private void paintNodePressed(Graphics2D g2) {
        if (nodeStatus.getNodeType() != iConstants.NODE_INTRUDER){
            g2.setColor(new Color(0, 200, 200, 100));            

            if(nodeStatus.isMobile()){
                nodeDestination.clear();
            }
            PaintGraphConnectivity paintGraph = new PaintGraphConnectivity();
            paintGraph.calcDestinationNodes(dataSimulation, nodeStatus, nodeDestination);

            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(3));
            for (Node no: nodeDestination){
                g2.drawLine(
                        (int)nodeStatus.getCoordX(),
                        (int)nodeStatus.getCoordY(),
                        (int)no.getCoordX(),
                        (int)no.getCoordY()
                    );
            }
            g2.setStroke(new BasicStroke(1));
        }
    }

    /**
     * Pinta a linha dos objetos que se movem na simulação.
     * @param g2 - graphics2d
     */
    private void paintLinesObjMoves(Graphics2D g2){
        LinkedList<Position> vect[] = dataSimulation.getPositionTrain();
        g2.setStroke(new BasicStroke(4));
        int r;
        int g;
        int b;
        for (int i = 0; i < vect.length; ++i){            
            if (i % 3 == 0){
                r = 255;
                g = (i+100 + i * 70)%255;
                b = (i+100 + i * 70)%255;
            }else if (i % 3 == 1){
                r = (i+100 + i * 70)%255;
                g = 255;
                b = (i+100 + i * 70)%255;
            }else{
                r = (i+100 + i * 70)%255;
                g = (i+100 + i * 70)%255;
                b = 255;
            }
            g2.setColor(new Color(r, g, b));
            for (int j = 0; j < vect[i].size() - 1; ++j){
                g2.drawLine(
                        (int)(vect[i].get(j).getCoordX()),
                        (int)(vect[i].get(j).getCoordY()),
                        (int)(vect[i].get(j + 1).getCoordX()),
                        (int)(vect[i].get(j + 1).getCoordY())
                    );
            }
        }
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Pinta a regua na tela de visualização da simulação.
     */
    private void paintRuler(Graphics2D g2) {        
        g2.setColor(Color.gray);
        g2.fillRect(0, 0, width, 40);
        g2.fillRect(0, 0, 40, height);
        g2.setColor(Color.black);

        paintScaleNumeric(g2, width, Cx, true);
        paintScaleNumeric(g2, height, Cy, false);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, 40, 40);
    }

    /**
     * Pinta na tela a escala numerica da regua.
     */
    private void paintScaleNumeric(Graphics2D g2, int dimension, double center,
            boolean isX) {
        double dim1 = center - dimension * (zoom) / 2;
        double dim2 = center + dimension * (zoom) / 2;
        double delta = (dim2 - dim1) * 100 / dimension;
        double incr = (int) Math.log10(delta);
        incr = delta > Math.pow(10, incr + 1) / 2.0 ? incr + 1 : incr;
        incr = (int) Math.pow(10.0, incr);
        incr /= 100;
        delta /= incr;

        int i = 0;
        int fact = (int)(dimension / 2 - center * zoom);
        while ((int) (fact + i * delta) > 40) {
            --i;
        }
        while ((int) (fact + i * delta) < 40) {
            ++i;
        }
        g2.setColor(Color.BLACK);
        int value;
        int factor = (int) (fact + i * delta);
        while (factor < dimension) {            
            value = (int)(i * 10 / incr);
            if (isX) {                
                g2.drawLine(factor, 40 - 1, factor, 30 - 1);
                g2.setColor(Color.GRAY);
                g2.drawLine(factor + 1, 40 - 1, factor + 1, 30 - 1);
                g2.setColor(Color.BLACK);
                g2.drawString(String.format("%d", value), factor, 20);
            } else {
                g2.drawLine(30 - 1, factor , 40 - 1, factor);
                g2.setColor(Color.GRAY);
                g2.drawLine(30 - 1, factor + 1, 40 - 1, factor + 1);
                g2.setColor(Color.BLACK);
                g2.drawString(String.format("%d", value), 5, factor);
            }
            ++i;
            factor = (int) (fact + i * delta);
        }
    }
}
