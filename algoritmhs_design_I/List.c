/* 
 * File:   List.c
 * Desc: Implementacion de la lista enlazada
 * Author: Juan J Garcia
 *         Federico Flaviani
 * Created on February 25, 2011, 6:55 AM
 */

#include <stdio.h>
#include <stdlib.h>
#include "List.h"

// Creacion de nueva lista vacia
List newList() {
  List llist = (List)malloc(sizeof(struct list));
  llist->head = NULL;
  llist->tail = NULL;
  llist->end = NULL;;
  llist->size = 0;
  return llist;
}

// Retorna el elemento a la cabeza de la lista
void *head(List llist) {
  if (size(llist) > 0)
    return llist->head;
}

// Retorna el ultimo elemento en la lista
void *end(List llist) {
  if (size(llist) > 0)
    return llist->end;
}

// Retorna el tamaño de la lista
int size(List llist) {
  if (llist->head != NULL)
    return llist->size;
  else 
    return 0;
}

// Retorna el inicio de la lista para el iterador
List init(List llist) {
  return llist;
}

// Condicion de parada del iterador sobre la lista
int ended(List llist) {
  if (llist != NULL && size(llist) > 0)
    return 1;
  else return 0;
}

// Retorna el proximo para el iterador de la lista
List next(List llist) {
  return llist->tail;
}

// Yield del iterador que retorna el elmento apuntado
void *yield(List llist) {
  return llist->head;
}

// Funcion para agregar un elemento a la lista
List append(List llist, void* obj) {

  if (search(llist,obj) < 0) {

    List temp = newList();

    // Caso en que la lista no sea vacia
    if (size(llist) > 0) {
      temp->head = obj;
      temp->end = llist->end;
      temp->size = llist->size + 1;
      temp->tail = (List)malloc(sizeof(struct list));
      *temp->tail = *llist;
      free(llist);
      return temp;

      // Caso en que la lista esta vacia
    } else {
      temp->head = obj;
      temp->end = obj;
      temp->size++;
      freeList(llist);
      return temp;
    }
  }
  return llist;
}

// Metodo que elimina el ultimo elemento agragado a la lista
void deleteEnd(List llist) {
  List temp, aux;
  void * out = NULL;
  
  if (size(llist) > 0) {
    out = llist->head;
    aux = newList();
    *aux = *llist;
    temp = llist->tail;
    if (temp != NULL)
      *llist = *temp;
    else {
      llist->head = NULL;
      llist->end = NULL;
      llist->tail = NULL;
      llist->size = 0;
    }
    aux->tail = NULL;
    freeList(aux);
  }
  free(out);
}

// Metodo que vacia y elimina cada elemento de la lista
void destroy(List llist) {
  List aux = llist;
  void * elem;
  
  while(llist->tail != NULL) {
    deleteEnd(llist);
  }
  llist->head = NULL;
  llist->end = NULL;
  llist->tail = NULL;
  llist->size = 0;

  llist = aux;
  
}

// Metodo que elimina un elemento dado en la lista
void delete(List llist, void* obj) {
  List temp, aux, temp_aux;

  if (size(llist) > 0) {
    
    temp_aux = llist;

    // Caso en que el elemento esta en la cabeza
    if(llist->head == obj) {
      
      if (llist->tail != NULL) {
        aux = newList();
        *aux = *llist;
        temp = llist->tail;
        *llist = *temp;
        aux->tail = NULL;
        freeList(aux);
      } else {
        llist->head = NULL;
        llist->end = NULL;
        llist->tail = NULL;
        llist->size = 0;
      }

      // Caso en que el elemento esta en la cola
    } else {

      while(llist->tail != NULL && llist->tail->head != obj) {
        llist->size--;
        llist = llist->tail;
      }

      if (llist->tail != NULL) {

        // Caso en que el elemento es el ultimo
        if (llist->tail->head == llist->end) {

          llist->end = llist->head;
          freeList(llist->tail);
          llist->tail = NULL;

          while (temp_aux != NULL) {
            temp_aux->end = llist->end;
            temp_aux = temp_aux->tail;
          }
          
          // Caso en que el elemento esta en el medio de la lista
        } else {
          
          aux = newList();
          *aux = *llist->tail;
          temp = llist->tail->tail;
          *llist->tail = *temp;
          llist->tail->head = temp->head;
          llist->size--;
          aux->tail = NULL;
          freeList(aux);
        }

      } else {

        llist = temp_aux;

        // Si no existe el elemento se recuperan los tamaños
        while(llist != NULL) {
          llist->size++;
          llist = llist->tail;
        }

      }
      
    }

  }

}

