/* 
 * File:   sudoku2sat.h
 * Desc: Header de la Implementacion del traductor de Sudoku a SAT
 *       y sus estructuras y metodos
 * Author: Juan J Garcia
 *         Federico Flaviani
 * Created on February 25, 2011, 6:55 AM
 */

#ifndef __SUDOKU2SAT_H
#define __SUDOKU2SAT_H

int literal(int row, int col, int value);
void atLeastOne(char* output, int n);
void justOne(char* output, int n);
void noRepeatOnRow(char* output, int n);
void noRepeatOnCol(char* output, int n);
void noRepeatSquare(char* output, int n);
void noRepeat(char* output, int n);
int problemLine(char* input,char* output, int num);
void firstAssig(char* input, char* output);

#endif

