import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;

public class App {

    static String url = "jdbc:mysql://localhost:3306/tarea";
    static String user = "root";
    static String password = "admin";

    public static void main(String[] args) throws Exception {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            EstudianteServices estudianteServices = new EstudianteServices();
            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("MENU PRINCIPAL");
                System.out.println("1. INSERTAR ESTUDIANTE");
                System.out.println("2. ACTUALIZAR ESTUDIANTE");
                System.out.println("3. ELIMINAR ESTUDIANTE");
                System.out.println("4. CONSULTAR ESTUDIANTES");
                System.out.println("5. CONSULTAR ESTUDIANTE POR EMAIL");
                System.out.println("6. SALIR");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1 -> estudianteServices.insertarEstudiante(connection);
                    case 2 -> estudianteServices.actualizarEstudiante(connection);
                    case 3 -> estudianteServices.eliminarEstudiante(connection);
                    case 4 -> estudianteServices.consultarTodos(connection);
                    case 5 -> estudianteServices.consultarPorCorreo(connection);
                    case 6 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida");
                }

            } while (opcion != 6);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
