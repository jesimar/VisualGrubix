
package br.com.vg.controller.structure;

/**
 * Classe responsável por armazenar os estados do simulação.
 * @author Jesimar Arantes
 */
public final class State {

    //-----------------------------ATRIBUTOS------------------------------------

    /**
     * Tempo que ocorre tal estado.
     */
    private final double time;

    /**
     * Id do evento.
     */
    private final int idEvent;

    /**
     * Tipo de envio de pacote. (unicast, broadcast).
     */
    private final int receiverId;

    /**
     * Nó de origem do pacote.
     */
    private final int idSender;

    /**
     * Nó de destino do pacote.
     */
    private final int internReceiverId;

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe State.
     * @param idEvent - id do evento.
     * @param receiverId - id do nó que esta recebendo o pacote.
     * @param idSender - id do nó que esta enviando o pacote.
     * @param internReceiverId - tipo de envio de pacote. (unicast ou broadcast).
     * @param time - tempo que o pacote foi enviado.
     */
    public State(int idEvent, int receiverId, int idSender, int internReceiverId,
            double time){
        
        this.idEvent = idEvent;
        this.receiverId = receiverId;
        this.idSender = idSender;
        this.internReceiverId = internReceiverId;        
        this.time = time;
    }
    
    //-----------------------------ATRIBUTOS------------------------------------
    
    //================================GET=======================================

    /**
     * Captura o id do evento do estado atual.
     * @return idEvent - id do evento do estado atual.
     */
    public int getIdEvent() {
        return idEvent;
    }

    /**
     * Captura o id do nó que está enviando o pacote.
     * @return idSender - id do nó que está enviando o pacote.
     */
    public int getIdSender() {
        return idSender;
    }

    /**
     * Captura o id do nó que está recebendo o estado atual.
     * @return internReceiverId - id do nó que está recebendo o pacote.
     */
    public int getInternReceiverId() {
        return internReceiverId;
    }

    /**
     * Captura o identificador de tipo de envio de pacote. 
     * (id = nó destino; id = -1 [broadcast]).
     * @return receiverId - tipo de envio de pacote.
     */
    public int getReceiverId() {
        return receiverId;
    }

    /**
     * Captura o tempo do estado atual.
     * @return time - tempo do estado atual.
     */
    public double getTime() {
        return time;
    }
}
