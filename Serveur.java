import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * MoteurCalcul
 */
public class Serveur {

    public static void main(String[] args) {
        
        try {
            // Création de 4 serveurs (4 registres, 4 objets et donc 4 urls)
            LocateRegistry.createRegistry(1099);
            String url1 = "rmi://127.0.0.1:1099/MoteurCalcul";
            MoteurCalcul moteurCalcul1 = new MoteurCalcul(url1);
            System.out.println("Enregistrement de l'objet avec l'url : " + url1);
            Naming.rebind(url1, moteurCalcul1);

            LocateRegistry.createRegistry(1100);
            String url2 = "rmi://127.0.0.1:1100/MoteurCalcul";
            MoteurCalcul moteurCalcul2 = new MoteurCalcul(url2);
            System.out.println("Enregistrement de l'objet avec l'url : " + url2);
            Naming.rebind(url2, moteurCalcul2);

            LocateRegistry.createRegistry(1101);
            String url3 = "rmi://127.0.0.1:1101/MoteurCalcul";
            MoteurCalcul moteurCalcul3 = new MoteurCalcul(url3);
            System.out.println("Enregistrement de l'objet avec l'url : " + url3);
            Naming.rebind(url3, moteurCalcul3);

            LocateRegistry.createRegistry(1102);
            String url4 = "rmi://127.0.0.1:1102/MoteurCalcul";
            MoteurCalcul moteurCalcul4 = new MoteurCalcul(url4);
            System.out.println("Enregistrement de l'objet avec l'url : " + url4);
            Naming.rebind(url4, moteurCalcul4);

        } catch (Exception e) {
            System.err.println("MoteurCalcul exception: " + e.getMessage());
        }
    }
}