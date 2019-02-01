package fr.dauphine.ja.student.pandemiage.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jcabi.aspects.Timeable;

import fr.dauphine.ja.pandemiage.common.AiInterface;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.GameInterface;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;
import fr.dauphine.ja.student.pandemiage.gameengine.City;
import fr.dauphine.ja.student.pandemiage.gameengine.GameEngine;
import fr.dauphine.ja.student.pandemiage.gameengine.Player;
import fr.dauphine.ja.student.pandemiage.gameengine.PlayerCard;
import fr.dauphine.ja.student.pandemiage.ui.Cli;

public class Ai implements AiInterface{
	/**
	 * Engines call this method. AI should play the 4 actions in this method within the time fixed in TurnDuration.
	 * Engines put the interrupt flag to true after this time. AI should check this flag.
	 * If AI didn't finished this method 1 second after the first timeout, the game is lost.
	 * @param g	Interface with the engine to get information
	 * @param p	Interface with the player to make actions
	 */
	@Timeable(limit = GameEngine.DEFAULT_TURN_DURATION, unit = TimeUnit.SECONDS)
	@Override
	public void playTurn(GameInterface g, PlayerInterface p) {
		
		// TODO Auto-generated method stub

		int actionleft=4;

		double scoreNeighbours=0.0; // Score of the action moveTo
		double scoreCard=0.0; // Score of actions Flyto and FlytoCharter 
		int indice;

		while(actionleft>0) {
			City me=((Player)p).getCurrentCity();
			if(me.setCubesChoiceDiseaseToCure()) {
				actionleft--;
			}
			else {
				scoreNeighbours=GameEngine.scoreOfMyLocation((Player)p);
				for(PlayerCardInterface card: p.playerHand())
					System.out.println("La carte "+card.getCityName());
				ArrayList<Double> tri=new ArrayList<Double>();
				for(PlayerCardInterface c: p.playerHand()) {
					tri.add(((Player)p).scoreOfTheCard(c));
				}
				if(tri.size()!=0) {
					ArrayList<Double> res=new ArrayList();
					res=max(tri);
					scoreCard=res.get(0);

					System.out.println("score de ma meilleur carte est a "+scoreCard);

					indice =res.get(1).intValue();
					PlayerCardInterface pci=p.playerHand().get(indice);
					if(scoreCard>scoreNeighbours) {
						if(pci.getCityName().equals(((Player)p).getCurrentCity().getName())) {// If i can use flytocharter
							try {
								String name=GameEngine.chooseCityMostInfected(GameEngine.getListCity()).getName();
								System.out.println("JE fais effectué une action, jeter la carte de la ville"+pci.getCityName()+" et FlyToCharter à la ville "+name);

								p.flyToCharter(name);
								actionleft--;
							}catch(UnauthorizedActionException e) {
								System.err.println("Exception in AI FlytoCharter");
								e.printStackTrace();
							}
						}
						else {
							try {
								System.out.println("JE fais effectué une action, je suis à "+((Player)p).getCurrentCity().getName()+" jeter la carte de la ville "+pci.getCityName()+" et FlyTo à la ville "+pci.getCityName());

								p.flyTo(pci.getCityName());
								actionleft--;
							}catch(UnauthorizedActionException e) {
								System.err.println("Exception in AI Flyto");
								e.printStackTrace();
							}
						}

					}


					else {
						//City city=GameEngine.chooseCityMostInfected(((Player)p).getCurrentCity().getNeighbours());
						City current=((Player)p).getCurrentCity();
						//int nbCubeCity=city.getNbCubes(Disease.BLACK)+city.getNbCubes(Disease.RED)+city.getNbCubes(Disease.YELLOW)+city.getNbCubes(Disease.BLUE);
						int nbCubeCurrent=current.getNbCubes(Disease.BLACK)+current.getNbCubes(Disease.RED)+current.getNbCubes(Disease.YELLOW)+current.getNbCubes(Disease.BLUE);
						List<City> listecitytest=((Player)p).getCurrentCity().getNeighbours();
						List<City> listecitytesttotal=new ArrayList<City>();
						int n=listecitytest.size();
						for(City c: listecitytest) {
							listecitytesttotal.addAll(c.getNeighbours());
						}
						listecitytesttotal.addAll(listecitytest);
						City finalCity=GameEngine.chooseCityMostInfected(listecitytesttotal);
						int nbCubeCity=finalCity.getNbCubes(Disease.BLACK)+finalCity.getNbCubes(Disease.RED)+finalCity.getNbCubes(Disease.YELLOW)+finalCity.getNbCubes(Disease.BLUE);
						
						List<City> chemin=new ArrayList<City>();
						City tmp;
						for(City c: listecitytest) {
							tmp=c;
							for(City other: c.getNeighbours()) {
								if(other.getName()==finalCity.getName()) {
									chemin.add(tmp);
									chemin.add(other); 
								}
							}
							if(tmp.getName()==finalCity.getName())
								chemin.add(tmp);
						}
						
						
						if(nbCubeCity>nbCubeCurrent){
							try {
								System.out.println("Je vais effectué une action,  MoveTo à la ville "+finalCity.getName()+" cette ville à un nbcube a "+finalCity.getNbCubes(finalCity.getDisease())+" \net ma current a "+current.getNbCubes(current.getDisease()));
								for(int i=0;i<chemin.size();i++) {
									p.moveTo(chemin.get(i).getName());
								}
								actionleft--;
							}catch(UnauthorizedActionException e) {
								System.err.println("Exception in AI FlytoCharter");
								e.printStackTrace();
							}

						}
						else {
							System.out.println("je vais Cure ma current Ville "+current.getName()+" et lui enlever un Cube");
							current.setCubesChoiceDiseaseToCure();
							actionleft--;
						}
					}
				}
			}

		}


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

			ArrayList<Double> r=min(tri);
			//System.out.println("score de la pire carte est a "+r.get(0));
			int indice=r.get(1).intValue();
			PlayerCardInterface def=p.playerHand().get(indice);
			System.out.println("je vais jeter la carte "+def.getCityName()+" de maladie "+def.getDisease());
			PlayerCard.addToDefauss(def);
			p.playerHand().remove(indice);
			listeR.add(def);
		}


		return listeR;
	}




	public ArrayList<Double> max(ArrayList<Double> tri) {
		Double tmp=tri.get(0);
		ArrayList<Double> res=new ArrayList<Double>();
		int indice=0;
		//System.out.println("\n\nLa taille de tri est a "+tri.size()+"\n\n");
		for(int i=1;i<tri.size();i++) {
			if(tri.get(i)>tmp) {
				tmp=tri.get(i);
				indice=i;
			}
		}

		res.add(tmp);
		res.add((double)indice);
		return res;
	}
	public ArrayList<Double> min(ArrayList<Double> tri) {
		Double tmp=tri.get(0);
		ArrayList<Double> res=new ArrayList<Double>();
		int indice=0;
		for(int i=1;i<tri.size();i++) {
			if(tri.get(i)<tmp) {
				tmp=tri.get(i);
				indice=i;
			}
		}

		res.add(tmp);
		res.add((double)indice);
		return res;
	}
}
