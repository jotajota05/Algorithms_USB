/**Se agrego una funcion hayGanador que establece si el tablero tiene
 * 3 casillas iguales en una fila, columna o diagonal. Esta funcion se 
 * utiliza en el programa principal Tic Tac Toe.
 * 
 * Se agrego una funcion igual que, dado 2 pares de enteros que 
 * representan la posicion de un elemento en la matriz tablero cada
 * uno, establece si las casillas que se encuentran en esas dos
 * posiciones son iguales.
 * 
 * No se realizo el procedimiento dibujar dado que se creo un tipo nuevo
 * Dibujar. 
 * 
 * Se agrego un procedimiento que permite rotar un tablero hacia la derecha
 * es decir, cambiar las posiciones de todos sus elementos de modo que parezca
 * que el tablero se ha volteado 90 grados hacia la derecha. Este procedimiento
 * se invoca luego en la funcion equivalenteA
 * 
 * Se agrego un procedimiento que permite voltear un tablero verticalmente. Es
 * decir, cambiar las posiciones de sus casillas para que se convierta en un 
 * tablero con la misma columna central pero las columnas de los lados
 * intercambiadas. Este procedimiento se utiliza en la funcion equivalenteA
 * 
 * Por ultimo se agrego una funcion identicoA que recibe como parametro de 
 * entrada un tablero y luego compara todas sus casillas con las casillas
 * del tablero con que se esta trabajando para establecer si son iguales.
 * Esta funcion se utiliza para la funcion equivalenteA.*/

public class Tablero{
		
	ValCasilla tab[][];
	
/**COnstructor del tipo tablero
 * PRE:true
 * POST: entrega un tablero con todas las casillas ValB*/
	public Tablero() {
		
		int i=0;
		int j=0;
		tab=new ValCasilla[3][3];
		while (i<3) {
			while (j<3) {
				tab[i][j]=new ValB();
				j++; }
			j=0;i++;}
	}

/**Coloca una ficha e un tablero
 * PRE:recibe una Valcasilla, na fila y una columna
 * POST:entrega un tablero con la jugada hecha en la posicion deseada*/
	public Tablero colocarFichaNuevoTablero(ValCasilla ficha,int fil,int col) {
		this.tab[fil][col]=ficha;
		return(this);
}
	
/**Coloca una ficha en el tablero*/
	public Tablero colocarFicha(Tablero t,ValCasilla ficha,int fil, int col){
		int i = 0;
		int j = 0;
		Tablero tNuevo = new Tablero();
		while (i<3){
			while (j<3){
				tNuevo.tab[i][j] = this.tab[i][j];
				j++;
			}
			j = 0;
			i++;
		}
		tNuevo = tNuevo.colocarFichaNuevoTablero(ficha,fil,col);
		return (tNuevo);
	}
	
/**Arroja ValCasilla que ocupa una posicion
 * PRE:recibe como parametro una posicion en el tablero
 * POST:retorna el valor de la ValAcsilla que ocupa esa posicion*/
	public ValCasilla obtenerPos (int fil,int col) {
		return(this.tab[fil][col]);
	}
	
/**Permite saber si una jugada es valida
 * PRE: recobe una posicion y un tablero
 * POST:entrega true si se puede jugar en esa casilla,false si la casilla esta ocupada*/
	public boolean jugadaValida (int fil,int col,Tablero t) {
		ValCasilla blanco=new ValB();
		return(t.obtenerPos(fil,col).obtener()==blanco.obtener());
	}
	
/**Para saber quien fue el ganador de una partida
 * PRE: recibe un tablero
 * POST: entrega, si lo hugo, la ValCasiila del ganador de la partida*/
	public ValCasilla ganador() {
		boolean gano=false;
		ValCasilla ganador=new ValB();
		int i=0;
		while (i<3&&!gano) {
			gano=igual(i,0,i,1)&&igual(i,1,i,2);
			if (gano) ganador=tab[i][0];i++;
		}
		i=0;
		while (i<3&&!gano) {
			gano=igual(0,i,1,i)&&igual(1,i,2,i);
			if (gano)ganador=tab[0][i];i++;
		}
		if (!gano) {
			gano=(igual(0,0,1,1)&&igual(1,1,2,2))||(igual(0,2,1,1)&&igual(1,1,2,0));
			if (gano) ganador=tab[1][1];
		}
		return(ganador);
	}
	
/**Para saber si hay ganador en la partida
 * PRE:recibe un tablero
 * POST:entrega true si hay ganador y false si no hay ganador en la partida*/
	public boolean hayGanador(){
		boolean gano=false;
		int i=0;
		while (i<3&&!gano) {
			gano=igual(i,0,i,1)&&igual(i,1,i,2);i++;
		}
		i=0;
		while (i<3&&!gano) {
			gano=igual(0,i,1,i)&&igual(1,i,2,i);i++;
		}
		if (!gano){
			gano=igual(0,0,1,1)&&igual(1,1,2,2)||igual(0,2,1,1)&&igual(1,1,2,0);
		}
		return(gano);
	}
	
/**Para saber si dos casillas son iguales
 * PRE: recibe dos posiciones en el tablero
 * POST:entrega true si las posiciones son iguales y false si son diferentes, y ademas si son 
 * no vacias*/
	public boolean igual (int fil1,int col1,int fil2,int col2) {
		boolean bool1=(tab[fil1][col1].obtener()==tab[fil2][col2].obtener())&&
					   tab[fil1][col1].obtener()!=" ";
		return (bool1);
	}

/**Funcion para contar cuantas jugadas se han hecho
 * PRE:recibe un tablero
 * POST: entrega un entero con el numero de casillas  ocupadas al hacer una jugada*/
	public int numJugadas () {
		int count = 0;
		int i = 0;
		int j = 0;
		while (i<3){
			while(j<3){
				if (!tab[i][j].esVacia()) count++;
				j++;
			}
		i++;
		}
		return (count);
	}
	
