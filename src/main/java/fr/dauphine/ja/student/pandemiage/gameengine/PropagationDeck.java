package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class PropagationDeck {
	private static List<PropagationCard> propagationdeck;

	public PropagationDeck(){
		propagationdeck=new LinkedList<PropagationCard>();
	}
 
	public static void lastDeckCard(){
		PropagationCard pc= propagationdeck.remove(propagationdeck.size());
		Disease d=pc.getCity().getDisease();
		if(pc.getCity().getNbCubes(d)==0){
			GameEngine.AvalaibleBLocks(3);
			GameEngine.GiveMeBlockFromReserve(d,3);
			pc.getCity().setNbCubes(pc.getCity().getNbCubes(d)+3,d);
		}
		else{
			pc.getCity().setNbCubes(3, d);
			pc.getCity().setEclosion(true, d);
			GameEngine.Outbreaks(pc.getCity(), d);
		}
	}

	public static List<PropagationCard> getPropagationdeck() {
		return propagationdeck;
	}

	public static void setPropagationdeck(List<PropagationCard> propagationdeck) {
		PropagationDeck.propagationdeck = propagationdeck;
	}
	


}
