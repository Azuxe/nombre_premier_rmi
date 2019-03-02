/**
 * Calcul
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calcul extends Remote {
 Object executerTache(Tache t) throws RemoteException;
}