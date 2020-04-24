package lab5;

public class Exponent extends Node {
    private Node value;

    public Exponent(Node value) {
        this.value = value;
    }

    Exponent(double value) {
        this.value = new Constant(value);
    }

    @Override
    public double evaluate() {
        return Math.exp(value.evaluate());
    }

    @Override
    public String toString() {
        StringBuilder s =  new StringBuilder();
        if(sign<0)s.append("-(");
        s.append("e^(");
        s.append(value.toString());
        s.append(')');
        if(sign<0)s.append(")");
        return s.toString();
    }

    @Override
    public Node diff(Variable var) {
        return new Prod(this, value.diff(var));
    }

    @Override
    int getArgumentsCount() {
        return 1;
    }
    boolean isZero(Variable variable) {
        return false;
    }

    @Override
    Node simplify(){
        return this;

    }
}