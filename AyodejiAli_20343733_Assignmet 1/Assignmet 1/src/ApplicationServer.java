import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
    public static void main(String[] args) {
        try {
            ApplicationHandler handler = new ApplicationHandlerImpl();
            // Create an RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ApplicationService", handler);
            System.out.println("Server is running and ApplicationService is registered.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
