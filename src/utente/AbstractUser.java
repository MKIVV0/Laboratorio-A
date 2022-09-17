/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package utente;

import emotionalsongs.EmotionalSongs;
import emotionalsongs.Song;

import java.util.LinkedList;

/**
 * Classe astratta rappresentante un utente generico e contenente
 * le funzioni comuni a tutti i tipi di utente.
 */
public abstract class AbstractUser {
    /**
     * Costruttore vuoto.
     */
    public AbstractUser(){}

    /**
     * Cerca le canzoni il cui titolo contiene la stringa passata come parametro.
     * @param title titolo della canzone.
     * @return lista di canzoni trovate.
     */
    public LinkedList<Song> findSong(String title){
        LinkedList<Song> tmp = new LinkedList<>();
        for(String b: EmotionalSongs.system_songs.values()) {
            String[] s = b.split("#");
            if(s[s.length-1].toLowerCase().contains(title.toLowerCase()))
                tmp.add(Song.fromCSV(b));
        }
        return tmp;
    }

    /**
     * Cerca le canzoni dell'autore e scritte nell'anno passati come parametri.
     * @param author autore della canzone.
     * @param year anno di rilascio della canzone.
     * @return lista di canzoni trovate.
     */
    public LinkedList<Song> findSong(String author, int year){
        LinkedList<Song> tmp = new LinkedList<>();
        String[] authortmp = author.split(" ");
        for(String b: EmotionalSongs.system_songs.values()) {
            String[] s = b.split("#");
            if(authortmp == null)
                continue;
            else if (authortmp.length < 2) {
                if ((s[2].equalsIgnoreCase(authortmp[0]) || s[3].equalsIgnoreCase(authortmp[0])) && Integer.parseInt(s[0])  == year)
                    tmp.add(Song.fromCSV(b));
            }
            else if (s[2].equalsIgnoreCase(authortmp[0]) && s[3].equalsIgnoreCase(authortmp[1]) && Integer.parseInt(s[0])  == year)
                tmp.add(Song.fromCSV(b));
        }
        return tmp;
    }

}
