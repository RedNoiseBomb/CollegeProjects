package main.java;

public class ConstraintEqualityVarVar extends Constraint {
    

    public ConstraintEqualityVarVar(Variable v1, Variable v2, String flag) {
        this.v1 = v1;
        this.v2 = v2;
        this.flag = flag;
    }

    public String toString() {
        String result = "";
        return result += this.v1 + " = " + this.v2;
    }

    protected boolean isSatisfied() {
        for (int i = 0; i < this.v1.d.vals.length; i++) {
            for (int j = 0; j < this.v2.d.vals.length; j++) {
                if (this.v1.d.vals[i] == this.v2.d.vals[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void reduce() {
        // for d1
        for (int i = 0; i < this.v1.d.vals.length; i++) {
            Boolean flag = false;
            for (int j = 0; j < this.v2.d.vals.length; j++) {
                if (this.v1.d.vals[i] == this.v2.d.vals[j]) {
                    flag = true;
                }
            }
            if (!flag) {
                v1.d.delete(i--);
            }
        }

        // for d2
        for (int i = 0; i < this.v2.d.vals.length; i++) {
            Boolean flag = false;
            for (int j = 0; j < this.v1.d.vals.length; j++) {
                if (this.v2.d.vals[i] == this.v1.d.vals[j]) {
                    flag = true;
                }
            }
            if (!flag) {
                v2.d.delete(i--);
            }
        }


    }

}
