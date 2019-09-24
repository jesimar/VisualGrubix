
package br.com.vg.controller.structure;

/**
 * Classe responsável por armazenar os movimentos dos nós sensores moveis.
 * @author Jesimar Arantes
 */
public final class Move implements iPositionable{

    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Nó que está se movendo.
     */
    private final Node node;

    /**
     * Tempo que o nó esta atualizando o seu movimento.
     */
    private final double time;       

    /**
     * Coordenada X para onde o nó se moveu.
     */
    private float coordX;

    /**
     * Coordenada Y para onde o nó se moveu.
     */
    private float coordY;

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe Move.
     * @param node - nó que está se movimentando.
     * @param time - tempo em que o nó esta se movimentando.
     * @param coordX - nova coordenada x do nó que esta se movimentando.
     * @param coordY - nova coordenada y do nó que esta se movimentando.
     */
    public Move(Node node, double time, float coordX, float coordY){        
        this.node = node;
        this.time = time;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //=================================GET======================================

    /**
     * Captura o tempo no qual o nó que esta se movendo.
     * @return time do nó que esta se movendo.
     */
    public double getTime() {
        return time;
    }

    /**
     * Captura o nó que está se movendo na rede.
     * @return node - nó.
     */
    public Node getNode(){
        return node;
    }

    /**
     * Captura a coordenada X
     * @return Coordenada X.
     */
    public float getCoordX() {
        return coordX;
    }

    /**
     * Seta a coordenada X
     * @param coordX - coordenada X.
     */
    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    /**
     * Captura a coordenada Y
     * @return Coordenada Y.
     */
    public float getCoordY() {
        return coordY;
    }

    /**
     * Seta a coordenada Y
     * @param coordY - coordenada Y.
     */
    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }
}
