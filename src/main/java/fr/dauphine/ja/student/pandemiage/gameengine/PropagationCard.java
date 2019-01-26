package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;

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

	public void Propagation(){

		Disease d= city.getDisease();
		if(city.getNbCubes(d)==3){
			city.setEclosion(true, d);
			GameEngine.Outbreaks(city,d);
		}
		else{
			
			if(GameEngine.getReserve().get(d)==0){
				GameEngine.setBool(false);
				return;
			}
			
			GameEngine.AvalaibleBLocks(1);
			GameEngine.GiveMeBlockFromReserve(d);
			city.setNbCubes(city.getNbCubes(d)+1,d);
		}
	}



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
