/* 
 * File:   CNF_Form.c
 * Desc: Header de la implementacion de la Formula CNF
 *       y sus estructuras y metodos
 * Author: Juan J Garcia
 *         Federico Flaviani
 * Created on February 25, 2011, 6:55 AM
 */

#ifndef CNF_FORM_H
#define	CNF_FORM_H
#include "List.h"

// Definiciones de valores de verdad
#define TRUE 1
#define FALSE -1
#define UNASSIGNED 0

// Estructura para representar una Asignacion realizada
struct asignation {
  int desition; // Indica si la asignacion es de desicion o no
  int variable;
  int value;
  int level;
};

typedef struct asignation * Asignation;

// Estructura para representar un vertice del Arbol de Asignaciones
struct treeVertice {
  Asignation asig;
  List incident;
};

typedef struct treeVertice * TreeVertice;

// Estructura para representar un Arbol de Asignaciones
struct asignationTree {
  TreeVertice* incidents;
  List leaf;
  Asignation asigMaxLevel;
  int numVar;
};

typedef struct asignationTree * AsignationTree;



// Estructura para representar un Literal de la Formula CNF
struct literal {
  int value;
  Asignation asig;
  int indVariable;
  List clauses;
};

typedef struct literal * Literal;

// Estructura para representar una Clausula de la formula CNF
struct clause {
  int numSat;
  int numNotSat;
  int isSatisf;
  int size;
  List literals;
  Literal unitLit;
};

typedef struct clause * Clause;

// Estructura para representar una Formula CNF
struct form {
  int numLiterals;
  int numVar;
  int numClauses;
  Literal *literals;
  List clauses;
  int satClauses;
  int numVarAssig;
  int isTrue;
  int clausulasAprendidas;
  List assigUnitProp;
};

typedef struct form * CNF_Form;

// Metodos para creacion de clausulas y la Formula CNF
Clause CNF_createClause(int);
CNF_Form CNF_create(int, int);
int CNF_addClause(CNF_Form, int [], int);

// Metodos para localizar variables o literales
int map(int);
int getVar(int);
int searchLitPos(Literal *, Literal, int);

// Metodos principales del DPLL
int CNF_DPLL(CNF_Form,int, AsignationTree);
int isSolution(CNF_Form);
int chooseLiteral(CNF_Form);

// Metodos para asignacion de variables y control del backtracking
void assignLiteral(CNF_Form, int, int, int, int,AsignationTree);
void updateClauses(CNF_Form, int, int, AsignationTree);
void restoreState(CNF_Form, int, int,AsignationTree);
void restoreClauses(CNF_Form, int, int);

// Metodos del Unit Propagation y Pure Literal Elimination
void unitPropagation(CNF_Form, int*,AsignationTree,int);
void restoreUnitProp(CNF_Form, int*,AsignationTree);
int isUnit(Clause);
void getUnitLiteral(Clause);
void pureLiteralElim(CNF_Form,int,AsignationTree);

// Metodos para el Arbol de Asignaciones
AsignationTree new_AsignationTree();
void init_Asignation(Asignation, int, int, int,int);
void AsignationTree_insert(AsignationTree ,Asignation ,TreeVertice);
Asignation findMaxLevelAsignation(AsignationTree);
void AsignationTree_findLeaf(AsignationTree,int,int);
void AsignationTree_update(AsignationTree);
void addLearnClause(CNF_Form, List);
void insertImplicationsForAClause(AsignationTree, Clause, int);

#endif	/* CNF_FORM_H */

