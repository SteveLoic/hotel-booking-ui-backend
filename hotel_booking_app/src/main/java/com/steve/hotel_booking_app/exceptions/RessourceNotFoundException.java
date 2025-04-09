package com.steve.hotel_booking_app.exceptions;

public class RessourceNotFoundException extends RuntimeException {

    private String ressourceName;

    private String field;

    private String fieldName;
    private Long fieldId;


    public RessourceNotFoundException(String ressourceName, String field, String fieldName, Long fieldId) {
        super(String.format("% not found with %s: %s", ressourceName, field, fieldName));
        this.ressourceName = ressourceName;
        this.field = field;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public RessourceNotFoundException(String ressourceName, String field, Long fieldId) {
        super(String.format("% not found with %s: %s", ressourceName, field, fieldId));
        this.ressourceName = ressourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public RessourceNotFoundException(String ressourceName) {
        super(String.format("User with email % not found", ressourceName));
        this.ressourceName = ressourceName;
    }


}
