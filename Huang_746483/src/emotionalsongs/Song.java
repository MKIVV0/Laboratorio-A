/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;

import java.util.LinkedList;

import static emotionalsongs.EmotionalSongs.song_emotions;

/**
 * La classe Song rappresenta la singola canzone, che contiene i campi
 * da un identificativo, un titolo, un autore e un anno di rilascio.
 */
public class Song {
	
	/**
     * Attributo che rappresenta l'identificativo
     * univoco della canzone.
	 */
    private String id;

    /**
     * Attributo che rappresenta il titolo della
     * canzone.
     */
    private String title;

    /**
     * Attributo che rappresenta l'autore della
     * canzone, costituito da nome e cognome.
     */
    private Author author;

    /**
     * Attributo che rappresenta l'anno di pubblicazione
     * della canzone.
     */
    private int yearReleased;
    
    /**
     * Costruttore per istanziare un oggetto di tipo Song.
     * @param yearReleased anno di rilascio
     * @param id identificativo della canzone
     * @param author autore della canzone
     * @param title titolo della canzone
     */
    public Song(int yearReleased, String id, Author author, String title){
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearReleased = yearReleased;
    }

    /**
     * Override del metodo equals(). Verifica se un oggetto di tipo song
     * passato come parametro è uguale all'oggetto chiamante.
     * @param o oggetto da confrontare.
     * @return true se i due oggetti corrispondono, false altrimenti.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Song)
            return this.id.equals(((Song)o).id);
        else return false;
    }

    /**
     * Ritorna l'identificativo dell'istanza di Song chiamante.
     * @return identificativo del chiamante.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Ritorna il titolo dell'istanza di Song chiamante.
     * @return titolo del chiamante.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Ritorna l'autore dell'istanza di Song chiamante.
     * @return autore del chiamante.
     */
    public Author getAuthor() {
        return this.author;
    }

    /**
     * Restituisce i feedback del brano.
     * @return lista di feedback associato al brano.
     */
    public LinkedList<Feedback> getFeedback(){
        LinkedList<Feedback> feedback = new LinkedList<>();
        for(String keySet: song_emotions.keySet()){
            String[] spl = keySet.split("#");
            if(spl[0].equals(this.getId()))
                feedback.add(Feedback.fromCSV(song_emotions.get(keySet)));
        }
        return feedback;
    }

    /**
     * Override del metodo toString(). Fornisce un formato all'output
     * dei dati relativi ad un oggetto di tipo Song.
     * @return la stringa formattata contenente i dettagli dell'oggetto di tipo Song.
     */
    @Override
    public String toString(){
        return this.author.getfirstName() + " " + this.author.getlastName() + " - " + this.title + " - " + this.yearReleased;
    }

    /**
     * Deserializza un oggetto di tipo Song serializzato e costruisce un nuovo oggetto
     * con i dati ricavati.
     * @param songInfo stringa serializzata di un oggetto di tipo Song.
     * @return nuovo oggetto di tipo Song a cui vengono passati i dati deserializzati.
     */
    public static Song fromCSV(String songInfo){
        String[] tmp = songInfo.split("#");
        return new Song(Integer.parseInt(tmp[0]), tmp[1], new Author(tmp[2], tmp[3]), tmp[4]);
    }

}
