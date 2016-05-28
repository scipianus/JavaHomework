import java.io.Serializable;

class Message implements Serializable {
    enum MsgType {MSG_INVALID, MSG_DISCONNECT, MSG_NICKNAME, MSG_LIST, MSG_TALK, MSG_BROADCAST}

    ;

    private MsgType type;
    private String message;
    private String receiver;

    Message() {
        type = MsgType.MSG_INVALID;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}