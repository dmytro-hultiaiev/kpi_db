import controller.Controller;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){
            Controller controller = new Controller(sessionFactory);
            Menu menu = new Menu(controller);
            menu.drawFirstMenu();
        }
    }
}
