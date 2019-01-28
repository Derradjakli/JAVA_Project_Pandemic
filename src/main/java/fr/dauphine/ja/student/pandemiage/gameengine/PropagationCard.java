package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;

public class PropagationCard {
	private City city;
	private int r;
	private int g;
	private int b;
	private Disease disease;

	public PropagationCard(City c){
		this.city=c;
		this.r=c.getR();
		this.g=c.getG();
		this.b=c.getB();
		
	}
 
	public void Propagation(){
		Disease d= city.getDisease(); 
		System.out.println("nb de cubes dans la ville AVANT propagation "+city.getNbCubes(d));
		if(city.getNbCubes(d)==3){
			city.setEclosion(true, d);
			GameEngine.Outbreaks(city,d);
		}
		else{
			if(GameEngine.getReserve().get(d)==0){
				GameEngine.setBool(false);
				return;
			}
			//if(GameEngine.AvalaibleBLocks(1,d)){
			GameEngine.GiveMeBlockFromReserve(d,1);
			city.setNbCubes(city.getNbCubes(d)+1,d);
		System.out.println("nb de cubes dans la ville APRES propagation "+city.getNbCubes(d));
			}
			
	}
	
	public Disease getDisease() {
		if(city.getR()==183 && city.getG()==18 && city.getB()==21){// TODO Auto-generated method stub
			city.setDisease(Disease.RED);
		}else if (city.getR()==107 && city.getG()==112 && city.getB()==184){
			city.setDisease(Disease.BLUE);
		}
		else if (city.getR()==153 && city.getG()==153 & city.getB()==153){
			city.setDisease(Disease.BLACK);	
		}
		else{
			city.setDisease(Disease.YELLOW);	
		}
		return city.getDisease();
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
