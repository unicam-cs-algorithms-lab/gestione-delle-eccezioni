package it.unicam.cs.asdl.slides.gestionedelleeccezioni;

/**
 * Esempio di eccezione personalizzata non controllata. Estendendo
 * {@link RuntimeException}, non obbliga il chiamante a dichiarare
 * {@code throws} o a installare un gestore. Non e' utilizzata da
 * {@link Purse}: illustra la definizione di un tipo di errore di dominio.
 *
 * @author Luca Tesei
 *
 */
public class InsufficientFundException extends RuntimeException {

    private static final long serialVersionUID = 6741919390005964432L;

    /**
     * Costruisce l'eccezione.
     */
    public InsufficientFundException() {
        super();
    }

    /**
     * Costruisce l'eccezione con il messaggio associato. 
     *
     * @param message messaggio associato all'eccezione
     */
    public InsufficientFundException(String message) {
        super(message);
    }

}
