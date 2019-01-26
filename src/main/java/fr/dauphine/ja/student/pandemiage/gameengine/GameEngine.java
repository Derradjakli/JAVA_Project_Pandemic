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

/**
 * Empty GameEngine implementing GameInterface
 *
 */
public class GameEngine implements GameInterface{

	private final String aiJar;
	private final String cityGraphFilename; 	
	private GameStatus gameStatus;
	private List<City> list;
	private static int vit_prop;// vitesse de propagation actuelle du jeu
	private static int nb_epidcard=0;// nombre de carte epidemie tiré
	private static int marqueur_prog=1;
	private static Map<Disease,Integer> reserve;
	private static boolean bool;
	private static int[]vitprop= {2,2,3,3,4,4};
	private static int cptprop=0;

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

	public GameEngine(String cityGraphFilename, String aiJar){
		this.cityGraphFilename = cityGraphFilename; 
		this.aiJar = aiJar; 
		this.gameStatus = GameStatus.ONGOING;
		this.reserve = new HashMap<Disease,Integer>(); 
		for(Disease d :Disease.values()){
			reserve.put(d, 24);
		}
		this.vit_prop=vitprop[cptprop];
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

	
	
	public static void AvalaibleBLocks(Integer i){
		for(Disease d :Disease.values()){
			//if(reserve.get(d)==-1){
			if((reserve.get(d)-i)<0){

			//	setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);

			//	setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
				
			}
		}
	}
	
	
	public void loop()  {
		// Load Ai from Jar file
		System.out.println("Loading AI Jar file " + aiJar);		
		AiInterface ai = AiLoader.loadAi(aiJar);		

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
			if(list.get(i).getName()==cityName) {
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
			if(list.get(i).getName()==cityName) {
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
		return this.vit_prop;
		//throw new UnsupportedOperationException(); 
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
	
public static void main(String [] args) throws IOException   {
		
		ArrayList<City> liste = new ArrayList<City>();
		liste = GMLReader.readGML("");
		System.out.println(liste.size());
		
		for(int i = 0; i < liste.size(); i++) {
			System.out.println("City Name : " +liste.get(i).getName());
			System.out.println("City Degree : " +liste.get(i).getDegree());
			System.out.println("City Neighbours :			 " +liste.get(i).getNeighbours_s());
		}
		
		
	}
	

}
