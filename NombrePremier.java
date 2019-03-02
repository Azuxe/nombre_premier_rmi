import java.io.Serializable;
import java.util.ArrayList;

public class NombrePremier implements Tache, Serializable {
    private static final long serialVersionUID = -4340984954416639702L;
    private int number;

    public NombrePremier(int number) {
        this.number = number;
    }

    public Object executer() {
        System.out.println("Execution en cours..");
        return calculerEstPremier();
    }

    // Fonction retournant true si number est un nombre premier, false sinon
    public boolean calculerEstPremier() {
        int j = 2;
        boolean isPrime = true;

        while (j < Math.sqrt(number) && isPrime) {
            if (number % j == 0) {
                isPrime = false;
            }
            j++;
        }
        if (isPrime) {
            return true;
        }
        return false;
    }
}