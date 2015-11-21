/* 
 * File:   CNF_Form.c
 * Desc: Implementacion de la Formula CNF
 *       y sus estructuras y metodos
 * Author: Juan J Garcia
 *         Federico Flaviani
 * Created on February 25, 2011, 6:55 AM
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "CNF_Form.h"

int backtrackToLevel;

void init_Asignation(Asignation a,int variable, int value, int level, int desition) {
  
  a->variable = variable;
  a->value = value;
  a->level = level;
  a->desition = desition;
}

AsignationTree new_AsignationTree(int numVariables)
{
  AsignationTree t = (AsignationTree)malloc(sizeof(struct asignationTree));
  t->incidents = (TreeVertice*)malloc((numVariables+1)*sizeof(struct treeVertice));
  t->numVar=numVariables;
  TreeVertice v;
  Asignation a;
  int i;
  for(i=0;i<numVariables+1;i++)
  {
      v=(TreeVertice)malloc(sizeof(struct treeVertice));
      a=(Asignation)malloc(sizeof(struct asignation));
      v->incident=newList();
      v->asig=a;
      t->incidents[i]=v;

  }
  t->leaf = (List)malloc(sizeof(struct list));
  t->incidents[numVariables]->asig->variable=numVariables+1;

  return t;
}

void AsignationTree_insert(AsignationTree t, Asignation in, TreeVertice dest) {
 /* if(in->desition == TRUE) {
    t->asigMaxLevel = in;
    TreeVertice In = (TreeVertice)malloc(sizeof(struct treeVertice));
    In->asig = in;
    In->incident = (List)malloc(sizeof(struct list));
    t->incidents = append(t->incidents,In);
    t->leaf = append(t->leaf,in);

    if(t->asigMaxLevel->level < in->level) {
        t->asigMaxLevel=in;
    }
  }
  
  dest->incident = append(dest->incident,in);*/
}

Asignation findMaxLevelAsignation(AsignationTree t) {
    List it = init(t->leaf);
    Asignation max = (Asignation)yield(it);
    Asignation a;
    for(it = init(t->leaf); ended(it); it=next(it)) {
        a = (Asignation)yield(it);
        if(max->level < a->level)
            max=a;
    }
    return max;
}

void AsignationTree_findLeaf(AsignationTree t, int asig,int max)
{
  /*  List it;
    Asignation a;
    for(it=init(t->incidents[asig]->incident); ended(it); it=next(it))
    {
        a=(Asignation)yield(it);
        if(a->desition==TRUE && -1==search(t->leaf,a))
        {
            t->leaf=append(t->leaf,a);
            if(a->level > max)
            {
                max=a->level;
                t->asigMaxLevel=a;
            }
        }
        else
        {
            if(a->desition==FALSE)
                AsignationTree_findLeaf(t,a->variable-1,max);
        }
    }
    if(asig==t->numVar)
        destroy(t->incidents[asig]->incident);*/

    List abiertos=newList();
    List cerrados=newList();
    List sucesores;

    abiertos=append(abiertos,t->incidents[asig]->asig);

    while(abiertos->size != 0)
    {
        Asignation nodo=abiertos->head;
	deleteEnd(abiertos);
	cerrados=append(cerrados,nodo);
	//generar sucesores de i y empilarlos
        sucesores=t->incidents[nodo->variable-1]->incident;
        if(sucesores->size==0)
        {
            t->leaf=append(t->leaf,nodo);
            if(nodo->level > max)
            {
                max=nodo->level;
                t->asigMaxLevel=nodo;
            }
        }
        for(sucesores = init(sucesores); ended(sucesores); sucesores=next(sucesores))
        {
		//rutina de eliminacion
		nodo=(Asignation)yield(sucesores);
		int estaEnAbiertos=search(abiertos,nodo);
		if(estaEnAbiertos==FALSE && search(cerrados,nodo)==FALSE)
		{
			abiertos=append(abiertos,nodo);
		}
		else
		{
			if(estaEnAbiertos==TRUE)
			{
                            delete(abiertos,nodo);
                            abiertos=append(abiertos,nodo);
			}
		}
	}
    }
    destroy(t->incidents[asig]->incident);
}

void AsignationTree_update(AsignationTree t)
{
    List it;
    Asignation a;
    delete(t->leaf,t->asigMaxLevel);
    for(it=init(t->leaf); ended(it); it=next(it))
    {
        a=(Asignation)yield(it);
        t->incidents[t->asigMaxLevel->variable-1]->incident=append(
        t->incidents[t->asigMaxLevel->variable-1]->incident,a);
    }
    t->asigMaxLevel=findMaxLevelAsignation(t);
}

