/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package emotionalsongs;

import utente.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import static emotionalsongs.DataHandler.*;
/**
 * Classe contenente il main del programma.
 */
public class EmotionalSongs {
	/**
	 * Attributo rappresentate la hashmap contenente i dati degli utenti registrati.
	 */
	static HashMap<String,String> reg_users = new HashMap<>();
	/**
	 * Attributo rappresentate la hashmap contenente i dati delle canzoni della repository di sistema.
	 */
	public static HashMap<String,String> system_songs = new HashMap<>();
	/**
	 * Attributo rappresentate la hashmap contenente i dati delle playlist dei singoli utenti.
	 */
	public static HashMap<String,String> uid_plnameANDsongs = new HashMap<>();
	/**
	 * Attributo rappresentate la hashmap contenente i dati delle valutazioni lasciate dagli utenti per le canzoni.
	 */
	public static HashMap<String,String> song_emotions = new HashMap<>();
	/**
	 * Attributo rappresentate un buffered reader, che legge l'input dato al programma.
	 */
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Costruttore di default.
	 */
	public EmotionalSongs(){
	}

	/**
	 * Metodo main, entry point del programma.
	 * @param args lista argomenti da dare al programma in esecuzione.
	 * Può essere nulla o contenere n parametri.
	 */
	public static void main(String[] args) {

		DataHandler.INIT_PROGRAM_FILES();

		reg_users = DataHandler.loadRegUsers();
		system_songs = DataHandler.loadSongs();
		uid_plnameANDsongs = DataHandler.loadPlsts();
		song_emotions = DataHandler.loadEmotion();

		AbstractUser user = new NotLoggedUser();

		boolean exit = false;

		do {
			if (user instanceof NotLoggedUser) {
				showNotLoggedMenu();
				String answer = readLine();
				switch (answer) {
					case "0":
						exit = true;
						break;
					case "1":
						searchByName(user);
						saveAllHashMaps();
						break;
					case "2":
						searchByAuthor(user);
						saveAllHashMaps();
						break;
					case "3":
						user = registerUser();
						String serializedUser = ((LoggedUser) user).toCSVString();
						String uid = ((LoggedUser) user).getId();
						reg_users.put(uid, serializedUser);
						if (user instanceof LoggedUser)
							System.out.println("\nUtente registrato con successo!");
						saveAllHashMaps();
						break;
					case "4":
						uid = readLine("Username: ");
						String pw = readLine("Password: ");
						if ((reg_users.containsKey(uid)) && ((reg_users.get(uid).split("#")[6]).equals(pw))) {
							user = ((NotLoggedUser) user).login(reg_users.get(uid));
							if (user instanceof LoggedUser) {
								((LoggedUser) user).loadPlaylists();
								System.out.println("\nLogin effettuato con successo!\n");
							}
						} else
							System.out.println("\nUsername o Password errata.");
						saveAllHashMaps();
						break;
					default:
						System.err.println("\nComando non valido");
				}
			}
			else {
				showLoggedMenu();
				String answer = readLine();
				switch (answer) {
					case "0":
						exit = true;
						break;
					case "1":
						searchByName(user);
						saveAllHashMaps();
						break;
					case "2":
						searchByAuthor(user);
						saveAllHashMaps();
						break;
					case "3":
						registerPlaylist(user);
						saveAllHashMaps();
						break;
					case "4":
						int n;
						String pName, i;
						Playlist p; Song brano;
						LinkedList<String> lpn; LinkedList<Song> songs;
						lpn = ((LoggedUser) user).getAllPlaylistname();
						if(lpn.isEmpty()){
							System.out.println("Nessuna playlist esistente\n");
							break;
						}
						printResult(lpn);
						do {
							n = readInt("\nSelezionare il numero della playlist da aggiornare " +
									"[0) per annullare]: ");
						}while(n < 0 || n > lpn.size());
						if(n==0)
							break;
						pName = lpn.get(n-1);
						p = ((LoggedUser) user).getPlaylist(pName);
						System.out.println("\n" + p);
						do {
							do {
								i = readLine("[0) per annullare]\n"+
										"1) per inserire brani cercandoli per titolo\n" +
										"2) per inserire brani cercandoli per autore e anno");
							} while ((!i.equals("1")) && (!i.equals("2")) && (!i.equals("0")));
							if(i.equals("0"))
								break;
							else if (i.equals("1"))
								songs = user.findSong(readLine("Inserisci il nome della canzone: "));
							else {
								String author = readLine("\nInserisci il nome dell'autore: ");
								int year = readInt("Inserisci l'anno di pubblicazione: ");
								songs = user.findSong(author, year);
							}
							if (songs.isEmpty())
								System.out.println("\nNessun brano corrisponde alla ricerca");
							else {
								System.out.println("\nBrani risultati dalla ricerca:");
								printResult(songs);
								n = readInt("\nSelezionare il numero del brano da aggiungere " +
										"[0) per tornare indietro]: ");
								brano = selectSong(songs, n);
								if (brano != null)
									p.addSong(brano);
							}
						} while (!(readLine("\nCercare altro da aggiungere? (Y,N)")).equalsIgnoreCase("N"));
						System.out.println("Torno al menu'");
						((LoggedUser)user).addPlaylist(p);
						saveAllHashMaps();
						break;
					case "5":
						lpn = ((LoggedUser) user).getAllPlaylistname();
						if(lpn.isEmpty()){
							System.out.println("\nDevi creare una playlist per poterne valutare i brani!\n");
							break;
						}
						printResult(lpn);
						do {
							n = readInt("\nSelezionare il numero della playlist da aprire " +
									"[0) per annullare]: ");
						}while(n < 0 || n > lpn.size());
						if(n==0)
							break;
						pName = lpn.get(n-1);
						p = ((LoggedUser) user).getPlaylist(pName);
						songs = p.getSongList();
						System.out.println("\nBrani della playlist " + p.getPlaylistName() + ":");
						printResult(songs);
						n = readInt("\nSelezionare il brano da valutare " +
								"[0) per tornare indietro]: ");
						brano = selectSong(songs, n);
						if (brano == null)
							break;
						insertSongEmotion(user, brano);
						saveAllHashMaps();
						break;
					case "6":
						user = ((LoggedUser) user).logout();
						System.out.println("Logout effettuato.");
						saveAllHashMaps();
						break;
					default:
						System.err.println("\nComando non valido");
				}
			}
		} while (!exit);

		saveAllHashMaps();

		System.out.println("\nProgramma terminato.");
	}

