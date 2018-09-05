package com.ge.digital.gearbox.common.message;

public class MessageResourceFactory {
    
    private static MessageResource instance;

    private MessageResourceFactory() {
    }

    public synchronized static MessageResource getMessageResource() {
        if (instance == null) {
            instance = new PropertyMessage();
        }
        return instance;
    }
}
