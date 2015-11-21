/*
  Proyecto Diseno de Algoritmo II - Objeto Pieza

  Juan Garcia 05-38207
  Federico Flaviani 99-31744

  Compilar con: 
  Correr con: 
*/

#include <stdio.h>
#include <stdlib.h>

using namespace std;

class Piece {

public:
  int large;
  Piece(int);
  Piece(const Piece &p);
  ~Piece();
  Piece* clone();
};

Piece::Piece(int l) {
  large = l;
}

Piece:: Piece(const Piece &p) {
  large = p.large;
}

Piece::~Piece() {

}

Piece* Piece::clone() {

  Piece* p = new Piece(this->large);
  
  return p;
}
