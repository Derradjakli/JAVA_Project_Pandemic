package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dauphine.ja.pandemiage.common.Disease;

public class City {
	private static int[][] tableauPositionGraphic=new int[49][3]; // Stocks the position of each city in the map for the UI  
	
	private int id;
	private String CityName;
	private int r;// The Red color of RGB
	private int g;// The GREEN color of RGB
	private int b;// The BLUE color of RGB
	private double weight;
	private float x;
	private float y;
	private int posX;
	private int posY;
	private float size;
	private double eigenvector_Centrality;
	private String edge_ID;
	private String edge_Label;
	private int degree;  //Neighbours number
	private boolean isOutBreaks=false;
	private boolean tabIsinitialized=false; // Say if the tableauPositionGraphic is initialized

	private   Map<Disease,Integer> m=new HashMap<Disease,Integer>(); // Permet de recuperer le nombre de cube sur chaque ville et sr chaque malade
	private Map<Disease,Boolean> m_ec=new HashMap<Disease,Boolean>();// Permet de recuperer quelle maladie a eclos sur une ville

	private Disease disease;
	private static Map<Disease,Boolean> m_cure=new HashMap<Disease,Boolean>(); // Permet de savoir pour chaque maladie si l'entidote a été decouvert
	private List<City> list; // list of neighbours
	

	public City(int id, String CityName, int r	,int g, int b, double weight, float x, float y, float size,double eigenvector_Centrality	,String edge_ID,String edge_Label	, int degree , List<City> list) {
		
		if(this.id <40)
			this.id = id;
		else
			this.id = id-3;
		this.CityName=CityName;
		this.r=r;// The Red color of RGB
		this.g=g;// The GREEN color of RGB
		this.b=b;// The BLUE color of RGB
		this.weight=weight;
		this.x=x;
		this.y=y;
		this.size=size;
		this.eigenvector_Centrality=eigenvector_Centrality;
		this.edge_ID=edge_ID;
		this.edge_Label=edge_Label;
		this.degree=degree; // Neighbours number
		this.list=list;
		if(r==183 && g==18 && b==21){// TODO Auto-generated method stub
			disease=Disease.RED;
		}else if (r==107 && g==112 && b==184){
			disease=Disease.BLUE;
		}
		else if (r==153 && g==153 & b==153){
			disease=Disease.BLACK;		}
		else{
			disease=Disease.YELLOW;		}
		for(Disease d: Disease.values()) {
			this.m.put(d,new Integer(0));
			this.m_ec.put(d, false);
			m_cure.put(d, false);
			//System.out.println(CityName+" - "+m.get(d));
		}
		if(!tabIsinitialized) {
			initializeArrayOfPostion();
		}
		System.out.println("Mon ID est a "+id);
		if(id<49) {
		posX=tableauPositionGraphic[id][1];
		posY=tableauPositionGraphic[id][2];
		}
		else {
			posX=0;
			posY=0;
		}
		
	//	System.out.println("\n\n**********************************************ma liste d'eclosion est à "+m_ec+"******************\n\n");
		
		
	}

