package it.unicam.cs.asdl2526.slides.gestionedelleeccezioni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Un oggetto della classe Purse è un borsellino che può contenere un certo
 * numero di monete.
 * 
 * @author Luca Tesei
 *
 */
public class Purse {

    private List<Coin> coins;

    /**
     * Crea un borsellino vuoto.
     */
    public Purse() {
        this.coins = new ArrayList<Coin>();
    }

    /**
     * Legge da un file di testo il contenuto del borsellino. Il file deve
     * contenere un numero pari di righe. Nelle righe pari deve esserci il
     * valore della moneta e nelle righe dispari deve esserci il nome della
     * moneta.
     * 
     * @param fileName
     *                     il nome del file da leggere (percorso completo o
     *                     relativo)
     * @throws IOException
     *                         se c'è un errore nel file o nella lettura del
     *                         file
     */
    public void readFile(String fileName) throws IOException {
        BufferedReader in = null;
        try {
            // apre il file e crea un buffered reader
            in = new BufferedReader(new FileReader(fileName));
            // chiama la lettura delle monete
            this.read(in);
        } finally {
            // chiude il file se era stato aperto
            if (in != null)
                in.close();
        }
    }

    /**
     * Legge da uno stream di caratteri la descrizione delle monete di questo
     * borsellino. Aggiunge le monete una ad una man mano che sono lette.
     * 
     * @param in
     *               il buffered reader da cui leggere le monete
     * @throws IOException
     *                         se c'è un errore nel file o nella lettura del
     *                         file
     */
    public void read(BufferedReader in) throws IOException {
        boolean done = false;
        // Ciclo fino a quando il file non è terminato
        while (!done) {
            // creo la moneta vuota
            Coin c = new Coin();
            // leggo il contenuto
            if (c.read(in))
                // lettura corretta
                this.add(c);
            else
                // le monete sono terminate
                done = true;
        }
    }

    /**
     * Restituisce il valore totale delle monete di questo borsellino.
     * 
     * @return la somma del valore delle monete di questo borsellino
     */
    public double getTotal() {
        double total = 0;
        // scorre tutte le monete
        for (Coin c : this.coins) {
            total += c.getValue();
        }
        return total;
    }

    /**
     * Inserisce una nuova moneta in questo borsellino.
     * 
     * @param c
     *              la moneta da inserire
     * 
     * @throws NullPointerException
     *                                  se la moneta passata è null
     */
    public void add(Coin c) {
        if (c == null)
            throw new NullPointerException(
                    "Tentativo di inserire una moneta null");
        this.coins.add(c);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("*** Purse Content ***\n");
        // scorre tutte le monete
        for (Coin c : this.coins) {
            sb.append(c.toString() + "\n");
        }
        sb.append("*** End Purse Content ***\n");
        return sb.toString();
    }

}