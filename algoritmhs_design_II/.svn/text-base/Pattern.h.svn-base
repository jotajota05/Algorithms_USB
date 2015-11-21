#ifndef PATTERN
#define PATTERN
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

  list<Pattern *> genVicinity();
  Pattern* vicinityOperator(int, int, int);
  void swap(int *, int, int);
  void deleteList(list<Pattern *> *);
  Pattern* perturb();
  Pattern* clone();
  int quality();
  void actualizar();
  void print();
  void swap();
  Pattern* addRequest(list<Piece *>);
  void addPiece(Piece *, int);
};

#endif
