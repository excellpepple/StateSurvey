public class CancelledSurveyException extends Exception{
    public CancelledSurveyException(String message, Throwable cause){
        super(message, cause);

    }
    public CancelledSurveyException(String message){
        super(message);

    }
    public CancelledSurveyException(){
        super("Your survey was cancelled.");
    }
}
