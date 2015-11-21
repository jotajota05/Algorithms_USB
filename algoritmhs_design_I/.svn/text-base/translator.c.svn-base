#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "sudoku2sat.h"

int main(int argc, char **argv) {

  char inst[81];
  int aux,n,value,num;
  int i = 0;

  if (argc != 3) {
    printf("\nERROR: Use ./test input output.cnf\n\n");
    exit(0);
  }

  sscanf(argv[1],"%d_%s",&aux,inst);
  n = aux*aux;
  num = 0;
  for (i=0 ; i<n*n ; i++) {
    value = inst[i] - '0';
    if (value != 0) {
      num++;
    }
  }

  n = problemLine(argv[1],argv[2],num);
  atLeastOne(argv[2],n);
  justOne( argv[2], n);
  noRepeat( argv[2], n);
  firstAssig( argv[1],  argv[2]);

}
