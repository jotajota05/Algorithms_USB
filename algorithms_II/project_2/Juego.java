import java.io.*;
import java.util.Random;

public class Juego {
	
	int nivel;
	String inic;
	Lista<Jugada> jug;
	File f;
	
/**Este es el constructor del tipo abstracto juego*/	

	public Juego(int nivel,String inic,Lista<Jugada> jug,File f){
		this.nivel=nivel;
		this.inic=inic;
		this.jug=jug;
		this.f=f;
	}
	
/**Se crea un nuevo juego, preguntando quien juega primero
 * PRE: true
 * POST: retorna un nuevo juego con el jugador que inicia la partida y sus variables de representacion
 * inicializadas*/
	
	public Juego nuevoJuego() {
		int level=this.nivel;
		String primero=this.inic;
		Lista<Jugada> listaDeJug=this.jug;
		File f=this.f;
		int primer=2;
		
		while (primer!=0&&primer!=1) {
			primer=Console.readInt("Quien Juega primero? (0=PC)(1=Usted)");
			if (primer!=0&&primer!=1) System.out.println("Seleccion invalida");
		}
		if (primer==0) primero="O";
		else if (primer==1) primero="X";
		Juego game=new Juego(level,primero,listaDeJug,f);
		return(game);
	}
	
/**Procedimiento para escribir en archivo el estado del juego
 * PRE: recibe un archivo,una lista de jugadas,un string que indica quien inicio la partida y el 
 * nivel que se esta jugando
 * POST: realiza sobre el archivo de entrada la escritura del estado del juego en el momento de 
 * en que se ha salvado*/
	
	public void salvarJuego(File f,Lista<Jugada> l,String inic,int nivel) {
		try {
		    FileWriter fw = new FileWriter(f);
		    BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter salida = new PrintWriter(bw);
		    salida.println(inic);
		    salida.println(nivel);
		    while (l instanceof Cons) {
		    	salida.print(l.obtObjeto().f);
		    	salida.println(l.obtObjeto().c);
		    	l=l.obtLista();
		    }
		    salida.close();
		}
		catch(IOException e) {}
	}
	
/**Procedimineto para leer de archivo el juego gauardado 
 * PRE: true
 * POST: entrega un String con la lectura de las lineas del archivo concatenadas en el mismo*/
	
	public String leerJuego() {
		String texto="";  
		String salida="";
		BufferedReader entrada=null;
		int i=0;
		try {            
			FileReader fr = new FileReader(this.f);
			entrada = new BufferedReader(fr);
		}
		catch (IOException e){}
			while (texto!=null) {
				try {
					texto=entrada.readLine();
				}
				catch (IOException e){}
				if (texto!=null) {
					salida=salida+texto;
				}
			i++;
			}
		return(salida);
	}
	
/**Procedimiento para cargar un juego guardado
 * PRE: recibe un tablero vacio y un tipo dibujar
 * POST: devuelve un tablero lleno con el estado cargado y plasmado en pantalla. y ademas 
 * asigna quien fue el primero que jugo*/	

	public void cargarJuego(Tablero t,Dibujar d) {
		String salida="";
		int i=2;
		int k=0;
		salida=leerJuego();
		while (i<=(salida.length()-2)) {
			if (salida.substring(0,1).equals("X")) { 
				k++;juegaEspecificoJugador(t,k,d,Integer.parseInt(salida.substring(i,i+1)),
									  Integer.parseInt(salida.substring(i+1,i+2)));
				i=i+2;
				if (i<=(salida.length()-2)) {
					k++;juegaEspecificoPC(t,k,d,Integer.parseInt(salida.substring(i,i+1)),
										  Integer.parseInt(salida.substring(i+1,i+2)));
				}
				i=i+2;
			}
			else {
				k++;juegaEspecificoPC(t,k,d,Integer.parseInt(salida.substring(i,i+1)),
		  				   				   Integer.parseInt(salida.substring(i+1,i+2)));
				i=i+2;
				if (i<=(salida.length()-2)) {
					k++;juegaEspecificoJugador(t,k,d,Integer.parseInt(salida.substring(i,i+1)),
											   Integer.parseInt(salida.substring(i+1,i+2)));
				}
				i=i+2;
			}
		}
		if ((salida.length()-2 % 2)==0) this.inic=salida.substring(0,1);
		else if (salida.substring(0,1).equals("X")) this.inic="O";
			 else this.inic="X";
	}
	
/**Procedimiento donde la computadora juega aleatoriamente (nivel 0)
 * PRE:recibe un tablero,un entero que representa el rango de las jugadas y un tipo dibujar
 * POST: realiza la jugada de la pc de manera aleatoria para el nivel 0*/
	