	/**
	 * Mostra il menu di base delle funzioni disponibile a tutti gli utenti.
	 */
	private static void showBaseMenu(){
		System.out.println("\n0) Esci");
		System.out.println("1) Cerca canzone per titolo");
		System.out.println("2) Cerca canzone per autore e anno");
	}
	/**
	 * Mostra il menu di base delle funzioni disponibile agli utenti non autenticati.
	 */
	private static void showNotLoggedMenu(){
		showBaseMenu();
		System.out.println("3) Registrati");
		System.out.println("4) Login");
	}
	/**
	 * Mostra il menu di base delle funzioni disponibile agli utenti autenticati.
	 */
	private static void showLoggedMenu(){
		showBaseMenu();
		System.out.println("3) Crea una playlist");
		System.out.println("4) Inserisci brano in una playlist");
		System.out.println("5) Inserisci le emozioni per una canzone");
		System.out.println("6) Logout");
	}

	/**
	 * Metodo senza argomenti che gestisce l'input da tastiera attraverso
	 * buffered reader.
	 */
	private static String readLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.err.println("input non valido, riprova: ");
			return "";
		}
	}
	/**
	 * Metodo con argomenti che gestisce l'input da tastiera attraverso
	 * buffered reader.
	 * @param msg la stringa da leggere.
	 */
	private static String readLine(String msg) {
		try {
			String s;
			do{
				System.out.print(msg);
				s = br.readLine();
			}while (s.equals(""));
			return s;
		} catch (IOException e) {
			System.err.println("input non valido, riprova: ");
			return readLine(msg);
		}
	}
	/**
	 * Metodo con argomenti che gestisce l'input da tastiera attraverso
	 * buffered reader.
	 * @param msg la stringa che da leggere.
	 * @return la stringa convertita in intero.
	 */
	private static int readInt(String msg) {
		try {
			System.out.print(msg);
			return Integer.parseInt(br.readLine());
		} catch (IOException | NumberFormatException e) {
			return readInt("input non valido, deve essere un intero! " + msg);
		}
	}

	/**
	 * Permette ad un utente non autenticato di registrarsi.
	 * @return un oggetto di tipo LoggedUser con i dati presi in input.
	 */
	private static AbstractUser registerUser() {
		String firstName = readLine("Inserisci il nome: ");
		String lastName = readLine("Inserisci il cognome: ");
		String CF = readLine("Inserisci il codice fiscale: ");
		System.out.println("Inserisci il tuo indirizzo fisico: ");
		String tmp;
		boolean correct = false;
		do {
			tmp = (readLine("Inserisci il qualificatore (VIA, VIALE, PIAZZA): ")).toUpperCase();
			if(tmp.equals("VIA") || tmp.equals("VIALE") || tmp.equals("PIAZZA"))
				correct = true;
			else
				System.err.println("\nQualificatore non valido.\n");
		}while(!correct);
		AddressQualifier aq = AddressQualifier.valueOf(tmp);
		String streetName = readLine("Inserisci il nome della via: ");
		String civicNum = readLine("Inserisci il numero civico: ");
		String city = readLine("Inserisci la citta': ");
		String province = readLine("Inserisci la provincia: ");
		Address address = new Address(aq, streetName, civicNum, city, province);
		String email = readLine("Inserisci il tuo indirizzo email: ");
		String userid;
		do {
			userid = readLine("Username: ");
		}while(alreadyExists(userid));
		String password = readLine("Inserisci una password: ");
		LoggedUser tmpUser = new LoggedUser(firstName,lastName,CF,address,email,userid,password);
		return tmpUser;
	}

	/**
	 * Stampa tutti gli elementi di una determinata lista esplicitandone l'indice per un' eventuale selezione.
	 * @param lista lista di oggetti.
	 */
	private static void printResult(LinkedList<? extends Object> lista){
		int i = 1;
		System.out.println();
		for (Object o : lista) {
			System.out.println(i + ") " + o.toString());
			i++;
		}
	}

	/**
	 * Restituisce la canzone nella posizione n-1.
	 * @param lista lista di canzoni.
	 * @param n indice della lista.
	 * @return null, se n >= 0, la canzone all'indice n-1 altrimenti.
	 */
	private static Song selectSong(LinkedList<Song> lista, int n){
		if(n <= 0 || n>lista.size())
			return null;
		else
			return lista.get(n-1);
	}

	/**
	 * Mostra il prospetto riassuntivo di un determinato brano.
	 * @param brano il brano.
	 */
	private static void showSongEmotions(Song brano){
		LinkedList<Feedback> feedbacks = brano.getFeedback();
		if(!feedbacks.isEmpty())
			printResult(feedbacks);
		else
			System.out.println(("Ancora nessun feedback per questo brano"));
	}

	/**
	 * Cerca i brani per nome e li stampa su console.
	 * @param user l'utente che cerca i brani.
	 */
	private static void searchByName(AbstractUser user){
		do {
			LinkedList<Song> titlesResult = user.findSong(readLine("Inserisci il nome della canzone: "));
			if (titlesResult.isEmpty())
				System.out.println("Nessun brano corrisponde alla ricerca\n");
			else {
				printResult(titlesResult);
				int n = readInt("\nSelezionare il numero del brano per vederne i feedback " +
						"[0) per tornare indietro]: ");
				Song brano = selectSong(titlesResult, n);
				if (brano != null)
					showSongEmotions(brano);
			}
		} while (!(readLine("Cercare altro? (Y,N)")).equalsIgnoreCase("N"));
		System.out.println("Torno al menu'");
	}

	/**
	 * Cerca i brani per autore e anno di pubblicazione e li stampa su console.
	 * @param user l'utente che cerca i brani.
	 */
	private static void searchByAuthor(AbstractUser user){
		do {
			String author = readLine("Inserisci il nome dell'autore: ");
			int year = readInt("Inserisci l'anno di pubblicazione: ");
			LinkedList<Song> namesResult = user.findSong(author, year);
			if (namesResult.isEmpty())
				System.out.println("\nNessun brano corrisponde alla ricerca");
			else {
				printResult(namesResult);
				int n = readInt("\nSelezionare il numero del brano per vederne i feedback " +
						"[0) per tornare indietro]: ");
				Song brano = selectSong(namesResult, n);
				if (brano != null)
					showSongEmotions(brano);
			}
		} while (!(readLine("\nCercare altro? (Y,N)")).equalsIgnoreCase("N"));
		System.out.println("Torno al menu'");
	}

	/**
	 * Crea una nuova playlist per l'utente parametro.
	 * @param user l'utente che registra una nuova playlist.
	 */
	private static void registerPlaylist(AbstractUser user) {
		String i, pName;
		int n;
		Playlist p;
		Song brano;
		LinkedList<Song> songs;
		System.out.println("Elenco delle tue playlist:\n");
		for (String s : ((LoggedUser) user).getAllPlaylistname())
			System.out.println(s);
		do {
			pName = readLine("\nInserisci il nome della playlist da creare [0) per annullare]: ");
			if (pName.equals("0")) {
				System.out.println("\nCreazione annullata.\n");
				return;
			}
		} while (alreadyExists((LoggedUser) user, pName));
		p = new Playlist(((LoggedUser) user).getId(), pName);
		do {
			do {
				i = readLine("\n[0) per annullare]\n" +
						"1) per inserire brani cercandoli per titolo\n" +
						"2) per inserire brani cercandoli per autore e anno");
			} while ((!i.equals("1")) && (!i.equals("2")) && (!i.equals("0")));
			if (i.equals("0"))
				break;
			else if (i.equals("1"))
				songs = user.findSong(readLine("\nInserisci il nome della canzone: "));
			else {
				String author = readLine("\nInserisci il nome dell'autore: ");
				int year = readInt("Inserisci l'anno di pubblicazione: ");
				songs = user.findSong(author, year);
			}
			if (songs.isEmpty())
				System.out.println("\nNessun brano trovato...");
			else {
				printResult(songs);
				n = readInt("\nSelezionare il numero del brano da aggiungere " +
						"[0) per tornare indietro]: ");
				brano = selectSong(songs, n);
				if (brano != null)
					p.addSong(brano);
			}
		} while (!(readLine("\nCercare altro da aggiungere? (Y,N)")).equalsIgnoreCase("N"));
		if (p.isEmpty())
			System.out.println("\nCreazione playlist annullata. Non contiene brani");
		else {
			((LoggedUser) user).addPlaylist(p);
			System.out.println("\nPlaylist creata con successo!");
		}
	}

	/**
	 * Assegna un nuovo feedback al brano passato come parametro.
	 * @param user l'utente che valuta il brano.
	 * @param brano il brano che viene valutato.
	 */
	private static void insertSongEmotion(AbstractUser user, Song brano){
		System.out.println("\n" + brano.getTitle() + ", quale emozione ti ha fatto provare?");
		for(Emotions e: Emotions.values())
			System.out.println(String.format("%d) %s: %s",e.ordinal(),e,e.getEXplanation()));
		System.out.println("[Qualsiasi altro input per annullare la valutazione]");
		String scelta = readLine();
		Emotions emotion;
		try {
			emotion = Emotions.values()[Integer.parseInt(scelta)];
		}catch (NumberFormatException | IndexOutOfBoundsException e){
			System.out.println("\nValutazione annullata.\n");
			return;
		}
		Feedback feedback;
		if (!song_emotions.containsKey(brano.getId() + '#' + emotion)) {
			feedback = new Feedback(brano.getId(), emotion);
		}
		else
			feedback = Feedback.fromCSV(song_emotions.get(brano.getId() + '#' + emotion));

		if(feedback.alreadyValued(((LoggedUser)user).getId())){
			System.out.println("\nHai gia' valutato il tag emozionale " + emotion +
					" per questo brano!\n");
			return;
		}
		int score;
		String comment;
		do {
			score = readInt("Inserisci una valutazione da 1 a 5: ");
		}while (score < 1 || score > 5);
		do {
			comment = readLine("1)Lascia un commento\n0)Salta\n");
		} while (!comment.equals("1") && !comment.equals("0"));
		if(comment.equals("0")){
			comment = ((LoggedUser)user).getId() + '@' + "";
		}
		else {
			do {
				comment = readLine("\nCommento [max 256 caratteri]: ");
				if (comment.length() > 256)
					System.err.println("Commento troppo lungo (> 256 caratteri). Riprova.");
			}while(comment.length() > 256);
			comment = ((LoggedUser)user).getId() + '@' + comment;
		}
		feedback.addScore(score);
		feedback.addComment(((LoggedUser) user).getId(), comment);
		String serializedFeedback = feedback.toCSVString();
		song_emotions.put(brano.getId() + "#" + emotion, serializedFeedback);
		System.out.println("\nGrazie per il commento!");
	}

	/**
	 * Verifica se un utente è già registrato tramite l'id di quest'ultimo.
	 * @param userId l'identificativo dell'utente.
	 * @return true, se è già registrato, false altrimenti
	 */
	public static boolean alreadyExists(String userId){
		if (reg_users.containsKey(userId)) {
			System.out.println("Username gia' in uso! Scegline un altro");
			return true;
		}
		return false;
	}

	/**
	 * Verifica se una playlist con un dato nome è già stata creata per un utente specificato.
	 * @param user un oggetto di tipo LoggedUser.
	 * @param plName il nome della playlist.
	 * @return true, se esiste già, false altrimenti
	 */
	public static boolean alreadyExists(LoggedUser user, String plName) {
		String tmp = user.getId() + "<SEP>" + plName;
		if(uid_plnameANDsongs.containsKey(tmp)) {
			System.out.println("\nPlaylist gia esistente!");
			return true;
		}
		return false;
	}

	/**
	 * Salva tutto il contenuto delle mappe nei rispettivi file.
	 */
	private static void saveAllHashMaps() {
		DataHandler.saveOnFile(REGISTERED_USER_PATH, reg_users);
		DataHandler.saveOnFile(PLAYLIST_PATH, uid_plnameANDsongs);
		DataHandler.saveOnFile(EMOTIONS_PATH, song_emotions);
	}
}