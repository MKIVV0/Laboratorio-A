/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;

import java.util.HashMap;
/**
 * Classe rappresentante la valutazione di una canzone.
 * Ogni feedback è distinto dalla coppia songId ed emotion.
 */
public class Feedback {
    /**
     * Attributo rappresentate l'identificativo della canzone alla quale
     * è associato il feedback.
     */
    private String songId;
    /**
    * Attributo rappresentante l'emozione associata al feedback.
    */
    private Emotions emotion;
   /**
    * Attributo rappresentante lo score cumulativo associato al feedback.
    */
    private int score;
    /**
     * Attributo rappresentante il numero totale di votanti associato al feedback.
     */
    private int totalNumberOfScores;
    /**
     * Attributo rappresentante la lista i commenti al feedback.
     */
    private HashMap<String, String> notes;

    /**
     * Costruttore per istanziare un oggetto di tipo Feedback che non
     * è ancora stato scritto su file.
     * @param sid l'identificativo della canzone.
     * @param e l'emozione associata.
     */
    public Feedback(String sid, Emotions e){
        this.songId = sid;
        this.emotion = e;
        this.totalNumberOfScores = 0;
        this.score = 0;
        this.notes = new HashMap<>();
    }

   /**
    * Costruttore per istanziare un oggetto di tipo Feedback che è già
    * tato scritto su file.
    * @param sid l'identificativo della canzone.
    * @param e l'emozione associata.
    * @param s score cumulativo associato al feedback.
    * @param n numero di utenti totali che ha votato il feedback.
    * @param hs lista di commenti associata al feedback.
    */
    public Feedback(String sid, Emotions e, int s, int n, HashMap<String, String> hs) {
        this.songId = sid;
        this.emotion = e;
        this.totalNumberOfScores = n;
        this.score = s;
        this.notes = hs;
    }

    /**
     * Calcola lo score medio del feedback.
     * @return la media delle votazioni del feedback.
     */
    private double calculateAVG() {
        if(this.totalNumberOfScores == 0 || this.score == 0)
            return 0;
        return this.score / this.totalNumberOfScores;
    }

    /**
     * Aggiunge cumulativamente uno score al feedback.
     * @param score lo score da aggiungere.
     */
    public void addScore(int score) {
        this.score += score;
        this.totalNumberOfScores++;
    }

    /**
     * Verifica se un utente ha già valutato l'emozione corrispondente al campo emotion per il brano
     * corrispondente al campo songId.
     * @param userId l'utente.
     * @return true, se l'emozione per il brano è già stato valutato, false altrimenti.
     */
    public boolean alreadyValued(String userId){
        return this.notes.containsKey(userId);
    }

    /**
     * Aggiunge un commento alla lista di commenti associata al feedback.
     * @param userId l'utente che commenta.
     * @param comment il commento.
     */
    public void addComment(String userId, String comment) {
        this.notes.put(userId, comment);
    }

    /**
     * Trova tutte le note associati ad una canzone.
     * @return la stringa con tutte le note.
     */
    public String getNotes(){
        String res = "";
        for(String s: this.notes.values()){
            String[] splitted = s.split("@");
            if(splitted.length == 2)
                res+="     -> " + splitted[1] + "\n";
        }
        return res;
    }

    /**
     * Override del metodo toString(). Fornisce un formato all'output
     * dei dati relativi ad un feedback.
     * @return la stringa formattata contenente i dettagli del feedback.
     */
    public String toString(){
        String s, note;
        if(this.totalNumberOfScores == 0 || this.score == 0){
            s = "Nessun utente ha ancora valutato questo brano";
        }
        else {
            s = this.totalNumberOfScores + " utenti hanno provato " +
            this.emotion.toString() + ", con una media di intensita di: " + this.calculateAVG();
            if(!(note = getNotes()).equals(""))
               s += "\n    Commenti degli utenti:\n" + note + "\n";
            else
                s += "\n    Ancora nessun commento per questa emozione...\n";
        }
        return s;
    }

     /**
      * Serializza l'oggetto di tipo Feedback chiamante, per la scrittura su file.
      * @return una stringa serializzata contenete i campi del chiamante.
      */
    public String toCSVString(){
        String tmp = this.songId + '#' + this.emotion + '#' + this.score + '#' + this.totalNumberOfScores + '#';
        for (String n: this.notes.values())
            tmp += n + "%";
        return tmp;
    }

    /**
     * Deserializza un oggetto di tipo Feedback serializzato e costruisce un nuovo oggetto
     * con i dati ricavati.
     * @param serializedFeedback stringa di un oggetto serializzato di tipo Feedback.
     * @return nuovo oggetto di tipo Feedback a cui vengono passati i dati deserializzati.
     */
    public static Feedback fromCSV(String serializedFeedback) {
        String[] tmp = serializedFeedback.split("#");
        if(tmp.length < 5)
            return new Feedback(tmp[0], Emotions.valueOf(tmp[1]));
        else {
            String line = tmp[4];
            String[] tmpEntry = line.split("%");
            HashMap<String, String> tmpNotes = new HashMap<>();
            for (int i = 0; i < tmpEntry.length; i++) {
                String mapValue = tmpEntry[i];
                String[] mapKey = mapValue.split("@");
                tmpNotes.put(mapKey[0], mapValue);
            }
            return new Feedback(tmp[0], Emotions.valueOf(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), tmpNotes);
        }
    }
}
