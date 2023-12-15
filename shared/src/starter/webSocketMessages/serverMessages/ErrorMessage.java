package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
    private final String errorMessage;

    public ErrorMessage(String message) {
        super(ServerMessageType.ERROR);
        this.errorMessage = message;
    }
}
