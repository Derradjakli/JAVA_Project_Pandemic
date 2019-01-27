package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dauphine.ja.pandemiage.common.AiInterface;
import fr.dauphine.ja.pandemiage.common.AiLoader;
import fr.dauphine.ja.pandemiage.common.DefeatReason;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.GameInterface;
import fr.dauphine.ja.pandemiage.common.GameStatus;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;
import java.util.Collections;
/**
 * Empty GameEngine implementing GameInterface
 *
 */
public class GameEngine implements GameInterface{

	private final String aiJar;
	private final String cityGraphFilename; 	
	private GameStatus gameStatus;
	private static List<City> list;
	private static int vit_prop;// vitesse de propagation actuelle du jeu
	private static int nb_epidcard=0;// nombre de carte epidemie tiré
	private static int marqueur_prog=1;
	private static Map<Disease,Integer> reserve=new HashMap<Disease,Integer>();
	private static boolean bool;
	private static int[] vitprop= {2,2,3,3,4,4};
	private static int cptprop=0;
	private static GameLevel level=GameLevel.Easy;
	private static Map<Disease, HashMap<City, Boolean>> outBreaksForEachCity=new HashMap<Disease,HashMap<City,Boolean>>();// Permet de recuperer quelle maladie a eclos sur une ville

	// Do not change!
	private void setDefeated(String msg, DefeatReason dr) {		
		gameStatus = GameStatus.DEFEATED;
		System.err.println("Player(s) have been defeated: " + msg);
		System.err.println("Result: " + gameStatus);
		System.err.println("Reason: " + dr);
		printGameStats();
		System.exit(2);
	}

	// Do not change!
	private void setVictorious() {
		gameStatus = GameStatus.VICTORIOUS;
		System.err.println("Player(s) have won.");
		System.err.println("Result: " + gameStatus);
		printGameStats();
		System.exit(0);
	}

	// Do not change!
	private void printGameStats() {
		Map<Disease, Integer> blocks = new HashMap<>();
		for(String city : allCityNames()) {
			for(Disease d : Disease.values()) {
				blocks.put(d, blocks.getOrDefault(city, 0) + infectionLevel(city, d));
			}
		}
		System.err.println(blocks);
		System.err.println("Infection-rate:"+infectionRate());
		for(Disease d : Disease.values()) {
			System.err.println("Cured-" + d + ":"+isCured(d));
		}
		System.err.println("Nb-outbreaks:"+getNbOutbreaks());
		System.err.println("Nb-player-cards-left:"+getNbPlayerCardsLeft());
	}

	public GameEngine(String cityGraphFilename, String aiJar) throws IOException{
		this.cityGraphFilename = cityGraphFilename; 
		this.aiJar = aiJar; 
		this.gameStatus = GameStatus.ONGOING;
		this.reserve = new HashMap<Disease,Integer>(); 
		for(Disease d :Disease.values()){
			reserve.put(d, 24);
		}
		this.vit_prop=vitprop[cptprop];
		System.out.println("je suis la");
		this.list=GMLReader.readGML(cityGraphFilename);
		for(City c:list) {
			System.out.println("Ville :"+c.getName());
		}
	}

	public static void Outbreaks(City city, Disease d){
		for(City c : city.getNeighbours()){
			if(!c.isEclosion(d)){
				if(c.getNbCubes(d)==3){
					Outbreaks(c,d);
				}
				else{
					c.setNbCubes(c.getNbCubes(city.getDisease())+1, city.getDisease());
				}
			}
		}
	}

	public static void GiveMeBlockFromReserve(Disease d, Integer n){
		reserve.replace(d,reserve.get(d)-n);
	}



	public static boolean AvalaibleBLocks(int i,Disease d){
		if(reserve.get(d)-i<0) {
			return false;
		}
		return true;
	}


