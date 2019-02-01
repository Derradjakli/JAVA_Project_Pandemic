package fr.dauphine.ja.student.pandemiage.ui;

import java.io.IOException;

import javax.swing.JFrame;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import fr.dauphine.ja.pandemiage.common.UnauthorizedActionException;
import fr.dauphine.ja.student.pandemiage.gameengine.GameEngine;

public class Cli {	    
	public static final String DEFAULT_AIJAR = "./target/pandemiage-1.0-SNAPSHOT-ai.jar"; 
	public static final String DEFAULT_CITYGRAPH_FILE = "./pandemic.graphml";
	public static final int DEFAULT_TURN_DURATION = 1;	//in seconds
	public static final int DEFAULT_DIFFICULTY = 0; // Normal
	public static final int DEFAULT_HAND_SIZE = 9;
	 
	
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

		g.Initialisation(g.getListcard(), g.getP(), g.pdeck, g.getPropdefauss());
		//g.Tour(ai, g.p, g.listcard, g.pdeck, g.propdefauss);
		/*LinkedList<PlayerCardInterface> listcard=new LinkedList<PlayerCardInterface>();
		Player p=new Player();
		PropagationDeck pdeck=null;
		PropagationDeck propdefauss=null;
		 */


		//g.Initialisation(g.listcard, g.p, g.pdeck, g.propdefauss);

		//g.Initialisation(g.listcard, g.p, g.pdeck, g.propdefauss);
		System.out.println("je suis a "+g.getP().getCurrentCity().getName()+" au coordonnée "+g.getP().getCurrentCity().getX()+" : "+g.getP().getCurrentCity().getY());

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
