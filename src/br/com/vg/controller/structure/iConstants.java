
package br.com.vg.controller.structure;

/**
 * Interface responsável por conter algumas flags do estado da simulação.
 * @author Jesimar Arantes
 */
public interface iConstants {

    //---------------------------------FLAGS------------------------------------

    //Flags dos estados da simulação.

    /**
     * Indica o modo da simulação como: corrente.
     */
    public static final byte PLAY = 0;

    /**
     * Indica o modo da simulação como: Parada.
     */
    public static final byte PAUSE = 1;

    /**
     * Indica o modo da simulação como: retrocedendo.
     */
    public static final byte BACK = 2;

    //Flags do mapping da simulação.

    /**
     * Indica o modo de exibição da simulação como: nenhum.
     */
    public static final byte NONE = 0;

    /**
     * Indica o modo de exibição da simulação como: Color (Colorido os nós).
     */
    public static final byte COLOR = 1;

    /**
     * Indica o modo de exibição da simulação como: Boarder Color (Colorido as
     * bordas dos nós).
     */
    public static final byte BOARDER_COLOR = 2;

    /**
     * Indica o modo de exibição da simulação como: Label (Número identificador).
     */
    public static final byte LABEL = 3;

    //Flags que indicam os tipos de nós da simulação.

    /**
     * Indica que um nó da rede é um nó normal.
     */
    public static final byte NODE_REGULAR = 0;

    /**
     * Indica que um nó da rede é um nó UAV. Ou seja, um nó móvel.
     */
    public static final byte NODE_UAV = 1;

    /**
     * Indica que um nó da rede é um Intruso. Ou seja, algo que queremos detectar.
     */
    public static final byte NODE_INTRUDER = 2;

    //Flags que indicam como os pacotes serão enviados.

    /**
     * Forma de envio do pacote da simulação em formato de pacotihos
     * sincronizados.
     */
    public static final byte PACKET_SYNCHRONOUS = 0;

    /**
     * Forma de envio do pacote da simulação em formato de pacotinhos
     * descincronizados.
     */
    public static final byte PACKET_ASYNCHRONOUS = 1;

    /**
     * Forma de envio do pacote da simulação em formato de onda.
     */
    public static final byte PACKET_WAVE = 2;

    /**
     * Forma de envio do pacote da simulação em formato de onda com transparência.
     */
    public static final byte PACKET_WAVE2 = 3;

    /**
     * Forma de envio do pacote da simulação em formato de onda com transparência
     * que diminui com a distancia.
     */
    public static final byte PACKET_WAVE3 = 4;    

    //Flags que indicam como serão coloridos os grafos de conexão da comunicação
    //da rede de sensores.

    /**
     * Grafo de comunicação desabilitado. Ou seja, não vai ser desenhado.
     */
    public static final byte GRAPH_CONNECTIVITY_DISABLE = 0;

    /**
     * Grafo de comunicação habilitado. Ou seja, vai ser desenhado o grafo.
     */
    public static final byte GRAPH_CONNECTIVITY_ENABLE = 1;

    /**
     * Grafo de comunicação com setas de orientação habilitado. Ou seja, vai ser
     * desenhado o grafo com as setas de orientação da direção da comunicação.
     */
    public static final byte GRAPH_CONNECTIVITY_ORIENTED = 2;

    /**
     * Tamanho máximo dos ciclos de animação (dos pacotes ou movimentação
     * de nós).
     */
    public static final byte SIZE_CYCLES_ANIM = 16;

    /**
     * Tamanho máximo dos ciclos de movimento.
     */
    public static final byte SIZE_CYCLES_MOVE = 12;

}
