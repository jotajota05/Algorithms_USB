/* 
  Proyecto Diseno de Algoritmo II - Objeto Cromosoma Para el 
  Algoritmo Genetico

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
#include <valarray>

using namespace std;

class Chromosome {

public:
  int **genes;
  int quality;
  int req;
  int width;
  int *tops;
  int heightMax;
  int area;
  int lines;

  Chromosome(int, int);
  ~Chromosome();
  void updateQuality();
  Chromosome* crossover(Chromosome*);
  void mutation();
  void fillChrom(int*, int*);
  void print();
};

// Constructor de la clase Chromosome
Chromosome::Chromosome(int r, int w) {
  req = r;
  width = w;
  genes = new int* [r];
  for (int i = 0; i < r; i++) {
    genes[i] = new int [w];
  }
  quality = 0;
  tops = new int[this->width];
}

// Destructor de la clase Chromosome
Chromosome::~Chromosome() {
  for (int i = 0; i < this->req; i++)
    delete [] this->genes[i];
  delete [] this->genes;
}

// Metodo que actualiza la calidad de un Cromosoma
void Chromosome::updateQuality() {
  this->heightMax = 0;
  this->area = 0;
  this->lines = 1000000;

  // Se actualiza el vector de alturas
  for (int j = 0; j < this->width; j++) {
    this->tops[j] = 0;
    for (int i = 0; i < this->req; i++) {
      this->tops[j] += this->genes[i][j];
    }
  }

  // Se actualiza el area ocupada y la altura maxima
  for (int i = 0; i < this->width; i++) {
    this->area += this->tops[i];
    if (this->tops[i] > this->heightMax)
      this->heightMax = this->tops[i];
    if (this->tops[i] < this->lines)
      this->lines = this->tops[i];
  }
  
  // Se actualiza la calidad segun la formula dada
  this->quality = this->width * this->heightMax - this->area;
 
}

// Funcion que realiza el Cruce entre dos cromosomas
Chromosome* Chromosome::crossover(Chromosome* c) {
  Chromosome* child = new Chromosome(this->req, this->width);
  for (int i = 0; i < this->req; i++) {
    for (int j = 0; j < this->width; j++) {
      if (i % 2 == 0) {
        child->genes[i][j] = this->genes[i][j];
      } else {
        child->genes[i][j] = c->genes[i][j];        
      }
    }
  }
  child->updateQuality();
  return child;
}

// Metodo que realiza la mutacion de un cromosoma
void Chromosome::mutation() {
  int ranI, ranJ1, ranJ2;

  for (int i = 0; i < this->req; i++) {
    ranI = i;// rand() % this->req;
    ranJ1 = rand() % this->width;
    ranJ2 = rand() % this->width;
    if (ranJ1 != ranJ2) {
      this->genes[ranI][ranJ2] += this->genes[ranI][ranJ1];
      this->genes[ranI][ranJ1] = 0;
    }
  }
  this->updateQuality();
}

// Metodo que genera una solucion aleatoria en un cromosoma
void Chromosome::fillChrom(int* numP, int* largeP ) {
  //srand(time(NULL));
  int ran;

  for (int i = 0; i < this->req; i++) 
    for (int j = 0; j < this->width; j++) 
      genes[i][j] = 0;

  for (int i = 0; i < this->req; i++) {
    for (int k = 0; k < numP[i]; k++) {
      ran = rand() % this->width;
      genes[i][ran] += largeP[i];
    }
  }
  this->updateQuality();
}

// Metodo que imprime un Cromosoma
void Chromosome::print() {

  for (int k = 0; k < this->width; k++) {  
    for (int l = 0; l < this->tops[k]; l++) {
      cout << l + 1 << " ";
    }
    cout << "\n";
  }
  cout << "\n";
}
