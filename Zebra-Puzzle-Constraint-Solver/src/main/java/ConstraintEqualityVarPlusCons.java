package main.java;

public class ConstraintEqualityVarPlusCons extends Constraint {

    Boolean abs;

    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, int cons, Boolean abs, String flag) {
        this.v1 = v1;
        this.v2 = v2;
        this.cons = cons;
        this.abs = abs;
        this.flag = flag;
    }

    public String toString() {
        String result = "";
        if (!abs)
            result += this.v1 + " = " + this.v2 + " + " + this.cons;
        else if (abs) {
            result += "|" + this.v1 + " - " + this.v2 + "| = " + this.cons;
        }
        return result;
    }

    protected boolean isSatisfied() {
        for (int i = 0; i < this.v1.d.vals.length; i++) {
            for (int j = 0; j < this.v2.d.vals.length; j++) {
                if (this.v1.d.vals[i] == this.v2.d.vals[j] + this.cons) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void reduce() {
        //from d1
        for (int i = 0; i < this.v1.d.vals.length; i++) {
            Boolean flag = false;
            if (!abs) {
                for (int j = 0; j < this.v2.d.vals.length; j++) {
                    if (this.v1.d.vals[i] == this.v2.d.vals[j] + this.cons)
                        flag = true;
                }
                if (!flag) {
                    v1.d.delete(i--);
                }
            } else {
                for (int j = 0; j < this.v2.d.vals.length; j++) {
                    if (this.v1.d.vals[i] == this.v2.d.vals[j] + this.cons ||
                            this.v1.d.vals[i] == this.v2.d.vals[j] - this.cons) {
                        flag = true;
                    }
                }
                if (!flag) {
                    v1.d.delete(i--);
                }
            }

            //from d2
            for (i = 0; i < this.v2.d.vals.length; i++) {
                flag = false;
                if (!abs) {
                    for (int j = 0; j < this.v1.d.vals.length; j++) {
                        if (this.v2.d.vals[i] == this.v1.d.vals[j] - this.cons) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        v2.d.delete(i--);
                    }
                } else {
                    for (int j = 0; j < this.v1.d.vals.length; j++) {
                        if (this.v2.d.vals[i] == this.v1.d.vals[j] - this.cons ||
                                this.v2.d.vals[i] == this.v1.d.vals[j] + this.cons) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        v2.d.delete(i--);
                    }
                }
            }
        }

    }
}