package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Player implements PlayerInterface{
	private List<City> listCity;
	private List<PlayerCardInterface> listCardHand;
	private City currentCity;
	private int action=4;
	private boolean switchturn=false; // boolean permet de savoir si les 4 actions sont fini
	private boolean myturn=false;
	public Player(City depart,List<City> listc) {
		currentCity=depart;
		listCity=listc;
	}
	@Override
	public void moveTo(String cityName) throws UnauthorizedActionException {
		if(!switchturn) {
			for(City i:currentCity.getNeighbours()) {
				if(i.getName()==cityName) {
					this.currentCity=i;
					action--;
					if(action==0) {
						switchturn=true;
					}
					return;
				}
			}
		}
		else {
			throw new UnauthorizedActionException("I can't moveTo"+cityName+"Cause it's not my Neighbour");
		}

	}

	@Override
	public void flyTo(String cityName) throws UnauthorizedActionException {
		if(!switchturn) {
			int n=listCardHand.size();
			for(int i=0;i<n;i++) {
				if(listCardHand.get(i).getCityName()==cityName) {
					
					this.currentCity=listCardHand.get(i).getCity(); // a voir comment recuperer la ville a partir du nom
					PlayerCard.addToDefauss(listCardHand.get(i));
					listCardHand.remove(i);// listcarddefaussé.add(
					
					action--;
					if(action==0)
						switchturn=true;
					return;

				}
				i++;
			}
		}
		throw new UnauthorizedActionException("I can't FlyTo"+cityName+"Cause it's not my Neighbour");
		
		

	}

	@Override
	public void flyToCharter(String cityName) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		// Parcours ma liste de cartes de jeux en ma possession, verifie si j'ai une carte qui correspond a ma position actuel sur la Map, Si oui je jette cette carte et je me deplace ensuite vers la ville que je souhaite
		if(!switchturn) {
			int n=listCardHand.size();
			for(int i=0;i<n;i++)  {
				if(listCardHand.get(i).getCityName()==this.currentCity.getName()) {
					for(City ci:listCity) {
						if(ci.getName()==cityName) {
							this.currentCity=ci;
							action--;
							PlayerCard.addToDefauss(listCardHand.get(i));// Add my card to the discarded cards list
							listCardHand.remove(i);// remove the card from my hand
							if(action==0)
								switchturn=true;
							return;
						}
					}
				}
				i++;
			}
			
		}
		throw new UnauthorizedActionException("I can't FlyToCharter"+cityName+"Cause it's not my Neighbour");
		
		
		
	}

	@Override
	public void skipTurn() {
		// TODO Auto-generated method stub
		this.myturn=false;

	}

	@Override
	public void treatDisease(Disease d) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		if(!this.switchturn) {
			if(this.currentCity.getNbCubes(d)>0) {
				if(!this.currentCity.isCure(d))
					this.currentCity.setNbCubes(this.currentCity.getNbCubes(d)-1, d);// Je decrémente le nombre de cube de 1 de Disease d
				else
					this.currentCity.setNbCubes(0, d); // si le remede existe alors on retire tout les blocks de Disease d
				action --;
				if(action==0)
					this.switchturn=true;
				return;
			}
		}
		throw new UnauthorizedActionException("I can't treadDisease "+d+" on the city "+this.currentCity.getName());
		
		
	}

	@Override
	public void discoverCure(List<PlayerCardInterface> cardNames) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		Disease d=cardNames.get(0).getDisease();
		if(cardNames.size()<5) {
			for(PlayerCardInterface c:cardNames) {
				if(d!=c.getDisease())
					throw new UnauthorizedActionException("The list of card is not of the correct colors");
			}
			if(!this.switchturn) {
				this.currentCity.setCure(d);
			}
			
		}
		throw new UnauthorizedActionException("There is no action left or the list of card is not of the correct size");
		

	}

	@Override
	public String playerLocation() {
		// TODO Auto-generated method stub
		return this.currentCity.getName();
	}

	@Override
	public List<PlayerCardInterface> playerHand() {
		// TODO Auto-generated method stub
		return this.listCardHand;
	}

}
