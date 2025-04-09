import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;

public class ApplicationFormV1 extends UnicastRemoteObject implements ApplicationForm, Serializable {
    private HashMap<Integer, String> questions;
    private HashMap<Integer, String> answers;

    public ApplicationFormV1() throws RemoteException {
        questions = new HashMap<>();
        answers = new HashMap<>();

        // Define the questions for the application form
        questions.put(1, "Full Name:");
        questions.put(2, "Address:");
        questions.put(3, "Email:");
        questions.put(4, "Contact Number:");
        questions.put(5, "Personal Statement:");

        // Initialize answers with empty strings
        for (int i = 1; i <= questions.size(); i++) {
            answers.put(i, "");
        }
    }

    @Override
    public String getFormInformation() throws RemoteException {
        return "University College Application Form (Version 1)";
    }

    @Override
    public int getTotalQuestions() throws RemoteException {
        return questions.size();
    }

    @Override
    public String getQuestion(int questionNumber) throws RemoteException {
        if (!questions.containsKey(questionNumber)) {
            throw new RemoteException("Invalid question number.");
        }
        return questions.get(questionNumber);
    }

    @Override
    public void answerQuestion(int questionNumber, String answer) throws RemoteException {
        if (questions.containsKey(questionNumber)) {
            answers.put(questionNumber, answer);
        } else {
            throw new RemoteException("Invalid question number.");
        }
    }

    @Override
    public String getSummary() throws RemoteException {
        StringBuilder summary = new StringBuilder("Application Summary:\n");
        for (int i = 1; i <= questions.size(); i++) {
            summary.append(questions.get(i)).append(" ").append(answers.get(i)).append("\n");
        }
        return summary.toString();
    }

    // Optionally override toString() for debugging or file saving purposes
    @Override
    public String toString() {
        try {
            return getSummary();
        } catch (RemoteException e) {
            return "Error retrieving summary.";
        }
    }
}
