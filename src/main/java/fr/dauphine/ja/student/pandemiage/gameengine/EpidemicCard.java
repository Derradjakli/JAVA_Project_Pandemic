package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.Collections;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class EpidemicCard extends PlayerCard {
	private boolean epidemic;
	

	public EpidemicCard(){
		epidemic=true;
	}

 
	public void Acceleration() { // DONNE LE NOMBRES DE CARTES PROPAGATION QUE JE VAIS TIRER
		if(GameEngine.getCptprop()<5){
		GameEngine.setCptprop(GameEngine.getCptprop()+1);
		GameEngine.setVit_prop(GameEngine.getVitprop()[GameEngine.getCptprop()]);
		}
	}
	
	public void Infection(PropagationDeck pc) {
		pc.lastDeckCard();
	}
	
	public void Intensification(PropagationDeck pc1,PropagationDeck propdefauss) {
		Collections.shuffle(propdefauss.getPropagationdeck());
		for(PropagationCard pc :propdefauss.getPropagationdeck()){
			pc1.setPropagationdeck(pc1.getPropagationdeck());
			List<PropagationCard> ls;
			ls=pc1.getPropagationdeck();
			ls.addAll(propdefauss.getPropagationdeck());
			pc1.setPropagationdeck(ls);
		}		
	}


	public boolean isEpidemic() {
		return epidemic;
	}


	public void setEpidemic(boolean epidemic) {
		this.epidemic = epidemic;
	}



}
