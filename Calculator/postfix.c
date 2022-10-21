#include "postfix.h"
#include <math.h>

// evaluate expression stored as an array of string tokens
double evaluate_postfix_expression(char ** args, int nargs) {
  struct double_stack * pointer = double_stack_new(nargs);
  for(int i =0; i < nargs; i++) {
     if (isdigit(args[i][0]) || isdigit(args[i][1])) {
        double value = atof(args[i]);
        double_stack_push(pointer, value);
     }
     else {
        double val2 = double_stack_pop(pointer);
        double val1 = double_stack_pop(pointer);
        switch(args[i][0]) {
           case 'X':
              double_stack_push(pointer, val1 * val2);
              break;
           case '+':
              double_stack_push(pointer, val1 + val2);
              break;
           case '-':
              double_stack_push(pointer, val1 - val2);
              break;
           case '/':
              double_stack_push(pointer, val1 / val2);
              break;
           case '^':
              double_stack_push(pointer, pow(val1, val2));
              break;
        }
     }
  }
  return double_stack_pop(pointer);
}
