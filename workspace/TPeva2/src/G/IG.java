package G;

import java.rmi.*;
public interface IG extends Remote{

	public void notifPanne(String idVoisin);

	public void notifRepar(String idVoisin);
}
