
package br.com.vg.util;

/**
 * Interface responsável por obter a resposta de um evento, e executar tal
 * evento de retorno.
 * @author Márcio Arantes e Jesimar Arantes
 */
public interface iCallBack {

    public void repaint();

    public void update(int index, double speed, double time);

}