	public void rotacionDerecha () {
		ValCasilla auxEsq = tab[0][0];
		tab[0][0] = tab[2][0];
		tab[2][0] = tab[2][2];
		tab[2][2] = tab[2][0];
		tab[2][0] = auxEsq;
		ValCasilla auxMed = tab[0][1];
		tab[0][1] = tab[1][0];
		tab[1][0] = tab[2][1];
		tab[2][1] = tab[1][2];
		tab[1][2] = auxMed;
	}

	public void reflexionEjeVertical () {
		ValCasilla aux1 = tab[0][0];
		ValCasilla aux2 = tab[1][0];
		ValCasilla aux3 = tab[2][0];
		tab[0][0] = tab[0][2];
		tab[1][0] = tab[1][2];
		tab[2][0] = tab[2][2];
		tab[0][2] = aux1;
		tab[1][2] = aux2;
		tab[2][2] = aux3;
	}
		
/**Funcion para saber si dos tableros son iguales
 * PRE: recibe dos tableros
 * POST:entrega true si son iguales y false si son diferentes*/
	public boolean identicoA (Tablero x) {
		int i = 0;
		int j = 0;
		boolean igual = true;
		while(i<3&&igual) {
			while(j<3&&igual) {
				if (x.tab[i][j]!=this.tab[i][j]) igual = false;
				j++;
			}
		j=0;i++;
		}
		return(igual);
    }
	
	public boolean equivalenteA (Tablero x) {
		int i = 0;
		int j = 0;
		boolean equi = this.identicoA(x);
		while (i<4 && equi==false) {
			x.rotacionDerecha();
			equi = this.identicoA(x);
			while (j<2 && equi==false) {
				x.reflexionEjeVertical();
				equi = this.identicoA(x);
				j++;
			}
			i++;
		}
		return (equi);
	}	
	
/**funcion para saber si un tablero esta lleno
 * PRE:recibe un tablero
 * POST:entrega true si el tablero esta lleno y false si tiene al menos una posicion libre*/
	public boolean estaLleno() {
		boolean lleno=true;
		int j=0;
		int i=0;
		while (i<3&&lleno) {
			while (j<3&&lleno) {
				lleno=!(this.tab[i][j].esVacia());
				j++;
			}
			j=0;i++;
		}
	return(lleno);
	}	
	
/**funcion para saber si un tablero es vacio
 * PRE:recibe un tablero
 * POST:entrega true si el tablero es vacio y false si tiene al menos una posicion ocupada*/
	public boolean esVacio() {
		boolean vacio=true;
		int i=0;
		int j=0;
		while (i<3&&vacio) {
			while (j<3&&vacio) {
				vacio=this.tab[i][j].esVacia();
				j++;
			}
			j=0;i++;
		}
	return(vacio);
	}
	
/**Para saber cuantas lineas posees para completar
 * PRE: recibe un tablero y una ValCasilla
 * POST: entrega las lineas que puede completar dicho jugador al que le correspone la ValCasila*/
	public int lineasParaCompletar(ValCasilla jug) {
		int count = 0;
		int i = 0;
		
		while (i<3) {
			if (tab[i][0]== jug||tab[i][0].esVacia()){
				if ((tab[i][1]==jug||tab[i][1].esVacia())&&(tab[i][2]==jug||tab[i][2].esVacia()))
					count++;
			}
			i++;
		}
		i = 0;
		while (i<3) {
			if (tab[0][i]==jug||tab[0][i].esVacia()) {
				if ((tab[1][i]==jug||tab[1][i].esVacia())&&(tab[2][i]==jug||tab[2][i].esVacia()))
					count++;
			}
			i++;
		}
	
		if (tab[1][1]==jug||tab[1][1].esVacia()) {
			if ((tab[0][0]==jug||tab[0][0].esVacia())&&(tab[2][2]==jug||tab[2][2].esVacia())) 
				count++;
			if ((tab[2][0]==jug||tab[2][0].esVacia())&&(tab[2][0]==jug||tab[2][0].esVacia())) 
				count++;
		}
		return(count);
	}

/**Funcion para saber el beneficio de hacer una jugada*/
	public int beneficio (ValCasilla jug) {
		
		ValCasilla oponente = jug.oponente();
		int beneficio = 0;
		if (!hayGanador())
			beneficio = this.lineasParaCompletar(jug)-this.lineasParaCompletar(oponente);
		else if (hayGanador()){
			if (ganador()==jug) beneficio = 9;
			else if (ganador()==oponente) beneficio = -9;
		}	
		return (beneficio);
	}	
}