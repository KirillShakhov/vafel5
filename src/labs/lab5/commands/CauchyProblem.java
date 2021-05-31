package labs.lab5.commands;
import labs.models.ICommand;
import labs.modules.MenuModule;
import java.util.ArrayList;

public class CauchyProblem implements ICommand {
    @Override
    public String getMessage() {
        return "Решение ОДУ(задача Коши)";
    }

    @Override
    public void execute() {
        MenuModule menu = new MenuModule(true);
        menu.addCommand(new RungeKuttaMethod());
        menu.execute();
    }
}
