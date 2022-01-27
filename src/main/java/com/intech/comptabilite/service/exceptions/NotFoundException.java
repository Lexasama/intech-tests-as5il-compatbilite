package com.intech.comptabilite.service.exceptions;


/**
 * Classe des Exception de type "Donnée non trouvée"
 */
public class NotFoundException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    public NotFoundException() {
        super();
    }

     public NotFoundException(String pMessage) {
        super(pMessage);
    }

    public NotFoundException(Throwable pCause) {
        super(pCause);
    }

    public NotFoundException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
