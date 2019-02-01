package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;

public class CitiesCard extends PlayerCard {

	private City city;
	private int r;
	private int g;
	private int b;
	private Boolean epidemic;
	
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
		if(r==153 && g==18 && b==21){// TODO Auto-generated method stub
			return Disease.RED;
		}
		if (r==107 && g==112 && b==184){
			return Disease.BLUE;
		}
		if (r==153 && g==153 & b==153){
			return Disease.BLACK;		}
		if (r==242 && g==255 & b==0){
			return Disease.YELLOW;		}
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

	public Boolean isEpidemic() {
		return epidemic;
	}

	public void setEpidemic(Boolean epidemic) {
		this.epidemic = epidemic;
	}




}
