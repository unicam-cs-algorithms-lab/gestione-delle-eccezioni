package it.unicam.cs.asdl.slides.gestionedelleeccezioni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Front-end dell'applicazione che legge da un file di testo le monete
 * da inserire in un borsellino e ne visualizza il valore totale.
 *
 * <p>
 * Questa versione mostra l'uso del costrutto try-with-resources,
 * introdotto in Java 7, per garantire la chiusura automatica delle
 * risorse che implementano l'interfaccia AutoCloseable.
 *
 * <p>
 * Il programma distingue le responsabilità:
 * <ul>
 *   <li>Il front-end interagisce con l'utente e gestisce le eccezioni.</li>
 *   <li>La classe Purse realizza la logica applicativa.</li>
 *   <li>Il BufferedReader viene chiuso automaticamente al termine
 *       del blocco try-with-resources.</li>
 * </ul>
 */
public class MainTryWithResources {

    public static void main(String[] args) {

        boolean done = false;

        String fileName = JOptionPane.showInputDialog(
                "Inserisci il nome del file contenente le monete");

        while (!done && fileName != null) {

            try {
                Purse myPurse = new Purse();

                /*
                 * La risorsa BufferedReader viene dichiarata
                 * nell'intestazione del try.
                 *
                 * Java garantisce la chiamata automatica a close()
                 * quando si esce dal blocco, sia normalmente sia
                 * a causa di un'eccezione.
                 */
                try (BufferedReader in =
                             new BufferedReader(new FileReader(fileName))) {

                    /*
                     * Il metodo read può propagare IOException
                     * o lanciare eccezioni non controllate.
                     *
                     * La classe Purse non deve preoccuparsi
                     * dell'interazione con l'utente.
                     */
                    myPurse.read(in);
                }

                /*
                 * Arriviamo qui soltanto se la lettura e la chiusura
                 * della risorsa sono terminate senza eccezioni.
                 */
                System.out.println(
                        "Valore totale delle monete: "
                                + myPurse.getTotal());

                done = true;

            } catch (IOException e) {

                /*
                 * Le eccezioni controllate di I/O vengono gestite
                 * dal front-end, che può informare l'utente.
                 */
                JOptionPane.showMessageDialog(
                        null,
                        "Errore durante la lettura del file:\n"
                                + e.getMessage(),
                        "Errore di I/O",
                        JOptionPane.ERROR_MESSAGE);

            } catch (NumberFormatException e) {

                /*
                 * Un valore numerico non valido produce una
                 * NumberFormatException, che è non controllata.
                 */
                JOptionPane.showMessageDialog(
                        null,
                        "Il file contiene un valore numerico non valido:\n"
                                + e.getMessage(),
                        "Errore di formato",
                        JOptionPane.ERROR_MESSAGE);
            }

            /*
             * Se la lettura non è riuscita, permettiamo all'utente
             * di scegliere un altro file.
             */
            if (!done) {
                fileName = JOptionPane.showInputDialog(
                        "Inserisci un altro file oppure premi Annulla");
            }
        }
    }
}