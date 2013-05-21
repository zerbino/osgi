package G;

import java.rmi.*;

import machines.IMachine;
public interface IG extends Remote{

	public void notifPanne(String idVoisin);

	public void notifRepar(String idVoisin);
	
	public void addMachines(IMachine c);
}
