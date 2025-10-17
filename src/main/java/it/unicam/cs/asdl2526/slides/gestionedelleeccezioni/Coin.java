package it.unicam.cs.asdl2526.slides.gestionedelleeccezioni;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

/**
 * Un oggetto di questa classe rappresenta una moneta degli USA con il suo
 * valore.
 */
public class Coin {

    private double value;

    private String name;

    /**
     * Crea una moneta con valori iniziali non significativi.
     */
    public Coin() {
        this.value = Double.NaN;
        this.name = null;
    }

    /**
     * Crea una moneta.
     * 
     * @param aValue
     *                   il valore della moneta
     * @param aName
     *                   il nome della moneta
     */
    public Coin(double aValue, String aName) {
        this.value = aValue;
        this.name = aName;
    }

    /**
     * @return il valore di questa moneta
     */
    public double getValue() {
        return this.value;
    }

    /**
     * @return la descrizione di questa moneta
     */
    public String getDescription() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Coin))
            return false;
        Coin other = (Coin) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(value) != Double
                .doubleToLongBits(other.value))
            return false;
        return true;
    }

    /**
     * Legge due righe da uno stream di caratteri cercando di interpretare la
     * prima riga come un valore e la seconda come un nome. Se la lettura ha
     * successo aggiorna il valore e la descrizione di questa moneta con i
     * valori letti.
     * 
     * @param in
     *               il buffered reader da cui leggere due righe
     * @return true se è stata letta una moneta, false se non ci sono ulteriori
     *         monete nel file
     * @throws IOException
     *                         se c'è un errore nella lettura del file o se il
     *                         file termina dopo aver letto una sola riga
     */
    public boolean read(BufferedReader in) throws IOException {
        String input = in.readLine();
        // Controllo se il file è finito o se contiene una riga vuota
        if (input == null || input.trim().isEmpty())
            // Non ci sono più monete da leggere
            // Segnalo con false in uscita
            return false;
        // provo a interpretare la linea come un double, può lanciare una
        // eccezione non controllata
        this.value = Double.parseDouble(input);
        // provo a leggere la descrizione
        this.name = in.readLine();
        // Controllo se il file è finito o se ho letto una riga vuota
        if (this.name == null || this.name.trim().isEmpty())
            // Situazione anomala non prevista!
            throw new EOFException("Coin name expected");
        // elimino eventuali spazi dal nome
        this.name = this.name.trim();
        return true;
    }

    @Override
    public String toString() {
        return "Coin [value=" + value + ", name=" + name + "]";
    }

}