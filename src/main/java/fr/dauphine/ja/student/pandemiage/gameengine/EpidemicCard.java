package fr.dauphine.ja.student.pandemiage.gameengine;

import java.util.Collections;
import java.util.List;

import fr.dauphine.ja.pandemiage.common.Disease;

public class EpidemicCard extends PlayerCard {
	private Boolean epidemic;
	

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
		PropagationDeck.lastDeckCard();
	}
	
	public void Intensification(PropagationDeck pc1,PropagationDeck propdefauss) {
		Collections.shuffle(PropagationDeck.getPropagationdeck());
		for(PropagationCard pc :propdefauss.getPropagationdeck()){
			PropagationDeck.setPropagationdeck(PropagationDeck.getPropagationdeck());
			List<PropagationCard> ls;
			ls=PropagationDeck.getPropagationdeck();
			ls.addAll(PropagationDeck.getPropagationdeck());
			PropagationDeck.setPropagationdeck(ls);
		}		
	}


	public Boolean isEpidemic() {
		return epidemic;
	}


	public void setEpidemic(Boolean epidemic) {
		this.epidemic = epidemic;
	}



}
