package labs.lab5;
import labs.models.IFuncXY;
import labs.models.Point;
import java.util.ArrayList;

public class AdamsMethodMath {
    IFuncXY function;
    double x0, y0;
    double xEnd;
    int steps;
    double eps;
    double[] arrayX;
    double[] arrayY;
    double h;

    public AdamsMethodMath(IFuncXY function, double x0, double y0, double xEnd, double eps) {
        this.function = function;
        this.xEnd = xEnd;
        this.eps = eps;
        this.x0 = x0;
        this.y0 = y0;
    }

    private void RungeKutta() {
        for (int i = 1; i < 4; i++) {
            double k1 = function.solve(arrayX[i - 1], arrayY[i - 1]);
            double k2 = function.solve(arrayX[i - 1] + h / 2, arrayY[i - 1] + k1 / 2);
            double k3 = function.solve(arrayX[i - 1] + h / 2, arrayY[i - 1] + k2 / 2);
            double k4 = function.solve(arrayX[i - 1] + h, arrayY[i - 1] + k3);
            arrayY[i] = arrayY[i - 1] + h * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        }
    }

    //Явная формула Адамса
    private double ExplicitAdams(int i) {
        return arrayY[i - 1] + h / 24 * (55 * function.solve(arrayX[i - 1], arrayY[i - 1]) - 59 * function.solve(arrayX[i - 2], arrayY[i - 2])
                + 37 * function.solve(arrayX[i - 3], arrayY[i - 3]) - 9 * function.solve(arrayX[i - 4], arrayY[i - 4]));
    }

    //Неявная формула Адамса
    private double ImplicitAdams(int i) {
        return arrayY[i - 1] + h / 24 * (9 * function.solve(arrayX[i], arrayY[i]) + 19 * function.solve(arrayX[i - 1], arrayY[i - 1])
                - 5 * function.solve(arrayX[i - 2], arrayY[i - 2]) + 1 * function.solve(arrayX[i - 3], arrayY[i - 3]));
    }

    public void calculate() {
        boolean flag = false;
        h = (xEnd - x0)/4;
        do {
            this.steps = (int) ((xEnd - x0)/h);
            arrayX = new double[steps];
            arrayY = new double[steps];
            this.arrayY[0] = y0;
            for (int i = 0; i < steps; i++) {
                arrayX[i] = x0 + i * h;
            }
            RungeKutta();
            double currantAccuracy;
            for (int i = 4; i < steps; i++) {
                arrayY[i] = ExplicitAdams(i);
                currantAccuracy = Math.abs(arrayY[i] - ImplicitAdams(i));
                if (currantAccuracy > eps) {
                    flag = false;
                    break;
                } else flag = true;
            }
            h = h / 2;
        } while (!flag);
    }
    public double[] getArrayX() { return arrayX; }
    public double[] getArrayY() { return arrayY; }
    public ArrayList<Point> getResult() {
        ArrayList<Point> result = new ArrayList<>();
        for(int i = 0; i < arrayX.length; i++){
            result.add(new Point(arrayX[i], arrayY[i]));
        }
        return result;
    }
}
