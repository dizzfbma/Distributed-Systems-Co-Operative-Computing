import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApplicationHandler extends Remote {
    long login(String username, String password) throws RemoteException, InvalidCredentialsException;
    ApplicationForm downloadApplicationForm(long sessionID) throws RemoteException, InvalidSessionIDException;
    void submitApplicationForm(long sessionID, ApplicationForm form) throws RemoteException, InvalidSessionIDException;
}
