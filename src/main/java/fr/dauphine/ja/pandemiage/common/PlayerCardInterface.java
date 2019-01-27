package fr.dauphine.ja.pandemiage.common;

import fr.dauphine.ja.student.pandemiage.gameengine.City;

/**
 * Interface for the Player Cards.
 *
 */

public interface PlayerCardInterface {
	
	/**
	 * 
	 * @return Name of the city, in String of this card
	 */
	public String getCityName(); 
	
	/**
	 * 
	 * @return Type of disease associated to this card
	 */
	public Disease getDisease(); 	
	public City getCity();
	public Boolean isEpidemic();

}
