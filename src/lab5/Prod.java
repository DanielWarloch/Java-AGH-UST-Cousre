package lab5;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    Prod(){}


    Prod(Node n1){
        args.add(n1);
    }
    Prod(double c){
        args.add(new Constant(c));
    }

    Prod(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }
    Prod(double c, Node n){
        args.add(new Constant(c));
        args.add(n);
    }

    Node getArg(int i){
        return args.get(i);
    }


    Prod mul(Node n){
        args.add(n);
        return this;
    }

    Prod mul(double c){
        Constant cc = new Constant(c);
        args.add(cc);
        return this;
    }


    @Override
    double evaluate() {
        double result =1;
        for (Node node: args) {
            result*=node.evaluate();
        }
        return sign*result;
    }
    int getArgumentsCount(){return args.size();}


    public String toString(){
        StringBuilder b =  new StringBuilder();
        if(sign<0)b.append("-");
        for (Node arg : args) {
            b.append(arg.toString());
            if (arg != args.get(args.size() - 1)){
                b.append("*");
            }
        }
        return b.toString();
    }
    Node diff(Variable var) {
        Sum r = new Sum();
        for(int i=0;i<args.size();i++){
            Prod m= new Prod();
            for(int j=0;j<args.size();j++){
                Node f = args.get(j);
                if(j==i)m.mul(f.diff(var));
                else m.mul(f);
            }
            if (!m.isZero(var)) r.add(m);
        }
        return r.simplify();
    }
    @Override
    boolean isZero(Variable var){
        for (Node arg :
                args) {
            if(arg instanceof Constant && arg.evaluate() == 0) return true;
        }
        return false;
    }




    @Override
    Node simplify(){
        for (Node arg : args) {
            arg.simplify();
        }
        List<Node> y = new ArrayList<>();
        double s = 1;
        for (int j =0; j < args.size(); ++j) {
            Node arg = args.get(j);
            if (arg instanceof Prod){
                for (int i = 0; i < arg.getArgumentsCount(); i++) {
                    this.mul(((Prod) arg).getArg(i));
                }
                args.remove(arg);
            }
        }
/*
        for (Node arg : args) {
            if (arg instanceof Prod){
                for (int i = 0; i < arg.getArgumentsCount(); i++) {
                    this.mul(((Prod) arg).getArg(i));
                }
                args.remove(arg);
            }
        }*/
        for (Node arg : args) {
            if(arg instanceof Constant ){
                double x = arg.evaluate();
                if (x != 0){
                    s*=x;
                    y.add(arg);
                }else {
                    return new Constant(0);
                }
            }
        }
        for (Node y1 : y) {
            args.remove(y1);
        }
        args.add(new Constant(s));



        return this;
        //TODO implement
    }
}
