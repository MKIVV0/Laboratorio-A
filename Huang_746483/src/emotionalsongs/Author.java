/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733051, CO
 */

package emotionalsongs;
/**
 * Classe rappresentante un Autore, il quale ha un nome e un cognome.
 */
public class Author {
    /**
	 * Nome dell'autore
	 */
    private String firstName;

    /**
	  * Cognome dell'autore
	  */
    private String lastName;

    /**
     * Costruttore per istanziare un oggetto di tipo Author.
     * @param firstName il nome dell'autore
     * @param lastName il cognome dell'autore
     */
    public Author(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter del campo lastName.
     * @return il nome dell'autore.
     */
    public String getfirstName(){
        return this.firstName;
    }

    /**
     * Getter del campo lastName.
     * @return il cognome dell'autore.
     */
    public String getlastName(){
        return this.lastName;
    }

    /**
     * Override del metodo equals(). Verifica se un oggetto di tipo Object
     * passato come parametro è uguale all'oggetto chiamante.
     * @param o oggetto istanza di object.
     * @return true se i due oggetti corrispondono, false altrimenti.
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Author)
            return this.firstName.equals(((Author)o).firstName) && this.lastName.equals(((Author)o).firstName);
        else return false;
    }
}
