import java.math.BigInteger;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * MoteurCalcul
 */
public class MoteurCalcul extends UnicastRemoteObject implements Calcul {

    private static final long serialVersionUID = 2688832544753610933L;
    private String url;

    @Override
    public Object executerTache(Tache t) throws RemoteException {
        System.out.println("Execution de la tache en cours sur le serveur : " + url);
        return t.executer();
    }

    public MoteurCalcul(String url) throws RemoteException {
        this.url = url;
    }
}