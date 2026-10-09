# Gestione delle eccezioni in Java

Progetto Maven associato alle slide **Gestione delle eccezioni** del laboratorio di Algoritmi e Strutture Dati. Gli esempi distinguono la **fine del file attesa** dagli **errori di formato** e dagli **errori di I/O**.

## Obiettivi didattici

- Distinguere situazioni previste (`Coin.read` restituisce `false` alla fine del file) da situazioni eccezionali (`EOFException` se manca il nome di una moneta).
- Distinguere eccezioni **controllate** (`IOException`, `EOFException`) e **non controllate** (`NumberFormatException`, `IllegalArgumentException`, `NullPointerException`).
- Comprendere la differenza fra `throw` (lancio) e `throws` (dichiarazione di propagazione).
- Seguire la propagazione `Coin.read` → `Purse.read` → `Purse.readFile` → `Main`.
- Studiare `try`/`catch` e `finally`, che chiude il lettore anche se la lettura fallisce.
- Capire perché la business logic non deve scegliere come comunicare un errore all'utente: è il front-end `Main` a gestirlo.
- Verificare che `Coin.read` non lasci uno stato parzialmente aggiornato quando la seconda riga è errata.

## Classi

- **Coin**: moneta con valore e nome; `read(BufferedReader)` legge due righe e restituisce `false` soltanto quando l'EOF precede una nuova moneta.
- **Purse**: collezione di monete; `read` delega a `Coin`, `readFile` apre e chiude il file con `finally`; `add`, `getTotal` e `toString` gestiscono il borsellino.
- **Main**: esempio di front-end interattivo con `JOptionPane`; intercetta gli errori e propone un nuovo tentativo.
- **InsufficientFundException**: esempio autonomo di eccezione personalizzata non controllata, non usata dal borsellino.
- **Coins**: costanti relative ad alcune monete americane; i campi delle interfacce sono implicitamente `public static final`.

## Formato dei dati

Una moneta occupa **due righe**: un valore `double`, poi il nome. File di esempio in `src/main/resources/`: `purse1.txt` (valido), `purse1_number_error.txt` (numero errato), `purse1_unexpected_end.txt` (nome mancante). Una riga vuota non è una fine del file: non è una moneta valida.

**Attenzione:** `Purse.read` aggiunge le monete già lette; se una moneta successiva fallisce, quelle precedenti restano nel borsellino. Non è un'operazione atomica. `Coin.read`, invece, aggiorna i campi solo dopo aver letto entrambe le righe.

## Esecuzione

Dalla radice del progetto:

```sh
mvn test
```

Per il front-end, eseguire `Main.main` dall'IDE e indicare, per esempio, `src/main/resources/purse1.txt` (relativo alla directory del progetto). Se si sceglie Cancel, il programma termina senza errore.

## Javadoc

```sh
javadoc -d docs -sourcepath src/main/java -subpackages it.unicam.cs.asdl.slides.gestionedelleeccezioni
```

La Javadoc descrive le condizioni d'uso e le eccezioni osservabili dall'API. I commenti nel corpo dei metodi sottolineano il percorso delle eccezioni e i punti in cui cambia lo stato.
