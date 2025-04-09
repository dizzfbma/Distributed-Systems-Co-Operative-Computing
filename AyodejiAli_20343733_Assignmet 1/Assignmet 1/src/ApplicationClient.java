import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ApplicationClient {
    public static void main(String[] args) {
        try {
            // Connect to the RMI registry and look up the service
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ApplicationHandler handler = (ApplicationHandler) registry.lookup("ApplicationService");

            Scanner scanner = new Scanner(System.in);

            // User login
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            long sessionID = handler.login(username, password);
            System.out.println("Login successful. Session ID: " + sessionID);

            // Download application form
            ApplicationForm form = handler.downloadApplicationForm(sessionID);
            System.out.println(form.getFormInformation());

            // Loop through each question and get user input
            int totalQuestions = form.getTotalQuestions();
            for (int i = 1; i <= totalQuestions; i++) {
                System.out.println("Question " + i + ": " + form.getQuestion(i));
                String answer = scanner.nextLine();
                form.answerQuestion(i, answer);
            }

            // Submit the completed application form
            handler.submitApplicationForm(sessionID, form);
            System.out.println("Application submitted successfully.");
            scanner.close();
        } catch (InvalidCredentialsException | InvalidSessionIDException ice) {
            System.err.println("Error: " + ice.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
