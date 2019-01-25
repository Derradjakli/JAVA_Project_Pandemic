package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;

public class CitiesCard extends PlayerCard {

	private City city;
	private int r;
	private int g;
	private int b;
	private boolean epidemic;
	
	public CitiesCard (City c){

		this.city=c;
		this.r=c.getR();
		this.g=c.getG();
		this.b=c.getB();
		this.epidemic=false;
	}

	public String getCityName() {
		return this.city.getName();
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

	public City getCity(){
		return this.city;
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

	public void setCity(City city) {
		this.city = city;
	}

	public boolean isEpidemic() {
		return epidemic;
	}

	public void setEpidemic(boolean epidemic) {
		this.epidemic = epidemic;
	}




}
