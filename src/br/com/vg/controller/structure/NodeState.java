
package br.com.vg.controller.structure;

/**
 * Classe responsável por armazenar os NodeStates (para desenhar na
 * tela a forma dos nós é a sua cor (Mapping)).
 * @author Jesimar Arantes
 */
public final class NodeState {

    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Tempo de ocorrencia do nodestate.
     */
    private final double time;

    /**
     * Nó que esta sendo atualizado sua visualização na tela.
     */
    private final Node node;

    /**
     * Nome do nodestate.
     */
    private final String name;

    /**
     * Tipo de valor do nodestate (Int ou Float).
     */
    private final String type;

    /**
     * Valor se tipo for int.
     */
    private int valueInt = -1;
    
    /**
     * Valor se tipo for float.
     */
    private float valueFloat = -1.0f;

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe NodeState.
     * @param node - nó que será a colorido.
     * @param time - tempo do ocorrencia do estado nodestate.
     * @param name - nome do tipo de ocorrência do nodestate.
     * @param type - tipo de value (int, float).
     * @param value - valor (indica a cor do nó, label do nó).
     */
    public NodeState(Node node, double time, String name, String type, String value){
        this.node = node;
        this.time = time;        
        this.name = name;
        this.type = type;
        if (type.equalsIgnoreCase("int")){
            this.valueInt = Integer.parseInt(value);
        }else if (type.equalsIgnoreCase("float")){
            this.valueFloat = Float.parseFloat(value);
        }
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //================================GET=======================================

    /**
     * Captura o nó que está sendo atualizado a sua visualização.
     * @return node - nó.
     */
    public Node getNode(){
        return node;
    }

    /**
     * Captura o nome do nodestate (Ex. Broadcast, Primeiro Envio, etc).
     * @return nome do nó.
     */
    public String getName() {
        return name;
    }

    /**
     * Captura time de ocorrência deste nodestate.
     * @return time do nó.
     */
    public double getTime() {
        return time;
    }

    /**
     * Captura o tipo de nó (Int ou Float).
     * @return type de nó.
     */
    public String getType() {
        return type;
    }

    /**
     * Captura o value se type for int.
     * @return valueFloat do nó.
     */
    public float getValueFloat() {
        return valueFloat;
    }

    /**
     * Captura o value se type for float.
     * @return valueInt do nó.
     */
    public int getValueInt() {
        return valueInt;
    }
}
