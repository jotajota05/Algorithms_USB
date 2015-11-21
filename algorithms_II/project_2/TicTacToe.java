/**Tic Tac Toe es el programa aplicacion que permite correr el programa,
 * desde aqui se crea y se desarrolla un juego en todas sus extensiones,
 * esta aplicacion permite comenzar una nuva partida, salvar juegos, cargar 
 * juegos guardados, entre otras cosas.
 * @author Tamara Mendt 0538546 y Juan Garcia 0538207
 * @version 18 de julio de 2007*/

import java.io.*;

public class TicTacToe {

	public static void main(String[] args) {
	
		System.out.println("* * * Bienvenido al TicTacToe * * *");System.out.println("");

//Declaracion de variables
		
		Jugador jugador=new Jugador(Console.readString("Cual es tu nombre?: "),new ValX("X"));
		Lista<Jugada> listaDeJug=new Lvac<Jugada>();
		File arch=new File("Status Juego.txt");
		Tablero tablero=new Tablero();
		Dibujar pantalla = new Dibujar(60,60,300,300,new Jugada(tablero,new ValB(),0,0));
		Juego juego=new Juego(0,"",listaDeJug,arch);
		String salida="";
		String jugo=" ";
		boolean juegoCargado=false;
		boolean interrumpido=false;
		int opcion=1;
		int opcion2=1;
		int i=0;
		int k=0;
		int m=0;
		
//Inicio del ciclo para realizar varias partidas de TicTacToe*
		
		while (m<10000) { 

			/**Inicializaciones de juego y Menu del juego*/	
		
			if (!juegoCargado||tablero.hayGanador()||interrumpido||tablero.estaLleno()) {
				interrumpido=false;
				juegoCargado=false;
				tablero=new Tablero();
				juego.nivel=5;
				k=0;
				
				opcion=juego.mostrarMenuPrincipal();
			}
		
//Se inicia la seleccion de la opcion y su correspondiente accion
		
			switch (opcion) {
			
			case 1 : //jugar una partida o torneo
				
				if (!juegoCargado) juego=juego.nuevoJuego();
				else k=juego.jug.length();
		
				while (juego.nivel!=0&&juego.nivel!=1&&juego.nivel!=2&&juego.nivel!=3&&juego.nivel!=4&&!juegoCargado) { 
					juego.nivel=Console.readInt("Que nivel desea jugar?");
					if (juego.nivel!=0&&juego.nivel!=1&&juego.nivel!=2&&juego.nivel!=3&&juego.nivel!=4) 
						System.out.println("Seleccion invalida (entre 0 y 4)");
				}
			
						while (!tablero.estaLleno()&&!tablero.hayGanador()&&!interrumpido) {
						
							if (!tablero.esVacio()&&!tablero.estaLleno()) 
								opcion2=juego.mostrarMenuInternoDePartida();
						
							switch (opcion2) {
		
							case 1 : //seguir con la partida
								
								if (juego.nivel==0&&!tablero.hayGanador()) {
									if (tablero.esVacio()&&juego.inic.equals("X")) jugo="O";
									if (tablero.esVacio()&&juego.inic.equals("O")) jugo="X";
									if (jugo.equals("X")) {
										k++;juego.juegaAleatorioPC(tablero,k,pantalla);jugo="O";
									}
									else  {
										k++;juego.juegaJugador(tablero,k,pantalla);jugo="X";
									}
								}
								if (juego.nivel!=0&&!tablero.hayGanador()) {
									if (tablero.esVacio()&&juego.inic.equals("X")) jugo="O";
									if (tablero.esVacio()&&juego.inic.equals("O")) jugo="X";
									if (jugo.equals("X")) {
										k++;juego.juegaPC(tablero,k,pantalla);jugo="O";
									}
									else  {
										k++;juego.juegaJugador(tablero,k,pantalla);jugo="X";
									}
								}
								if (tablero.hayGanador()) {
									juego.decision(tablero,jugador);
									m++;i++;
								}
								else if (tablero.estaLleno()) {
									System.out.println("");
									System.out.println("Ha habido un empate");
								}
							
						
							break;
						
							case 2 ://salvar el juego
								juego.salvarJuego(juego.f,juego.jug,juego.inic,juego.nivel);
								opcion=1;
								opcion2=1;
								System.out.println("");
								System.out.println("El juego se guardo satisfactoriamente");
							break;
							
							case 3://interrumpir un juego
								interrumpido=true;
								juego.jug=new Lvac<Jugada>();
								opcion=1;
								opcion2=1;
							break;
							
							case 4://deshacer jugada
								Jugada jugEli=juego.jug.obtener(k);
								tablero.tab[jugEli.f][jugEli.c]=new ValB();
								if (juego.jug.obtener(k).jugador.obtener()=="X") jugo="O";
								else jugo="X";
								juego.jug=juego.jug.elimina(k);
								juego.mostrarTablero(pantalla);
								k--;
							break;
						
					
							default : System.out.println("Opcion no valida vuelva a ingresarla");
						}
					}
	
			break;
			
			case 2 ://cargar un juego
				tablero=new Tablero();
				juego.cargarJuego(tablero,pantalla);
				salida=juego.leerJuego();
				juego.nivel=Integer.parseInt(salida.substring(1,2));
				juego.inic=salida.substring(0,1);
				juegoCargado=true;
				opcion=1;
				opcion2=1;
			break;
			
			case 3 : //salir del juego
				m=10000;System.out.println("");System.out.println("Hasta luego!!");
			break;
			
			default : System.out.println("Opcion no valida vuelva a ingresarla");
			}
			
			if (tablero.hayGanador()) juego.jug=new Lvac<Jugada>();
			m++;
		}
	}
}