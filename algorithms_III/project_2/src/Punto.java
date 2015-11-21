public class Punto extends Vertice {

	public String id;
	public int numBusq;
	public double coorX,coorY;
	
	public Punto(String id,double coorX,double coorY) {
		this.id=id;
		this.numBusq=-1;
		this.coorX=coorX;
		this.coorY=coorY;
	}
	
	public String obtID() {
		return id;
	}
}
