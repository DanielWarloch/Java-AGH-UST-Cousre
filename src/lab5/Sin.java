package lab5;

public class Sin extends Node {
    private Node value;


    Sin(Node value) {
        this.value = value;
    }

    Sin(double value) {
        this.value = new Constant(value);
    }

    @Override
    public double evaluate() {
        return Math.sin(value.evaluate());
    }

    @Override
    public String toString() {
        StringBuilder s =  new StringBuilder();
        if(sign<0)s.append("-(");
        s.append("sin(");
        s.append( value.toString());
        s.append( ')');
        if(sign<0)s.append(")");
        return s.toString();
    }

    @Override
    public Node diff(Variable var) {
        Node cos = new Cos(value);

        return new Prod(cos, value.diff(var));
    }

    @Override
    int getArgumentsCount() {
        return 1;
    }

    @Override
    boolean isZero(Variable variable) {
        return false;
    }

    @Override
    Node simplify(){
        return this;

    }
}