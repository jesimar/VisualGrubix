
package br.com.vg.controller.control;

import br.com.vg.controller.structure.Move;
import br.com.vg.controller.structure.Node;
import br.com.vg.controller.structure.Position;
import static br.com.vg.controller.structure.iConstants.*;
import br.com.vg.util.iCallBack;

/**
 * Classe responsável pelos eventos que se movem mas não tem animação de envio
 * de pacotes correspondentes.
 * @author Jesimar Arantes
 */
public final class EventMove extends EventGeneric{

    //-----------------------------CONSTRUTOR-----------------------------------

    /**
     * Construtor da classe EventGeneric.
     * @param time - tempo atual.     
     */
    public EventMove(double time){
        super(time);
    }

    //--------------------------MÉTODOS PÚBLICOS--------------------------------

    //===============================OTHER======================================
    
    /**
     * Roda o evento de movimento do simulador.
     * @param data - dados da simulação.
     * @param repaint - callback para desenhar na tela.
     * @param speedAnimation - velocidade da animação.
     * @param statusSimulation - indica o status da animação (Ex. Play, Back).
     * @throws Exception - Exceção lançada.
     */
    @Override
    public void runMove(DataSimulation data, iCallBack repaint,
            float speedAnimation, byte statusSimulation) throws Exception{
        try {
            for (byte i = 0; i < SIZE_CYCLES_MOVE; ++i){
                cyclesEvent = i;
                if (statusSimulation == PLAY){
                    movimentation(data, (byte)0);//Usado quando a simulação esta em Play
                }else if (statusSimulation == BACK){
                    movimentation(data, (byte)1);//Usado quando a simulação esta em Back
                }
                repaint.repaint();
                Thread.sleep((int)(10 * speedAnimation));
            }

            if (statusSimulation == PLAY){
                calcPositionsLines(data, (byte)0);//Usado quando a simulação esta em Play
            }else if (statusSimulation == BACK){
                calcPositionsLines(data, (byte)1);//Usado quando a simulação esta em Back
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("\nError Event Move: \n" + ex);
        }
    }

    //-------------------------MÉTODOS PRIVADOS---------------------------------

    //===============================OTHER======================================

    /**
     * Faz a movimentação dos nós no simulador. E suaviza o seu movimento.
     * @param mode -indica se a simulação esta em PLAY ou BACK.
     */
    private void movimentation(DataSimulation data, byte mode){
        float x;
        float y;
        float fator = cyclesEvent/(SIZE_CYCLES_MOVE - 1.0f);
        for (Node no: data.getListNodeMove()){
            for (int j = mode; j < no.getVectorMove().length - 1 + mode; ++j){
                Move moveAnt = no.getVectorMove()[j - mode];
                Move moveProx = no.getVectorMove()[j - mode + 1];            
                if (time > moveAnt.getTime() && time <= moveProx.getTime()){
                    if (mode == 0){
                        x = fator * (moveProx.getCoordX() -
                                moveAnt.getCoordX()) + moveAnt.getCoordX();
                        y = fator * (moveProx.getCoordY() -
                                moveAnt.getCoordY()) + moveAnt.getCoordY();
                    }else{
                        x = fator * (moveAnt.getCoordX() - moveProx.getCoordX())
                            + moveProx.getCoordX();
                        y = fator * (moveAnt.getCoordY() - moveProx.getCoordY())
                            + moveProx.getCoordY();
                    }
                    no.setNewPosition(x, y);
                    break;
                }
            }
        }
    }   

    /**
     * Calcula a posição dos nós que se movem para fazer a linha tracejada.
     * @param data - dados da simulação.
     * @param mode - mode (Play ou Back).
     */
    private void calcPositionsLines(DataSimulation data, byte mode){
        int i = 0;
        for (Node no: data.getListNodeMove()){
            for (int j = mode; j < no.getVectorMove().length - 1 + mode; ++j){
                Move moveAnt = no.getVectorMove()[j - mode];
                Move moveProx = no.getVectorMove()[j - mode + 1];
                if (time >= moveAnt.getTime() && time <= moveProx.getTime()){
                    if (mode == 0){                        
                        data.addPositionTrain(i, new Position(moveAnt.getCoordX(),
                                moveAnt.getCoordY()));                        
                    }else if (mode == 1){
                        if (data.getPositionTrain()[i].size() > 0){
                            data.getPositionTrain()[i].removeLast();
                        }
                    }
                }
            }
            ++i;
        }
    }
}
