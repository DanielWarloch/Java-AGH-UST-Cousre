package lab5;

import javafx.util.Pair;

import java.util.*;

public class test {
    public static void main(String[] args) {
        buildAndPrint();
        buildAndEvaluate();
        checkpoint();
        find100points();
        diffPoly();
        diffCircle();
    }


    private static void buildAndPrint(){
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2.1,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(7);
        System.out.println(exp.toString());
        System.out.println(exp.toString().equals("2.1*x^3 + x^2 + (-2)*x + 7"));
    }

    private static void buildAndEvaluate() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(new Power(x, 3))
                .add(-2, new Power(x, 2))
                .add(-1, x)
                .add(2);
        for (double v = -5; v < 5; v += 0.1) {
            x.setValue(v);
            System.out.printf(Locale.US, "f(%f)=%f\n", v, exp.evaluate());
        }
    }


    static void checkpoint(){
            Variable x = new Variable("x");
            Variable y = new Variable("y");
            Node circle = new Sum()
                    .add(new Power(x,2))
                    .add(new Power(y,2))
                    .add(8,x)
                    .add(4,y)
                    .add(16);
            System.out.println(circle.toString());

            double xv = 100*(Math.random()-.5);
            double yv = 100*(Math.random()-.5);
            x.setValue(xv);
            y.setValue(yv);
            double fv = circle.evaluate();
            System.out.print(String.format("Punkt (%f,%f) leży %s koła %s \n\r",xv,yv,(fv<0?"wewnątrz":"na zewnątrz"),circle.toString()));
    }

    static void find100points(){
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x, 2))
                .add(new Power(y, 2))
                .add(8, x)
                .add(4, y)
                .add(16);

        List<Pair<Double, Double>> lista = new ArrayList<>();

        while (lista.size()<100-1) {
            double xv = 100 * (Math.random() - .5);
            double yv = 100 * (Math.random() - .5);
            x.setValue(xv);
            y.setValue(yv);
            double fv = circle.evaluate();
            if (fv<0){
                lista.add(new Pair<>(xv, yv));
            }
        }
        System.out.println(String.format("Lista 100 pkt lezacych wewntrz %s", circle.toString()));
        for (Pair<Double,Double> para: lista) {
            System.out.println("X:\t" + para.getKey() + "\t Y:\t" + para.getValue());
        }
    }
    static void diffPoly() {
        Variable x = new Variable("x");
        Node exp = new Sum()
                .add(2,new Power(x,3))
                .add(new Power(x,2))
                .add(-2,x)
                .add(7)
                .add(8);
        System.out.print("exp=");
        System.out.println(exp.toString());

        Node d = exp.diff(x);
        System.out.print("d(exp)/dx=");
        System.out.println(d.toString());
        System.out.print("d(exp)/dx=");
        System.out.println(d.toString());

    }
    static void diffCircle() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Node circle = new Sum()
                .add(new Power(x,2))
                .add(new Power(y,2))
                .add(8,x)
                .add(4,y)
                .add(16);
        System.out.print("f(x,y)=");
        System.out.println(circle.toString());

        Node dx = circle.diff(x);
        System.out.print("d f(x,y)/dx=");
        System.out.println(dx.toString());
        System.out.print("d f(x,y)/dy=");
        Node dy = circle.diff(y);
        System.out.println(dy.toString());

    }

}
