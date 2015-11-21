#ifndef CHROMOSOME
#define CHROMOSOME

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

#endif
