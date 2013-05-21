package machines;

import java.rmi.Remote;

public interface IMachine extends Remote {
	
	public void panne();
	
	public void calculGauche();
	
	public void calculDroit();
	
	public void recevoirNotifPanne(String idVoisin);
	
	public void recevoirNotifRepar(String idVoisin);
	
	public void reparation();
	
	

}
