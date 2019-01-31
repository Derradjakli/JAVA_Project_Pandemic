package fr.dauphine.ja.student.pandemiage.gameengine;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.dauphine.ja.pandemiage.common.AiInterface;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.GameInterface;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Ai implements AiInterface{
	/**
	 * Engines call this method. AI should play the 4 actions in this method within the time fixed in TurnDuration.
	 * Engines put the interrupt flag to true after this time. AI should check this flag.
	 * If AI didn't finished this method 1 second after the first timeout, the game is lost.
	 * @param g	Interface with the engine to get information
	 * @param p	Interface with the player to make actions
	 */
	@Override
	public void playTurn(GameInterface g, PlayerInterface p) {
		// TODO Auto-generated method stub
		Action a=Action.NotDefined;
		Long start=System.nanoTime();
		Long finishTime=System.nanoTime();
		int i=(int) (finishTime-start);
		int actionleft=0;
		int [] tab=new int[2];
		double scoreNeighbours=0.0; // Score of the action moveTo
		double scoreCard=0.0; // Score of actions Flyto and FlytoCharter 
		int indice;
		ArrayList<Double> tri=new ArrayList<Double>();
		while(((Player)p).getActionLeft()>0) {
		
			///tab=GameEngine.scoreOfMyLocation((Player)p);
			//scoreNeighbours=(double)tab[1]/(double)tab[0];
			scoreNeighbours=GameEngine.scoreOfMyLocation((Player)p);
			
			for(PlayerCardInterface c: p.playerHand()) {
				tri.add(((Player)p).scoreOfTheCard(c));
			}
			scoreCard=(double)max(tri).get(0);
			indice =(int)max(tri).get(1);
			PlayerCardInterface pci=p.playerHand().get(indice);

			if(scoreCard>scoreNeighbours) {
				if(pci.getCityName().equals(((Player)p).getCurrentCity().getName())) {// If i can use flytocharter
					try {
						String name=GameEngine.chooseCityMostInfected(GameEngine.getListCity()).getName();
						System.out.println("JE fais effectué une action, jeter la carte de la ville"+pci.getCityName()+" et FlyToCharter à la ville "+name);
						
						p.flyToCharter(name);
					}catch(UnauthorizedActionException e) {
						System.err.println("Exception in AI FlytoCharter");
						e.printStackTrace();
					}
				}
				else {
					try {
						System.out.println("JE fais effectué une action, jeter la carte de la ville"+pci.getCityName()+" et FlyTo à la ville "+pci.getCityName());
						
						p.flyTo(pci.getCityName());
					}catch(UnauthorizedActionException e) {
						System.err.println("Exception in AI FlytoCharter");
						e.printStackTrace();
					}
				}
				
			}
			else {
				City city=GameEngine.chooseCityMostInfected(((Player)p).getListCity());
				City current=((Player)p).getCurrentCity();
				int nbCubeCity=city.getNbCubes(Disease.BLACK)+city.getNbCubes(Disease.RED)+city.getNbCubes(Disease.YELLOW)+city.getNbCubes(Disease.BLUE);
				int nbCubeCurrent=current.getNbCubes(Disease.BLACK)+current.getNbCubes(Disease.RED)+current.getNbCubes(Disease.YELLOW)+current.getNbCubes(Disease.BLUE);
				
				if(nbCubeCity>nbCubeCurrent){
					try {
						System.out.println("JE fais effectué une action,  MoveTo à la ville "+city.getName()+" cette ville à un nbcube a "+city.getNbCubes(city.getDisease())+" \net ma current a "+current.getNbCubes(current.getDisease()));
						
						p.moveTo(city.getName());
					}catch(UnauthorizedActionException e) {
						System.err.println("Exception in AI FlytoCharter");
						e.printStackTrace();
					}
					
				}
				else {
					System.out.println("je vais Cure ma current Ville "+current.getName()+" et lui enlever un Cube");
					current.setCubesChoiceDiseaseToCure();
				}
			}

		}
		//if(g.turnDuration()>i){
	}
	/**
	 * Once the player finished its actions and received Player Cards, he must discards to fit to the maximum hand size 
	 * @param g	Interface with the game
	 * @param p Interface with the actions
	 * @param maxHandSize maximum size of the player hand
	 * @param nbEpidemicCards number of epidemic cards that were picked by the player during this turn.
	 * @return The list of discarded cards
	 */
	@Override
	public List<PlayerCardInterface> discard(GameInterface g, PlayerInterface p, int maxHandSize, int nbEpidemicCards) {
		List<PlayerCardInterface> listeR=new ArrayList<PlayerCardInterface>();
		while(p.playerHand().size()>maxHandSize) { // While im not with the correct size
			ArrayList<Double> tri=new ArrayList<Double>();
			for(PlayerCardInterface c:p.playerHand()) {
				tri.add(((Player)p).scoreOfTheCard(c));
			}

			ArrayList r=min(tri);
			int indice=(int)r.get(1);
			PlayerCardInterface def=p.playerHand().get(indice);
			System.out.println("je vais jeter la carte "+def.getCityName()+" de maladie "+def.getDisease());
			PlayerCard.addToDefauss(def);
			p.playerHand().remove(indice);
			listeR.add(def);
		}


		return listeR;
	}



	public ArrayList max(ArrayList<Double> tri) {
		Double tmp=tri.get(0);
		ArrayList res=new ArrayList();
		int indice=0;
		for(int i=1;i<tri.size();i++) {
			if(tri.get(i)>tmp) {
				tmp=tri.get(i);
				indice=i;
			}
		}
		res.add(tmp);
		res.add(indice);
		return res;
	}
	public ArrayList min(ArrayList<Double> tri) {
		Double tmp=tri.get(0);
		ArrayList res=new ArrayList();
		int indice=0;
		for(int i=1;i<tri.size();i++) {
			if(tri.get(i)<tmp) {
				tmp=tri.get(i);
				indice=i;
			}
		}
		res.add(tmp);
		res.add(indice);
		return res;
	}

}
