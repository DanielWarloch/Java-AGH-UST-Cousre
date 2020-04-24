package lab5;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {
    List<Node> args = new ArrayList<>();

    Sum() {
    }

    Sum(Node n1, Node n2) {
        args.add(n1);
        args.add(n2);
    }


    Sum add(Node n) {
        args.add(n);
        return this;
    }

    Sum add(double c) {
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c, n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result = 0;
        for (Node node : args) result += node.evaluate();
        return sign * result;
    }

    int getArgumentsCount() {
        return args.size();
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) b.append("-(");

        for (int i = 0; i < args.size(); i++) {
            b.append(args.get(i).toString());
            if (i < args.size() - 1) b.append(" + ");
        }
        if (sign < 0) b.append(")");
        return b.toString();
    }

    Node diff(Variable var) {
        Sum r = new Sum();
        for (Node arg : args) if (!isZero(var)) r.add(arg.diff(var));
        return r.simplify();
    }

    @Override
    boolean isZero(Variable var) {
        //return args.stream().allMatch(Node::isZero);
        for (Node arg : args) if (!arg.isZero(var)) return false;
        return true;
    }

    @Override
    Node simplify() {
        for (Node arg : args) {
            arg.simplify();
        }
        double x = 0;
        List<Node> y = new ArrayList<>();

        for (Node arg : args) {
            if (arg instanceof Constant) {
                y.add(arg);
                double z = arg.evaluate();
                if (z != 0) {
                    x += z;
                }
            }else if (arg.getArgumentsCount() == 0) {
                y.add(arg);
            }
        }
        for (Node y1 : y) {
            args.remove(y1);
        }
        if (x != 0)
            args.add(new Constant(x));
        return this;
    }
}




