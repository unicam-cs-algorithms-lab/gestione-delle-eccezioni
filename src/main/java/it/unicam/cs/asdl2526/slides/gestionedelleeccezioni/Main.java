package it.unicam.cs.asdl2526.slides.gestionedelleeccezioni;

import java.io.EOFException;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Semplice classe di test interattivo per la lettura di monete da un file.
 * 
 * @author Luca Tesei
 *
 */
public class Main {

    public static void main(String[] args) {
        boolean done = false;
        /*
         * Inserire il nome completo a partire dalla cartella del progetto, ad
         * esempio
         * "src/main/java/resources/purse1.txt"
         */
        String fileName = JOptionPane.showInputDialog("Enter File name");
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
                System.out.println(
                        "Il file passato si interrompe inaspettatamente");
            } catch (IOException e) {
                System.out.println("Input/Output error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out
                        .println("Error reading the value: " + e.getMessage());
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
