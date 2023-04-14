package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.print.event.PrintEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintSolver {

    private Domain dom;
    private List<Variable> variableSet;
    private List<Constraint> constraintSet;

    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
    }

    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++)
            System.out.println(variableSet.get(i));
        System.out.println("");
        //print constraints
        for(int i = 0; i < constraintSet.size(); i++)
            System.out.println(constraintSet.get(i).toString());
        return "";
    }

    private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if(currentLine.startsWith("Domain-")) {
                    //this is our domain - i.e. a datastructure that contains values and can be updated, played with etc.
                    String s = currentLine.replace("Domain-","");
                    String[] array = s.split(","); 
                    int[] vals = new int[array.length];
                    for(int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                } else if (currentLine.startsWith("Var-")) {
                    //this is the code for every variable (a name and a domain)
                    String s = currentLine.replace("Var-","");
                    Variable var = new Variable(s, dom); 
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
                    //TODO
                    //this is the code for the constraints
                    //ConstraintEqualityVarPlusCons:
                    if (currentLine.startsWith("Cons-eqVPC")) {
                        String regexPattern = "\\(|\\)|\\ ";
                        String s = currentLine.replace("Cons-eqVPC(", "").replaceAll(regexPattern, "");
                        String[] values = s.split("=");
                        String[] values2 = values[1].split("\\+");
                        String val1Name = values[0];
                        String val2Name = values2[0];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if (element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2,
                                Integer.parseInt(values2[1]), false, "vpc");
                        constraintSet.add(eq);
                    }
                    
                    //ConstraintEqualityVarVar:
                    if (currentLine.startsWith("Cons-eqVV")) {
                        String s = currentLine.replace("Cons-eqVV(", "");
                        s = s.replace(")", "");
                        String[] values = s.split(" = ");
                        String val1Name = values[0];
                        String val2Name = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if (element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarVar eq = new ConstraintEqualityVarVar(v1, v2, "vv");
                        constraintSet.add(eq);
                    }

                    //ConstraintEqualityVarCons:
                    if (currentLine.startsWith("Cons-eqVC")) {
                        String s = currentLine.replace("Cons-eqVC(", "");
                        s = s.replace(")", "");
                        String[] values = s.split(" = ");
                        String val1Name = values[0];
                        String cons = values[1];
                        Variable v1 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            }
                        }
                        ConstraintEqualityVarCons eq = new ConstraintEqualityVarCons(v1, Integer.parseInt(cons), "vc");
                        constraintSet.add(eq);
                    }

                    //ConstraintDifferenceVarCons:
                    if (currentLine.startsWith("Cons-diff")) {
                        String s = currentLine.replace("Cons-diff(", "");
                        s = s.replace(")", "");
                        String[] values = s.split(",");
                        String val1Name = values[0];
                        String val2Name = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if (element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintDifferenceVarVar eq = new ConstraintDifferenceVarVar(v1, v2, "diff");
                        constraintSet.add(eq);
                    }

                    //ConstraintAbs:
                    if (currentLine.startsWith("Cons-abs")) {
                        String s = currentLine.replace("Cons-abs(", "");
                        s = s.replace(")", "");
                        String[] values = s.split(" - ");
                        String[] values2 = values[1].split(" = ");
                        String val1Name = values[0];
                        String val2Name = values2[0];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if (element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2,
                                Integer.parseInt(values2[1]), true, "abs");
                        constraintSet.add(eq);
                    }
                }

            }

            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }

    public List<Variable> copy(List<Variable> variableSet) {
        List<Variable> backupVarSet = new ArrayList<Variable>();
        for (int i = 0; i < variableSet.size(); i++) {
            Variable var = new Variable(variableSet.get(i).name, variableSet.get(i).d);
            backupVarSet.add(var);
        }
        return backupVarSet;
    }

    public List<Constraint> conSetUpdate(List<Variable> varSet, List<Constraint> conSet) {
        for (int i = 0; i < conSet.size(); i++) {
            for (int j = 0; j < varSet.size(); j++) {
                if (conSet.get(i).flag == "vpc") {
                    if (conSet.get(i).v1.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v1.d = varSet.get(j).d;
                    }
                    if (conSet.get(i).v2.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v2.d = varSet.get(j).d;
                    }
                }
                if (conSet.get(i).flag == "vv") {
                    if (conSet.get(i).v1.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v1.d = varSet.get(j).d;
                    }
                    if (conSet.get(i).v2.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v2.d = varSet.get(j).d;
                    }
                }
                if (conSet.get(i).flag == "vc") {
                    if (conSet.get(i).v1.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v1.d = varSet.get(j).d;
                    }
                }
                if (conSet.get(i).flag == "diff") {
                    if (conSet.get(i).v1.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v1.d = varSet.get(j).d;
                    }
                    if (conSet.get(i).v2.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v2.d = varSet.get(j).d;
                    }
                }
                if (conSet.get(i).flag == "abs") {
                    if (conSet.get(i).v1.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v1.d = varSet.get(j).d;
                    }
                    if (conSet.get(i).v2.name.equals(varSet.get(j).name)) {
                        conSet.get(i).v2.d = varSet.get(j).d;
                    }
                }
            }
        }
        return conSet;
    }
    
    public List<Variable> varSetUpdate(List<Variable> varSet, List<Constraint> conSet) {
        for (int i = 0; i < varSet.size(); i++) {
            for (int j = 0; j < conSet.size(); j++) {
                if (conSet.get(j).v1.name.equals(varSet.get(i).name)) {
                    varSet.set(i, conSet.get(j).v1);
                }
                if (conSet.get(j).v2 != null) {
                    if (conSet.get(j).v2.name.equals(varSet.get(i).name)) {
                        varSet.set(i, conSet.get(j).v2);
                    }
                }
            }
        }
        return varSet;
    }

    public boolean isReducedToOneDomain (List<Variable> varSet) {
        for (int i = 0; i < varSet.size(); i++) {
            if (varSet.get(i).d.vals.length > 1 || varSet.get(i).d.vals.length == 0) {
                return false;
            }
        }
        variableSet = varSet;
        return true;
    }
    
    public boolean isEmptyDomain (List<Variable> varSet) {
        for (int i = 0; i < varSet.size(); i++) {
            if (varSet.get(i).d.vals.length == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean reduce(List<Variable> varSet, List<Constraint> conSet) {
        for (int i = 0; i < conSet.size(); i++) {
            conSet.get(i).reduce();
            varSet = varSetUpdate(varSet, conSet);
            conSet = conSetUpdate(varSet, conSet);
        }

        if (isReducedToOneDomain(varSet)) {
            return true;
        } else if (isEmptyDomain(varSet)) {
            return false;
        } else {
            for (int i = 0; i < varSet.size(); i++) {
                if (varSet.get(i).d.vals.length > 1) {
                    List<Variable> varSet1 = copy(varSet);
                    List<Variable> varSet2 = copy(varSet);
                    varSet1.get(i).d = varSet.get(i).d.split(varSet.get(i).d)[0];
                    varSet2.get(i).d = varSet.get(i).d.split(varSet.get(i).d)[1];
                    if (reduce(varSet1, conSetUpdate(varSet1, conSet))) {
                        return true;
                    }
                    if (reduce(varSet2, conSetUpdate(varSet2, conSet))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public ArrayList<String> printAnswer(String file) {
        ConstraintSolver problem = new ConstraintSolver();
        problem.parse("data.txt");
        problem.reduce(problem.variableSet, problem.constraintSet);
        ArrayList<String> answer = new ArrayList<String>();
        for (int i = 0; i < problem.variableSet.size(); i++) {
            answer.add("Sol-" + problem.variableSet.get(i).name + "-" + problem.variableSet.get(i).d.vals[0]);
            System.out.println(answer.get(i));
        }
        return answer;
    }

    public static void main(String[] args) {
        ConstraintSolver problem = new ConstraintSolver();
        problem.parse("data.txt");
        System.out.println(problem + "\n------------------");
        problem.reduce(problem.variableSet, problem.constraintSet);
        System.out.println(problem);
        ArrayList<String> r = problem.printAnswer("data.txt");
    }
}