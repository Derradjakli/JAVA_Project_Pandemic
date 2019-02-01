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
		if(GameEngine.getCptprop()<7){
			GameEngine.setCptprop(GameEngine.getCptprop()+1);
			GameEngine.setVit_prop(GameEngine.getVitprop()[GameEngine.getCptprop()]);
		}
	}

	public void Infection(PropagationDeck pc,PropagationDeck propdefauss) {
		pc.lastDeckCard(propdefauss);
	}

	public void Intensification(PropagationDeck pc1,PropagationDeck propdefauss) {
		//System.out.println("avant shuffle");
		Collections.shuffle(propdefauss.getPropagationdeck());
		//System.out.println("apres shuffle");
		if(!propdefauss.getPropagationdeck().isEmpty()){
			while(!propdefauss.getPropagationdeck().isEmpty()){
				PropagationCard pc=propdefauss.getPropagationdeck().get(0);
				propdefauss.getPropagationdeck().remove(0);
				//System.out.println(propdefauss.getPropagationdeck().size());
				//System.out.println("premiere carte de propdefauss est "+propdefauss.getPropagationdeck().get(0).getCity().getName());
				//System.out.println("carte supprimée est "+pc.getCity().getName());

				//pc1.getPropagationdeck().add(pc);
			}
		}
//		System.out.println("apres le while");
		/*
		for(PropagationCard pc :propdefauss.getPropagationdeck()){
			PropagationDeck.setPropagationdeck(PropagationDeck.getPropagationdeck());
			List<PropagationCard> ls;
			ls=PropagationDeck.getPropagationdeck();
			ls.addAll(PropagationDeck.getPropagationdeck());
			PropagationDeck.setPropagationdeck(ls);
		}
		 */		
	}


	public Boolean isEpidemic() {
		return epidemic;
	}


	public void setEpidemic(Boolean epidemic) {
		this.epidemic = epidemic;
	}



}