	public void juegaAleatorioPC(Tablero t,int k,Dibujar d) {
		System.out.println("");System.out.println("LA PC HA JUGADO");
		int fil=0;
		int col=0;
		boolean jugo=false;
		ValCasilla O=new ValO("O");
		while (!jugo&&!(t.estaLleno())) {
			Random r=new Random();
			fil=r.nextInt(3);
			Random p=new Random();
			col=p.nextInt(3);
			if (t.jugadaValida(fil,col,t)) {
				t=t.colocarFichaNuevoTablero(O,fil,col);
				jugo=true;
			}
		}
		d.jug=new Jugada(t,O,fil,col);
		mostrarTablero(d);
		this.jug=this.jug.insertaEnOrden(k,new Jugada(t,O,fil,col));
	}
	
/**Procedimiento donde la computadora juega especificamente donde queremos (nivel 0)
 * PRE:recibe un tablero,un entero que representa el rango de la jugada, un tipo dibujar y
 * dos enteros que representan la fila y la columna donde se va a jugar
 * POST:realiza una jugada que nosotros queremos especificamente,es usada al momento de cargar
 * un juego*/	
	
	public void juegaEspecificoPC(Tablero t,int k,Dibujar d,int fil,int col) {
		boolean jugo=false;
		ValCasilla O=new ValO("O");
		while (!jugo&&!(t.estaLleno())) {
			if (t.jugadaValida(fil,col,t)) {
				t=t.colocarFichaNuevoTablero(O,fil,col);
				jugo=true;
			}
		}
		d.jug=new Jugada(t,O,fil,col);
		mostrarTablero(d);
		this.jug=this.jug.insertaEnOrden(k,new Jugada(t,O,fil,col));
	}
	
/**Procedimiento donde la PC juega inteligentemente
 * PRE:recibe un tablero,un entero que representa el rango de la jugada y un tipo dibujar
 * POST: realiza una jugada inteligente por parte del PC,usando el arbol minmax*/
	
	public void juegaPC(Tablero t,int k,Dibujar d) {
		System.out.println("");System.out.println("LA PC HA JUGADO");
		int[] posicion = new int[2];
		ValCasilla O = new ValO("O");
		if (this.inic.equals("O")){
			ArbolDeJuego Inteligencia = new ArbolMax(t,2,O);
			posicion = Inteligencia.jugadaOpt();
		}
		else if (this.inic.equals("X")){
			ArbolDeJuego Inteligencia = new ArbolMin(t,2,O);
			posicion = Inteligencia.jugadaOpt();
		}
		t=t.colocarFichaNuevoTablero(O,posicion[0],posicion[1]);
		d.jug=new Jugada(t,O,posicion[0],posicion[1]);
		mostrarTablero(d);
		this.jug=this.jug.insertaEnOrden(k,new Jugada(t,O,posicion[0],posicion[1]));
	}
	
/**Procedimiento donde el jugador juega especificamente donde queremos (nivel 0)
 * PRE:recibe un tablero,un entero que representa el rango de la jugada, un tipo dibujar y
 * dos enteros que representan la fila y la columna donde se va a jugar
 * POST:realiza una jugada que nosotros queremos especificamente,es usada al momento de cargar
 * un juego*/		
	
