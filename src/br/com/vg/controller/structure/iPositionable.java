/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vg.controller.structure;

/**
 *
 * @author jesimar
 */
public interface iPositionable {

    /**
     * Captura a coordenada X
     * @return Coordenada X.
     */
    public float getCoordX();

    /**
     * Seta a coordenada X
     * @param coordX - coordenada X.
     */
    public void setCoordX(float coordX);

    /**
     * Captura a coordenada Y
     * @return Coordenada Y.
     */
    public float getCoordY();

    /**
     * Seta a coordenada X
     * @param coordY - coordenada Y.
     */
    public void setCoordY(float coordY);

}
