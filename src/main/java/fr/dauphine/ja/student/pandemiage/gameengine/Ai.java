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
		Long start=System.nanoTime();
		Long finishTime=System.nanoTime();
		int i=(int) (finishTime-start);
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
		while(p.playerHand().size()>maxHandSize) {
			ArrayList<Double> tri=new ArrayList<Double>();
			for(PlayerCardInterface c:p.playerHand()) {
				tri.add(((Player)p).scoreOfTheCard(c));
			}
			Double tmp=tri.get(0);
			int indice=0;
			for(int i=1;i<tri.size();i++) {
				if(tri.get(i)<tmp) {
					tmp=tri.get(i);
					indice=i;
				}
			}
			
			PlayerCardInterface def=p.playerHand().get(indice);
			PlayerCard.addToDefauss(def);
			p.playerHand().remove(indice);
			listeR.add(def);
		}


		return listeR;
	}

}
