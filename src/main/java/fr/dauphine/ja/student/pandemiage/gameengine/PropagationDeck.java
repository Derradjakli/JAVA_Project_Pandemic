package fr.dauphine.ja.student.pandemiage.gameengine;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class PropagationDeck {
	private LinkedList<PropagationCard> propagationdeck;

	public PropagationDeck(){
		propagationdeck=new LinkedList<PropagationCard>();
	}
 
	public void lastDeckCard(PropagationDeck propdefauss){
		//PropagationCard pc= propagationdeck.remove(propagationdeck.size()-1);
		//PropagationCard pc= propdefauss.getPropagationdeck().remove(propdefauss.getPropagationdeck().size()-1);
		//System.out.println("je rentre dans infection");
		//System.out.println(propdefauss.getPropagationdeck().size());

		//PropagationCard pc= propdefauss.getPropagationdeck().remove(0);
		PropagationCard pc= this.getPropagationdeck().get(0);
		this.getPropagationdeck().remove(0);
		propdefauss.getPropagationdeck().add(pc);
		//System.out.println("j'ai chopé la premiere carte");

		Disease d=pc.getDisease();
		
		//System.out.println(pc.getCity().getName());
		//System.out.println(pc.getCity().getM().containsKey(d));
		//System.out.println(pc.getCity().getDisease());

		if(pc.getCity().getNbCubes(d)==0){
		//if(pc.getCity().getM().get(d)==0){
	
			//System.out.println("dans le if de lastdeckcard");

			if(GameEngine.AvalaibleBLocks(3,d)) {
				GameEngine.GiveMeBlockFromReserve(d,3);
				pc.getCity().setNbCubes(pc.getCity().getNbCubes(d)+3,d);
			}
			else {
				throw new UnsupportedOperationException("No more cubes left for the Disease "+d);
			}
		}
		else{
//			System.out.println("dans le else de lastdeckcard");

			pc.getCity().setNbCubes(3, d);
			pc.getCity().setEclosion(true, d);
			GameEngine.Outbreaks(pc.getCity(), d);
		}
		propagationdeck.add(pc);
	}

	public List<PropagationCard> getPropagationdeck() {
		return propagationdeck;
	}

	public void setPropagationdeck(LinkedList<PropagationCard> propagationdeckk) {
		propagationdeck = propagationdeckk;
	}
	
	public PropagationCard getFirstPropagationcard() {
		return propagationdeck.removeFirst();
	}
	public PropagationCard getLastPropagationcard() {
		return propagationdeck.removeLast();
		//return PropagationDeck.propagationdeck.removeLast();
	}
	


}