/*void AsignationTree_update(AsignationTree t) {
    List it;
    Asignation asig = (Asignation)malloc(sizeof(struct asignation));
    asig->desition = FALSE;
    asig->level = t->asigMaxLevel->level;
    asig->value = t->asigMaxLevel->value*(-1);
    asig->variable = t->asigMaxLevel->variable;
    delete(t->leaf,t->asigMaxLevel);
    //buscar la hoja de mayor nivel en t->leaf y asignarla a t->asigMaxLevel
    t->asigMaxLevel = findMaxLevelAsignation(t);
    TreeVertice max = (TreeVertice)malloc(sizeof(struct treeVertice));
    max->asig=asig;
    max->incident = (List)malloc(sizeof(struct list));

    destroy(t->incidents);//borrar la lista de indidentes y sus contenidos
                        //mosca no debes destruir todo
    for (it = init(t->leaf); ended(it); it = next(it))
    {
        TreeVertice v = (TreeVertice)malloc(sizeof(struct treeVertice));
        v->asig = (Asignation)yield(it);
        v->incident = (List)malloc(sizeof(struct list));
        max->incident = append(max->incident,v->asig);
        t->incidents = append(t->incidents,v);
    }

    t->incidents = append(t->incidents,max);
}*/

// Metodo que agrega una clausula de aprendizaje a la lista de clausulas
// In: La Formula CNF y las hojas de asignaciones del arbol
void addLearnClause(CNF_Form f, List leafs) {
  List it;
  Asignation as;
  int lit;
  Clause cl = CNF_createClause(size(leafs));
  for (it = init(leafs); ended(it); it = next(it)) {
    as = (Asignation)yield(it);
    if (as->value == TRUE)
      lit = map(-(as->variable));
    else
      lit = map(as->variable);
    cl->literals = append(cl->literals,f->literals[lit]);
    cl->numNotSat++;
    f->literals[lit]->clauses=append(f->literals[lit]->clauses,cl);
  }
  cl->size=cl->numNotSat;
  cl->isSatisf=FALSE;
  f->clauses = append(f->clauses,cl);
  f->numClauses++;
  f->clausulasAprendidas++;
}

// Funcion que crea una nueva clausula del tamaño
// In: tamaño de la clausula, dado por el numero de literales que contiene
// Out: La nueva clausula creada
Clause CNF_createClause(int size) {
  Clause c = (Clause)malloc(sizeof(struct clause));
  c->numSat = 0;
  c->numNotSat = 0;
  c->isSatisf = UNASSIGNED;
  c->size = size;
  c->literals = newList();
  return c;
}

// Funcion que crea la formula CNF para representar el problema
// In: Recibe el numero de Variables y el numero de Clausulas
// Out: La formula CNF del problema
CNF_Form CNF_create(int numVar, int numCla) {
  CNF_Form f = (CNF_Form)malloc(sizeof(struct form));
  f->numLiterals = 2 * numVar;
  f->numVar = numVar;
  f->numClauses = numCla;
  f->literals = (Literal *)malloc(2*numVar*(sizeof(struct literal)));
  f->clauses = newList();
  f->satClauses = 0;
  f->numVarAssig = 0;
  f->clausulasAprendidas=0;
  f->isTrue=UNASSIGNED;
  f->assigUnitProp = newList();
  int i;
  Literal lit;

  // Se agregan los literales a la formula
  for (i = 0; i < 2 * numVar; i++) {
    lit = (Literal)malloc(sizeof(struct literal));
    lit->value = UNASSIGNED;
    lit->clauses = newList();
    f->literals[i] = lit;
  }
  return f;
}

// Funcion que agrega un Clausula a la formula CNF
// In: Recibe la formula CNF y los literales que se quieren agregar a la clausula
int CNF_addClause(CNF_Form f, int lit[], int size) {
  Clause c = CNF_createClause(size);
  int i;
  for (i = 0; i < size; i++) {
    c->literals = append(c->literals, (f->literals[map(lit[i])])); 
    (f->literals)[map(lit[i])]->clauses = append((f->literals)[map(lit[i])]->clauses, c);
  }
  f->clauses = append(f->clauses, c);
  return 1;
}

