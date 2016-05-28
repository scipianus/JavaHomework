import java.io.Serializable;

class ServerResponse implements Serializable {
    private Object response;

    private Message initialMessage;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Message getInitialMessage() {
        return initialMessage;
    }

    public void setInitialMessage(Message initialMessage) {
        this.initialMessage = initialMessage;
    }
}