	public String getName() {
		return CityName;
	}
	public boolean isOutBreaks() {
		return this.isOutBreaks;
	}
	public double getWeight() {
		return this.weight;
	}
	public float getX() {
		return this.x;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public void initializeArrayOfPostion() {
		
		tableauPositionGraphic[1][0]=1;
		tableauPositionGraphic[1][1]=40;
		tableauPositionGraphic[1][2]=220;
		
		tableauPositionGraphic[2][0]=2;
		tableauPositionGraphic[2][1]=145;
		tableauPositionGraphic[2][2]=190;
		
		tableauPositionGraphic[3][0]=3;
		tableauPositionGraphic[3][1]=230;
		tableauPositionGraphic[3][2]=190;
		
		tableauPositionGraphic[4][0]=4;
		tableauPositionGraphic[4][1]=292;
		tableauPositionGraphic[4][2]=200;
		
		tableauPositionGraphic[5][0]=5;
		tableauPositionGraphic[5][1]=265;
		tableauPositionGraphic[5][2]=245;
		
		tableauPositionGraphic[6][0]=6;
		tableauPositionGraphic[6][1]=176;
		tableauPositionGraphic[6][2]=250;
		
		tableauPositionGraphic[7][0]=7;
		tableauPositionGraphic[7][1]=433;
		tableauPositionGraphic[7][2]=230;
		
		tableauPositionGraphic[8][0]=8;
		tableauPositionGraphic[8][1]=445;
		tableauPositionGraphic[8][2]=150;
		
		tableauPositionGraphic[9][0]=9;
		tableauPositionGraphic[9][1]=507;
		tableauPositionGraphic[9][2]=192;
		
		tableauPositionGraphic[10][0]=10;
		tableauPositionGraphic[10][1]=530;
		tableauPositionGraphic[10][2]=137;
		
		tableauPositionGraphic[11][0]=11;
		tableauPositionGraphic[11][1]=561;
		tableauPositionGraphic[11][2]=175;
		
		tableauPositionGraphic[12][0]=12;
		tableauPositionGraphic[12][1]=620;
		tableauPositionGraphic[12][2]=123;
		
		tableauPositionGraphic[13][0]=13;
		tableauPositionGraphic[13][1]=526;
		tableauPositionGraphic[13][2]=282;
		
		tableauPositionGraphic[14][0]=14;
		tableauPositionGraphic[14][1]=598;
		tableauPositionGraphic[14][2]=230;
		
		tableauPositionGraphic[15][0]=15;
		tableauPositionGraphic[15][1]=665;
		tableauPositionGraphic[15][2]=190;
		
		tableauPositionGraphic[16][0]=16;
		tableauPositionGraphic[16][1]=589;
		tableauPositionGraphic[16][2]=302;
		
		tableauPositionGraphic[17][0]=17;
		tableauPositionGraphic[17][1]=658;
		tableauPositionGraphic[17][2]=270;
		
		tableauPositionGraphic[18][0]=18;
		tableauPositionGraphic[18][1]=720;
		tableauPositionGraphic[18][2]=220;
		
		tableauPositionGraphic[19][0]=19;
		tableauPositionGraphic[19][1]=798;
		tableauPositionGraphic[19][2]=277;
		
		tableauPositionGraphic[20][0]=20;
		tableauPositionGraphic[20][1]=735;
		tableauPositionGraphic[20][2]=300;
		
		tableauPositionGraphic[21][0]=21;
		tableauPositionGraphic[21][1]=667;
		tableauPositionGraphic[21][2]=348;
		
		tableauPositionGraphic[22][0]=22;
		tableauPositionGraphic[22][1]=745;
		tableauPositionGraphic[22][2]=362;
		
		tableauPositionGraphic[23][0]=23;
		tableauPositionGraphic[23][1]=810;
		tableauPositionGraphic[23][2]=407;
		
		tableauPositionGraphic[24][0]=24;
		tableauPositionGraphic[24][1]=855;
		tableauPositionGraphic[24][2]=300;
		
		tableauPositionGraphic[25][0]=25;
		tableauPositionGraphic[25][1]=902;
		tableauPositionGraphic[25][2]=195;
		
		tableauPositionGraphic[26][0]=26;
		tableauPositionGraphic[26][1]=975;
		tableauPositionGraphic[26][2]=192;
		
		tableauPositionGraphic[27][0]=27;
		tableauPositionGraphic[27][1]=1034;
		tableauPositionGraphic[27][2]=225;
		
		tableauPositionGraphic[28][0]=28;
		tableauPositionGraphic[28][1]=907;
		tableauPositionGraphic[28][2]=255;
		
		tableauPositionGraphic[29][0]=29;
		tableauPositionGraphic[29][1]=913;
		tableauPositionGraphic[29][2]=319;
		
		tableauPositionGraphic[30][0]=30;
		tableauPositionGraphic[30][1]=982;
		tableauPositionGraphic[30][2]=310;
		
		tableauPositionGraphic[31][0]=31;
		tableauPositionGraphic[31][1]=1040;
		tableauPositionGraphic[31][2]=285;
		
		tableauPositionGraphic[32][0]=32;
		tableauPositionGraphic[32][1]=866;
		tableauPositionGraphic[32][2]=355;
		
		tableauPositionGraphic[33][0]=33;
		tableauPositionGraphic[33][1]=917;
		tableauPositionGraphic[33][2]=410;
		
		tableauPositionGraphic[34][0]=34;
		tableauPositionGraphic[34][1]=999;
		tableauPositionGraphic[34][2]=407;
		
		tableauPositionGraphic[35][0]=35;
		tableauPositionGraphic[35][1]=866;
		tableauPositionGraphic[35][2]=459;
		
		tableauPositionGraphic[36][0]=36;
		tableauPositionGraphic[36][1]=1047;
		tableauPositionGraphic[36][2]=570;
		
		tableauPositionGraphic[37][0]=37;
		tableauPositionGraphic[37][1]=604;
		tableauPositionGraphic[37][2]=385;
		
		tableauPositionGraphic[38][0]=38;
		tableauPositionGraphic[38][1]=599;
		tableauPositionGraphic[38][2]=538;
		
		tableauPositionGraphic[39][0]=39;
		tableauPositionGraphic[39][1]=550;
		tableauPositionGraphic[39][2]=455;
		
		tableauPositionGraphic[40][0]=40;
		tableauPositionGraphic[40][1]=500;
		tableauPositionGraphic[40][2]=395;
		
		tableauPositionGraphic[41][0]=41;
		tableauPositionGraphic[41][1]=340;
		tableauPositionGraphic[41][2]=512;
		
		tableauPositionGraphic[42][0]=42;
		tableauPositionGraphic[42][1]=290;
		tableauPositionGraphic[42][2]=577;
		
		tableauPositionGraphic[43][0]=43;
		tableauPositionGraphic[43][1]=203;
		tableauPositionGraphic[43][2]=587;
		
		tableauPositionGraphic[44][0]=44;
		tableauPositionGraphic[44][1]=194;
		tableauPositionGraphic[44][2]=505;
		
		tableauPositionGraphic[45][0]=45;
		tableauPositionGraphic[45][1]=220;
		tableauPositionGraphic[45][2]=410;
		
		tableauPositionGraphic[46][0]=46;
		tableauPositionGraphic[46][1]=132;
		tableauPositionGraphic[46][2]=345;
		
		tableauPositionGraphic[47][0]=47;
		tableauPositionGraphic[47][1]=57;
		tableauPositionGraphic[47][2]=320;
		
		tableauPositionGraphic[48][0]=48;
		tableauPositionGraphic[48][1]=228;
		tableauPositionGraphic[48][2]=330;
		this.tabIsinitialized=true;
		
	}
	public float getY() {
		return this.y;
	}
	public List<City> getNeighbours(){
		return this.list;
	}

	public boolean setCubesChoiceDiseaseToCure() {// return true if the city was cured, false otherwise
		int diseaseToCure=this.getNbCubes(Disease.BLACK);
		Disease d=Disease.BLACK;
		if(diseaseToCure<this.getNbCubes(Disease.RED)) {
			diseaseToCure=this.getNbCubes(Disease.RED);	
			d=Disease.RED;
		}
		if(diseaseToCure<this.getNbCubes(Disease.YELLOW)) {
			d=Disease.YELLOW;
			diseaseToCure=this.getNbCubes(Disease.YELLOW);

		}
		
		if(diseaseToCure<this.getNbCubes(Disease.BLUE)) {
			d=Disease.BLUE;
			
			diseaseToCure=this.getNbCubes(Disease.BLUE);
		}
		if(diseaseToCure>0) {
			this.setNbCubes(diseaseToCure-1, d);
			return true;
		}
		return false;
	}
	
	public List<String> getNeighbours_s(){
		int n = this.getNeighbours().size();
		ArrayList<String> s = new ArrayList<String>();
		for(int i=0;i<n;i++) {
			s.add(this.getNeighbours().get(i).getName());
		}

		return s;


	}
	public int getId() {
		return this.id;
	}

	public int getR() {
		return r;
	}
	public int getG() {
		return g;
	}
	public int getB() {
		return b;
	}
	public float getSize() {
		return size;
	}
	public double getEin_Cent() {
		return this.eigenvector_Centrality;
	}
	public String getEdg_ID() {
		return this.edge_ID;
	}
	public String getEdge_Label() {
		return this.edge_Label;
	}
	public int getDegree() {
		return this.degree;
	}
	public void setName(String name) {
		this.CityName=name;
	}
	public void setR(int r) {
		this.r=r;
	}
	public void setG(int g) {
		this.g=g;
	}
	public void setB(int b) {
		this.b=b;
	}
	public void setSize(float s) {
		this.size=s;
	}
	public void setEin_Cent(double d) {
		this.eigenvector_Centrality=d;
	}
	public void setEdg_ID(String s) {
		this.edge_ID=s;
	}
	public void setEdge_Label(String s) {
		this.edge_Label=s;
	}
	public void setDegree(int d) {
		this.degree=d;
	}
	

	public Map<Disease, Integer> getM() {
		return m;
	}

	public void setM(Map<Disease, Integer> m) {
		this.m = m;
	}

	public int getNbCubes(Disease d) {
		//System.out.println("je suis dans get nbcubes de city et m est a "+m+" et d "+d+" et m.get(d) "+m.get(d));
		return m.get(d);
	}

	public void setNbCubes(int nbCubes,Disease d) {
		this.m.put(d, nbCubes);
	}

	public boolean isEclosion(Disease d) {
		//System.out.println("je suis dans iseclosion de city et m est a "+m_ec+" et d "+d+" et m.get(d) "+m_ec.get(d));
		return this.m_ec.get(d);
	}

	public void setEclosion(boolean eclosion,Disease d) {
		this.m_ec.put(d, eclosion);
	}
	public boolean isCure(Disease d) {
		return m_cure.get(d);
	}
	public void setCure(Disease d) {
		this.m_cure.put(d, true);
	}


	


	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}




}
