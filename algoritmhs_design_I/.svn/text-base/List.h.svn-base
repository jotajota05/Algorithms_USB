/* 
 * File:   List.h
 * Desc: Headers de la lista enlazada
 * Author: Juan J Garcia
 *         Federico Flaviani
 * Created on February 25, 2011, 6:55 AM
 */

#ifndef LIST_H
#define	LIST_H

// Estrucutra recursiva de la lista enlazada
struct list {
  void *head;
  struct list *tail;
  void *end;
  int size;
};

typedef struct list * List;

/* struct entero { */
/*   int val; */
/* }; */

// Creacion de lista vacia
List newList();

// Metodos para extraccion de campos
void *head(List);
void *end(List);
int size(List);

// Metodos para el iterador sobre la lista
List init(List);
int ended(List);
List next(List);
void *yield(List);

// Metodos de modificacion y acciones sobre la lista
List append(List, void*);
void deleteEnd(List);
void destroy(List);
void delete(List, void*);
int  search(List, void*);
void freeList(List);

/* void display(List); */
/* void display_aux(List); */

#endif	/* LIST_H */
