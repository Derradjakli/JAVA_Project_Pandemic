package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;

public class PlayerCard implements PlayerCardInterface {
	private City city;
	private int r;
	private int g;
	private int b;
	private static List<PlayerCardInterface> defauss;
	

	public PlayerCard(City c){
		this.city=c;
		this.r=c.getR();
		this.g=c.getG();
		this.b=c.getB();
	}
	
	@Override
	public String getCityName() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void addToDefauss(PlayerCardInterface playerCardInterface) {
		defauss.add(playerCardInterface);
	}
	public City getCity() {
		return city;
	}
/*
 A FINIR
	public Disease getDisease() {
		if(city.getR()==183 && city.getG()==18 && city.getB()==21){// TODO Auto-generated method stub
		city.setDisease(Disease.RED);
		return Disease.RED;
	}
*/

	@Override
	public Disease getDisease() {
		// TODO Auto-generated method stub
		return null;
	}
}
