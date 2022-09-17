/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;

import java.io.*;
import java.util.HashMap;
/**
 * Classe che si occupa di gestire la corrispondenza dei dati tra i file e
 * le hashtable contenute nel programma.
 */
public class DataHandler {
	/**
	 * Attributo rappresentate il percorso base della cartella "data".
	 */
	public static final String DATA_BASE_PATH = "." + File.separator + ".." + File.separator + "data" + File.separator;
	/**
	 * Attributo rappresentante il percorso del file Canzoni.csv
	 */
	public static final String SONG_PATH = DATA_BASE_PATH + "Canzoni.csv";
	/**
	 * Attributo rappresentante il percorso del file Playlist.csv
	 */
	public static final String PLAYLIST_PATH = DATA_BASE_PATH + "Playlist.csv";
	/**
	 * Attributo rappresentante il percorso del file Emozioni.csv
	 */
	public static final String EMOTIONS_PATH = DATA_BASE_PATH + "Emozioni.csv";
	/**
	 * Attributo rappresentante il percorso del file UtentiRegistrati.csv
	 */
	public static final String REGISTERED_USER_PATH = DATA_BASE_PATH + "UtentiRegistrati.csv";

	/**
	 * Costruttore di default.
	 */
	public DataHandler(){
	}

	/**
	 * Salva nel file al percorso specificato il contenuto della hashmap
	 * data come argomento.
	 * @param filePath percorso del file.
	 * @param map hashmap da salvare.
	 */
	public static void saveOnFile(String filePath, HashMap<String,String> map) {
		try{
			FileWriter fw = new FileWriter(filePath);
			for(String s: map.values()) {
				fw.write(s + "\n");
				fw.flush();
			}
			fw.close();
		}
		catch(IOException ioe) {
			System.err.println("Errore: IOException");
		}
	}

	/**
	 * Inizializza i file, cartelle e hashtable. Se non esistono,
	 * vengono create, altrimenti, rimangono inalterati.
	 */
	public static final void INIT_PROGRAM_FILES() {
		File dir = new File(DATA_BASE_PATH);
		File reg_users = new File(REGISTERED_USER_PATH);
		File songs = new File(SONG_PATH);
		File emotions = new File(EMOTIONS_PATH);
		File playlists = new File(PLAYLIST_PATH);
		if (!dir.exists())
			dir.mkdir();
		try {
			reg_users.createNewFile();
			if(songs.createNewFile()){
				File tmp = new File("." + File.separator + ".." + File.separator + "Canzoni.csv");
				BufferedReader br = new BufferedReader(new FileReader(tmp));
				FileWriter fw = new FileWriter(songs);
				String s;
				while((s = br.readLine()) != null) {
					fw.write(s + "\n");
					fw.flush();
				}
				fw.close();
				br.close();
				tmp.delete();
			}
			emotions.createNewFile();
			playlists.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Carica il contenuto del file UtentiRegistrati.csv nella hashmap reg_users
	 * contenuta nel main.
	 * @return una hashmap contenente i dati caricati dal file.
	 */
	public static HashMap<String, String> loadRegUsers(){
		HashMap<String, String> tmp = new HashMap<String, String>();
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(REGISTERED_USER_PATH));
			while ((s = br.readLine()) != null) {
				String[] splitted = s.split("#");
				tmp.put(splitted[5], s);
			}
			br.close();
		}catch (IOException e){System.err.println("ioexc");}
		return tmp;
	}
	/**
	 * Carica il contenuto del file Canzoni.csv nella hashmap system_songs
	 * contenuta nel main.
	 * @return una hashmap contenente i dati caricati dal file.
	 */
	public static HashMap<String, String> loadSongs(){
		HashMap<String, String> tmp = new HashMap<String, String>();
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(SONG_PATH));
			while ((s = br.readLine()) != null) {
				String[] spl = s.split("#");
				tmp.put(spl[1], s);
			}
			br.close();
		}catch (IOException e){System.err.println("ioexc");}
		return tmp;
	}
	/**
	 * Carica il contenuto del file Emozioni.csv nella hashmap song_emotions
	 * contenuta nel main.
	 * @return una hashmap contenente i dati caricati dal file.
	 */
	public static HashMap<String, String> loadEmotion(){
		HashMap<String, String> tmp = new HashMap<String, String>();
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(EMOTIONS_PATH));
			while ((s = br.readLine()) != null) {
				String[] spl = s.split("#");
				tmp.put((spl[0]+"#"+spl[1]), s);
			}
			br.close();
		}catch (IOException e){System.err.println("ioexc");}
		return tmp;
	}
	/**
	 * Carica il contenuto del file Playlist.csv nella hashmap uid_plnameANDsongs
	 * contenuta nel main.
	 * @return una hashmap contenente i dati caricati dal file.
	 */
	public static HashMap<String, String> loadPlsts() {
		HashMap<String, String> tmp = new HashMap<String, String>();
		String s = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(PLAYLIST_PATH));
			while ((s = br.readLine()) != null) {
				String[] splitted = s.split("£");
				tmp.put(splitted[0], s);
			}
			br.close();
		}catch (IOException e){System.err.println("ioexc");}
		return tmp;
	}
}