	public void loop() throws UnauthorizedActionException  {
		// Load Ai from Jar file
		System.out.println("Loading AI Jar file " + aiJar);		
		//AiInterface ai = AiLoader.loadAi(aiJar);	
		City c=this.getCity("Atlanta");
		Player p=new Player(c,list);
		System.out.println("Je suis dans "+p.getCurrentCity().getName());
		p.moveTo(p.getCurrentCity().getNeighbours().get(0).getName());
		System.out.println("Je suis dans "+p.getCurrentCity().getName());
		System.out.println(p.playerHand());
		// Create the player Card
		List<PlayerCardInterface> listcard=new ArrayList<PlayerCardInterface>();
		int cpt=0;
		for(int i=0;i<48;i++) {
			if(list.get(i).getName().equals("Delhi")) {
				System.out.println("j'ai trouvé la carte");
				cpt=i;
			}
			listcard.add(new CitiesCard(list.get(i)));
		}

		//Create the Epidemic Card
		int j=4;
		if(level.equals(GameLevel.Easy)) {
			j=4;
		}
		if(level.equals(GameLevel.Medium)) {
			j=5;
		}
		if(level.equals(GameLevel.Hard)) {
			j=6;
		}
		for(int i=0;i<j;i++) {
			listcard.add(new EpidemicCard());
		}

		//Collections.shuffle(listcard);
		System.out.println("je suis la");
		p.addToPlayerHand(listcard.get(4));
		System.out.println("je suis ici");

		PlayerCardInterface c1=p.playerHand().get(0);
		System.out.println(p.playerHand());
		//PlayerCardInterface c2=p.playerHand().get(1);

		System.out.println("Card ville "+c1.getCity().getName());
		p.flyTo(c1.getCity().getName());
		System.out.println(p.getCurrentCity().getName());
		System.out.println("action left "+p.getActionLeft());
		System.out.println(p.getCurrentCity().getNeighbours_s());
		p.moveTo(p.getCurrentCity().getNeighbours().get(1).getName());
		System.out.println(p.getCurrentCity().getName());

		p.addToPlayerHand(listcard.get(cpt));
		System.out.println("Mes cartes en main");
		for(PlayerCardInterface c2:p.playerHand()) {
			System.out.println(c2.getCityName());
		}
		System.out.println("je suis a "+p.getCurrentCity().getName());
		p.flyToCharter("Algiers");
		System.out.println(p.getCurrentCity().getName());



		// Very basic game loop
		while(gameStatus == GameStatus.ONGOING) {

			for(Disease d :Disease.values()){
				if(reserve.get(d)<0){
					setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
				}
			}

			if(Math.random() < 0.5)		
				setDefeated("Game not implemented.", DefeatReason.UNKN);
			else
				setVictorious();			
		}
	}						



	@Override
	public List<String> allCityNames() {


		ArrayList<String> s=new ArrayList<String>();
		int n=list.size();

		for(int i=0;i<n;i++) {
			s.add(list.get(i).getName());
		}
		return s;
		//throw new UnsupportedOperationException(); 

		// TODO

		//	throw new UnsupportedOperationException(); 
	}

	@Override
	public List<String> neighbours(String cityName) {
		// TODO

		int n=list.size();
		for(int i=0;i<n;i++) {
			if(list.get(i).getName().equals(cityName)) {
				return list.get(i).getNeighbours_s();
			}
		}
		throw new UnsupportedOperationException(); 
	}

