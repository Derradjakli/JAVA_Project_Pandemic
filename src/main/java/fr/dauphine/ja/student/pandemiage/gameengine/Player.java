package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.dauphine.ja.pandemiage.common.DefeatReason;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;

public class Player implements PlayerInterface{
	private List<City> listCity;
	private static List<PlayerCardInterface> listCardHand;
	private City currentCity;
	//PlayerCardInterface c=new PlayerCard(currentCity);

	private int action=4;
	private boolean switchturn=false; // boolean permet de savoir si les 4 actions sont fini

	public Player(City depart,List<City> listc) {
		currentCity=depart;
		listCity=listc;
		listCardHand=new ArrayList<PlayerCardInterface>();
	}

	@Override
	public void moveTo(String cityName) throws UnauthorizedActionException {
		if(!switchturn) {
			System.out.println("je suis dans switchturn");
			for(City i:currentCity.getNeighbours()) {
				if(i.getName().equals(cityName)) {
					this.currentCity=i;
					System.out.println("ma current ville est mtn a "+currentCity.getName());
					action--;
					if(action==0) {
						switchturn=true;
					}
					return;
				}
			}


			throw new UnauthorizedActionException("I can't moveTo"+cityName+"Cause it's not my Neighbour");

		}

	}

	public City getCurrentCity() {
		return this.currentCity;
	}
	public int getActionLeft() {
		return action;
	}

	@Override
	public void flyTo(String cityName) throws UnauthorizedActionException {
		System.out.println("switch turn dans fly to est a "+switchturn);
		if(!switchturn) {
			//System.out.println("je rentre dans le if de flyto");
			int n=listCardHand.size();
			PlayerCardInterface card;
			System.out.println("Dans flyto ma liste de carte est mtn a ");
			for(PlayerCardInterface carde:listCardHand)
				System.out.println("la carte "+carde.getCityName());

			for(int i=0;i<n;i++) {
				card=listCardHand.get(i);
				System.out.println("j'ai la carte "+listCardHand.get(i).getCityName()+" et mon nombre de carte est a "+n);
				//	if(listCardHand.get(i).getCityName().equals(cityName)) {
				if(card.getCityName()==cityName) {


					System.out.println("j'ai trouvé la ville pour me deplacer");
					this.currentCity=((PlayerCard) (card)).getCity(); // a voir comment recuperer la ville a partir du nom
					System.out.println("ma ville courante mtn est a "+currentCity.getName());
					PlayerCard.addToDefauss(card);
					listCardHand.remove(i);// listcarddefaussé.add(
					System.out.println("ma mise a jour de liste de carte est mtn a ");
					for(PlayerCardInterface carde:listCardHand)
						System.out.println("la carte "+carde.getCityName());

					action--;
					if(action==0)
						switchturn=true;
					return;

				}
				i++;
			}
		}
		else
			throw new UnauthorizedActionException("I can't FlyTo"+cityName+"Cause there's no card in my hand of this city or no more action left");



	}

	@Override
	public void flyToCharter(String cityName) throws UnauthorizedActionException {
		// TODO Auto-generated method stub
		// Parcours ma liste de cartes de jeux en ma possession, verifie si j'ai une carte qui correspond a ma position actuel sur la Map, Si oui je jette cette carte et je me deplace ensuite vers la ville que je souhaite
		if(!switchturn) {
			int n=listCardHand.size();
			for(int i=0;i<n;i++)  {
				//	if(listCardHand.get(i).getCityName().equals(this.currentCity.getName())) {
				if(listCardHand.get(i).getCityName()==this.currentCity.getName()) {

					for(City ci:listCity) {
						if(ci.getName().equals(cityName)) {
							this.currentCity=ci;
							action--;
							PlayerCard.addToDefauss(listCardHand.get(i));// Add my card to the discarded cards list
							listCardHand.remove(i);// remove the card from my hand
							for(PlayerCardInterface carde:listCardHand)
								System.out.println("ma liste de carte est mtn a "+carde.getCityName());
							if(action==0)
								switchturn=true;
							return;
						}
					}
				}
				i++;
			}

		}
		throw new UnauthorizedActionException("I can't FlyToCharter "+cityName+" Cause there's no card of my currentCity in my hand or no more action left");



	}

