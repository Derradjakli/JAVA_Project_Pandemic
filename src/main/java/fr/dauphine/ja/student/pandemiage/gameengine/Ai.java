package fr.dauphine.ja.student.pandemiage.gameengine;

import java.time.Clock;
import java.util.List;

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
		if(p.playerHand().size()>maxHandSize) {
		int compteurRed=0;
		int compteurBlue=0;
		int compteurYellow=0;
		int compteurBlack=0;
		
		for(PlayerCardInterface c: p.playerHand()) {
			if(c.getDisease()==Disease.BLACK)
				compteurBlack++;
			if(c.getDisease()==Disease.BLUE)
				compteurBlue++;
			if(c.getDisease()==Disease.RED)
				compteurRed++;
			if(c.getDisease()==Disease.YELLOW)
				compteurYellow++;
			
		}
		
		}
		
		
		return null;
	}

}
