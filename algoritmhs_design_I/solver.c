#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "CNF_Form.h"

int procesarArchivo(FILE* archivo) {
  CNF_Form f;
  char line[256];
  int literals[128];
  int numVar;
  int numClauses;
  int i;
  while (fgets(line, sizeof line, archivo) != NULL) {
    if (line[0] == 'c' || line[0] == '\n') {
      continue;
    }
    if (line[0] == 'p') {
      strtok(line, " ");
      strtok(NULL, " ");
      numVar = atoi(strtok(NULL, " "));
      numClauses = atoi(strtok(NULL, " "));
      printf("\nNumero de Variables = %d \n", numVar);
      printf("Numero de Clausulas =  %d\n", numClauses);
      f = CNF_create(numVar, numClauses);
      continue;
    }
    i = 0;
    literals[i] = atoi(strtok(line, " "));
    for (i = 1; literals[i] = atoi(strtok(NULL, " ")); i++) { }
    CNF_addClause(f, literals, i);
  }
  int solution[numVar];
  int itsSolution = FALSE;
  int var;
  double start, end, timeExec;

  start = time(NULL);
  AsignationTree t=new_AsignationTree(numVar);
  itsSolution = CNF_DPLL(f,1,t);
  end = time(NULL);
  timeExec = end - start;

  if (itsSolution == TRUE) {

    printf("SAT = ");
    for (i = 0; i < f->numLiterals; i++) {
      var = getVar(i);
      if (f->literals[i]->value == TRUE)
        solution[i/2] = var;
      else solution[i/2] = -var;
    }
    for (i = 0; i < f->numVar; i++) {
      if (solution[i] > 0)
        printf("%d ",solution[i]);
    }
    printf("\nVar Assig = %d\n",f->numVarAssig);
    printf("Tiempo de ejecucion = %f\n",timeExec);

  } else {

    printf("UNSAT = ");
    for (i = 0; i < f->numLiterals; i++) {
      var = getVar(i);
      if (f->literals[i]->value == TRUE)
        solution[i/2] = 0;
      else solution[i/2] = 0;
    }
    for (i = 0; i < f->numVar; i++) {
      if (solution > 0)
        printf("%d ",solution[i]);
    }
    printf("\nVar Assig = %d\n",f->numVarAssig);
    printf("Tiempo de ejecucion = %f\n",timeExec);
  }

  printf("Finalizado.\n");
}

main(int argc, char** argv) {
  FILE* archivo;


  /*if (argc != 2) {
    printf("%s\n", "Error: do ./solver <inputFile>");

    return (EXIT_FAILURE);
  }*/

  if ((archivo = fopen(argv[1], "r")) != NULL) {
    procesarArchivo(archivo);
    fclose(archivo);
    return (EXIT_SUCCESS);
  } else {
    printf("%s\n", "Problema al abrir el archivo.");
    printf("%s\n", "Se abortara la ejecucion.");
    return (EXIT_FAILURE);
  }
  return (EXIT_SUCCESS);
}
