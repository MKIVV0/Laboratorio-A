/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;
/**
 * Classe contenente costanti enumerative che rappresentano
 * le emozioni contenute nella tabella delle emozioni associata
 * ad ogni singola canzone.
 * <p>Ogni costante enumerativa che rappresenta un emozione ha la sua relativa descrizione.</p>
 */
public enum Emotions {
    /**
     * Emozione stupore
     */
    AMAZEMENT("Feeling of wonder or happiness"),
    /**
     * Emozione meraviglia
     */
    SOLEMNITY("Feeling of transcendence, inspiration. Thrills."),
    /**
     * Emozione tenerezza
     */
    TENDERNESS("Sensuality, affect, feeling of love"),
    /**
     * Emozione nostalgia
     */
    NOSTALGIA("Dreamy, melancholic, sentimental feelings"),
    /**
     * Emozione calma
     */
    CALMNESS("Relaxation, serenity, meditativeness"),
    /**
     * Emozione forza
     */
    POWER("Feeling strong, heroic, triumphant, energetic"),
    /**
     * Emozione gioia
     */
    JOY("Feels like dancing, bouncy feeling, animated, amused"),
    /**
     * Emozione tensione
     */
    TENSION("Feeling Nervous, impatient, irritated"),
    /**
     * Emozione tristezza
     */
    SADNESS("Feeling Depressed, sorrowful");

    /**
     * Variabile che contiente la descrizione dell'emozione
    */
    private String explanation;


    /**
     * Costruttore enum
     * @param expl descrizione della rispettiva emozione
     */
    Emotions(String expl){
        this.explanation = expl;
    }


    /**
     * Getter della descrizione delle varie costanti
     * @return una stringa con la descrizione dell'emozione
     */
    public String getEXplanation(){
        return this.explanation;
    }



    /**
     * Override del metodo toString(). Ritorna una determinata
     * stringa, in base al valore della variabile enumerativa che
     * deve essere trasformata in stringa.
     * @return la stringa corrispondente all'emozione.
     */
    @Override
    public String toString() {
        switch(this) {
            case AMAZEMENT: return "AMAZEMENT";
            case SOLEMNITY: return "SOLEMNITY";
            case TENDERNESS: return "TENDERNESS";
            case NOSTALGIA: return "NOSTALGIA";
            case CALMNESS:return "CALMNESS";
            case POWER:return "POWER";
            case JOY:return "JOY";
            case TENSION:return "TENSION";
            case SADNESS:return "SADNESS";
            default: return "";
        }
    }
}