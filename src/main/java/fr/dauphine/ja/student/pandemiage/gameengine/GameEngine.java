package fr.dauphine.ja.student.pandemiage.gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import fr.dauphine.ja.pandemiage.common.AiInterface;
import fr.dauphine.ja.pandemiage.common.AiLoader;
import fr.dauphine.ja.pandemiage.common.DefeatReason;
import fr.dauphine.ja.pandemiage.common.Disease;
import fr.dauphine.ja.pandemiage.common.GameInterface;
import fr.dauphine.ja.pandemiage.common.GameStatus;
import fr.dauphine.ja.pandemiage.common.PlayerCardInterface;
import fr.dauphine.ja.pandemiage.common.PlayerInterface;
import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;
import fr.dauphine.ja.student.pandemiage.ai.AiMethods;
import fr.dauphine.ja.student.pandemiage.ui.Cli;
import fr.dauphine.ja.student.pandemiage.ui.Panneau;

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
	private static  Map<Disease,Integer> reserve=new HashMap<Disease,Integer>();
	private static boolean bool;
	private static int[] vitprop= {2,2,2,3,3,4,4};
	private static int cptprop=0;
	private static Player p;
	private static GameLevel level=GameLevel.Easy;
	private static Map<Disease, HashMap<City, Boolean>> outBreaksForEachCity=new HashMap<Disease,HashMap<City,Boolean>>();// Permet de recuperer quelle maladie a eclos sur une ville
	private int turnduration=0;
	private static Map<Disease,Boolean> remedes = new HashMap<Disease,Boolean>();
	private static int cptOutbreaks=0;
	private static List<PlayerCardInterface> listcard;

	private  PropagationDeck pdeck;
	private  PropagationDeck propdefauss;


	//For the options when using cmd line 
	public static final String DEFAULT_AIJAR = "./target/pandemiage-1.0-SNAPSHOT-ai.jar"; 
	public static final String DEFAULT_CITYGRAPH_FILE = "./pandemic.graphml";
	public static final int DEFAULT_TURN_DURATION = 1;	//in seconds
	public static final int DEFAULT_DIFFICULTY = 0; // Normal
	public static final int DEFAULT_HAND_SIZE = 9;


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
			remedes.put(d, false);
		}
		this.vit_prop=vitprop[cptprop];
		this.list=GMLReader.readGML(cityGraphFilename);
		City atlanta=getCityString("Atlanta",list);
		p=new Player(atlanta,list);
		pdeck=new PropagationDeck();
		propdefauss =new PropagationDeck();
		listcard=new LinkedList<PlayerCardInterface>();
	}

	public City getCityString(String name,List<City> liste) {
		for(City c:liste) {
			if(c.getName().equals(name))
				return c;
		}
		throw new UnsupportedOperationException("Name "+name+" for a city doesn't exist in the Map");
	}
	public static void Outbreaks(City city, Disease d){
		System.out.println("OUTBREAKS "+city.getName());
		cptOutbreaks++;
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


	public static List<City >getListCity() {
		return list;
	}
	public static boolean AvalaibleBLocks(int i,Disease d){
		if(reserve.get(d)-i<0) {
			return false;
		}
		return true;
	}

	public List<PlayerCardInterface> Shuffle(List<PlayerCardInterface>lc){
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
		ArrayList<PlayerCardInterface>t1=new ArrayList<>();
		ArrayList<PlayerCardInterface>t2=new ArrayList<>();
		ArrayList<PlayerCardInterface>t3=new ArrayList<>();
		ArrayList<PlayerCardInterface>t4=new ArrayList<>();
		ArrayList<PlayerCardInterface>t5=new ArrayList<>();
		ArrayList<PlayerCardInterface>t6=new ArrayList<>();
		if(j==4){
			while(!lc.isEmpty()){
				if(!lc.isEmpty()){
					t1.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t2.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t3.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t4.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
			}

			t1.add(new EpidemicCard());Collections.shuffle(t1);
			t2.add(new EpidemicCard());Collections.shuffle(t2);
			t3.add(new EpidemicCard());Collections.shuffle(t3);
			t4.add(new EpidemicCard());Collections.shuffle(t4);
			lc.addAll(t1);
			lc.addAll(t2);
			lc.addAll(t3);
			lc.addAll(t4);
		}

		if(j==5){
			while(!lc.isEmpty()){
				if(!lc.isEmpty()){
					t1.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t2.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t3.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t4.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t5.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
			}
			t1.add(new EpidemicCard());Collections.shuffle(t1);
			t2.add(new EpidemicCard());Collections.shuffle(t2);
			t3.add(new EpidemicCard());Collections.shuffle(t3);
			t4.add(new EpidemicCard());Collections.shuffle(t4);
			t5.add(new EpidemicCard());Collections.shuffle(t5);
			lc.addAll(t1);
			lc.addAll(t2);
			lc.addAll(t3);
			lc.addAll(t4);
			lc.addAll(t5);
		}
		if(j==6){
			while(!lc.isEmpty()){
				if(!lc.isEmpty()){
					t1.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t2.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t3.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t4.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t5.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
				if(!lc.isEmpty()){
					t6.add(lc.get(lc.size()-1));
					lc.remove(lc.size()-1);
				}
			}
			t1.add(new EpidemicCard());Collections.shuffle(t1);
			t2.add(new EpidemicCard());Collections.shuffle(t2);
			t3.add(new EpidemicCard());Collections.shuffle(t3);
			t4.add(new EpidemicCard());Collections.shuffle(t4);
			t5.add(new EpidemicCard());Collections.shuffle(t5);
			t6.add(new EpidemicCard());Collections.shuffle(t6);
			lc.addAll(t1);
			lc.addAll(t2);
			lc.addAll(t3);
			lc.addAll(t4);
			lc.addAll(t5);
			lc.addAll(t6);
		}
		System.out.println("taille de lc "+lc.size());
		for(PlayerCardInterface pci : lc){
			System.out.println(pci.getCityName());
		}
		System.out.println("premiere carte est "+lc.get(0).getCityName());
		System.out.println("derniere carte est "+lc.get(lc.size()-1).getCityName());

		return lc;
	}
	/*
		for(int i=0;i<(lc.size())/4;i++){
			t1.add(lc.get(i));
			lc.remove(i);
		}
		for(int i=(lc.size())/4;i<(lc.size())/2;i++){
			t2.add(lc.get(i));
			lc.remove(i);
		}
		for(int i=(lc.size())/2;i<(lc.size()-(lc.size())/4);i++){
			t3.add(lc.get(i));
			lc.remove(i);
		}
		for(int i=(lc.size()-(lc.size())/4);i<lc.size();i++){
			t4.add(lc.get(i));
			lc.remove(i);
		}


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

		t1.add(new EpidemicCard());Collections.shuffle(t1);
		t2.add(new EpidemicCard());Collections.shuffle(t2);
		t3.add(new EpidemicCard());Collections.shuffle(t3);
		t4.add(new EpidemicCard());Collections.shuffle(t4);

		j-=4;
		System.out.println("j est egal a "+j);

		if(j==1){
			t1.add(new EpidemicCard());
		}

		if (j==2){
			t1.add(new EpidemicCard());
			t2.add(new EpidemicCard());
		}

		System.out.println(lc.size());

		lc.addAll(t1);
		lc.addAll(t2);
		lc.addAll(t3);
		lc.addAll(t4);

		return lc;
	}
	 */
	public void Initialisation(List<PlayerCardInterface>listcard,Player p,PropagationDeck pdeck,PropagationDeck propdefauss){
		//System.out.println("Loading AI Jar file " + aiJar);		
		//AiInterface ai = AiLoader.loadAi(aiJar);	
		//City c=this.getCity("Atlanta");
		//Player p=new Player(c,list);
		//p=new Player(c,list);
		//PropagationDeck pdeck=new PropagationDeck();
		//PropagationDeck propdefauss =new PropagationDeck();
		//pdeck=new PropagationDeck();
		//propdefauss =new PropagationDeck();
		// Create the player Card
		//List<PlayerCardInterface> listcard=new LinkedList<PlayerCardInterface>();
		//listcard=new LinkedList<PlayerCardInterface>();
		int cpt;
		for(int i=0;i<48;i++) {
			if(list.get(i).getName().equals("Delhi")) {
				System.out.println("j'ai trouvé la carte");
				cpt=i;
			}
			listcard.add(new CitiesCard(list.get(i)));
			pdeck.getPropagationdeck().add(new PropagationCard(list.get(i)));
		}

		Shuffle(listcard);
		System.out.println("taille de la pdeck "+pdeck.getPropagationdeck().size());
		System.out.println("taille de la propdefauss "+propdefauss.getPropagationdeck().size());
		Collections.shuffle(pdeck.getPropagationdeck());
		System.out.println("shuffle ok");

		int j=5;
		int compteurEpidemic=0;

		while(j>0){
			PlayerCardInterface card=listcard.get(listcard.size()-1);
			listcard.remove(listcard.size()-1);
			p.addToPlayerHand(card);
			System.out.println("playerhand ok");
			System.out.println("taille de la listcard "+listcard.size());

			if(((PlayerCard)card).isEpidemic()){
				compteurEpidemic++;
				((EpidemicCard)card).Acceleration();
				System.out.println("acceleration ok");
				((EpidemicCard)card).Infection(pdeck,propdefauss);
				System.out.println("infection ok");
				((EpidemicCard)card).Intensification(pdeck, propdefauss);
				System.out.println("intensification ok");
				PlayerCard.addToDefauss(card);
				p.getListCardHand().remove(card);
				System.out.println("je pose la carte épidemie dans la defausse, ainsi j'ai un nombre de cartes dans ma main égal  à "+p.playerHand().size());
			}
			j--;
			//PlayerCard.addToDefauss(card);
			//p.getListCardHand().remove(card);
			//System.out.println("je pose la carte épidemie dans la defausse, ainsi j'ai un nombre de cartes dans ma main égal  à "+p.playerHand().size());
		}
		//p.setListCardHand(p.getListCardHand().remove(card));
		int tour1=3;
		int tour2=3;
		int tour3=3;
		while(tour1>0){
			PropagationCard pc=pdeck.getLastPropagationcard();
			if(AvalaibleBLocks(3,pc.getDisease())){
				if(pc.getCity().getNbCubes(pc.getDisease())>0){
					//city.setEclosion(true, d);
					//GameEngine.Outbreaks(city,d);
					pc.getCity().setEclosion(true, pc.getDisease());
					Outbreaks(pc.getCity(),pc.getDisease());
				}
				else{
					GiveMeBlockFromReserve(pc.getDisease(),3);
					pc.getCity().setNbCubes(pc.getCity().getNbCubes(pc.getDisease())+3,pc.getDisease());
					propdefauss.getPropagationdeck().add(pc);
					tour1--;
				}
			}
			else{
				setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
			}
		}

		while(tour2>0){
			PropagationCard pc=pdeck.getLastPropagationcard();
			if(AvalaibleBLocks(2,pc.getDisease())){
				if(pc.getCity().getNbCubes(pc.getDisease())>1){
					pc.getCity().setEclosion(true, pc.getDisease());
					Outbreaks(pc.getCity(),pc.getDisease());
				}
				else{
					GiveMeBlockFromReserve(pc.getDisease(),2);
					pc.getCity().setNbCubes(pc.getCity().getNbCubes(pc.getDisease())+2,pc.getDisease());
					propdefauss.getPropagationdeck().add(pc);
					tour2--;
				}
			}
			else{
				setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
			}
		}
		while(tour3>0){
			PropagationCard pc=pdeck.getLastPropagationcard();
			if(AvalaibleBLocks(1,pc.getDisease())){
				if(pc.getCity().getNbCubes(pc.getDisease())>2){
					pc.getCity().setEclosion(true, pc.getDisease());
					Outbreaks(pc.getCity(),pc.getDisease());
				}
				else{
					GiveMeBlockFromReserve(pc.getDisease(),1);
					pc.getCity().setNbCubes(pc.getCity().getNbCubes(pc.getDisease())+1,pc.getDisease());
					propdefauss.getPropagationdeck().add(pc);
					tour3--;
				}
			}
			else{
				setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
			}
		}

		System.out.println("taille de mon deck de cartes propagation "+pdeck.getPropagationdeck().size());
		System.out.println("taille de la main du joueur "+p.playerHand().size());
		System.out.println("taille de la listcard est "+listcard.size());
		System.out.println("taille de la propdefauss est "+ propdefauss.getPropagationdeck().size());

		for(City c : list ){
			for(Disease d: Disease.values()){
				System.out.println(c.getName()+" - "+c.getNbCubes(d)+" "+d);
			}
		}

		System.out.println("------------------------------------");
		System.out.println("FIN INITIALISATION");
		System.out.println("-------------------------------------");
	}


	public static int getPosX() {
		return p.getCurrentCity().getPosX();
	}
	public static int getPosY() {
		return p.getCurrentCity().getPosY();
	}
	public static Player getPlayer() {
		return p;
	}
	public void Tour(Player p,List<PlayerCardInterface> listcard2,PropagationDeck pdeck,PropagationDeck propdefauss) throws InterruptedException{
		JFrame fenetre= new Fenetre();
		while(gameStatus == GameStatus.ONGOING) {
			
			fenetre.repaint();
			//fenetre.setLocation(200, 400);
			//fenetre.setResizable(false);
			//fenetre.setSize(600, 100);
			//fenetre.setAlwaysOnTop(false);
			//fenetre.setLocation(1000, 400);
			System.out.println("undercorated");
			Thread.sleep(1000);
			if(listcard2.isEmpty()){
				setDefeated("No more PlayerCards available",DefeatReason.NO_MORE_PLAYER_CARDS);
			}

			for(Disease d :Disease.values()){
				if(reserve.get(d)<0){
					setDefeated("Plus de c"+ "ubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
				}
			}
			/*
			System.out.println("Loading AI Jar file " + aiJar);		
			//AiInterface ai = AiLoader.loadAi(aiJar);	
			City c=this.getCity("Atlanta");
			Player p=new Player(c,list);
			PropagationDeck pdeck=new PropagationDeck();
			PropagationDeck propdefauss =new PropagationDeck();
			// Create the player Card
			List<PlayerCardInterface> listcard=new LinkedList<PlayerCardInterface>();
			int cpt=0;
			for(int i=0;i<48;i++) {
				if(list.get(i).getName().equals("Delhi")) {
					System.out.println("j'ai trouvé la carte");
					cpt=i;
				}
				listcard.add(new CitiesCard(list.get(i)));
				PropagationDeck.getPropagationdeck().add(new PropagationCard(list.get(i)));
			}

			Shuffle(listcard);




			if(tour>3){
			 */
			p.setAction(4);
			p.setSwitchturn(false);
			for(Disease d : Disease.values()){
				for(City cc : list){
					cc.setEclosion(false, d);
				}
			}
			//System.out.println(p.playerHand().size());
			int j=2;
			int compteurEpidemic=0;
			playTurn(this,p);
			while(j>0){
				if(p.playerHand().size()>9){
					List<PlayerCardInterface> discardliste=discard(this, p, 9,compteurEpidemic );
				}
				else{
					
					if(listcard2.size()!=0) {
						PlayerCardInterface card; card=listcard2.remove(listcard2.size()-1);
						p.addToPlayerHand(card);
						if(((PlayerCard)card).isEpidemic()){
							compteurEpidemic++;
							((EpidemicCard)card).Acceleration();
							((EpidemicCard)card).Infection(pdeck,propdefauss);
							if(cptOutbreaks==8){
								setDefeated("number of Outbreaks too high",DefeatReason.TOO_MANY_OUTBREAKS);
							}
							((EpidemicCard)card).Intensification(pdeck, propdefauss);
							PlayerCard.addToDefauss(card);
							p.getListCardHand().remove(card);
							System.out.println("je pose la carte épidemie dans la defausse, ainsi j'ai un nombre de cartes dans ma main égal  à "+p.playerHand().size());
						}

					}
					else 
						setDefeated("Plus de carte joueur",DefeatReason.NO_MORE_PLAYER_CARDS);
				}
				j--;
			}
		
		

		//System.out.println(p.playerHand().size());
		for(PlayerCardInterface c2:p.playerHand()) {
			System.out.println(c2.getCityName()+" - "+c2.getDisease());
		}

		int cptyellow=0;
		int cptred=0;
		int cptblack=0;
		int cptblue=0;
		for(PlayerCardInterface pci : p.playerHand()){
			if(pci.getDisease().equals(Disease.BLACK)){
				cptblack++;
			}
			if(pci.getDisease().equals(Disease.BLUE)){
				cptblue++;
			}
			if(pci.getDisease().equals(Disease.YELLOW)){
				cptyellow++;
			}
			if(pci.getDisease().equals(Disease.RED)){
				cptred++;
			}
		}


		if(cptred==5){
			int n=p.playerHand().size();
			for(int i=0;i<p.playerHand().size();i++) {
			//for(PlayerCardInterface pci : p.playerHand()){
				if(p.playerHand().get(i).getDisease().equals(Disease.RED)){
					PlayerCard.addToDefauss(p.playerHand().get(i));
					p.playerHand().remove(p.playerHand().get(i));
					}
			}
			System.out.println("j'ai trouvé le remede "+Disease.RED);
			
			remedes.replace(Disease.RED, true);
		}
		if(cptblack==5){
			int n=p.playerHand().size();
			for(int i=0;i<p.playerHand().size();i++) {
			//for(PlayerCardInterface pci : p.playerHand()){
				if(p.playerHand().get(i).getDisease().equals(Disease.BLACK)){
					PlayerCard.addToDefauss(p.playerHand().get(i));
					p.playerHand().remove(p.playerHand().get(i));

				}
			}
			System.out.println("j'ai trouvé le remede "+Disease.BLACK);
			remedes.replace(Disease.BLACK, true);
		}
		if(cptyellow==5){
			int n=p.playerHand().size();
			for(int i=0;i<p.playerHand().size();i++) {
			//for(PlayerCardInterface pci : p.playerHand()){
				if(p.playerHand().get(i).getDisease().equals(Disease.YELLOW)){
					PlayerCard.addToDefauss(p.playerHand().get(i));
					p.playerHand().remove(p.playerHand().get(i));
				}
			}
			System.out.println("j'ai trouvé le remede "+Disease.YELLOW);
			
			remedes.replace(Disease.YELLOW, true);
		}
		if(cptblue==5){
			int n=p.playerHand().size();
			for(int i=0;i<p.playerHand().size();i++) {
			//for(PlayerCardInterface pci : p.playerHand()){
				if(p.playerHand().get(i).getDisease().equals(Disease.BLUE)){
					PlayerCard.addToDefauss(p.playerHand().get(i));
					p.playerHand().remove(p.playerHand().get(i));
				}
			}
			System.out.println("j'ai trouvé le remede "+Disease.BLUE);
			
			remedes.replace(Disease.BLUE, true);
		}
		if(remedes.get(Disease.BLACK) & remedes.get(Disease.BLUE) & remedes.get(Disease.RED) & remedes.get(Disease.YELLOW)){
			setVictorious();
		}

		while (vit_prop>0){
			PropagationCard pc=pdeck.getLastPropagationcard();
			if(AvalaibleBLocks(1,pc.getDisease())){
				pc.Propagation();
				if(cptOutbreaks==8){
					setDefeated("number of Outbreaks too high",DefeatReason.TOO_MANY_OUTBREAKS);
				}
				vit_prop--;
			}
			else{
				setDefeated("No More Avalaible Blocks ",DefeatReason.NO_MORE_BLOCKS);
			}
		}
		System.out.println("\n\n je vais faire play \n\n");
		//playTurn(this,p);
		
	}
	}


	/****************************************************************************************************************************************************************/
	/*****************************************************************************************************************************************************************/

	/*****************************************************************************************************************************************************************/

	/*****************************************************************************************************************************************************************/
	/*****************************************************************************************************************************************************************/

	/**
	 * @throws InterruptedException ***************************************************************************************************************************************************************/
	public void playTurn(GameInterface g, PlayerInterface p) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("je vais jouer une action");
		int actionleft=4;
		
		double scoreNeighbours=0.0; // Score of the action moveTo
		double scoreCard=0.0; // Score of actions Flyto and FlytoCharter 
		int indice;
		
		while(actionleft>0) {//((Player)p).getActionLeft()>0) {
			System.out.println("je vais scorer ma location");
			City me=((Player)p).getCurrentCity();
			if(me.setCubesChoiceDiseaseToCure()) {
				System.out.println("j'ai traité ma ville courante "+me.getName());
				actionleft--;
			}
			else {
				scoreNeighbours=GameEngine.scoreOfMyLocation((Player)p);
				System.out.println("score de mes voisin est a "+scoreNeighbours);
				System.out.println("Mon jeu de carte en mains est à \n");
				for(PlayerCardInterface card: p.playerHand())
					System.out.println("La carte "+card.getCityName());
				ArrayList<Double> tri=new ArrayList<Double>();
				for(PlayerCardInterface c: p.playerHand()) {
					tri.add(((Player)p).scoreOfTheCard(c));
				}
				//System.out.println("j'ai fini le parcours de ma liste");
				if(tri.size()!=0) {
					ArrayList<Double> res=new ArrayList();
					res=max(tri);
					scoreCard=res.get(0);

					System.out.println("score de ma meilleur carte est a "+scoreCard);

					indice =res.get(1).intValue();
					System.out.println("j'ai recuperer l'indice "+indice );
					PlayerCardInterface pci=p.playerHand().get(indice);
					System.out.println("j'ai recuperer la carte de l'indince");
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
				}
				else {
					City city=GameEngine.chooseCityMostInfected(((Player)p).getCurrentCity().getNeighbours());
					City current=((Player)p).getCurrentCity();
					int nbCubeCity=city.getNbCubes(Disease.BLACK)+city.getNbCubes(Disease.RED)+city.getNbCubes(Disease.YELLOW)+city.getNbCubes(Disease.BLUE);
					int nbCubeCurrent=current.getNbCubes(Disease.BLACK)+current.getNbCubes(Disease.RED)+current.getNbCubes(Disease.YELLOW)+current.getNbCubes(Disease.BLUE);

					if(nbCubeCity>nbCubeCurrent){
						try {
							//System.out.println("JE fais effectué une action,  MoveTo à la ville "+city.getName()+" et ma current est à "+current.getName());
							System.out.println("JE fais effectué une action,  MoveTo à la ville "+city.getName()+" cette ville à un nbcube a "+city.getNbCubes(city.getDisease())+" \net ma current a "+current.getNbCubes(current.getDisease()));
							
							p.moveTo(city.getName());
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
			//Thread.sleep(1000);
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
		ArrayList<Double> res=new ArrayList();
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
		ArrayList<Double> res=new ArrayList();
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


	/*****************************************************************************************************************************************************************/
	/*****************************************************************************************************************************************************************/
	/*****************************************************************************************************************************************************************/

	/**
	 * @throws InterruptedException ***************************************************************************************************************************************************************/
	public void loop() throws UnauthorizedActionException, InterruptedException  {
		// Load Ai from Jar file

		System.out.println("Loading AI Jar file " + aiJar);		
		//AiInterface ai = AiLoader.loadAi("C:/Users/derra/OneDrive/Bureau/aijar.jar");
		System.out.println("gotta playturn");
		Tour(p, listcard, pdeck, propdefauss);
		/**
		// Load Ai from Jar file
		System.out.println("Loading AI Jar file " + aiJar);		
		//AiInterface ai = AiLoader.loadAi(aiJar);	
		City c=this.getCity("Atlanta");
		Player p=new Player(c,list);
		System.out.println("Je suis dans "+p.getCurrentCity().getName());
		p.moveTo(p.getCurrentCity().getNeighbours().get(0).getName());
		System.out.println("Je suis dans "+p.getCurrentCity().getName());
		System.out.println(p.playerHand());
		PropagationDeck pdeck=new PropagationDeck();
		// Create the player Card
		List<PlayerCardInterface> listcard=new ArrayList<PlayerCardInterface>();

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
	PropagationDeck pdeck=new PropagationDeck();
	// Create the player Card
	List<PlayerCardInterface> listcard=new ArrayList<PlayerCardInterface>();

	int cpt=0;
	for(int i=0;i<48;i++) {
		if(list.get(i).getName().equals("Delhi")) {
			System.out.println("j'ai trouvé la carte");
			cpt=i;
		}
		listcard.add(new CitiesCard(list.get(i)));
		pdeck.getPropagationdeck().add(new PropagationCard(list.get(i)));
	}




	System.out.println("taille ma pile de cartejoueurs : "+listcard.size());
	Shuffle(listcard);

	System.out.println(" Mon deck nouvelle taille "+listcard.size());


	System.out.println("je suis la");

	p.addToPlayerHand(listcard.get(4));
	System.out.println(p.playerHand().size());
	System.out.println("je suis ici");

	PlayerCardInterface c1=p.playerHand().get(0);
	//System.out.println(p.playerHand());
	//System.out.println(p.SeeCards());
	for(PlayerCardInterface c2:p.playerHand()) {
		System.out.println(c2.getCityName()+" - "+c2.getDisease());
	}
	AiMethods imed = new AiMethods(list.get(5),list);
	System.out.println(imed.getCurrentCity().getName());
	imed.dijkstra(list.get(5));
	//p.SeeCards();
	//PlayerCardInterface c2=p.playerHand().get(1);

	// Very basic game loop
	while(gameStatus == GameStatus.ONGOING) {

		for(Disease d :Disease.values()){
			if(reserve.get(d)<0){
				setDefeated("Plus de cubes disponibles.",DefeatReason.NO_MORE_BLOCKS);
			}
		}
		/*
			if(Math.random() < 0.5)		
				setDefeated("Game not implemented.", DefeatReason.UNKN);
			else
				setVictorious();			
		 */

	}		



	//}	
	//}						




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
		return this.turnduration;
		//throw new UnsupportedOperationException(); 
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
		return p.playerHand().size();
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

	
	public static double scoreOfMyLocation(Player p) {
		//System.out.println("je suis dans scoreofmylocation");
		City c=p.getCurrentCity();
		int[] tab=new int[2];
		double resultat=0.0;
		int n=c.getNeighbours().size();
		City tmp;
		System.out.println("le nombre de mes voisins est a"+n);
		for(int i=0;i<n;i++) {
			tmp=c.getNeighbours().get(i);
//		for(City parcours: c.getNeighbours()) {
			for(int j=0;j<list.size();j++) {
				if(c.getNeighbours().get(i).getName()==list.get(j).getName())
					tmp=list.get(j);
			}
			
			System.out.println("liste de voisin de mes voisins est "+tmp.getNeighbours()+" son nom est à "+tmp.getName());
			tab=scoreOfEachRegion(tmp.getDisease(),tmp.getNeighbours());
			if(tab[0]!=0)
				resultat+=(double)tab[1];///(double)tab[0];
			System.out.println("le resultat de mylocation est pour l'instant a "+resultat);
		}
		tab=scoreOfEachRegion(c.getDisease(),c.getNeighbours());
		if(tab[0]!=0)
			resultat+=(double)tab[1]/(double)tab[0];
		//System.out.println("j'ai recuperer la city c à "+c+" et j'appelle score of eachregion et c.getdisease est a "+c.getDisease());
		System.out.println("je vais renvoyer resultat = "+resultat+" pour ma location");
		return resultat;
	}

	public static List<City> getListCityWithDisease(Disease d){
		List<City> res =new ArrayList<City>();
		for(City c: list) {
			if(c.getDisease()==d)
				res.add(c);
		}
		return res;
	}

	public static City chooseCityMostInfected(List <City> liste) {
		for(City c:liste) {
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=7) {
				return c;
			}
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=6) {
				return c;
			}
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=5) {
				return c;
			}
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=4) {
				return c;
			}
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=3) {
				return c;
			}
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=2) {
				return c;
			}
			if(c.getNbCubes(Disease.BLACK)+c.getNbCubes(Disease.BLUE)+c.getNbCubes(Disease.RED)+c.getNbCubes(Disease.YELLOW)>=1) {
				return c;
			}
		}
		return liste.get((int)(Math.random()*liste.size()));

	}

	/**Return the number of eclosion for the disease d in the same turn. Take the list of the region**/
	public static int[] scoreOfEachRegion(Disease d,List<City> liste) {
		//System.out.println("je suis dans score of eachregion");
		int cpt=0;
		int nbcubes=0;
		int[] tab=new int[2];
		System.out.println("je vais rentrer dans le for");
		for(City c:liste) {
			System.out.println("je suis rentré dans le for");
			//System.out.println(liste);
			//System.out.println("je parcours la liste pour scoreofeachregion et c est à "+c);
			if(c.getNbCubes(d)>0) {
				if(c.isEclosion(d)) 
					nbcubes+=6;

				if(c.getNbCubes(d)==3) 
					nbcubes+=6;

				if(c.getNbCubes(d)==2) 
					nbcubes+=4;

				if(c.getNbCubes(d)==1) 
					nbcubes+=2;
				System.out.println(" Le score est pour l'instant de "+nbcubes+" pour cpt a "+cpt);
				cpt++;
			}
		}
		tab[0]=cpt;
		tab[1]=nbcubes;
		System.out.println("j'ai fini ce traitement");
		return tab;
	}





	public static void main(String [] args) throws IOException, UnauthorizedActionException, InterruptedException   {


		// Faire rentrer en paramètre le nom du graph, le jar et le niveau de difficulté

		String aijar = DEFAULT_AIJAR; 
		String cityGraphFile = DEFAULT_CITYGRAPH_FILE; 
		int difficulty = DEFAULT_DIFFICULTY; 
		int turnDuration = DEFAULT_TURN_DURATION;
		int handSize = DEFAULT_HAND_SIZE;

		Options options = new Options();
		CommandLineParser parser = new DefaultParser();

		options.addOption("a", "aijar", true, "use <FILE> as player Ai.");
		options.addOption("d", "difficulty", true, "Difficulty level. 0 (Introduction), 1 (Normal) or 3 (Heroic).");
		options.addOption("c", "citygraph", true, "City graph filename.");
		options.addOption("t", "turnduration", true, "Number of seconds allowed to play a turn.");
		options.addOption("s", "handsize", true, "Maximum size of a player hand.");
		options.addOption("h", "help", false, "Display this help");

		try {
			CommandLine cmd = parser.parse( options, args);

			if(cmd.hasOption("a")) {
				aijar = cmd.getOptionValue("a");				
			}

			if(cmd.hasOption("g")) {
				cityGraphFile = cmd.getOptionValue("c");
			}

			if(cmd.hasOption("d")) {
				difficulty = Integer.parseInt(cmd.getOptionValue("d"));
			}

			if(cmd.hasOption("t")) {
				turnDuration = Integer.parseInt(cmd.getOptionValue("t"));
			}
			if(cmd.hasOption("s")) {
				handSize = Integer.parseInt(cmd.getOptionValue("s"));
			}

			/* ... */ 

			if(cmd.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "pandemiage", options );
				System.exit(0);
			}			

		} catch (ParseException e) {
			System.err.println("Error: invalid command line format.");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "pandemiage", options );
			System.exit(1);
		}
		System.out.println("aijar : "+aijar+"cityGraphFile : "+ cityGraphFile +"difficulty : "+ difficulty + "turnDuration : " + turnDuration + "handSize  :"+handSize   );

		GameEngine g=new GameEngine(cityGraphFile, aijar);

		g.Initialisation(g.listcard, g.p, g.pdeck, g.propdefauss);
		//g.Tour(ai, g.p, g.listcard, g.pdeck, g.propdefauss);
		/*LinkedList<PlayerCardInterface> listcard=new LinkedList<PlayerCardInterface>();
		Player p=new Player();
		PropagationDeck pdeck=null;
		PropagationDeck propdefauss=null;
		 */


		//g.Initialisation(g.listcard, g.p, g.pdeck, g.propdefauss);

		//g.Initialisation(g.listcard, g.p, g.pdeck, g.propdefauss);
		System.out.println("je suis a "+g.p.getCurrentCity().getName()+" au coordonnée "+g.p.getCurrentCity().getX()+" : "+g.p.getCurrentCity().getY());

		//JFrame fenetre= new Fenetre();
		//fenetre.setLocation(200, 400);
		//fenetre.setResizable(false);
		//fenetre.setSize(600, 100);
		//fenetre.setAlwaysOnTop(false);
		//fenetre.setLocation(1000, 400);
		System.out.println("undercorated");
		//fenetre.setUndecorated(true);
		g.loop();
		/*
		for(int i = 0; i < liste.size(); i++) {
			System.out.println("City Name : " +liste.get(i).getName());
			System.out.println("City Degree : " +liste.get(i).getDegree());
			System.out.println("City Neighbours :			 " +liste.get(i).getNeighbours_s());
		}*/


	}

}



