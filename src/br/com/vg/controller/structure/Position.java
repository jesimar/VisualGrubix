/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vg.controller.structure;

/**
 * Classe posição que contém um par ordenado (x, y) da posição de qualquer
 * elemento.
 * @author Jesimar Arantes
 */
public class Position implements iPositionable{

    /**
     * Coordenada X.
     */
    private float coordX;

    /**
     * Coordenada Y.
     */
    private float coordY;

    /**
     * Construtor da classe.
     * @param coordX - coordenada X.
     * @param coordY - coordenada Y.
     */
    public Position(float coordX, float coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }

    /**
     * Captura a coordenada X.
     * @return coordenada X.
     */
    public float getCoordX() {
        return coordX;
    }

    /**
     * Seta a coordenada X.
     * @param coordX - coordenada X.
     */
    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    /**
     * Captura a coordenada Y.
     * @return coordenada Y.
     */
    public float getCoordY() {
        return coordY;
    }

    /**
     * Seta a coordenada Y.
     * @param coordY - coordenada Y.
     */
    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

}
