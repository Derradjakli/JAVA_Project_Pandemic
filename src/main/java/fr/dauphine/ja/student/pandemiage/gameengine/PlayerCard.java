package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;

public class PlayerCard  implements PlayerCardInterface{

	private static List<PlayerCardInterface> defauss=new ArrayList<PlayerCardInterface>();
	
	
	
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
}
	
	
