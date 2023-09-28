import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ThreeSAT {

    private static List<Clause> clauses;
    private static List<Integer> varAssignments;
    private static int numClauses;
    private static int numVariables;

	public ThreeSAT(int num_clauses, int num_variables) {
        clauses = new ArrayList<Clause>(num_clauses);
        varAssignments = new ArrayList<Integer>(num_variables);
        numClauses = num_clauses;
        numVariables = num_variables;
    }

    private class Variable {
        int id;
        boolean negated;

        private Variable(int id) {
            this.id = Math.abs(id);

            if (id < 0) {
                this.negated = true;
            } else {
                this.negated = false;
            }
        }

        private boolean getBoolean(int assignment) {
            if (assignment == 1 && !this.negated) {
                return true;
            } else if (assignment == 1 && this.negated) {
                return false;
            } else if (assignment == -1 && !this.negated) {
                return false;
            } else {
                return true;
            }
        }
    }

    private class Clause {
        List<Variable> variables;
        List<Boolean> state; // track if after assignment each variable is T/F

        private Clause(int numVariables) {
            this.variables = new ArrayList<Variable>(numVariables);
            this.state = new ArrayList<Boolean>(numVariables);
        }

        private void addToClause(Variable v) {
            this.variables.add(v);
        }

        private void setVariables() {
            for (Variable var : this.variables) {
                int index = var.id - 1;
                int assignment = varAssignments.get(index);
                boolean val = var.getBoolean(assignment);

                this.state.add(val);
            }
        }

        private boolean isSatisfied() {
            return (state.get(0) || state.get(1) || state.get(2));
        }
    }

    private static void parse_input() {
        Scanner input = new Scanner(System.in);

        int numVariables = input.nextInt();
        int numClauses = input.nextInt();
        ThreeSAT threeSAT = new ThreeSAT(numClauses, numVariables);

        while (numClauses > 0) {
            Variable var1 = threeSAT.new Variable(input.nextInt());
            Variable var2 = threeSAT.new Variable(input.nextInt());
            Variable var3 = threeSAT.new Variable(input.nextInt());

            Clause clause = threeSAT.new Clause(3);
            clause.addToClause(var1);
            clause.addToClause(var2);
            clause.addToClause(var3);

            clauses.add(clause);

            numClauses--;
        }

        input.close();
    }

    private static int numClausesSatisfied() {
        int count = 0;

        for (Clause c : clauses) {
            if (c.isSatisfied()) {
                count++;
            }
        }

        return count;
    }

    private static void assignLiterals() {
        Random r = new Random();
        int values[] = {-1, 1};

        for (int i = 0; i < numVariables; i++) {
            int num = r.nextInt(2); // randomly generate index of 0 or 1
            int assignment = values[num]; // assign variable -1 or 1 based on index
            varAssignments.add(i, assignment);
        }
    }

    private static void setClauses() {
        for (Clause c : clauses) {
            c.setVariables();
        }
    }

    public static void main(String[] args) {
		parse_input();

        while (true) {
            assignLiterals();
            setClauses();
            int numSatisfied = numClausesSatisfied();
            if (numSatisfied >= (7 / 8) * clauses.size()) {
                break;
            }
        }

        for (int i = 0; i < numVariables; i++) {
            if (i == varAssignments.size()) {
                System.out.println(varAssignments.get(i));
            } else {
                System.out.print(varAssignments.get(i) + " ");
            }
        }
	}
}