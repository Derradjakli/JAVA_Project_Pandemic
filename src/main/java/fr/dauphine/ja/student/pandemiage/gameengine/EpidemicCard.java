package fr.dauphine.ja.student.pandemiage.gameengine;

import fr.dauphine.ja.pandemiage.common.Disease;

public class EpidemicCard extends PlayerCard {
	private boolean epidemic;


	public EpidemicCard(){
		epidemic=true;
	}


	public void Acceleration() {

	}
	public void Infection() {

	}
	public void Intensification() {

	}


	public boolean isEpidemic() {
		return epidemic;
	}


	public void setEpidemic(boolean epidemic) {
		this.epidemic = epidemic;
	}



}
