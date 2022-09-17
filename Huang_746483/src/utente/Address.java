/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package utente;

/**
 * Classe che rappresenta un indirizzo fisico.
 */
public class Address {
    /**
     * Rappresenta il qualificatore dell'indirizzo.
    */
    private AddressQualifier qualifier;

    /**
     * Rappresenta il nome dell'indirizzo.
     */
    private String streetName;

    /**
     * Rappresenta il numero civico dell'indirizzo.
    */
    private String civicNumber;

    /**
     * Rappresenta la città in cui è localizzato l'indirizzo.
     */
    private String city;

    /**
     * Rappresenta la provincia in cui è locata la città.
    */
    private String province;

    /**
     * Costruttore per istanziare un oggetto di tipo Address.
     * @param aq il qualificatore della via.
     * @param sn il nome della via.
     * @param cn il numero civico della via.
     * @param c la città in cui è locata la via.
     * @param p la provincia di appartenenza della città.
     */
    public Address(AddressQualifier aq, String sn, String cn, String c, String p) {
        this.qualifier = aq;
        this.streetName = sn;
        this.civicNumber = cn;
        this.city = c;
        this.province = p;
    }

    /**
     * Serializza l'oggetto di tipo Address chiamante, per la scrittura su file.
     * @return una stringa serializzata contenete i campi del chiamante.
     */
    public String toCSVString(){
        return this.qualifier + "£" + this.streetName + "£" 
    			+ this.civicNumber + "£" + this.city + "£" + this.province;
    }

    /**
     * Deserializza un oggetto di tipo Address serializzato e costruisce un nuovo oggetto
     * con i dati ricavati.
     * @param addressInfo stringa di un oggetto serializzato di tipo Address
     * @return nuovo oggetto di tipo Address a cui vengono passati i dati deserializzati.
     */
    public static Address fromCSV(String addressInfo){
        String[] tmp = addressInfo.split("£");
        return new Address(AddressQualifier.valueOf(tmp[0]), tmp[1], tmp[2], tmp[3], tmp[4]);
    }
}
