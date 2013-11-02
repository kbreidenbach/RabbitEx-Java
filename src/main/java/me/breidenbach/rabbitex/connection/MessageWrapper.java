package me.breidenbach.rabbitex.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * User: Kevin E. Breidenbach
 * Date: 11/1/13
 * Time: 9:44 PM
 * © 2013 Kevin E. Breidenbach
 */
class MessageWrapper {
    static enum MessageType {
        MESSAGE, ERROR;
    }

    static enum ErrorAction {
        REQUEUE, REJECT
    }

    private final String message;
    private final MessageType messageType;

    private String errorExchange;
    private String errorSubject;
    private ErrorAction errorAction;

    MessageWrapper(String message) {
        this(message, null);
    }

    MessageWrapper(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType == null ? MessageType.MESSAGE : messageType;
    }

    String getMessage() { return message; }
    MessageType getMessageType() {return messageType; }

    void setErrorExchange(String errorExchange) { this.errorExchange = errorExchange; }
    String getErrorExchange() { return errorExchange; }

    void setErrorSubject(String errorSubject) { this.errorSubject = errorSubject; }
    String getErrorSubject() { return errorSubject; }

    void setErrorAction(ErrorAction errorAction) { this.errorAction = errorAction; }
    ErrorAction getErrorAction() { return errorAction; }

    String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    static MessageWrapper fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, MessageWrapper.class);
    }
}