
package br.com.vg.controller.structure;

/**
 * Classe genérica responsável por modelar o nó.
 * @author Jesimar Arantes
 */
public abstract class NodeGeneric implements iPositionable{

    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Id de identificação do nó sensor.
     */
    protected final int id;

    /**
     * Raio de alcance de comunicação do nó sensor.
     */
    protected final float radiusCommunication;

    /**
     * Coordenada X onde o nó se encontra.
     */
    protected float coordX;

    /**
     * Coordenada Y onde o nó se encontra.
     */
    protected float coordY;

    //-------------------------------CONSTRUTOR---------------------------------

    /**
     * Construtor da classe Node.
     * @param id - id do nó.
     * @param coordX - coordenada x do nó.
     * @param coordY - coordenada y do nó.
     * @param radiusComunication - raio de alcance de comunicação do nó.
     */
    public NodeGeneric(int id, float coordX, float coordY, float radiusComunication){
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.radiusCommunication = radiusComunication;
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //================================GET=======================================

    /**
     * Captura o id do nó sensor.
     * @return id do nó sensor.
     */
    public int getId(){
        return id;
    }

    /**
     * Captura o raio de comunicação do nó sensor.
     * @return raio de comunicação do sensor.
     */
    public float getRadiusCommunication() {
        return radiusCommunication;
    }
}
