package main.java;

public abstract class Constraint {

    Variable v1, v2;
    String flag;
    int cons;
    Boolean abs;
    
    public String toString() {
        //TODO
        return "";
    }

    protected boolean isSatisfied() {
        //TODO
        return true;
    }

    protected void reduce() {
        //TODO
    }

}
