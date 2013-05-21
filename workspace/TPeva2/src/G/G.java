package G;

import java.rmi.RemoteException;
import java.util.LinkedList;

import machines.IMachine;

public class G implements IG{
	private LinkedList<IMachine> machines = new LinkedList<IMachine>();

	public void addMachines(IMachine c)  throws RemoteException{
		machines.add(c);
		System.out.println("Machine added: " + c);
	}
	public static void main(String[] args) throws RemoteException{
		//if (System.getSecurityManager() == null) {
		//	System.setSecurityManager(new SecurityManager());
		//}
		try {
			String name = "ServerChat";
			Chat monChat = new Chat();
			// on enregistre le server/service dans l'annuaire
			IChat stub = (IChat) UnicastRemoteObject.exportObject(monChat, 0);
			// mettre IChat
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("Chat bound");
		} catch (Exception e) {
			System.err.println("Chat server exception:");
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
	public LinkedList<IMachine> getMachines() {
		return machines;
	}

	public void setMachines(LinkedList<IMachine> machines) {
		this.machines = machines;
	}

	@Override
	public void notifPanne(String idVoisin) {
		for(IMachine m:machines){
			if(m.getId().equals(idVoisin)){
				m.getVoisinGauche().recevoirNotifPanne(idVoisin);
				m.getVoisinDroit().recevoirNotifPanne(idVoisin);
				break;
			}
		}
	}

	@Override
	public void notifRepar(String idVoisin) {
		for(IMachine m:machines){
			if(m.getId().equals(idVoisin)){
				m.getVoisinGauche().recevoirNotifPanne(idVoisin);
				m.getVoisinDroit().recevoirNotifPanne(idVoisin);
				break;
			}
		}
	}

}
