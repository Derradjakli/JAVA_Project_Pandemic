package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;

public class PlayerCard  implements PlayerCardInterface{

	private static List<PlayerCardInterface> defauss;
	
	
	
	public static void addToDefauss(PlayerCardInterface playerCardInterface) {
		defauss.add(playerCardInterface);
	}
	
	
	public static void addToDefauss(PlayerCard playerCard) {
		defauss.add(playerCard);
	}


	@Override
	public String getCityName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Disease getDisease() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public City getCity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	/*
	private City city;
	private int r;
	private int g;
	private int b;

	public PlayerCard(City c){
		this.city=c;
		this.r=c.getR();
		this.g=c.getG();
		this.b=c.getB();
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
*/
	
}