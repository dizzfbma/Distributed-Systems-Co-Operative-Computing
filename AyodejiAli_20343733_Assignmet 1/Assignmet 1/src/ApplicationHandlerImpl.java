import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ApplicationHandlerImpl extends UnicastRemoteObject implements ApplicationHandler {
    private HashMap<Long, String> activeSessions;
    private final String VALID_USERNAME = "ayodeji";
    private final String VALID_PASSWORD = "ali";

    public ApplicationHandlerImpl() throws RemoteException {
        activeSessions = new HashMap<>();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException {
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            long sessionID = System.currentTimeMillis();
            activeSessions.put(sessionID, username);
            System.out.println("User logged in successfully. Session ID: " + sessionID);
            return sessionID;
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionID) throws RemoteException, InvalidSessionIDException {
        if (!activeSessions.containsKey(sessionID)) {
            throw new InvalidSessionIDException("Invalid session ID.");
        }
        System.out.println("Application form downloaded for session: " + sessionID);
        return new ApplicationFormV1();
    }

    @Override
    public void submitApplicationForm(long sessionID, ApplicationForm form) throws RemoteException, InvalidSessionIDException {
        if (!activeSessions.containsKey(sessionID)) {
            throw new InvalidSessionIDException("Invalid session ID.");
        }

        // Build filename based on the first two words of the applicant's.
        String summary = form.getSummary();
        String firstName = "ayodeji";
        String lastName = "ali";
        // parsing for first question for first name and last name
        try {
            String[] lines = summary.split("\n");
            if (lines.length > 0) {
                String[] parts = lines[0].split(":");
                if (parts.length > 1) {
                    String name = parts[1].trim();
                    String[] nameParts = name.split(" ");
                    if (nameParts.length >= 2) {
                        firstName = nameParts[0];
                        lastName = nameParts[1];
                    } else {
                        firstName = nameParts[0];
                    }
                }
            }
        } catch (Exception e) {
            // Fallback values used if parsing fails.
        }
        String filename = firstName + "_" + lastName + "_" + System.currentTimeMillis() + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(summary);
            System.out.println("Application form submitted and saved to " + filename);
        } catch (IOException e) {
            throw new RemoteException("Error saving application form.", e);
        }
    }
}
