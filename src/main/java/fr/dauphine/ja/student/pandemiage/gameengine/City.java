package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dauphine.ja.pandemiage.common.Disease;

public class City {
	private String CityName;
	private int r;// The Red color of RGB
	private int g;// The GREEN color of RGB
	private int b;// The BLUE color of RGB
	private double weight;
	private float x;
	private float y;
	private float size;
	private double eigenvector_Centrality;
	private String edge_ID;
	private String edge_Label;
	private int degree;  //Neighbours number
	private boolean isOutBreaks=false;

	private   Map<Disease,Integer> m=new HashMap<Disease,Integer>(); // Permet de recuperer le nombre de cube sur chaque ville et sr chaque malade
	private Map<Disease,Boolean> m_ec=new HashMap<Disease,Boolean>();// Permet de recuperer quelle maladie a eclos sur une ville

	private Disease disease;
	private static Map<Disease,Boolean> m_cure=new HashMap<Disease,Boolean>(); // Permet de savoir pour chaque maladie si l'entidote a été decouvert
	private List<City> list; // list of neighbours


	public City(String CityName, int r	,int g, int b, double weight, float x, float y, float size,double eigenvector_Centrality	,String edge_ID,String edge_Label	, int degree , List<City> list) {
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
			this.m_cure.put(d, false);
			//System.out.println(CityName+" - "+m.get(d));
		}
	//	System.out.println("\n\n**********************************************ma liste d'eclosion est à "+m_ec+"******************\n\n");
		
		
	}

	public String getName() {
		return CityName;
	}
	public float getX() {
		return this.x;
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
