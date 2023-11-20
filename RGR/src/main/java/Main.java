import controller.Controller;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Menu menu = new Menu(controller);
        menu.drawFirstMenu();
    }
}
