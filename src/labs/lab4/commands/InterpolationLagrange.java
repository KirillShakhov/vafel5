package labs.lab4.commands;

import labs.models.ICommand;
import labs.models.IFuncX;
import labs.lab4.LagrangianIntegration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InterpolationLagrange implements ICommand {
    Scanner scanner = new Scanner(System.in);
    @Override
    public String getMessage() {
        return "Интерполирование многочленом Лагранжа";
    }


    @Override
    public void execute() {
        System.out.println("Интерполирование многочленом Лагранжа:");
        Map<String, IFuncX> funcs = new HashMap<>();
        // 1
        funcs.put("sin(x)", Math::sin);
        // 2
        funcs.put("2x", x -> 2*x);
        //

        /*
        Вывод и обработка ввода. Не трогать.
        */
        int i = 1;
        ArrayList<String> keys = new ArrayList<>();
        for (Map.Entry<String, IFuncX> entry : funcs.entrySet()) {
            System.out.println((i++) + ". " + entry.getKey());
            keys.add(entry.getKey());
        }
        String str = scanner.nextLine();
        try {
            IFuncX func1 = funcs.get(keys.get(Integer.parseInt(str) - 1));
            LagrangianIntegration.solve(func1);
        } catch (Exception e) {
            System.out.println("Нет такого уравнения");
        }
    }
}
