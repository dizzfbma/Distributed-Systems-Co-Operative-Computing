import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApplicationForm extends Remote {
    String getFormInformation() throws RemoteException;
    int getTotalQuestions() throws RemoteException;
    String getQuestion(int questionNumber) throws RemoteException;
    void answerQuestion(int questionNumber, String answer) throws RemoteException;
    String getSummary() throws RemoteException;
}
