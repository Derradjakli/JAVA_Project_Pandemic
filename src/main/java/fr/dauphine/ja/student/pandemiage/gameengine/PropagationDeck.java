package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class PropagationDeck {
	private static LinkedList<PropagationCard> propagationdeck;

	public PropagationDeck(){
		propagationdeck=new LinkedList<PropagationCard>();
	}
 
	public static void lastDeckCard(PropagationDeck propdefauss){
		//PropagationCard pc= propagationdeck.remove(propagationdeck.size()-1);
		PropagationCard pc= propdefauss.getPropagationdeck().remove(propdefauss.getPropagationdeck().size()-1);
		Disease d=pc.getCity().getDisease();
		
		System.out.println(pc.getCity().getName());
		System.out.println(pc.getCity().getM().containsKey(d));

		//if(pc.getCity().getNbCubes(d)==0){
		if(pc.getCity().getM().get(d)==0){
	
			System.out.println("dans le if");

			if(GameEngine.AvalaibleBLocks(3,d)) {
				GameEngine.GiveMeBlockFromReserve(d,3);
				pc.getCity().setNbCubes(pc.getCity().getNbCubes(d)+3,d);
			}
			else {
				throw new UnsupportedOperationException("No more cubes left for the Disease "+d);
			}
		}
		else{
			System.out.println("dans le else");

			pc.getCity().setNbCubes(3, d);
			pc.getCity().setEclosion(true, d);
			GameEngine.Outbreaks(pc.getCity(), d);
		}
		propdefauss.getPropagationdeck().add(pc);
	}

	public static List<PropagationCard> getPropagationdeck() {
		return propagationdeck;
	}

	public static void setPropagationdeck(List<PropagationCard> propagationdeck) {
		PropagationDeck.propagationdeck = (LinkedList)propagationdeck;
	}
	
	public static PropagationCard getFirstPropagationcard() {
		return PropagationDeck.propagationdeck.removeFirst();
	}
	public static PropagationCard getLastPropagationcard() {
		return PropagationDeck.propagationdeck.removeLast();
	}
	


}
