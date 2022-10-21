#include "infix.h"

int operator_evaluation(char elem) {
  if (elem == '+' || elem == '-') return 1;
  if (elem == 'X' || elem == '/') return 2;
  if (elem == '^') return 3;

  return 0;
}

// evaluate expression stored as an array of string tokens
double evaluate_infix_expression(char ** args, int nargs) {
  char * postfix_arr[nargs];
  int npostfix_arr = 0;

  struct double_stack * point = double_stack_new(nargs);

  for (int i = 0; i < nargs; i++) {
    if (isdigit(args[i][0]) || isdigit(args[i][1])) 
    {
      postfix_arr[npostfix_arr++] = args[i];
    }
    else if (args[i][0] == '(') 
    {
      double_stack_push(point, i);
    }
    else if (args[i][0] == ')') {
      while (args[(int)peek(point)][0] != '(') {
        postfix_arr[npostfix_arr++] = args[(int) double_stack_pop(point)];
      }
      double_stack_pop(point);
    }
    else {
      while (!isEmpty(point) && (operator_evaluation(args[((int) peek(point))][0]) >= operator_evaluation(args[i][0]))) {
        postfix_arr[npostfix_arr++] = args[(int) double_stack_pop(point)];
      }
      double_stack_push(point, i);
    }
  }
  
  while (!isEmpty(point)) {
    postfix_arr[npostfix_arr++] = args[(int) double_stack_pop(point)];
  }

  return evaluate_postfix_expression(postfix_arr, npostfix_arr);
}
