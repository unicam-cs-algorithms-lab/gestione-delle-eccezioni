package it.unicam.cs.asdl.slides.gestionedelleeccezioni;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.Test;

/** Test didattici per EOF, propagazione e coerenza dello stato. */
public class ExceptionHandlingTest {
    private BufferedReader reader(String text) {
        return new BufferedReader(new StringReader(text));
    }

    @Test
    public void testLetturaCorrettaEFinePrevista() throws IOException {
        // Una moneta completa viene letta; l'EOF successivo e' previsto.
        Coin c = new Coin();
        BufferedReader in = reader("0.25\nQuarter\n");
        assertTrue(c.read(in));
        assertEquals(0.25, c.getValue(), 0.000001);
        assertEquals("Quarter", c.getDescription());
        assertFalse(c.read(in));
    }

    @Test
    public void testEOFInattesoNonModificaMoneta() throws IOException {
        // Se manca il nome, il metodo lancia EOFException e non modifica i campi.
        Coin c = new Coin(0.01, "Penny");
        assertThrows(EOFException.class, () -> c.read(reader("0.25\n")));
        assertEquals(0.01, c.getValue(), 0.000001);
        assertEquals("Penny", c.getDescription());
    }

    @Test
    public void testNumeroNonValidoNonModificaMoneta() {
        // parseDouble segnala un errore di formato con eccezione non controllata.
        Coin c = new Coin(0.05, "Nickel");
        assertThrows(NumberFormatException.class, () -> c.read(reader("pippo\nCoin\n")));
        assertEquals("Nickel", c.getDescription());
        assertEquals(0.05, c.getValue(), 0.000001);
    }

    @Test
    public void testNomeVuotoNonEUnaFineNormale() {
        // Una riga vuota per il nome non equivale a EOF tra monete.
        Coin c = new Coin();
        assertThrows(IllegalArgumentException.class, () -> c.read(reader("0.1\n   \n")));
    }

    @Test
    public void testPurseLetturaMultipla() throws IOException {
        // Purse delega a Coin.read e si ferma soltanto a EOF previsto.
        Purse p = new Purse();
        p.read(reader("0.25\nQuarter\n0.05\nNickel\n"));
        assertEquals(0.30, p.getTotal(), 0.000001);
        assertTrue(p.toString().contains("Quarter"));
    }

    @Test
    public void testPurseMantieneMoneteGiaLetteSeSegueErrore() {
        // Il metodo non e' atomico: una moneta valida e' gia' stata aggiunta.
        Purse p = new Purse();
        assertThrows(EOFException.class, () -> p.read(reader("0.25\nQuarter\n0.1\n")));
        assertEquals(0.25, p.getTotal(), 0.000001);
    }

    @Test
    public void testFileEsempi() throws IOException {
        // I file allegati distinguono un caso valido da due errori diversi.
        Purse p = new Purse();
        p.readFile("src/main/resources/purse1.txt");
        assertEquals(0.61, p.getTotal(), 0.000001);
        assertThrows(NumberFormatException.class, () -> new Purse().readFile("src/main/resources/purse1_number_error.txt"));
        assertThrows(EOFException.class, () -> new Purse().readFile("src/main/resources/purse1_unexpected_end.txt"));
    }

    @Test
    public void testArgomentiNull() {
        // Il contratto delle API non accetta lettori, nomi di file o monete null.
        assertThrows(NullPointerException.class, () -> new Coin().read(null));
        assertThrows(NullPointerException.class, () -> new Purse().read(null));
        assertThrows(NullPointerException.class, () -> new Purse().readFile(null));
        assertThrows(NullPointerException.class, () -> new Purse().add(null));
    }

    @Test
    public void testEqualsHashCodeETostring() {
        // Oggetti distinti ma logicamente uguali rispettano il contratto hash.
        Coin a = new Coin(0.25, "Quarter");
        Coin b = new Coin(0.25, "Quarter");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, new Coin(0.05, "Nickel"));
        assertTrue(a.toString().contains("Quarter"));
    }

    @Test
    public void testEccezionePersonalizzata() {
        // La nostra eccezione estende RuntimeException: e' unchecked.
        InsufficientFundsException e = new InsufficientFundsException("Saldo insufficiente");
        assertEquals("Saldo insufficiente", e.getMessage());
        assertTrue(e instanceof RuntimeException);
    }
}
