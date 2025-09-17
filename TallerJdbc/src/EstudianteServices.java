import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class EstudianteServices {

    public void insertarEstudiante(Connection conn) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Estado civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
        String estadoCivil = sc.nextLine().toUpperCase();

        String sql = "INSERT INTO Estudiante (Nombre, Apellido, Correo, Edad, EstadoCivil) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, nombre);
        stm.setString(2, apellido);
        stm.setString(3, correo);
        stm.setInt(4, edad);
        stm.setString(5, estadoCivil);

        int res = stm.executeUpdate();
        System.out.println(res > 0 ? "Estudiante insertado." : "Error al insertar.");
    }

    public void actualizarEstudiante(Connection conn) throws Exception {
    Scanner sc = new Scanner(System.in);

   
    System.out.println("\nListado de estudiantes:");
    consultarTodos(conn);


    System.out.print("\nIngrese el ID del estudiante que desea actualizar: ");
    int id = sc.nextInt();
    sc.nextLine(); 


    String checkSql = "SELECT * FROM Estudiante WHERE ID = ?";
    PreparedStatement checkStm = conn.prepareStatement(checkSql);
    checkStm.setInt(1, id);
    ResultSet rs = checkStm.executeQuery();

    if (!rs.next()) {
        System.out.println("No se encontró un estudiante con ese ID.");
        return;
    }

    int opcion;
    do {
        System.out.println("\n¿Qué campo desea actualizar?");
        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. Correo");
        System.out.println("4. Edad");
        System.out.println("5. Estado Civil");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
        opcion = sc.nextInt();
        sc.nextLine(); 

        String sql = "";
        PreparedStatement stm = null;
        int res = 0;

        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                String nombre = sc.nextLine();
                sql = "UPDATE Estudiante SET Nombre = ? WHERE ID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, nombre);
                stm.setInt(2, id);
                res = stm.executeUpdate();
                break;
            case 2:
                System.out.print("Nuevo apellido: ");
                String apellido = sc.nextLine();
                sql = "UPDATE Estudiante SET Apellido = ? WHERE ID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, apellido);
                stm.setInt(2, id);
                res = stm.executeUpdate();
                break;
            case 3:
                System.out.print("Nuevo correo: ");
                String correo = sc.nextLine();
                sql = "UPDATE Estudiante SET Correo = ? WHERE ID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, correo);
                stm.setInt(2, id);
                res = stm.executeUpdate();
                break;
            case 4:
                System.out.print("Nueva edad: ");
                int edad = sc.nextInt();
                sc.nextLine();
                sql = "UPDATE Estudiante SET Edad = ? WHERE ID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, edad);
                stm.setInt(2, id);
                res = stm.executeUpdate();
                break;
            case 5:
                System.out.print("Nuevo estado civil (SOLTERO, CASADO, VIUDO, UNION_LIBRE, DIVORCIADO): ");
                String estadoCivil = sc.nextLine().toUpperCase();
                sql = "UPDATE Estudiante SET EstadoCivil = ? WHERE ID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, estadoCivil);
                stm.setInt(2, id);
                res = stm.executeUpdate();
                break;
            case 6:
                System.out.println("Saliendo del menú de actualización...");
                break;
            default:
                System.out.println("Opción inválida.");
        }

        if (opcion >= 1 && opcion <= 5) {
            System.out.println(res > 0 ? "Campo actualizado correctamente." : "No se pudo actualizar.");
        }

    } while (opcion != 6);
}


    public void eliminarEstudiante(Connection conn) throws Exception {

    System.out.println("Listado de estudiantes actuales:");
    consultarTodos(conn); 

    
    Scanner sc = new Scanner(System.in);
    System.out.print("\nDigite el ID del estudiante a eliminar: ");
    int id = sc.nextInt();

    
    String sql = "DELETE FROM Estudiante WHERE ID = ?";
    PreparedStatement stm = conn.prepareStatement(sql);
    stm.setInt(1, id);

    int res = stm.executeUpdate();
    System.out.println(res > 0 ? "Estudiante eliminado." : "No se encontró el estudiante con ese ID.");
}

    public void consultarTodos(Connection conn) throws Exception {
        String sql = "SELECT * FROM Estudiante";
        var stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            System.out.printf("ID: %d | %s %s | Correo: %s | Edad: %d | Estado civil: %s\n",
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Correo"),
                    rs.getInt("Edad"),
                    rs.getString("EstadoCivil"));
        }
    }

    public void consultarPorCorreo(Connection conn) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Correo a buscar: ");
        String correo = sc.nextLine();

        String sql = "SELECT * FROM Estudiante WHERE Correo=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, correo);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            System.out.printf("ID: %d | %s %s | Correo: %s | Edad: %d | Estado civil: %s\n",
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getString("Apellido"),
                    rs.getString("Correo"),
                    rs.getInt("Edad"),
                    rs.getString("EstadoCivil"));
        } else {
            System.out.println("No se encontró el estudiante.");
        }
    }
}
