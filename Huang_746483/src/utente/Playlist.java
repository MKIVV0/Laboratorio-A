/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package utente;

import java.util.Iterator;
import java.util.LinkedList;
import emotionalsongs.*;

/**
 * Classe rappresentante una playlist, composta da uno user id, per
 * indicare l'appartenenza ad un utente, e il nome associato.
 */
public class Playlist {
	/**
	 * Attributo rappresentate l'identificativo dell'utente
	 * a cui appartiene la playlist.
	 */
    private String userId;
	/**
	 * Attributo rappresentate il nome della playlist.
	 */
	private String playlistName;
	/**
	 * Attributo rappresentate la lista delle canzoni
	 * appartenenti alla playlist.
	 */
	private LinkedList<Song> songList = new LinkedList<>();

	/**
	 * Costruttore per istanziare una playlist vuota.
	 * @param userId identificativo dell'utente.
	 * @param pName nome della playlist.
	 */
	public Playlist(String userId, String pName) {
		this.userId = userId;
		this.playlistName = pName;
	}

	/**
	 * Costruttore per istanziare una playlist non vuota,
	 * a cui aggiungere delle canzoni.
	 * @param userId identificativo dell'utente.
	 * @param pName nome della playlist.
	 * @param l lista delle canzoni della playlist.
	 */
	public Playlist(String userId, String pName, LinkedList<Song> l) {
        this.userId = userId;
		this.playlistName = pName;
		this.songList = l;
	}

	/**
	 * Aggiunge una canzone alla playlist se e solo se non è ancora contenuta.
	 * @param song identificativo dell'utente.
	 */
	public void addSong(Song song){
		for (Song s: this.songList)
			if (s.equals(song)) {
				System.out.println("\nCanzone già presente nella playlist!");
				return;
			}
		this.songList.add(song);
	}

	/**
	 * Getter del campo songList.
	 * @return la lista delle canzoni del chiamante.
	 */
	public LinkedList<Song> getSongList() { 
		return this.songList;
	}

	/**
	 * Controlla se la lista di canzoni è vuota o meno.
	 * @return true, se è vuota, false altrimenti.
	 */
	public boolean isEmpty(){
		return this.songList.isEmpty();
	}

	/**
	 * Getter del campo playlistName.
	 * @return la lista delle canzoni del chiamante.
	 */
	public String getPlaylistName() {
		return this.playlistName;
	}

	/**
	 * Override del metodo toString():
	 * @return un formato leggibile per l'utente di una playlist.
	 */
	@Override
    public String toString() {
        String tmp = "";
        for (Song s: this.songList)
            tmp += s.toString() + "\n";
        return "Brani nella playlist " + this.playlistName + ": \n" + tmp;
    }

	/**
	 * Serializza l'oggetto di tipo Playlist chiamante, per la scrittura su file.
	 * @return una stringa serializzata contenete i campi del chiamante.
	 */
	public String toCSVString(){
		String tmp = this.userId + "<SEP>" + this.playlistName + "£";
		Iterator<Song> ite = this.songList.iterator();
		while(ite.hasNext()) {
			 tmp += ite.next().getId() + "@";
		}
		return tmp;
	}

	/**
	 * Deserializza un oggetto di tipo Playlist serializzato e costruisce un nuovo oggetto
	 * con i dati ricavati.
	 * @param serializedPl stringa di un oggetto serializzato di tipo Playlist
	 * @return nuovo oggetto di tipo Playlist a cui vengono passati i dati deserializzati.
	 */
	public static Playlist fromCSV(String serializedPl){
		String[] playlistTmp = serializedPl.split("£");
		String[] playlistDetails = playlistTmp[0].split("<SEP>");
		String[] songsTmp = playlistTmp[1].split("@");
		LinkedList<Song> tmpList = new LinkedList<>();
		for (int i = 0; i < songsTmp.length; i++) {
			tmpList.add(Song.fromCSV(EmotionalSongs.system_songs.get(songsTmp[i])));
		}
		return new Playlist(playlistDetails[0], playlistDetails[1], tmpList);
	}

}