package main.java;

public class ConstraintDifferenceVarVar extends Constraint {

    public ConstraintDifferenceVarVar(Variable v1, Variable v2, String flag) {
        this.v1 = v1;
        this.v2 = v2;
        this.flag = flag;
    }
    
    public String toString() {
        String result = "";
        return result += this.v1 + " \u2260 " + this.v2;
    }

    protected boolean isSatisfied() {
        if (this.v1.d.vals.length == 1 && this.v2.d.vals.length == 1 &&
                this.v1.d.vals[0] == this.v2.d.vals[0]) {
            return false;
        }
        else
            return true;
    }

    protected void reduce() {
        if (this.v2.d.vals.length == 1) {
            for (int i = 0; i < this.v1.d.vals.length; i++) {
                if (this.v1.d.vals[i] == this.v2.d.vals[0]) {
                    v1.d.delete(i--);
                }
            }
        }
        if (this.v1.d.vals.length == 1) {
            for (int i = 0; i < this.v2.d.vals.length; i++) {
                if (this.v2.d.vals[i] == this.v1.d.vals[0]) {
                    v2.d.delete(i--);
                }
            }
        }
    }

}
