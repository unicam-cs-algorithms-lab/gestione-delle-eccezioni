package it.unicam.cs.asdl.slides.gestionedelleeccezioni;

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

    /** Restituisce un hash coerente con {@link #equals(Object)}. */
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

    /** Confronta valore e nome: due monete distinte possono essere logicamente uguali. */
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
     * Legge una moneta rappresentata da due righe consecutive: valore numerico
     * e nome. Una fine del file prima della prima riga indica normalmente
     * che non ci sono altre monete: in questo caso restituisce {@code false}.
     * Una fine del file dopo il valore e prima del nome e' invece inattesa
     * e viene segnalata con {@link EOFException} (eccezione controllata).
     * <p>
     * {@link Double#parseDouble(String)} puo' lanciare
     * {@link NumberFormatException}, eccezione non controllata. Il metodo
     * aggiorna lo stato soltanto dopo aver letto e validato entrambe le righe:
     * se fallisce, i campi rimangono invariati.
     *
     * @param in lettore da cui prelevare le righe
     * @return {@code true} se una moneta e' stata letta; {@code false} se
     *         la fine del file precede una nuova moneta
     * @throws NullPointerException se {@code in} e' {@code null}
     * @throws IOException se la lettura fallisce o manca il nome della moneta
     * @throws NumberFormatException se la prima riga non rappresenta un double
     * @throws IllegalArgumentException se il nome e' vuoto
     */
    public boolean read(BufferedReader in) throws IOException {
        if (in == null)
            throw new NullPointerException("Lettore nullo");
        String input = in.readLine();
        // EOF tra due monete: evento previsto, non eccezionale.
        if (input == null)
            return false;
        // Variabili locali: lo stato dell'oggetto non cambia se la lettura fallisce.
        double newValue = Double.parseDouble(input.trim());
        String newName = in.readLine();
        // EOF a meta' della descrizione: formato incompleto.
        if (newName == null)
            throw new EOFException("Nome della moneta mancante");
        newName = newName.trim();
        if (newName.isEmpty())
            throw new IllegalArgumentException("Nome della moneta vuoto");
        // i campi dell'oggetto sono aggiornati ora che tutti i valori sono
        // corretti e disponibili, altrimenti sarebbe stato eseguito un
        // aggiornamento parziale
        this.value = newValue;
        this.name = newName;
        return true;
    }

    /** @return una descrizione testuale, senza stamparla sullo standard output */
    @Override
    public String toString() {
        return "Coin [value=" + value + ", name=" + name + "]";
    }

}