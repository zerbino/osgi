package machines;

import G.IG;

public class Machine implements IMachine {

	private Machine voisinGauche, voisinDroit;
	private IG controleur;
	private String id;
	private boolean voisinGaucheBreakdown = false, voisinDroitBreakdown = false;
	
	private boolean breakdown = false;

	public Machine(String id) {
		super();
		this.id = id;
	}
	
	

	public boolean isVoisinGaucheBreakdown() {
		return voisinGaucheBreakdown;
	}






	public boolean isVoisinDroitBreakdown() {
		return voisinDroitBreakdown;
	}




	



	@Override
	public void panne() {
		double rand = Math.random();
		if(rand>0.9){
			this.breakdown = true;
			controleur.notifPanne(this.getId());
			
		}
		

	}

	@Override
	public void calculGauche() {
		System.out.println("calculs sur la machine "+this.getId()+" delegue a la machine "+this.getVoisinGauche());
		this.getVoisinGauche().calcul();

	}

	@Override
	public void calculDroit() {
		System.out.println("calculs sur la machine "+this.getId()+" delegue a la machine "+this.getVoisinDroit());


	}
	
	public void setBreakdownVoisin(boolean breakdown, String idVoisin){
		if(idVoisin.equals(this.getVoisinGauche().getId())){
			this.voisinGaucheBreakdown = breakdown;
		}
		else{
			if(idVoisin.equals(this.getVoisinDroit().getId())){
				this.voisinDroitBreakdown = breakdown;
			}
			else{
				
			}
		}
	}

	@Override
	public void recevoirNotifPanne(String idVoisin) {
		
		this.setBreakdownVoisin(true, idVoisin);

	}

	@Override
	public void recevoirNotifRepar(String idVoisin) {
		this.setBreakdownVoisin(false, idVoisin);

	}

	@Override
	public void reparation() {
		this.breakdown = false;

	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Machine getVoisinGauche() {
		return voisinGauche;
	}

	@Override
	public Machine getVoisinDroit() {
		return this.voisinDroit;
	}
	
	

	public void setVoisinGauche(Machine voisinGauche) {
		this.voisinGauche = voisinGauche;
	}



	public void setVoisinDroit(Machine voisinDroit) {
		this.voisinDroit = voisinDroit;
	}



	@Override
	public void calcul() {
		if(!breakdown){
			if(!this.isVoisinDroitBreakdown()){
				this.voisinDroit.calcul();
			}
			if(!this.isVoisinGaucheBreakdown()){
				this.voisinGauche.calcul();
			}
		}
		
	}

}