// Funcion que entrega el literal asociado a una variable
// In: recibe el Id de una variable que puede ser X o -X
// Out: Retorna la posicion del literal asociado a la variable de entrada
int map(int etiquetaLit) {
  if (etiquetaLit > 0) {
    return 2 * etiquetaLit - 2;
  } else if (etiquetaLit < 0) {
    return -2 * etiquetaLit - 1;
  } else {
    printf("Error: etiqueta de literal no valida.");
    return (EXIT_FAILURE);
  }
}

// Funcion que regresa la Variable asociada a un literal
// In: Recibe la posicion (id) del literal
// Out: retorna el id de la variable asociada al literal
int getVar(int lit) {
  if (lit % 2 == 0)
    return (lit + 2) / 2;
  else 
    return -(lit + 2) / 2;
}

// Funcion que corre el Algoritmo de DPLL para resolver el problema
// In: Recibe la funcion en CNF, el nivel de asignacion y el Arbol de asignaciones
// Out: Retorna TRUE si encuentra solucion y FALSE en caso contrario
int CNF_DPLL(CNF_Form f, int level,AsignationTree t) {

  backtrackToLevel;
  int lit;
  int numAssigUP = 0;


  int IsSolution=isSolution(f);

  if (IsSolution == TRUE)
  {
    return TRUE;

  } else if(IsSolution == FALSE){

      destroy(t->leaf);
      AsignationTree_findLeaf(t,f->numVar,0);
      if(f->clausulasAprendidas<100)
         addLearnClause(f,t->leaf);
      

    backtrackToLevel = level-1;
    return FALSE;
  } 
  else
  {

    pureLiteralElim(f,level,t);

    lit = chooseLiteral(f);

 
    assignLiteral(f,lit,TRUE,level,TRUE,t);

    unitPropagation(f,&numAssigUP,t,level);

    if (CNF_DPLL(f,level+1,t) == TRUE)

      return TRUE;

    else
    {

      restoreUnitProp(f,&numAssigUP,t);

      restoreState(f,lit,TRUE,t);

      if(level==backtrackToLevel)
      {
        assignLiteral(f,lit,FALSE,level,FALSE,t);

        AsignationTree_update(t);

        unitPropagation(f,&numAssigUP,t,level);

        if (CNF_DPLL(f,level+1,t) == TRUE)
            return TRUE;

        else
        {
            restoreUnitProp(f,&numAssigUP,t);
            restoreState(f,lit,FALSE,t);
        }
      }
    }
    if(level==backtrackToLevel)
    {
     //   if(t->asigMaxLevel!=NULL)
            backtrackToLevel=t->asigMaxLevel->level;
       // else
          //  backtrackToLevel=level-1;
    }
    return FALSE;
  }
}

// Funcion que retorna si una funcion es una solucion
// In: Recibe la funcion en CNF
// Out: Entrega TRUE si una formula es solucion o FALSE en caso contrario
int isSolution(CNF_Form f)
{
    List it;
    Clause cl;
  if (f->numVarAssig == f->numVar && f->satClauses == f->numClauses)
  {
      f->isTrue=TRUE;
    return TRUE;
  }
    if(f->isTrue==FALSE)
    {
        //f->isTrue=UNASSIGNED;
        return FALSE;
    }
  if(f->numVarAssig != f->numVar)
  {
      f->isTrue=UNASSIGNED;
      return UNASSIGNED;
  }
}

// Funcion que elige un literal a ser asignado
// In: Recibe la formula en CNF
// Out: Entrega la posicion del literal en el arreglo de literales
int chooseLiteral(CNF_Form f) {
  int i = 0;
  for (i = 0; i < f->numLiterals; i++)
    if (f->literals[i]->value == UNASSIGNED) {
      return i;
    }
  return -1;
}

// Metodo que le asigna un valor de verdad a una variable
// In: Recibe la Formula CNF, el litetaral a asignar, el valor, el nivel de la 
//     asignacion y una desicion
void assignLiteral(CNF_Form f, int lit, int value,int level, int desition,AsignationTree t) {

  int posLit1 = lit;
  int posLit2 = 0;
  int variable=(lit+2)/2;
  if (lit % 2 == 0)
  {
    posLit2 = lit + 1;
    init_Asignation(t->incidents[variable-1]->asig
            ,variable,value,level,desition);
  }
  else
  {
    posLit2 = lit - 1;
    init_Asignation(t->incidents[variable-1]->asig
            ,variable,value*(-1),level,desition);
  }
  f->literals[posLit1]->value = value;
  f->literals[posLit1]->asig = t->incidents[variable-1]->asig;
  f->literals[posLit2]->asig = t->incidents[variable-1]->asig;
  if (value == TRUE)
    f->literals[posLit2]->value = FALSE;
  else
    f->literals[posLit2]->value = TRUE;
  updateClauses(f, posLit1, posLit2,t);
  f->numVarAssig++;
}

