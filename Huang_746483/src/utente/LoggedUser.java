/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package utente;

import java.util.LinkedList;
import static emotionalsongs.EmotionalSongs.uid_plnameANDsongs;

/** Classe rappresentate un utente autenticato.
 */
public class LoggedUser extends AbstractUser {
    /**
     * Attributo rappresentate il nome dell'utente registrato.
     */
    private String firstName;
    /**
     * Attributo rappresentate il cognome dell'utente registrato.
     */
    private String lastName;
    /**
     * Attributo rappresentate il codice fiscale dell'utente registrato.
     */
    private String CF;
    /**
     * Attributo rappresentate l'indirizzo fisico dell'utente registrato.
     */
    private Address address;
    /**
     * Attributo rappresentate l'indirizzo email dell'utente registrato.
     */
    private String email;
    /**
     * Attributo rappresentate l'identificativo dell'utente registrato.
     */
    private String userID;
    /**
     * Attributo rappresentate la password dell'utente registrato.
     */
    private String password;
    /**
     * Attributo rappresentate la lista di playlist associata.
     * all'utente registrato.
     */
    private LinkedList<Playlist> playlistList;

    /**
     * Costruttore per istanziare un oggetto di tipo LoggedUser.
     * @param fn il nome dell'utente.
     * @param ln il cognome dell'utente.
     * @param CF il codice fiscale dell'utente.
     * @param a l'indirizzo fisico dell'utente.
     * @param email l'indirizzo email dell'utente.
     * @param uid l'identificativo dell'utente.
     * @param pwd la password dell'utente.
     */
    public LoggedUser(String fn, String ln, String CF, Address a, String email, String uid, String pwd) {
        this.firstName = fn;
        this.lastName = ln;
        this.CF = CF;
        this.address = a;
        this.email = email;
        this.userID = uid;
        this.password = pwd;
        this.playlistList = new LinkedList<>();
    }

    /**
     * Aggiunge una playlist alla lista di playlist dell'utente.
     * @param p oggetto di tipo Playlist.
     */
    public void addPlaylist(Playlist p) {
       if(this.playlistList.contains(p))
           this.playlistList.remove(p);
        this.playlistList.add(p);
        String[] s = p.toCSVString().split("£");
        uid_plnameANDsongs.put(s[0],s[0]+"£"+s[1]);
    }

    /**
     * Cerca e restituisce la playlist il cui nome è passato come parametro.
     * @param playlistName il nome della playlist.
     * @return Playlist, se viene trovata, null altrimenti.
     */
    public Playlist getPlaylist(String playlistName) {
        for(Playlist i: this.playlistList)
            if(i.getPlaylistName().equals(playlistName))
                return i;
        return null;
    }

    /**
     * Restituisce la lista dei nomi delle playlist dell'utente.
     * @return una lista contente le playlist.
     */
    public LinkedList<String> getAllPlaylistname() {
        LinkedList<String> s = new LinkedList<>();
        for (Playlist p: this.playlistList)
            s.add(p.getPlaylistName());
        return s;
    }

    /**
     * Getter per il campo userId.
     * @return l'id dell'utente.
     */
    public String getId() {
        return this.userID;
    }

    /**
     * Disconnette l'utente autenticato.
     * @return un utente non autenticato.
     */
    public AbstractUser logout(){
        return new NotLoggedUser();
    }

    /**
     * Serializza l'oggetto di tipo Playlist chiamante, per la scrittura su file.
     * @return una stringa serializzata contenete i campi del chiamante.
     */
    public String toCSVString(){
        return this.firstName + '#' + this.lastName + '#' + this.CF + '#' + this.address.toCSVString() +
                '#' + this.email + '#' + this.userID + '#' + this.password;
    }

    /**
     * Carica tutte le playlist associate all'utente dalla hash table corrispondente.
     */
    public void loadPlaylists() {
        for(String s: uid_plnameANDsongs.keySet()) {
            String[] values = s.split("<SEP>");
            if (values[0].equals(this.getId()))
                this.playlistList.add(Playlist.fromCSV(uid_plnameANDsongs.get(s)));
        }
    }
}