package labs.lab5.commands;
import labs.lab4.LagrangianIntegrationMath;
import labs.lab5.AdamsMethodMath;
import labs.models.IFuncX;
import labs.models.IFuncXY;
import labs.models.Point;
import labs.modules.GraphModule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdamsMethod {
    public static void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Метод Адамса:");
        Map<String, IFuncXY> funcs = new HashMap<>();
        // 1
        funcs.put("y' + xy - x^2 = 0", (x, y) -> -(x*y)+Math.pow(x, 2));
        // 2
        funcs.put("yy' - x = 0", (x, y) -> x/y);
        //
        funcs.put("xy' + y - e^x = 0", (x, y) -> (-y+Math.pow(Math.E, x))/2);
        /*
        Вывод и обработка ввода. Не трогать.
        */
        int i = 1;
        ArrayList<String> keys = new ArrayList<>();
        for (Map.Entry<String, IFuncXY> entry : funcs.entrySet()) {
            System.out.println((i++) + ". " + entry.getKey());
            keys.add(entry.getKey());
        }
        String str = scanner.nextLine();
        IFuncXY func1 = null;
        try {
            func1 = funcs.get(keys.get(Integer.parseInt(str) - 1));
        } catch (Exception e) {
            System.out.println("Нет такого уравнения");
        }
        if(func1 != null) {
            try {
                double x0, y0, end, eps;
                while (true){
                    try{
                        System.out.println("Введите x0:");
                        x0 = Double.parseDouble(scanner.nextLine());
                        System.out.println("Введите y0:");
                        y0 = Double.parseDouble(scanner.nextLine());
                        System.out.println("Введите конец отрезка:");
                        end = Double.parseDouble(scanner.nextLine());
                        System.out.println("Введите точность:");
                        eps = Double.parseDouble(scanner.nextLine());
                        break;
                    }catch (Exception ignored){}
                }
                Map<String, ArrayList<IFuncX>> map_func = new HashMap<>();
                Map<String, ArrayList<Point>> point_func = new HashMap<>();
                //Получение исходных данных
                AdamsMethodMath adamsMethodMath = new AdamsMethodMath(func1, x0, y0, end, eps);
                adamsMethodMath.calculate();
                ArrayList<Point> xy = adamsMethodMath.getResult();
                for(Point p : xy){
                    System.out.println("x: ["+p.getX()+"] y: ["+p.getY()+"]");
                }
                point_func.put("Исходные данные", xy);
                // Интерполяция
                ArrayList<Point> interpolation = LagrangianIntegrationMath.Interpolation(xy, 100);
                point_func.put("Интерполяция", interpolation);
                // Рисуем график
                new GraphModule(map_func, point_func);
            } catch (Exception e) {
                System.out.println("Произошла ошибка");
                e.printStackTrace();
            }
        }
    }
}