// Metodo que actualiza las clausulas asociadas a una asignacion de una variable
// In: Recibe la formula CNF y las posiciones de los literales asociados a
//     la variable asignada
void updateClauses(CNF_Form f, int posLit1, int posLit2, AsignationTree t) {
  List it;
  Clause cl;

  // Actualizacion de las clausulas del literal positivo
  Literal lit = f->literals[posLit1];
  for (it = init(lit->clauses); ended(it); it = next(it)) {
    cl = ((Clause)yield(it));
    if (cl->isSatisf == TRUE && lit->value == TRUE)
      cl->numSat++;
    else {
      if (lit->value == TRUE) {
        cl->numSat++;
        cl->isSatisf = TRUE;
        f->satClauses++;
      } else {
        cl->numNotSat++;
        if(cl->size == cl->numNotSat)
        {
            cl->isSatisf=FALSE;
            f->isTrue=FALSE;
            insertImplicationsForAClause(t,cl,f->numVar);
        }

      }
    }
  }

  // Actualizacion de las clausulas del literal negado
  lit = f->literals[posLit2];
  for (it = init(lit->clauses); ended(it); it = next(it)) {
    cl = ((Clause)yield(it));
    if (cl->isSatisf == TRUE && lit->value == TRUE)
      cl->numSat++;
    else {
      if (lit->value == TRUE) {
        cl->numSat++;
        cl->isSatisf = TRUE;
        f->satClauses++;
      } else {
        cl->numNotSat++;
        if(cl->size == cl->numNotSat)
        {
            cl->isSatisf=FALSE;
            f->isTrue=FALSE;
            insertImplicationsForAClause(t,cl,f->numVar);
        }
      }
    }
  }
}

// Metodo que recupera el estado de las asignaciones realizadas en el Bactracking
// In: Recibe la formula CNF, el literal a recuperar y el valor a desasignar
void restoreState(CNF_Form f, int lit, int value, AsignationTree t) {
  int posLit1 = lit;
  int posLit2 = 0;


  if (lit % 2 == 0)
    posLit2 = lit + 1;
  else
    posLit2 = lit - 1;

  destroy(t->incidents[f->literals[posLit1]->asig->variable-1]->incident);
  restoreClauses(f, posLit1, posLit2);
  f->literals[posLit1]->value = UNASSIGNED;
  //free(f->literals[posLit1]->asig);
  f->literals[posLit1]->asig=NULL;
  f->literals[posLit2]->value = UNASSIGNED;
  f->literals[posLit2]->asig=NULL;
  //f->literals[posLit1]->asig->desition=0;
  //f->literals[posLit1]->asig->level=0;
  //f->literals[posLit1]->asig->value=0;
  //f->literals[posLit1]->asig->variable=0;
  f->numVarAssig--;
}

// Metodo que recupera el valor de las clausulas asociadas a la asignacion de
// una variable al hacer Backtracking
// In: Recibe una formula CNF y las posiciones de los literales asociados a la
//     variable asignada
void restoreClauses(CNF_Form f, int posLit1, int posLit2) {
  List it;
  Clause cl;

  // Se restauran las clausulas de la variable positiva
  Literal lit = f->literals[posLit1];
  for (it = init(lit->clauses); ended(it); it = next(it)) {
    cl = ((Clause)yield(it));
    if (lit->value == TRUE) {
      cl->numSat--;
      if (cl->numSat == 0) {
        cl->isSatisf = UNASSIGNED;
        f->satClauses--;
      }
    } else {
      cl->numNotSat--;
      if(cl->numSat==0)
          cl->isSatisf=UNASSIGNED;
    }
  }

  // Se restauran las clausulas de la variable negada
  lit = f->literals[posLit2];
  for (it = init(lit->clauses); ended(it); it = next(it)) {
    cl = ((Clause)yield(it));
    if (lit->value == TRUE) {
      cl->numSat--;
      if (cl->numSat == 0) {
        cl->isSatisf = UNASSIGNED;
        f->satClauses--;
      }
    } else {
      cl->numNotSat--;
      if(cl->numSat==0)
        cl->isSatisf = UNASSIGNED;
    }
  }
  f->isTrue=UNASSIGNED;
}

