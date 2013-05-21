package G;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.LinkedList;

import machines.IMachine;
import machines.Machine;

public class G implements IG{
	private HashMap<String,IMachine> machines=new HashMap<String,IMachine>();

	public void addMachines(IMachine c)  throws RemoteException{
		machines.put(c.getId(), c);
		System.out.println("Machine added: " + c);
	}
	public static void main(String[] args) throws RemoteException{
		//if (System.getSecurityManager() == null) {
		//	System.setSecurityManager(new SecurityManager());
		//}
		try {
			String name = "G";
			G monG = new G();
			// on enregistre le server/service dans l'annuaire
			IG stub = (IG) UnicastRemoteObject.exportObject(monG, 0);
			// mettre IChat
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("Ring system bound");
			Machine m1=new Machine("m1");
			Machine m2=new Machine("m2");
			Machine m3=new Machine("m3");
			Machine m4=new Machine("m4");
			m1.setVoisinGauche(m4);
			m1.setVoisinDroit(m2);
			m2.setVoisinGauche(m1);
			m2.setVoisinDroit(m3);
			m3.setVoisinGauche(m2);
			m3.setVoisinDroit(m4);
			m4.setVoisinGauche(m3);
			m4.setVoisinDroit(m1);
			monG.addMachines(m1);
			monG.addMachines(m2);
			monG.addMachines(m3);
			monG.addMachines(m4);
		} catch (Exception e) {
			System.err.println("Ring system exception:");
			e.printStackTrace();
		}
		/*
		Chat chat = new Chat();
		IClient client1 = new Client("client1", chat);
		chat.addClient(client1);
		IClient client2 = new Client("client2", chat);
		chat.addClient(client2);
		while(true){
			String nomClient = Clavier.lireString();
			String message;
			if (nomClient.equals(client1.getId())){
				message = Clavier.lireString();
				client1.envoyerMessage(message);
			}else{
				message = Clavier.lireString();
				client2.envoyerMessage(message);
			}
		}
		 */
	}

	@Override
	public void notifPanne(String idVoisin) {
		IMachine m=machines.get(idVoisin);
			if(m!=null){
				m.getVoisinGauche().recevoirNotifPanne(idVoisin);
				m.getVoisinDroit().recevoirNotifPanne(idVoisin);
			}
	}

	@Override
	public void notifRepar(String idVoisin) {
		IMachine m=machines.get(idVoisin);
		if(m!=null){
				m.getVoisinGauche().recevoirNotifPanne(idVoisin);
				m.getVoisinDroit().recevoirNotifPanne(idVoisin);
			}
		}

}
