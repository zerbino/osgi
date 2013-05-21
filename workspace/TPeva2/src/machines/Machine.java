package machines;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import G.IG;

public class Machine implements IMachine {

	private Machine voisinGauche, voisinDroit;
	private IG controleur;
	private String id;
	private boolean voisinGaucheBreakdown = false,
			voisinDroitBreakdown = false;

	private boolean breakdown = false;

	public Machine(String id, IG controleur) {
		super();
		this.id = id;
		this.controleur = controleur;
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
		if (rand > 0.9) {
			this.breakdown = true;
			controleur.notifPanne(this.getId());

		}

	}

	@Override
	public void calculGauche() {
		System.out.println("calculs sur la machine " + this.getId()
				+ " delegue a la machine " + this.getVoisinGauche());
		this.getVoisinGauche().calcul();

	}

	@Override
	public void calculDroit() {
		System.out.println("calculs sur la machine " + this.getId()
				+ " delegue a la machine " + this.getVoisinDroit());
		this.getVoisinDroit().calcul();

	}

	public void setBreakdownVoisin(boolean breakdown, String idVoisin) {
		if (idVoisin.equals(this.getVoisinGauche().getId())) {
			this.voisinGaucheBreakdown = breakdown;
		} else {
			if (idVoisin.equals(this.getVoisinDroit().getId())) {
				this.voisinDroitBreakdown = breakdown;
			} else {

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
		this.panne();
		if (!breakdown) {
			System.out.println("Calcul de la machine "+this.getId());
			if (!this.isVoisinDroitBreakdown()) {
				this.calculDroit();
			}
			if (!this.isVoisinGaucheBreakdown()) {
				this.calculGauche();
			}
		}

	}

	public static void main(String[] args) {
		try {
			String name = "G";
			Registry registry = LocateRegistry.getRegistry();
			IG monG = (IG) registry.lookup(name);
			System.out.println("Serveur trouvé");

			Machine m1 = new Machine("m1", monG);
			Machine m2 = new Machine("m2",monG);
			Machine m3 = new Machine("m3",monG);
			Machine m4 = new Machine("m4",monG);
			m1.setVoisinGauche(m4);
			m1.setVoisinDroit(m2);
			IMachine stub1 = (IMachine) UnicastRemoteObject.exportObject(m1, 2);

			m2.setVoisinGauche(m1);
			m2.setVoisinDroit(m3);
			IMachine stub2 = (IMachine) UnicastRemoteObject.exportObject(m2, 3);

			m3.setVoisinGauche(m2);
			m3.setVoisinDroit(m4);
			IMachine stub3 = (IMachine) UnicastRemoteObject.exportObject(m3, 4);

			m4.setVoisinGauche(m3);
			m4.setVoisinDroit(m1);
			IMachine stub4 = (IMachine) UnicastRemoteObject.exportObject(m4, 5);

			monG.addMachines(stub1);
			monG.addMachines(stub2);
			monG.addMachines(stub3);
			monG.addMachines(stub4);
			
			m1.calcul();

		} catch (Exception e) {
			System.err.println("Client exception:");
			e.printStackTrace();
		}
	}
}
