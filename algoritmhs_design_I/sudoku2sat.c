/* 
 * File:   sudoku2sat.c
 * Desc: Implementacion del traductor de Sudoku a SAT
 *       y sus estructuras y metodos
 * Author: Juan J Garcia
 *         Federico Flaviani
 * Created on February 25, 2011, 6:55 AM
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// Funcion que entrega el literal correspondiente a una casilla de sudoku
// In: recibe el tamaño del sudoku, la fila, la columna y el valor de la casilla
// Out: Entrega el entero que representa el literal de la casilla
int literal(int n, int row, int col, int value) {
  return (row * n * n) + (col * n) + value;
}

// Metodo que escribe las restricciones de que debe haber al menos un valor en una
// casilla de sudoku dada
// In: Recibe el archivo de salida y el tamaño del sudoku
void atLeastOne(char* output, int n) { 

  FILE *fd = fopen(output, "a");
  int i,j,value;
  for ( i=0 ; i<n ; i++ )
    for ( j=0 ; j<n ; j++ ) {
      for ( value=0 ; value<n ; value++ ) 
        fprintf(fd,"%d ",literal(n,i,j,value+1));
      fprintf(fd,"0\n");
    }
  fclose(fd);
}

// Metodo que escribe las restricciones de que solo puede haber un valor en una
// casilla de sudoku dada
// In: Recibe el archivo de salida y el tamaño del sudoku
void justOne(char* output, int n) {

  FILE *fd = fopen(output, "a");
  int i,j,value1,value2;
  for ( i=0 ; i<n ; i++ )
    for ( j=0 ; j<n ; j++ )
      for ( value1=0 ; value1<n ; value1++ )
        for ( value2=0 ; value2<n ; value2++ ) {
          if (value1 >= value2) continue;
          fprintf(fd,"-%d -%d",literal(n,i,j,value1+1),literal(n,i,j,value2+1));
          fprintf(fd," 0\n");
        }
  fclose(fd);
}

// Metodo que escribe las restricciones de que no se puede repetir  un valor en una
// fila de sudoku dada
// In: Recibe el archivo de salida y el tamaño del sudoku
void noRepeatOnRow(char* output, int n) {

  FILE *fd = fopen(output, "a");
  int i1,j1,i2,j2,value;
  for ( value=0 ; value<n ; value++ )
    for ( i1=0 ; i1<n ; i1++ ) {
      for ( j1=0 ; j1<n ; j1++ ) {
        for ( i2=i1 ; i2<n ; i2++) {
          for ( j2=j1+1 ; j2<n ; j2++ ) {
            fprintf(fd,"-%d -%d",literal(n,i1,j1,value+1),literal(n,i2,j2,value+1));
            fprintf(fd," 0\n");
          }
          break;
        }
      }
    }
  fclose(fd);

}

// Metodo que escribe las restricciones de que no se puede repetir  un valor en una
// columna de sudoku dada
// In: Recibe el archivo de salida y el tamaño del sudoku
void noRepeatOnCol(char* output, int n) {

  FILE *fd = fopen(output, "a");
  int i1,j1,i2,j2,value;
  for ( value=0 ; value<n ; value++ )
    for ( j1=0 ; j1<n ; j1++ ) {
      for ( i1=0 ; i1<n ; i1++ ) {
        for ( j2=j1 ; j2<n ; j2++) {
          for ( i2=i1+1 ; i2<n ; i2++ ) {
            fprintf(fd,"-%d -%d",literal(n,i1,j1,value+1),literal(n,i2,j2,value+1));
            fprintf(fd," 0\n");
          }
          break;
        }
      }
    }
  fclose(fd);

}

// Metodo que escribe las restricciones de que no se puede repetir  un valor en un
// cuadro de sudoku dado
// In: Recibe el archivo de salida y el tamaño del sudoku
void noRepeatSquare(char* output, int n) {

  FILE *fd = fopen(output, "a");
  int i1,j1,i2,j2,value;
  for ( value=0 ; value<n ; value++ )
    for ( i1=0 ; i1<n ; i1++ ) {
      for ( j1=0 ; j1<n ; j1++ ) {

        for ( i2=i1 ; i2<=((((i1/(int)sqrt(n))+1)*(int)sqrt(n))-1) ; i2++) {
          for ( j2=(j1/(int)sqrt(n))*(int)sqrt(n) ; j2<=((((j1/(int)sqrt(n))+1)*(int)sqrt(n))-1) ; j2++ ) {
            if ((i1 > i2) || (i1 >= i2 && j1 >= j2)) continue;
            fprintf(fd,"-%d -%d",literal(n,i1,j1,value+1),literal(n,i2,j2,value+1));
            fprintf(fd," 0\n");
          }
        }
     
      }
    }
  fclose(fd);
}

// Metodo que escribe las restricciones de que no se puede repetir  un valor en una
// fila, columna o cuadro dado
// In: Recibe el archivo de salida y el tamaño del sudoku
void noRepeat(char* output, int n) {
  
  noRepeatOnRow( output, n);
  noRepeatOnCol( output, n);
  noRepeatSquare( output, n);

}

// Metodo que escribe en el archivo los valores de la linea de problema del SAT
// In: Recibe la instancia de entrada, el archivo de salida y el tamaño del sudoku  
int problemLine(char* input, char* output, int num) {

  FILE *fd = fopen(output, "w");
  int n = 0;
  int aux = input[0] - '0';
  n = aux*aux;
  fprintf(fd,"c Sudoku Game to SAT\nc\np cnf %d %d\n", (int)pow(n,3) , (int)pow(n,2) + ((n*(n-1))/2)*(int)pow(n,2) + (n+n+n)*n*((n*(n-1))/2) + num);
  fclose(fd);
  return n;
}

// Metodo que escribe las clausulas con las asignaciones parciales del sudoku
// In: Recibe la instancia de entrada y el archivo de salida
void firstAssig(char* input, char* output) {

  FILE *fd = fopen(output, "a");
  char inst[81];
  int aux,n,value;
  int i = 0;

  sscanf(input,"%d_%s",&aux,inst);
  n = aux*aux;

  for (i=0 ; i<n*n ; i++) {
    value = inst[i] - '0';
    if (value != 0) {
      fprintf(fd,"%d 0\n",literal(n,i/n,i%n,value));
    }
  }
  
  fclose(fd);
}
