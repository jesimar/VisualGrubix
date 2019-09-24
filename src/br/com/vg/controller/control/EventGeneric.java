
package br.com.vg.controller.control;

import br.com.vg.util.iCallBack;

/**
 * Classe genérica responsável pelos eventos do simulador.
 * @author Jesimar Arantes
 */
public abstract class EventGeneric {

    //-----------------------------ATRIBUTOS------------------------------------    

    /**
     * Indice do ciclo de animação para fazer a animação (dos pacotes ou
     * movimentação de nós).
     */
    protected static byte cyclesEvent;

    /**
     * Tempo do evento atual.
     */
    protected double time;

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe EventGeneric.
     * @param time - tempo atual.
     */
    public EventGeneric(double time){
        this.time = time;
    }
    
    //-------------------------MÉTODOS ABSTRATOS--------------------------------

    /**
     * Roda o evento corrente do simulador de animação
     * @param repaint - callback para desenhar na tela.
     * @param speedAnimation - velocidade da animação.     
     * @throws Exception - Exceção Lançada.
     */
    public void runMsg(iCallBack repaint, float speedAnimation) throws Exception{        
    }

    /**
     * Roda o evento corrente do simulador de movimento.
     * @param data - dados da simulação.
     * @param repaint - callback para desenhar na tela.
     * @param speedAnimation - velocidade da animação.
     * @param statusSimulation - indica o status da simulação (Ex. Play, Back).
     * @throws Exception - Exceção Lançada.
     */
    public void runMove(DataSimulation data, iCallBack repaint,
            float speedAnimation, byte statusSimulation) throws Exception{        
    }

    //================================GET=======================================

    /**
     * Captura o tempo atual deste evento.
     * @return time - tempo deste evento.
     */
    public double getTime(){
        return time;
    }
}
