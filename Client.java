import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Client
 */
public class Client {

    public static void main(String args[]) {

        // Test pour savoir si les informations de connexion à la base de données sont fournies
        if (args.length != 2){
            System.out.println("Merci de saisir les identfiants de la base de donnée en paramètre");
            return;
        }

        // Instanciation du security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Initialisation des clients
        try {
            // Récupération du driver 
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            // Initialisation des accès bd
            String url = "jdbc:postgresql://localhost:5432/";
            String user = args[0];
            String passwd = args[1];

            // Connexion à la db
            Connection conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion effective !");

            // Creation de la base
            Statement Stmt = conn.createStatement();
            try {
                Stmt.execute("CREATE DATABASE nombre_premier");
            } catch (Exception e) {

            }
            conn.close();

            url = "jdbc:postgresql://localhost:5432/nombre_premier";
            Connection con = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion effective !");

            // Creation de la table
            Stmt = con.createStatement();
            Stmt.execute("CREATE TABLE IF NOT EXISTS liste (nombre INTEGER NOT NULL PRIMARY KEY, isprime boolean NOT NULL)");

            String sql = "SELECT * FROM liste order by nombre asc";
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultat = statement.executeQuery(sql);
            ResultSetMetaData metadata = resultat.getMetaData();

            // permet de reprendre au dernier nombre inscrit dans la base
            if (resultat.last()) {
                ThreadNombrePremier.cpt = new Compteur(resultat.getInt(1));
            }else{
                ThreadNombrePremier.cpt = new Compteur(1);
            }

            new Thread(new ThreadNombrePremier("127.0.0.1", 1099, con)).start();
            new Thread(new ThreadNombrePremier("127.0.0.1", 1100, con)).start();
            new Thread(new ThreadNombrePremier("127.0.0.1", 1101, con)).start();
            new Thread(new ThreadNombrePremier("127.0.0.1", 1102, con)).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ThreadNombrePremier implements Runnable {

    public static Compteur cpt;
    private String address;
    private int port;
    private Connection con;

    public ThreadNombrePremier(String address, int port, Connection con) {
        this.address = address;
        this.port = port;
        this.con = con;
    }

    @Override
    public void run() {
        try {
            String nom = "//" + address + ":" + port + "/MoteurCalcul";
            Calcul c = (Calcul) Naming.lookup(nom);
            while (true) {
                int i = cpt.inc();
                NombrePremier estPremier = new NombrePremier(i);
                boolean b = (boolean) c.executerTache(estPremier);
                // Insertion en base
                System.out.println(i + " " + b);
                try {
                    PreparedStatement stm = con.prepareStatement("INSERT INTO liste VALUES (?,?);");
                    stm.setInt(1, i);
                    stm.setBoolean(2, b);
                    stm.executeUpdate();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                Thread.sleep(20);
            }
        } catch (RemoteException | NotBoundException | MalformedURLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Compteur {
    private int cpt;

    public Compteur(int i) {
        this.cpt = i;
    }

    public synchronized int inc() {
        this.cpt++;
        return this.cpt - 1;
    }
}