// Funcion que devuelve la posicion de un elemento en la lista
int search(List llist, void* obj) {
  int retval = -1;
  int i = 1;
  List temp;
  temp = llist;

  while(llist != NULL) {
    if(llist->head == obj)
      return i;
    else
      i++;
    llist = llist->tail;
  }
  llist = temp;
  return retval;
}

// Metodo que libera la memoria de una lista no usada
void freeList(List llist) {
  List aux;
  while(llist != NULL) {
    aux = llist->tail;
    free(llist);
    llist = aux;
  }
}


/* void display(List llist) { */
/*   if (size(llist) > 0) { */
/*     List i; */
/*     for (i = init(llist); ended(i); i = next(i)) */
/*       printf("%d\n",((struct entero *)yield(i))->val); */
/*   } */
/* } */

/* void display_aux(List llist) { */
/*   List temp; */
/*   temp = (List)malloc(sizeof(struct list)); */
/*   temp = llist; */

/*   while(llist->tail != NULL) { */
/*     printf("%d\n", ((struct entero *)(llist->head))->val); */
/*     llist = llist->tail; */
/*   } */

/*   llist = temp; */
/* } */

/* int pmain(void) { */
  
/*   struct entero *num1 = (struct entero *)malloc(sizeof(struct entero *)); */
/*   struct entero *num2 = (struct entero *)malloc(sizeof(struct entero *)); */
/*   struct entero *num3 = (struct entero *)malloc(sizeof(struct entero *)); */
/*   struct entero *num4 = (struct entero *)malloc(sizeof(struct entero *)); */
/*   struct entero *num5 = (struct entero *)malloc(sizeof(struct entero *)); */
/*   num1->val = 1; */
/*   num2->val = 2; */
/*   num3->val = 3; */
/*   num4->val = 4; */
/*   num5->val = 5; */
  
/*   List llist = newList(); */
  
/*   llist = append(llist,num1); */
/*   llist = append(llist,num2); */
/*   llist = append(llist,num3); */
/*   llist = append(llist,num3); */
/*   llist = append(llist,num4); */
/*   llist = append(llist,num5); */

/*   display(llist); */

/*   printf("Numero %d en la Posicion %d\n",num3->val,search(llist,num3)); */
/*   printf("Numero %d en la Posicion %d\n",num1->val,search(llist,num1)); */
/*   printf("Numero %d en la Posicion %d\n",num5->val,search(llist,num5)); */
/*   printf("Numero %d en la Posicion %d\n",num4->val,search(llist,num4)); */
/*   printf("Numero %d en la Posicion %d\n",num2->val,search(llist,num2)); */

/*   printf("Lo que saco de la lista es la cabeza\n"); */
/*   deleteEnd(llist); */
/*   printf("Lo que saco de la lista es la cabeza\n"); */
/*   deleteEnd(llist); */
/*   printf("Lo que saco de la lista es la cabeza\n"); */
/*   deleteEnd(llist); */

/*   destroy(llist); */

/*   display(llist); */

/*   printf("Size = %d\n",size(llist)); */
/*   delete(llist,num5); */
/*   printf("Size = %d\n",size(llist)); */
/*   delete(llist,num1); */
/*   printf("Size = %d\n",size(llist)); */
/*   delete(llist,num3); */
/*   printf("Size = %d\n",size(llist)); */
/*   delete(llist,num5); */
/*   printf("Size = %d\n",size(llist)); */

/*   display(llist); */
 
/*   display(llist); */

/*   printf("Cabeza de la lista= %d\n",(((struct entero *)(head(llist)))->val)); */
/*   printf("Fin de la lista= %d\n",(((struct entero *)(end(llist)))->val)); */

/*   display(llist); */

/*   printf("Numero %d en la Posicion %d\n",num3->val,search(llist,num3)); */
/*   printf("Numero %d en la Posicion %d\n",num1->val,search(llist,num1)); */
/*   printf("Numero %d en la Posicion %d\n",num5->val,search(llist,num5)); */
/*   printf("Numero %d en la Posicion %d\n",num4->val,search(llist,num4)); */
/*   printf("Numero %d en la Posicion %d\n",num2->val,search(llist,num2)); */

/*   display(llist); */

/* } */
