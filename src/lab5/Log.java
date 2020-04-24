package lab5;

public class Log extends Node {
    private Constant base;
    private Node value;

    Log(double base, Node value) {
        this.base = new Constant(base);
        this.value = value;
    }

    @Override
    public double evaluate() {
        return Math.log(value.evaluate()) / Math.log(base.evaluate());
    }

    @Override
    public String toString() {
        StringBuilder s =  new StringBuilder();
        if(sign<0)s.append("-(");
        s.append("log_");
        s.append(base.toString());
        s.append(" (");
        s.append(value.toString());
        s.append(')');
        if(sign<0)s.append(")");
        return s.toString();
    }

    @Override
    public Node diff(Variable var) {
        return new Prod(new Log(base.evaluate(), new Constant(Math.E)), value.diff(var));
    }

    @Override
    int getArgumentsCount() {
        return 1;
    }
    boolean isZero(Variable variable) {
        return value instanceof Constant && value.evaluate() == 0;
    }

    @Override
    Node simplify(){
        return this;

    }
}