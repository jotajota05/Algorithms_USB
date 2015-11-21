/**El tipo abstracto Dibujar tiene como modelo de reprsentacion 4 enteros que
 * representan, el ancho y el largo de la pantalla donde van a aparecer
 * las imagenes y las coordenadas del punto que se utiliza como referencia para 
 * dibujar las imagenes; y por ultimo una variable del tipo Jugada que indica 
 * que es lo que se va a dibujar en la pantalla que se quiere construir para 
 * representar el juego
 * 
 * El unico procedimiento que tiene es paint que se encarga de dibujar todas
 * las imagenes que deben aparecer en el marco. En el procedmiento se invocan
 * procedmiento pre establecidos para poder crear figuras variadas. Se comienza
 * dibujando el tablero y luego se invoca un ciclo que revisa cada casilla del
 * tablero introducido. Cuando consigue una casilla no vacia, dibuja la figura
 * correspondiente a esa casilla.*/

import java.awt.*;

public class Dibujar extends Frame {

		int HOR_Tamano;
		int VER_Tamano;
		int p1;
		int p2;
		Jugada jug;

/**Constructor del tipo*/
	public Dibujar(int p1,int p2, int HOR_Tamano,int VER_Tamano,Jugada jug) {

		this.HOR_Tamano = HOR_Tamano;
		this.VER_Tamano =  VER_Tamano;
		this.p1 = p1;
		this.p2 = p2;
		this.jug = jug;
	}

/**Procedimineto para dibujar la pantalla dl juego
 * PRE:recibe un objeto del tipo Graphics
 * POST:entrega en pantalla el dibujo del tablero y las respectivas fichas que se van jugando*/
public void paint (java.awt.Graphics g) {

	setSize(HOR_Tamano,VER_Tamano);
	g.setColor(java.awt.Color.black);
	g.fillRect(p1,p2+50,158,4);
	g.fillRect(p1,p2+100,158,4);
	g.fillRect(p1+50,p2,4,160);
	g.fillRect(p1+100,p2,4,160);
	g.drawString("TIC-TAC-TOE",p1+40,p2+190);
	
	int i = 0;
	int j = 0;
	ValCasilla x = new ValX("X");
	ValCasilla o = new ValO("O");
	
	while (i<3) {
		while (j<3) {
			int aux1 = j*56 + p1;
			int aux2 = i*56 + p2;
			if (jug.t.tab[i][j].obtener() ==x.obtener()) {
				int[] X = {aux1,aux1+8,aux1+19,aux1+32,aux1+40,aux1+27,aux1+37,aux1+27,aux1+19,aux1+11,aux1+2,aux1+11,aux1};
				int[] Y = {aux2+40,aux2+40,aux2+23,aux2+40,aux2+40,aux2+17,aux2,aux2,aux2+13,aux2,aux2,aux2+17,aux2+40};
				g.setColor(java.awt.Color.red);
				g.fillPolygon(X, Y, 13);
				g.setColor(java.awt.Color.black);
				g.drawPolygon(X, Y, 13);
			}
			else if (jug.t.tab[i][j].obtener() ==o.obtener()){
				g.setColor(java.awt.Color.blue);
				g.fillOval(aux1,aux2,40,40);
				g.setColor(java.awt.Color.white);
				g.fillOval(aux1+7,aux2+7,25,25);
				g.setColor(java.awt.Color.black);
				g.drawOval(aux1,aux2,39,39);
				g.drawOval(aux1+7,aux2+7,25,25);
			}
			j++;
		}
		j=0;
		i++;
	}
}


}
