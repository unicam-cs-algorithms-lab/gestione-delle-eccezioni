package it.unicam.cs.asdl.slides.gestionedelleeccezioni;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;

/**
 * Front-end interattivo che dimostra la gestione competente delle eccezioni.
 * La business logic propaga gli errori, mentre questo metodo li comunica
 * all'utente e consente di riprovare. La cancellazione e' un evento previsto.
 *
 * @author Luca Tesei
 *
 */
public class Main {

    public static void main(String[] args) {
        boolean done = false;
        // I catch piu' specifici devono precedere quelli piu' generali.
        /*
         * Inserire il nome completo a partire dalla cartella del progetto, ad
         * esempio
         * "src/main/resources/purse1.txt"
         */
        String fileName = JOptionPane.showInputDialog("Enter File name");
        // Cancel non e' un errore: termina normalmente l'interazione.
        if (fileName == null)
            return;
        while (!done) {
            // Installo il gestore di tutte le possibili eccezioni che possono
            // essere lanciate e comunico il problema all'utente sullo standard
            // output
            try {
                Purse myPurse = new Purse();
                myPurse.readFile(fileName);
                System.out.println(myPurse.toString());
                System.out.println("Total: " + myPurse.getTotal());
                done = true;
            } catch (EOFException e) {
                /* L'ordine dei catch conta: EOFException è sottoclasse di
                   IOException e quindi il catch va messo prima di quello
                   di IOException, altrimenti EOFException viene
                   intercettata dal catch di IOException
                 */
                System.out.println(
                        "Il file passato si interrompe inaspettatamente");
            } catch (IOException e) {
                System.out.println("Input/Output error: " + e.getMessage());
            } catch (NumberFormatException e) {
                /* Anche in questo caso l'ordine dei catch conta:
                NumberFormatException è sottoclasse di
                IllegalArgumentException e quindi il catch va messo prima
                di quello di IllegalArgumentException, altrimenti
                NumberFormatException viene intercettata dal catch di
                IllegalArgumentException
                 */
                System.out
                        .println(
                                "Error reading the value: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Dati non validi: " + e.getMessage());
            }
            if (!done) {
                fileName = JOptionPane
                        .showInputDialog("Try another file or Cancel to exit");
                if (fileName == null)
                    done = true;
            }
        }
    }
}
