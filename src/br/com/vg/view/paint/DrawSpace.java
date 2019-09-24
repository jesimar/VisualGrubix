
package br.com.vg.view.paint;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.JPanel;

/**
 * Classe responsável por dar zoom na aba monitor onde é exibida a área de
 * monitoramento da rede de sensor sem fio.
 * @author Marcio Arantes
 */
public abstract class DrawSpace extends JPanel{

    //-----------------------------ATRIBUTOS------------------------------------

    protected final double taxaZoom = 1.1;
    
    protected double zoom = 1.2;
    protected double Cx = 0;
    protected double Cy = 0;
    
    protected double dx = 0;
    protected double dy = 0;

    protected int width;
    protected int height;

    private double Xt;
    private double Yt;
    
    //-----------------------------CONSTRUTOR-----------------------------------

    public DrawSpace() {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                MousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                MouseReleased(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MouseMoved(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                MouseDragged(e);
            }

        });
        this.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                MouseWheelMoved(e);
            }
        });
        init();
    }   

    //------------------------------MÉTODOS PRIVADOS----------------------------

    //================================OTHER=====================================
    
    private void MouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            this.MouseClicked(e, getXReal(e.getX()), getYReal(e.getY()));
        }else{
            this.Cx = getXReal(e.getX());
            this.Cy = getYReal(e.getY());
            Xt = width/2-Cx*zoom;
            Yt = height/2-Cy*zoom;
        }
        repaint();
    }

    private void MouseMoved(MouseEvent e) {
        this.MouseMoved(e, getXReal(e.getX()), getYReal(e.getY()));
        repaint();
    }

    private void MouseWheelMoved(MouseWheelEvent e) {
        zoom *= (e.getWheelRotation() < 0 ? taxaZoom : 1.0 / taxaZoom);
        Xt = width/2-Cx*zoom;
        Yt = height/2-Cy*zoom;
        this.MouseWheelMoved(e, getXReal(e.getX()), getYReal(e.getY()));
        repaint();
    }

    private void MousePressed(MouseEvent e) {
        this.dx = getXReal(e.getX());
        this.dy = getYReal(e.getY());
        MousePressed(e, getXReal(e.getX()),getYReal(e.getY()));
        repaint();
    }

    private void MouseReleased(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
        MouseReleased(e, getXReal(e.getX()),getYReal(e.getY()));
        repaint();
    }

    private void MouseDragged(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        this.Cx += dx-getXReal(e.getX());
        this.Cy += dy-getYReal(e.getY());
        this.dx = getXReal(e.getX());
        this.dy = getYReal(e.getY());

        Xt = width/2-Cx*zoom;
        Yt = height/2-Cy*zoom;
                
        repaint();
    }  

    //-----------------------------MÉTODOS PÚBLICOS-----------------------------

    //================================OTHER=====================================

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        updateSpaceDraw(preferredSize.width, preferredSize.height);
    }

    /**
     * Redimensiona o espaço de desenho da aba monitor.
     * @param w - largura da aba monitor
     * @param h - altura da aba monitor
     */
    public void updateSpaceDraw(int w, int h){
        width = w;
        height = h;
        Xt = width/2-Cx*zoom;
        Yt = height/2-Cy*zoom;
        //this.setBounds(0, 0, width, height);
    }


    /**
     * Retorna a configuração do sistema de zoom para os valores default.
     */
    public void resetSystem(){
        this.Cx = 0;
        this.Cy = 0;
        dx = 0;
        dy = 0;
        zoom = 1.2;
        Xt = width / 2 - Cx * zoom;
        Yt = height / 2 - Cy * zoom;
        init();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;        

        g2.translate(Xt, Yt);
        g2.scale(zoom, zoom);

        paintComponentDepois(g2);

        g2.scale(1.0/zoom, 1.0/zoom);
        g2.translate(-Xt, -Yt);

        paintComponentAntes(g2);
    }

    //=================================GET======================================

    /**
     * Captura o valor da coordenada x.
     * @param X - coordenada x.
     * @return retorna um ponto X com as cordenadas reais, de onde se clicou
     * apos ter calculado o zoon
     */
    public double getXReal(double X){
        return this.Cx + (X - width/2 ) / zoom;
    }

    /**
     * Captura o valor da coordenada y.
     * @param Y - coordenada y.
     * @return retorna um ponto Y com as cordenadas reais, de onde se clicou
     * apos ter calculado o zoon
     */
    public double getYReal(double Y){
        return this.Cy + (Y - height/2) / zoom;
    }

    //=================================SET======================================

    /**
     * Seta o valor das coordenadas do centro x e y.
     * @param Cx - Centro da coordenada x
     * @param Cy - Centro da coordenada y
     */
    public void setCentro(double Cx, double Cy){
        this.Cx = Cx;
        this.Cy = Cy;
        Xt = width/2-Cx*zoom;
        Yt = height/2-Cy*zoom;
        repaint();
    }

    //-----------------------------MÉTODOS PROTECTED----------------------------

    //================================OTHER=====================================

    protected void init(){

    }
    
    /**
     * @param e MouseEvent
     * @param X Posição X real, de onde se clicou, considerando o zoon
     * @param Y Posição Y real, de onde se clicou, considerando o zoon
     */
    protected void MouseClicked(MouseEvent e, double X, double Y){

    }

    /**
     * @param e MouseEvent
     * @param X Posição X real, de onde se clicou, considerando o zoon
     * @param Y Posição Y real, de onde se clicou, considerando o zoon
     */
    protected void MouseMoved(MouseEvent e, double X, double Y){
        
    }

    /**
     * @param e MouseEvent
     * @param X Posição X real, de onde se clicou, considerando o zoon
     * @param Y Posição Y real, de onde se clicou, considerando o zoon
     */
    protected void MouseWheelMoved(MouseWheelEvent e, double X, double Y){

    }

    protected void MousePressed(MouseEvent e, double X, double Y){        

    }

    protected void MouseReleased(MouseEvent e, double X, double Y){
        
    }
    
    public void clickedZoomMais(){
        zoom *= taxaZoom;
        Xt = width/2-Cx*zoom;
        Yt = height/2-Cy*zoom;        
        repaint();
    }
    
    public void clickedZoomMenos(){
        zoom /= taxaZoom;
        Xt = width/2-Cx*zoom;
        Yt = height/2-Cy*zoom;     
        repaint();
    }
    
    public void ajustarWindow(){
        resetSystem();
        repaint();
    }

//    public void moverEsquerda(){
//        this.Cx += dx-10;
//        this.dx = 10;
//        Xt = width/2-Cx*zoom;
//        repaint();
//    }
    
    /**Pinta no Graphics antes da mudança de cordenadas devido a translação e o 
     * zoon que será aplicado.
     * @param g2
     */
    protected void paintComponentAntes(Graphics2D g2){

    }

    /**Pinta no Graphics depois da mudança de cordenadas devido a translação e o
     * zoon que será aplicado.(apos)<Br>
     * g2.translate(width/2-Cx*zoon, height/2-Cy*zoon);<br>
     * g2.scale(zoon, zoon);<br>
     * @param g2
     */
    protected void paintComponentDepois(Graphics2D g2){

    }
}
