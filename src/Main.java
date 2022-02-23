import business.DeliveryService;
import dao.Serializator;
import model.Administrator;
import model.Client;
import model.Employee;
import model.RegisteredUsers;
import presentation.EmployeeView;
import presentation.LogIn;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        deserializare();
//        Client c1=new Client("George","client","client");
//        RegisteredUsers.addUser(c1);
//        Administrator a1=new Administrator("admin","admin");
//        RegisteredUsers.addUser(a1);
//        Employee e1=new Employee("employee","employee");
//        RegisteredUsers.addUser(e1);
        EmployeeView empl=new EmployeeView("employee");
        empl.setVisibility(false);
        DeliveryService.addObs(empl);
        new LogIn(empl);
        serializareStaff();

    }
    private static void serializareStaff() {
        Serializator.serializeAdmins();
        Serializator.serializeEmployees();
    }

    private static void deserializare() {
        Serializator.deserializeOrders();
        Serializator.deserializeClients();
        Serializator.deserializeAdmins();
        Serializator.deserializeEmployees();
        Serializator.deserializeBaseProducts();
        Serializator.deserializeCompositeProducts();
    }
}
