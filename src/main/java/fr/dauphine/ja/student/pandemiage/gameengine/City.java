package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.List;

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
	private int degree; // Neighbours number
	private int nbCubes;
	private boolean eclosion;
	private Disease disease;
	
	private List<City> list;
	
	public City(String CityName, int r	,int g, int b, double weight, float x, float y, float size,double eigenvector_Centrality	,String edge_ID,String edge_Label	, int degree , List<City> list, int nbCubes,boolean eclosion) {
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
		this.nbCubes=nbCubes;
		this.eclosion=eclosion;
	}
	
	public String getName() {
		return CityName;
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
	public int getNbCubes() {
		return nbCubes;
	}

	public void setNbCubes(int nbCubes) {
		this.nbCubes = nbCubes;
	}

	public boolean isEclosion() {
		return eclosion;
	}

	public void setEclosion(boolean eclosion) {
		this.eclosion = eclosion;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	
	
	
}