	@Override
	public int infectionLevel(String cityName, Disease d) {
		// TODO

		int n=this.list.size();
		for(int i=0;i<n;i++) {
			if(list.get(i).getName().equals(cityName)) {
				return list.get(i).getNbCubes(d);
			}
		}


		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isCured(Disease d) {
		for(City c: list) {
			if(c.isCure(d)) {
				return true;
			}
		}
		return false;
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public int infectionRate() {
		// TODO
		return vitprop[cptprop];
		//throw new UnsupportedOperationException(); 
	}
	public City getCity(String cityName) {
		for(City c:list) {
			if(c.getName().equals(cityName)) 
				return c;

		}
		throw new UnsupportedOperationException("There is no city for the name "+cityName);
	}
	@Override
	public GameStatus gameStatus() {
		// TODO
		return this.gameStatus;
		//throw new UnsupportedOperationException(); 
	}

	@Override 
	public int turnDuration() {
		// TODO
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean isEradicated(Disease d) {
		// TODO
		int n=list.size();
		for(int i=0;i<n;i++) {
			if(list.get(i).getNbCubes(d)!=0) {
				return false;
			}
		}
		return true;
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public int getNbOutbreaks() {
		// TODO 
		return this.marqueur_prog;
		//throw new UnsupportedOperationException(); 
	}

	@Override
	public int getNbPlayerCardsLeft() {
		// TODO 
		return Player.listCardHand.size();
		//throw new UnsupportedOperationException(); 
	}

	public static Map<Disease, Integer> getReserve() {
		return reserve;
	}

	public static void setReserve(Map<Disease, Integer> reserve) {
		GameEngine.reserve = reserve;
	}

	public static boolean isBool() {
		return bool;
	}

	public static void setBool(boolean bool) {
		GameEngine.bool = bool;
	}
	public static int getVit_prop() {
		return vit_prop;
	}

	public static void setVit_prop(int vit_prop) {
		GameEngine.vit_prop = vit_prop;
	}

	public static int getNb_epidcard() {
		return nb_epidcard;
	}

	public static void setNb_epidcard(int nb_epidcard) {
		GameEngine.nb_epidcard = nb_epidcard;
	}

	public static int indice (int[] tab){

		return 0;
	}
	public static int[] getVitprop() {
		return vitprop;
	}

	public static void setVitprop(int[] vitprop) {
		GameEngine.vitprop = vitprop;
	}

	public static int getCptprop() {
		return cptprop;
	}

	public static void setCptprop(int cptprop) {
		GameEngine.cptprop = cptprop;
	}


	/**Return for each Disease, the number of cubes associated in the map**/
	public Map<Disease,Integer> scoreOfEachDisease(){
		Map<Disease,Integer> m=new HashMap<Disease,Integer>();
		for(City c:list) {
			if(c.getDisease()==Disease.BLACK) {
				m.put(Disease.BLACK, m.get(Disease.BLACK)+c.getNbCubes(Disease.BLACK));
			}
			if(c.getDisease()==Disease.BLUE) {
				m.put(Disease.BLUE, m.get(Disease.BLUE)+c.getNbCubes(Disease.BLUE));
			}
			if(c.getDisease()==Disease.RED) {
				m.put(Disease.RED, m.get(Disease.RED)+c.getNbCubes(Disease.RED));
			}
			if(c.getDisease()==Disease.YELLOW) {
				m.put(Disease.YELLOW, m.get(Disease.YELLOW)+c.getNbCubes(Disease.YELLOW));
			}
		}
		return m;
	}

	public int[] scoreOfMyLocation(Player p) {
		City c=p.getCurrentCity();
		return scoreOfEachRegion(c.getDisease(),c.getNeighbours());
	}

	public List<City> getListCityWithDisease(Disease d){
		List<City> res =new ArrayList<City>();
		for(City c: list) {
			if(c.getDisease()==d)
				res.add(c);
		}
		return res;
	}
	/**Return the number of eclosion for the disease d in the same turn. Take the list of the region**/
	public int[] scoreOfEachRegion(Disease d,List<City> liste) {
		int cpt=0;
		int nbcubes=0;
		int[] tab=new int[2];
		for(City c:liste) {
			if(c.getNbCubes(d)>0) {
				if(c.isEclosion(d)) 
					nbcubes+=3;

				if(c.getNbCubes(d)==3) 
					nbcubes+=3;

				if(c.getNbCubes(d)==2) 
					nbcubes+=2;

				if(c.getNbCubes(d)==1) 
					nbcubes+=1;
				cpt++;
			}
		}
		tab[0]=cpt;
		tab[1]=nbcubes;
		return tab;
	}




	public static void main(String [] args) throws IOException, UnauthorizedActionException   {
		/*
		ArrayList<City> liste = new ArrayList<City>();
		liste = GMLReader.readGML("");
		System.out.println(liste.size());
		 */
		// Faire rentrer en paramètre le nom du graph, le jar et le niveau de difficulté
		GameEngine g=new GameEngine("pandemic.graphml","");

		g.loop();
		/*
		for(int i = 0; i < liste.size(); i++) {
			System.out.println("City Name : " +liste.get(i).getName());
			System.out.println("City Degree : " +liste.get(i).getDegree());
			System.out.println("City Neighbours :			 " +liste.get(i).getNeighbours_s());
		}*/


	}


}