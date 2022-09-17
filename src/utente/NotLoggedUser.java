/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package utente;

/**
 * Classe rappresentante l'utente non autenticato.
 * I parametri non vengono usati, ma sono stati messi in
 * vista dell'implementazione concorrente del sistema, dove
 * potranno esserci più utenti non autenticati.
 */
public class NotLoggedUser extends AbstractUser {

    /**
     * Attributo rappresentate il numero di utenti non autenticati presenti.
     */
    private static int guestNumber = 0;

    /**
     * Attributo rappresentate l'identificativo dell'utente non autenticati.
     */
	private String guestID;

    /**
     * Costruttore per instanziare un oggetto di tipo NotLoggedUser.
     */
    public NotLoggedUser(){
        guestID = "Guest" + guestNumber;
        guestNumber++;
    }

    /**
     * Permette ad un utente non autenticato di autenticarsi
     * @param serializedUser la stringa serializzata di un oggetto di tipo LoggedUser.
     * @return un oggetto di tipo LoggedUser a cui vengono passati i dati estratti.
     */
    public AbstractUser login(String serializedUser){
    	 String[] tmp = serializedUser.split("#");
         AbstractUser u = new LoggedUser(tmp[0], tmp[1], tmp[2], Address.fromCSV(tmp[3]), tmp[4], tmp[5], tmp[6]);
         return u;
    }

}
