/* 
  Proyecto Diseno de Algoritmo II - Objeto Patron

  Juan Garcia 05-38207
  Federico Flaviani 99-31744

  Compilar con: 
  Correr con: 
*/

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <list>
#include <time.h>

#include "Piece.h"

using namespace std;

class Pattern {

public:
  int width;
  int height;
  int heightMax;
  int num_pieces;
  list<Piece *>* pieces;
  int *tops;
  int area_ocup;
  int area_no_ocup;
  int lines;

  Pattern();
  Pattern(int, int);
  Pattern(list<Piece *>, int, int);
  ~Pattern();

  list<Pattern *> genVicinity();
  Pattern* vicinityOperator(int, int, int);
  void swap(int *, int, int);
  void updateRemovePaper(Piece *);
  void updateAddPaper(Piece *);
  void deleteList(list<Pattern *> *);
  Pattern* perturb();
  Pattern* clone();
  int calcHeight();
  int quality();
  void actualizar();
  void print();
  void swap();
  Pattern* addRequest(list<Piece *>);
  void addPiece(Piece *, int);
};

// Constructor de la clase Pattern
Pattern::Pattern() {
  num_pieces = 0;
  area_ocup = 0;
  area_no_ocup = 0;
  width = 0;
  height = 0;
  lines = 100000000;
}

// Constructor de la clase Pattern
Pattern::Pattern(int w, int h) {
  num_pieces = 0;
  area_ocup = 0;
  width = w;
  height = h;
  pieces = new list<Piece *>[w];
  tops = new int[w];
  area_no_ocup = w * h;
  lines = h;
}

// Constructor de la clase Pattern
Pattern::Pattern(list<Piece *> l_pieces, int w, int h) {
  int area = 0;
  list<Piece *>::iterator it;
  pieces = new list<Piece *>[w];
  tops = new int[w];
  num_pieces = l_pieces.size();
  for (it = l_pieces.begin(); it != l_pieces.end(); ++it)
    area += (*it)->large;
  area_ocup = area;
  area_no_ocup = w * h - area;
  lines = h;
  width = w;
  height = h;
}

// Destructor de la clase Patter
Pattern::~Pattern() {
  list<Piece *>::iterator it;
  for (int i = 0; i < this->width; i++) {
    for (it = this->pieces[i].begin(); it != this->pieces[i].end(); ++it) {
      (*it)->~Piece();
    }
  }
    
  delete [] this->pieces;
  delete [] this->tops;

}

list<Pattern *> Pattern::genVicinity() {

  list<Pattern *> list;
  int count = 0;

  for (int i = 0; i < this->width; i++) {
    for (int j = 1; j <= this->pieces[i].size(); j++) {
      for (int w = 0; w < this->width; w++) {
        list.push_back(this->vicinityOperator(i,j,w));        
      }
    }
  }
  
  return list;

}

Pattern* Pattern::vicinityOperator(int from, int pieceNum, int to) {

  int count = 0;
  Pattern* p = this->clone();
  Piece* pi;

  if ((from >= 0) && (from < this->width) && (pieceNum > 0)) {

    if (p->pieces[from].size() < pieceNum)
      return p;
  
    list<Piece *>::iterator it;
    for (it = p->pieces[from].begin(); it != p->pieces[from].end(); it++) {
      count++;
      if (count == pieceNum) {
        pi = (*it)->clone();
        (p->pieces[from]).erase(it);
        p->tops[from] -= pi->large;
        break;
      }
    }
  }

  (p->pieces[to]).push_back(pi);
  p->tops[to] += pi->large;
  p->actualizar();
  return p;
}

void Pattern::deleteList(list<Pattern *> * l) {
  list<Pattern *>::iterator v;
  list<Piece *>::iterator it;
  for (v = l->begin(); v != l->end(); v++) {
    (*v)->~Pattern();
  }
}

Pattern* Pattern::perturb() {

  Pattern * pert = this->clone();
  list<Pattern *> vic_list;
  list<Pattern *>::iterator it_l;
  int count = 0;
  int k = 0;
  int ran;

  srand(time(NULL));

  while (k < 10) {
    vic_list = pert->genVicinity();
    ran = (this->width * this->num_pieces) / (rand() % 10 + 1);
    for (it_l = vic_list.begin(); it_l != vic_list.end(); it_l++) {
      ++count;
      if (count == ran)
        if ((*it_l)->quality() >= pert->quality()) {
          pert->~Pattern();
          pert = (*it_l)->clone();
          break;
        }
    }
    k++;
    pert->deleteList(&vic_list);
  }
  return pert;
}

void Pattern::swap(int * v, int a, int b) {
  int aux = v[a];
  v[a] = v[b];
  v[b] = aux;
}

// Metodo para clonar un patron
Pattern* Pattern::clone() {

  Pattern* p = new Pattern(this->width, this->height);
  p->area_no_ocup = this->area_no_ocup;
  p->area_ocup = this->area_ocup;
  p->height = this->height;
  p->heightMax = this->heightMax;
  p->width = this->width;
  p->lines = this->lines;
  p->num_pieces = this->num_pieces;

  for(int i = 0; i < this->width; i++) {
    list<Piece *>::iterator it;
    for (it = this->pieces[i].begin(); it != this->pieces[i].end(); ++it) {
      p->pieces[i].push_back((*it)->clone());
    }
  }
         
  for (int i = 0; i < p->width; i++)
    p->tops[i] = this->tops[i];

  return p;
}

void Pattern::actualizar() {
  this->heightMax = 0;
  this->lines = 1000000000;
  for (int i = 0; i < this->width; i++) {
    if (this->tops[i] > this->heightMax)
      this->heightMax = this->tops[i];
    if (this->tops[i] <= this->lines)
      this->lines = this->tops[i];
  }

  this->area_no_ocup = this->width * this->heightMax - this->area_ocup;
}

// Funcion de calidad
int Pattern::quality() {
  return area_no_ocup;
}

void Pattern::print() {

  for (int k = 0; k < this->width; k++) {  
    for (int l = 0; l < this->tops[k]; l++) {
      cout << l + 1 << " ";
    }
    cout << "\n";
  }
  cout << "\n";
}

Pattern * Pattern::addRequest(list<Piece *> l) {
  int ran;
  Pattern * p = this->clone();
  list<Piece *>::iterator it;
  for (it = l.begin(); it != l.end(); it++) {
    ran = rand() % p->width;
    p->addPiece(*it,ran);
  }
  return p;
}

void Pattern::addPiece(Piece * pi, int position) {
  this->pieces[position].push_back(pi);
  this->tops[position] += pi->large;
  this->area_ocup += pi->large;
  this->actualizar();
}
