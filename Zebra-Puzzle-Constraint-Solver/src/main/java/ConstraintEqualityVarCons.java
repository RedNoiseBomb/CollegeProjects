package main.java;

public class ConstraintEqualityVarCons extends Constraint{
    
    int cons;

    public ConstraintEqualityVarCons(Variable v1, int cons, String flag) {
        this.v1 = v1;
        this.cons = cons;
        this.flag = flag;
    }

    public String toString() {
        String result = "";
        return result += this.v1 + " = " + this.cons;
    }

    protected boolean isSatisfied() {
        for (int i = 0; i < this.v1.d.vals.length; i++) {
            if (this.v1.d.vals[i] == cons) {
                return true;
            }
        }
        return false;
    }

    protected void reduce() {
        for (int i = 0; i < this.v1.d.vals.length; i++) {
            if (this.v1.d.vals[i] != cons) {
                this.v1.d.delete(i--);
            }
        }
    }
}