	@Override
	public void skipTurn() {
		// TODO Auto-generated method stub
		this.switchturn=false;

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
				if(!d.equals(c.getDisease()))
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
		return listCardHand;
	}

	public void SeeCards(){
		for(PlayerCardInterface pc: listCardHand){
			pc.getCityName();
		}
	}

	public double scoreOfEachCardInRegion(PlayerCardInterface c) {

		int[] tab=new int[2];

		tab=GameEngine.scoreOfEachRegion(c.getDisease(),GameEngine.getListCityWithDisease(c.getDisease()));
		double res=0.0;
		if(tab[0]!=0)
			res=(double)(tab[1])/(double)(tab[0]);
		System.out.println("Ici j'ai fini de calculer le score de la carte");
		return res;
	}
	public int scoreOfEachCardInCure(PlayerCardInterface c1) {
		int cpt=0;

		if(GameEngine.getRemedes(c1.getDisease())){
			return 0;
		}
		else {
			for(PlayerCardInterface c: this.playerHand()) {
				if(c1.getDisease()==c.getDisease())
					cpt++;
			}
			switch(cpt) {
			case 1: return 1;
			case 2: return 3;
			case 3: return 5;
			case 4: return 8;
			case 5: return 12;
			case 6: // voir si on peut faire charter avec ou bien voir si elle peut nous mener a une region blindé de maladie
			}
		}
		return cpt;
	}

	public int scoreOfEachCardInMove(PlayerCardInterface c) {
		int incrementation=0;
		/*Map<Disease,Integer> m=new HashMap<Disease,Integer>();
		Set<Disease> c2=m.keySet();
		Iterator<Disease> it = c2.iterator();

		Disease cle = it.next(); // tu peux typer plus finement ici
		Disease tmp1=cle;
		Integer tmp = m.get(cle); // tu peux typer plus finement ici
		while (it.hasNext()){
			 cle = it.next(); // tu peux typer plus finement ici
			 Integer valeur = m.get(cle); // tu peux typer plus finement ici

			 if(tmp<valeur) {
				 tmp=valeur;
				 tmp1=cle;
			 }
		}
		if(c.getDisease()==tmp1) {
			incrementation=2;
		}*/
		if(c.getCityName().equals(currentCity.getName()))
			return 10;
		else {
			for(City c1:currentCity.getNeighbours()) {
				if(c1.getName().equals(c.getCityName())) {
					return 6+incrementation;
				}
			}
		}
		return 1;
	}

	public double scoreOfTheCard(PlayerCardInterface c) {
		//System.out.println("probleme dans move");
		double move=scoreOfEachCardInMove(c);
		//System.out.println("probleme dans Cure");

		double cure=scoreOfEachCardInCure(c);
		//System.out.println("probleme dans Region");

		double region=scoreOfEachCardInRegion(c);
		//System.out.println("aucun prob");

		return move+cure+region*3;

	}
	public void addToPlayerHand(PlayerCardInterface c) {
		listCardHand.add(c);
	}
	public List<City> getListCity() {
		return listCity;
	}
	public void setListCity(List<City> listCity) {
		this.listCity = listCity;
	}
	public static List<PlayerCardInterface> getListCardHand() {
		return listCardHand;
	}
	public static void setListCardHand(List<PlayerCardInterface> listCardHand) {
		Player.listCardHand = listCardHand;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public boolean isSwitchturn() {
		return switchturn;
	}
	public void setSwitchturn(boolean switchturn) {
		this.switchturn = switchturn;
	}
	public void setCurrentCity(City currentCity) {
		this.currentCity = currentCity;
	}

}