	public void juegaEspecificoJugador(Tablero t,int k,Dibujar d,int fil,int col) {
		boolean jugo=false;
		ValCasilla X=new ValX("X");
		while (!jugo&&!(t.estaLleno())) {
			if (t.jugadaValida(fil,col,t)) {
				t=t.colocarFichaNuevoTablero(X,fil,col);
				jugo=true;
			}
		}
		d.jug=new Jugada(t,X,fil,col);
		mostrarTablero(d);
		this.jug=this.jug.insertaEnOrden(k,new Jugada(t,X,fil,col));
	}
	
/**Procedimiento donde el jugador juega
 * PRE:recibe un tablero,un entero que representa el rango de la jugada y un tipo dibujar
 * POST:realiza la jugada que el jugador desea a travez de consola*/
	
	public void juegaJugador(Tablero t,int k,Dibujar d) {
		System.out.println("");System.out.println("Tu Turno");
		int fil=3;
		int col=3;
		boolean jugo=false;
		ValCasilla X=new ValX("X");

		while (!jugo&&!(t.estaLleno())) {
			fil=3;
			while (fil!=0&&fil!=1&&fil!=2){
				fil=Console.readInt("Inserte la fila en que desea jugar");
				if (fil!=0&&fil!=1&&fil!=2) System.out.println("Fila invalida");
			}
			col=3;
			while (col!=0&&col!=1&&col!=2) {
				col=Console.readInt("Inserte la columna en que desea jugar");	
				if (col!=0&&col!=1&&col!=2) System.out.println("Columna invalida");
			}
			if (t.jugadaValida(fil,col,t)) {
				t=t.colocarFichaNuevoTablero(X,fil,col);
				jugo=true;
			}
			else System.out.println("No es jugada Valida,vuelva a hacerla");
		}
		d.jug=new Jugada(t,X,fil,col);
		mostrarTablero(d);
		this.jug=this.jug.insertaEnOrden(k,new Jugada(t,X,fil,col));
	}
	
	
/**Procedimiento que realiza la decision de las jugadas
 * PRE:recibe un tablero y un jugador
 * POST:realiza la decision de quien ha ganado el juego,lanzando un mensaje a traves de consola*/
	
	public void decision(Tablero tablero,Jugador jugador) {
		if (tablero.ganador().obtener()=="X") {
			System.out.println("");
			System.out.println("Felicidades "+jugador.nombre+", has Ganado!!");
		}
		else {
			System.out.println("");
			System.out.println("Lo siento "+jugador.nombre+", has Perdido!!");
		}	
	}
	
/**Procedimiento que muestra el menu principal
 * PRE: true
 * POST: muestra por consola el menu principal y pide la opcion deseada*/
	public int mostrarMenuPrincipal() {
		int opcion=0;
		System.out.println("");
		System.out.println("Que desea hacer?");System.out.println("");
		System.out.println("1)Empezar una partida");
		System.out.println("2)Cargar una partida");
		System.out.println("3)Salir del juego");
		opcion=Console.readInt("Opcion: ");
		return(opcion);
	}
	
/**Procedimineto para mostrar el menu interno de juego
 * PRE: true
 * POST: muestra por consola el menu interno del juego y pide la opcion deseada*/
	
	public int mostrarMenuInternoDePartida() {
		int opcion2=0;
		System.out.println("");
		System.out.println("Y Ahora que desea hacer?");
		System.out.println("1)Seguir el juego");
		System.out.println("2)Salvar el juego");
		System.out.println("3)Interrumpir el juego");
		System.out.println("4)Deshacer jugada");
		opcion2=Console.readInt("Opcion: ");
		return(opcion2);
	}
	
	
/**Procedimineto para mostrar el tablero en pantalla
 * PRE: recibe un tipo dibujar
 * POST: muestra en pantalla la ventana con el tablero de juego y las jugadas*/
	
	public void mostrarTablero(Dibujar d) {
		d.setTitle("Juego");
	    d.setVisible(true);
	}
	
}