void insertImplicationsForAClause(AsignationTree t,Clause clau,int var)
{
    List it;
    Literal lit;

    for(it=init(clau->literals); ended(it); it=next(it))
    {
        lit=(Literal)yield(it);
        if(lit->asig->variable-1 != var)
    t->incidents[var]->incident=append(t->incidents[var]->incident,lit->asig);
    }
}

// Metodo que aplica Unit Propagation sobre la formula despues de una asignacion
// In: Recibe la formula en CNF, un entero que lleva las variables asignadas
//     en Unit Propagation en el nivel actual y el nivel.
void unitPropagation(CNF_Form f, int *numAssigUP,AsignationTree t,int level) {
  List it;
  Clause cl;
  Literal unitLit;
  int lit;
  int continues=1;

  while(continues)
  {
      continues=0;
    for (it = init(f->clauses); ended(it); it = next(it))
    {
    
        cl = (Clause)yield(it);
    
        if (cl->isSatisf == UNASSIGNED && isUnit(cl) == TRUE)
        {
         getUnitLiteral(cl);
      
         if (cl->unitLit != NULL)
         {
       
            unitLit = cl->unitLit;
               
            lit = searchLitPos(f->literals, unitLit, f->numLiterals);

            assignLiteral(f,lit,TRUE,level,FALSE,t);

            insertImplicationsForAClause(t, cl,unitLit->asig->variable-1);

            f->assigUnitProp = append(f->assigUnitProp,unitLit);
            (*numAssigUP)++;

             if(f->isTrue==FALSE)
                break;
             
            continues=1;
          }
       }
    }
    if(f->isTrue==FALSE)
        break;
  }
}

// Metodo que recupera el  Unit Propagation sobre la formula despues de unas 
// asignaciones en cascada hechas por Unit propagation
// In: Recibe la formula en CNF, un entero que lleva las variables asignadas
//     en Unit Propagation en el nivel actual.
void restoreUnitProp(CNF_Form f, int *numAssigUP,AsignationTree t) {
  List it;
  Literal lit;
  int posL;
  List litToDel = newList();

  if (*numAssigUP == 0)
    return;

  for (it = init(f->assigUnitProp); ended(it); it = next(it)) {

    if (*numAssigUP == 0)
      break;

    lit = (Literal)yield(it);
    posL = searchLitPos(f->literals, lit, f->numLiterals);

    restoreState(f,posL,TRUE,t);

    litToDel = append(litToDel,lit);
    (*numAssigUP)--;

  }

  for (it = init(litToDel); ended(it); it = next(it)) {
    lit = (Literal)yield(it);
    delete(f->assigUnitProp,lit);
  }

  freeList(litToDel);

}

// Funcion que determina si una clausula es unitaria
// In: Recibe una clausula
// Out: Entrega TRUE si la clausula es unitaria, FALSE en caso contrario
int isUnit(Clause cl) {
  if (cl->numSat == 0 && cl->numNotSat == (cl->size -1))
    return TRUE;
  else return FALSE;
}

// Metodo que busca el literal unitario en una clausula unitaria
// In: Recibe una clausula unitaria
void getUnitLiteral(Clause cl) {
  List it;
  Literal lit;
  for (it = init(cl->literals); ended(it); it = next(it)) {
    lit = (Literal)yield(it);
    if (lit->value == UNASSIGNED)
      cl->unitLit = lit;
  }
}

// Metodo que busca la posicion de un literal en el arreglo de literales de la formula
// In: Recibe el arreglo de literales, el literal a buscar y numero de literales
int searchLitPos(Literal *literals, Literal l, int tam) {
  int i = 0;
  for (i = 0; i < tam; i++) {
    if (literals[i] == l)
      return i;
  }
  return -1;
}

// Metodo que aplica Pure Literal Elimination
// Recibe la formula CNF del problem y el nivel en donde se encuentra
void pureLiteralElim(CNF_Form f,int level,AsignationTree t) {
  int i = 0;
  for (i = 0; i < f->numLiterals; i++) {
    if (size(f->literals[i]->clauses) == 0) {
      if (f->literals[i]->value == UNASSIGNED)
        assignLiteral(f,i,FALSE,level,FALSE,t);
    }
  }
}
