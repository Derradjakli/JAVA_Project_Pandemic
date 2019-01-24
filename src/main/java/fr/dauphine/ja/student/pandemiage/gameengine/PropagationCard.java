package fr.dauphine.ja.student.pandemiage.gameengine;

public class PropagationCard {
	private City city;
	private int r;
	private int g;
	private int b;

	public PropagationCard(City c){
		this.city=c;
		this.r=c.getR();
		this.g=c.getG();
		this.b=c.getB();
	}
/*
	public void Propagation(){
		if(tour==1){
			city.setNbCubes(city.getNbCubes()+3);
		}
		else if(tour ==2){
			if(city.getNbCubes()<=1){
				city.setNbCubes(city.getNbCubes()+2);
			}
			else{
				city.setEclosion(true);
				city.
			}

			else{

			}
		}
*/

		public City getCity() {
			return city;
		}
		public void setCity(City city) {
			this.city = city;
		}
		public int getR() {
			return r;
		}
		public void setR(int r) {
			this.r = r;
		}
		public int getG() {
			return g;
		}
		public void setG(int g) {
			this.g = g;
		}
		public int getB() {
			return b;
		}
		public void setB(int b) {
			this.b = b;
		}




	}
