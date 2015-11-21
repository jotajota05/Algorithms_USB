/*
  Proyecto Diseno de Algoritmo II - CSP con Busqueda Local

  Juan Garcia 05-38207
  Federico Flaviani 99-31744

  Compilar con: make 
  Correr con: ./CSP Metaheuristica Instancia --- Ej: ./CSP ILS 66
*/

#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <list>
#include <algorithm>
#include <time.h>
#include <math.h>

#include "Piece.h"
#include "Pattern.h"
#include "Chromosome.h"

struct arreglo
{
    int tam;
    int** O;
    int* S;
    list<int>* patConi;
};

using namespace std;

double diffclock(clock_t clock1,clock_t clock2) {
  double diffticks = clock1 - clock2;
  double diffms = (diffticks * 1000) / CLOCKS_PER_SEC;
  return diffms;
} 

// Procedimiento que genera una solucion inicial greedy

Pattern * init_pattern(list<Piece *> pieces, int W, int H) {

  Pattern * p = new Pattern(pieces, W, H);
  int i, j, k, l;
  int min_large = 1000000;
  int min_level = 0;
  int count = 0;
  list<Piece *>::iterator it;
  Piece * actual_p;
  bool locate = false;
  bool final = false;
  int reset;

  for (i = 0; i < W; i++)
    p->tops[i] = 0;
  i = W;

  for (it = pieces.begin(); it != pieces.end(); it++) {

    //sleep(1);

    if (i == W)
      i = 0;

    actual_p = *it;
                       
    p->pieces[i].push_back(actual_p);
    p->tops[i] += actual_p->large;
  
    i++;

    //(*p).print();
  }
  
  p->actualizar();
  return p;
}

// Metodo para comparar dos piezas
bool comparePieces(Piece * p1, Piece * p2) {
  if ((*p1).large > (*p2).large)
    return true;
  return false;
}

// Metodo para comparar dos patrones
bool comparePatterns(Pattern * p1, Pattern * p2) {
  if (p1->quality() > p2->quality())
    return true;
  return false;
}

// Metodo para comparar dos cromosomas
bool compareChromosomes(Chromosome * p1, Chromosome * p2) {
  if (p1->quality <= p2->quality)
    return true;
  return false;
}

// Metodo que libera la memoria de una lista de patrones
void deleteList(list<Pattern *> * l) {
  list<Pattern *>::iterator v;
  list<Piece *>::iterator it;
  for (v = l->begin(); v != l->end(); v++) {
    (*v)->~Pattern();
  }
}

// Implementacion de Busqueda Local

Pattern* localSearch( Pattern* initial) {

  int k = 0;
  Pattern* actual = initial->clone();
  Pattern* next;
  list<Pattern *> vicinity;
  list<Pattern *>::iterator v;

  while (k < 50) {

    // Se genera la vecindad
    vicinity = actual->genVicinity();

    for (v = vicinity.begin(); v != vicinity.end(); v++) {
      next = *v;
      if (next->quality() < actual->quality()) {
        actual->~Pattern();
        actual = next->clone();
        k++;
        break;
      }
    }
    k++;
    deleteList(&vicinity);
  }
  return actual;
}

// Implementacion de Busqueda Local Mejor Mejor

Pattern* localSearchMejorMejor(Pattern* initial) {

  int k = 0;
  Pattern* actual = initial->clone();
  Pattern* next;
  Pattern* best;
  list<Pattern *> vicinity;
  list<Pattern *>::iterator v;

  while (k < 50) {

    best = actual;
    // Se genera la vecindad
    vicinity = actual->genVicinity();

    for (v = vicinity.begin(); v != vicinity.end(); v++) {
      next = *v;
      if (next->quality() < best->quality()) {
        best = next;
      }
    }
    if (best->quality() < actual->quality()) {
      actual->~Pattern();
      actual = best->clone();
    }
    k++;
    deleteList(&vicinity);
  }
  return actual;
}

// Implementacion de ILS

Pattern* ILS(Pattern* initial) {
  
  Pattern *s1, *s2;
  Pattern *s_mejor = initial->clone();
  int k = 0;
 
  while( k < 10) {
    s1 = s_mejor->perturb();
    s2 = localSearch(s1);

    if(s2->quality() < s_mejor->quality()) {
      s_mejor->~Pattern();
      s_mejor = s2->clone();
    }
    s1->~Pattern();
    s2->~Pattern();
    k++;  
  }
  return s_mejor;
}

/*
struct arreglo PierceSuliman(int L,int heightsP[],int numPieces[],
                             int typePieces)
{
    sort(heightsP,numPieces);
    list<int[typePieces]> l;
    int aux=0;
    int alpha[]={1};
    alpha[0]=(L-aux)/heightsP[0];
    aux+=alpha[0]*heightsP[0];
    int k=0;
    int primerAlpha=alpha[k];
    while(k!=-1)
    {
        int* alpha[typePieces]=(int*)malloc(typePieces*sizeof(int));
        for(int i=0;i<k;i++)
        {
            alpha[i]=(L-aux)/heightsP[i];
            aux+=alpha[i]*heightsP[i];
        }
        alpha[k]=primerAlpha;
        for(int i=k+1;i<typePieces;i++)
        {
            alpha[i]=(L-aux)/heightsP[i];
            aux+=alpha[i]*heightsP[i];
        }
 *      for(int i=k+1;i<typePieces;i++)//verificar
        {
            alpha[i]=(L-aux)/heightsP[i];
            aux+=alpha[i]*heightsP[i];
        }//verificar
        if(L-aux<=L-1)
            l.push_back(alpha);//calcular la lista de los patrones con i
        int k=findIndiceAlphaPositivo(alpha);
        int primerAlpha=alpha[k]-1;
    }
    struct arreglo a=pasarAArreglo(l);//poner el paso 5 en esta rutina y calcular Sj
    return a;
}

void calculateFirstProbabilities(int &TM,int Z[],int typePieces,int numPieces[],
                                 int Mi[],int antsProbabilityi[])
{
    TM=0;
    for(int i=0; i<typePieces; i++)
    {
        Z[i]=1;
        Mi[i]=numPieces[i];
        TM+=Mi[i];
    }
    for(int i=0;i<typePieces;i++)
    {
        antsProbabilityi[i]=Mi[i]/TM;
    }
}

void calculateFirstProb2(int Pj[],int i,struct arreglo p)
{
    int Sd=0;
    list<int>::iterator it;
    for(it=p.patConi[i].begin();it!= p.patConi[i]; it++)
        Sd+=p.S[*it];
    for(it=p.patConi[i].begin();it!= p.patConi[i]; it++)
    {
        int j=*it;
        Pj[j]=1-p.S[j]/Sd;
    }
 * for(int i=0;i<typePieces;i++)//verificar
            {//Pi es de la iteracion anterior
                 V[i]=(Pi[i]-numPieces[i])*heightsP[i];
                 Pi[i]=0;
                 TOV+=TOV;
            }//verificar
}

void calculate1(int &TOV,int typePieces, int numPieces[],int heightsP[],int Pi[],
                int numAnts,int Nj[],int ant[],int V[],struct arreglo p)
{
            TOV=0;
            for(int i=0;i<typePieces;i++)
            {//Pi es de la iteracion anterior
                 V[i]=(Pi[i]-numPieces[i])*heightsP[i];
                 Pi[i]=0;
                 TOV+=TOV;
            }
            for(int j=0;j<p.tam;j++)
                Nj[j]=0;
            for(int i=0;i<numAnts;i++)
                Nj[ant[i]]++;
}

void calculate2(int &TM,int typePieces,int Pi[],int numPieces[],int Mi[],int ant,
                struct arreglo p)
{
    TM=0;
    for(int i=0;i<typePieces;i++)
    {
          Pi[i]+=p.O[i][ant];
          int aux=numPieces[i]-Pi[i];
          if(aux>0)
          {
              Mi[i]=aux;
              //falta actualizar los S[j]
          }
          else
          {
             Mi[i]=0;
          }
          TM+=Mi[i];
    }
}

int calculateSolution(int numAnts,int ant[],int X[],struct arreglo p)
{
            for(int i=0;i<numAnts;i++)
                X[i]=0;
            for(int i=0;i<numAnts;i++)
                X[ant[i]]++;
            int Solution=0;
            for(int i=0;i<p->tam;i++)
            {
                Solution+=X[i]*p.S[i];
            }
            return Solution;
}

int seleccionarCut(int typePieces,int antsProbabilityi[])
{
    return 0;
}

int seleccionarAnts(int antsProbabilityi[],int typePieces,int Pj[],
                    struct arreglo p)
{
    int i=seleccionarCut(typePieces,antsProbabilityi);
    calculateFirstProb2(Pj,i,struct arreglo p);

    //escoger probabilisticamente j usando Pj p.patConi

    return 0;
}

int seleccionarAntsWorst(int i,
                        int typePieces,int Pj[],int Nj[],struct arreglo p)
{
    list<int> ceros=new list<int>;
    list<int>::iterator it;
    int min;
    int i2=0;
    int total=0;
    for (it = p.patConi[i].begin(); it != p.patConi[i].end(); it++)
    {
        int j=*it;
        int aux=p.S[j];
        if(aux!=0)
        {
            Pj[j]=(Nj[j]/aux)*p.O[i][j];
            if(aux<min || i2=0)
                min=aux;
            total+=Pj[j];
            i2++;
        }
        else
            ceros.push_back(j);
    }
    if(i2==0)
    {
        for (it = p.patConi[i].begin(); it != p.patConi[i].end(); it++)
        {
            int j=*it;
            int denomi=0;
            for(int i3=0;i3<typePieces;i3++)
                denomi+=p.O[i3][j];
            Pj[j]=p.O[i][j]/denomi;
            total+=Pj[j];
        }
    }
    else
    {
        list<int>::iterator it2;
        for (it2 = ceros.begin(); it2 != ceros.end(); it2++)
        {
            int j=*it2;
            Pj[j]=(Nj[j]/(min*0.05))*p.O[i][j];
            total+=Pj[j];
        }
    }

    for (it = p.patConi[i].begin(); it != p.patConi[i].end(); it++)
    {
        int j=*it;
        Pj[j]=Pj[j]/total;
    }
    //escoger probabilisticamente un j y devolverlo

    return 0;
}

int seleccionarAntsBest(int i,int typePieces,int Pj[],int Nj[],struct arreglo p)
{
    list<int> ceros=new list<int>;
    list<int>::iterator it;
    int min;
    int i2=0;
    int total=0;
    for (it = p.patConi[i].begin(); it != p.patConi[i].end(); it++)
    {
        int j=*it;
        int aux=Nj[j]*p.S[j];
        if(aux!=0)
        {
            Pj[j]=((1/aux)^gamma)*p.O[i][j];
            if(aux<min || i2=0)
                min=aux;
            total+=Pj[j];
            i2++;
        }
        else
            ceros.push_back(j);
    }
    if(i2==0)
    {
        for (it = p.patConi[i].begin(); it != p.patConi[i].end(); it++)
        {
            int j=*it;
            int denomi=0;
            for(int i3=0;i3<typePieces;i3++)
                denomi+=p.O[i3][j];
            Pj[j]=p.O[i][j]/denomi;
            total+=Pj[j];
        }
    }
    else
    {
        list<int>::iterator it2;
        for (it2 = ceros.begin(); it2 != ceros.end(); it2++)
        {
            int j=*it2;
            Pj[j]=((1/(min*0.05))^gamma)*p.O[i][j];
            total+=Pj[j];
        }
    }

    for (it = p.patConi[i].begin(); it != p.patConi[i].end(); it++)
    {
        int j=*it;
        Pj[j]=Pj[j]/total;
    }
    //escoger probabilisticamente un j y devolverlo

    return 0;
}

void calculateAntProb(int &TOV,int TM,int typePieces,int V[],int Mi[],int Z[],
                      int antsProbabilityi[])
{
                int total=0;
                int min;
                int sumMin=0;
                list<int> ceros=new list<int>;
                for(int i=0;i<typePieces;i++)
                {
                    if(TOV!=0 || V[i]!=0)
                        Z[i]=1-V[i]/TOV;
                    else if(TOV!=0)
                        ceros.push_back(i);
                    else
                    {
                        antsProbabilityi[i]=(Mi[i]/TM)*Z[i];
                        if(V[i]<min || i==0)
                            min=V[i];
                        total+=antsProbabilityi[i];
                    }
                    sumMin=min*(i+1);
                }
                if(TOV!=0)
                {
                    while(!ceros.empty())
                    {
                        int i=ceros.pop_back();
                        V[i]=min*0.05;
                    }
                    TOV+=sumMin*0.05;
                    total=0;
                    for(int i=0;i<typePieces;i++)
                    {
                        Z[i]=1-V[i]/TOV;
                        antsProbabilityi[i]=(Mi[i]/TM)*Z[i];
                        total+=antsProbabilityi[i];
                    }
                }
                for(int i=0;i<typePieces;i++)
                    antsProbabilityi[i]=antsProbabilityi[i]/total;
    }
 *
 * void * improve(TM,typePieces,Pi,numPieces,Mi,ant[numAnts],p)
 * {
 *           if(Solution<=SolutionBefore)
                {
                    //seleccionar j por equacion 17-2
                    ant[numAnts]=seleccionarAntsWorst(i,typePieces,Pj,Nj,p);
                }
                else
                {
                    //seleccionar j por equacion 11-2
                    ant[numAnts]=seleccionarAntsBest(i,typePieces,Pj,Nj,p);
                }
                calculate2(TM,typePieces,Pi,numPieces,Mi,ant[numAnts],p);
                numAnts++;
 * }

Pattern* ACO(int L,int heightsP[],int numPieces[], int typePieces)
{
    struct arreglo p=PierceSuliman(L,heightsP,numPieces,typePieces);

    int X[p.tam];
    int Xbest[p.tam];
    int Pj[p.tam];
    int Nj[p.tam];
    int S[p.tam];
    int Pi[typePieces];//inicializar en 0
    int antsProbabilityi[typePieces];
    int Mi[typePieces];
    int V[typePieces];
    int Z[typePieces];
    int totalPieces=0;

    for(int i=0;i<typePieces;i++)
    {
        totalPieces+=numPieces[i];
    }

    //FindInitialSolution
    int TM;
    calculateFirstProbabilities(TM,Z,typePieces,numPieces,Mi,antsProbabilityi);
    int ant[totalPieces];
    int numAnts=0;
    while(TM!=0)
    {
        ant[numAnts]=seleccionarAnts(antsProbabilityi,typePieces,Pj,p);
        numAnts++;
    }
    int bestSolution=calculateSolution(numAnts,ant,Xbest,p);
    int SolutionBefore=bestSolution;
    int Solution=bestSolution;
    int k=0;
    while(k<1000)
    {
            int TOV;
            calculate1(TOV,typePieces,numPieces,heightsP,Pi,numAnts,Nj,ant,V,p);
            while(TM!=0)
            {
                calculateAntProb(TOV,TM,typePieces,V,Mi,Z,antsProbabilityi);
                int i=seleccionarCut(typePieces,antsProbabilityi);
                if(Solution<=SolutionBefore)
                {
                    //seleccionar j por equacion 17-2
                    ant[numAnts]=seleccionarAntsWorst(i,typePieces,Pj,Nj,p);
                }
                else
                {
                    //seleccionar j por equacion 11-2
                    ant[numAnts]=seleccionarAntsBest(i,typePieces,Pj,Nj,p);
                }
                calculate2(TM,typePieces,Pi,numPieces,Mi,ant[numAnts],p);
                numAnts++;
            }
            SolutionBefore=Solution;
            Solution=calculateSolution(numAnts,ant,X,p);
            //Calcular best solution
            if(Solution<bestSolution)
            {
                bestSolution=Solution;
                for(int i=0;i<p->tam;i++)
                    Xbest[i]=X[i];
            }
            k++;
 *          improve(TM,typePieces,Pi,numPieces,Mi,ant[numAnts],p);//verificar
     }
}
*/

// Implementacion de GRASP

Pattern* GRASP(list<Piece *> * piecesPart, int req, int width, int height) {
  Pattern * pat = new Pattern(width, height);
  Pattern * pat_aux;
  list<Pattern *> RCL;
  list<Pattern *>::iterator it;
  list<Piece *>::iterator it_p;
  int ran, count;
  srand(time(NULL));
  
  for (int i = 0; i < req; i++) {

    // Se general la lista de candidatos
    for (int j = 0; j < 10; j++) {
      RCL.push_back(pat->addRequest(piecesPart[i]));
    }
    
    // Se ordena la lista RCL por calidad
    RCL.sort(comparePatterns);
    ran = rand() % 10 + 1;
    count = 0;

    for (it = RCL.begin(); it != RCL.end(); it++) {
      count++;
      // Se selecciona el patron para usar en la construccion
      if (count == ran) {
        pat->~Pattern();
        pat = (*it)->clone();
        break;
      }
    }
   
    RCL.clear();
    // Se aplica busqueda local a la solucion actual
    pat_aux = localSearch(pat);
    pat = pat_aux->clone();
    pat_aux->~Pattern();
  
  }

  return pat;
}

// Implementacion de Simulated Anneling

Pattern* SA(Pattern* initial) {

  int i = 0;

  int a = 0;
  int maxAccept = 20;  

  double k = 0.0;
  double maxIter = 200.0;
  double ro = 1.05;

  double temp = 1000.0;
  double alfa = 0.8;


  double delta;
  int from, pieceNum, to;
  double suerte;
  
  Pattern* actual = initial->clone();
  Pattern *next;
  
  srand(time(NULL));

  while (i < 50) {

    while (k < maxIter && a < maxAccept) {

      while (true) {
        from = rand() % actual->width;
        if ((actual->pieces[from]).size() != 0) {
          pieceNum = rand() % (actual->pieces[from]).size() + 1;
          to = rand() % actual->width;
          break;
        } else continue;
      }

      // Se genera el vecino proximo a evaluar
      next = actual->vicinityOperator(from,pieceNum,to);

      // Se calcula el delta con la comparacion de calidades
      delta = (double)next->quality() - (double)actual->quality();

      if ( delta < 0 ) {
        // Se acepta el nuevo patron
        actual->~Pattern();
        actual = next->clone();
        a++;
      } else {
        // Se lanza el dado para ver si se acepta el patron de mas baja calidad
        suerte = (double)rand() / ( (double)(RAND_MAX)+(double)(1) );
        if (temp > 0.0) {
          if (suerte < (1.0 / (double)exp(delta/temp)) ) {
            actual->~Pattern();
            actual = next->clone();
            a++;
          }
        }
      }

      k += 1;
    }
    // Disminuye la temperatura
    temp *= alfa;
    // Aumenta el numero de iteraciones
    maxIter *= ro;
    k = 0.0;
    a = 0;
    i++;
  }
  
  return actual;
}

Chromosome* GA(int request, int width, int *numPieces, int *largePieces) {

  int sizePop = 400;
  list<Chromosome *> population;// = new Chromosome*[sizePop];
  list<Chromosome *> best;
  list<Chromosome *> worst;
  list<Chromosome *> child;
  list<Chromosome *>::iterator it;
  list<Chromosome *>::reverse_iterator rit;
  list<Chromosome *>::iterator itB;
  list<Chromosome *>::iterator itW;
  Chromosome * ch;
  int count = 0;
  double suerte;
  double probMut = 0.3;
  int ran;

  // Se genera la Poblacion inicial

  for (int i = 0; i < sizePop; i++) {
    ch = new Chromosome(request,width);
    ch->fillChrom(numPieces,largePieces);
    population.push_back(ch);
  }

  for (int g = 0; g < 1000; g++) {

    // Se ordena la poblacion por calidad
    population.sort(compareChromosomes);
  
    // Se toman los mejores
    count = 0;
    for (it = population.begin(); it != population.end(); it++) {
      best.push_back(*it);
      if (++count == sizePop/4) break;
    }

    // Se toman los peores
    count = 0;
    for (rit = population.rbegin(); rit != population.rend(); ++rit) {
      worst.push_front(*rit);
      if (++count == sizePop/4) break;
    }

    // Se cruzan los mejores con los peores
    itB = best.begin();
    itW = worst.begin();
    while (itB != best.end() && itW != worst.end()) {
      child.push_back((*itB)->crossover(*itW));
      itB++;
      itW++;
    }

    // Se cruzan los mejores entre ellos
    itB = best.begin();
    rit = best.rbegin();
    while (itB != best.end() && rit != best.rend()) {
      child.push_back((*itB)->crossover(*rit));
      itB++;
      ++rit;
    }

    // Se decide si se realiza mutacion
    suerte = (double)rand() / ( (double)(RAND_MAX)+(double)(1) );

    if (suerte < probMut) {
      ran = rand() % child.size() + 1;
      count = 0;
      for (it = child.begin(); it != child.end(); it++) {
        count++;
        (*it)->mutation();
        if (count == ran) break;
      }
    }

    // Se realiza la repoblacion
    count = 0;
    for (rit = population.rbegin(); rit != population.rend(); ++rit) {
      population.erase(--(rit.base()));
      if (count == child.size()) break;
    }

    for (it = child.begin(); it != child.end(); it++) {
      population.push_back(*it);
    }

    best.clear();
    worst.clear();
    child.clear();

  }

  population.sort(compareChromosomes);
  
  return population.front();

}

// Funcion para calcular la cota superior de tela
int calcHeight(int *pieces, int n, int* heights) {

  int i = 0;
  int height = 0;
  for (i = 0; i < n; i++)
    height = height + (pieces[i] * heights[i]);

  return height;
}

////////////////////////////////////////////////////////
// Programa principal
////////////////////////////////////////////////////////

int main(int argc, char **argv) {

  int i, j;

  int width;
  int *numPieces;
  int totalPieces;
  int *largePieces;
  int request;

  string MH = argv[1];

  int a1[] = {4, 4, 4, 4};
  int a2[] = {4, 2, 6, 8};
  int b1[] = {3, 5, 7, 9, 5, 15, 6, 5};
  int b2[] ={8, 4, 6, 2, 8, 5, 9, 7};
  int c1[] = {7, 5, 11, 20, 5, 8, 10};
  int c2[] = {6, 2, 3, 5, 2, 5, 4};
  int d1[] = {20, 20, 20, 20, 20};
  int d2[] = {5, 4, 3, 2, 7};
  int e1[] = {13, 5, 17, 9, 15, 15, 6, 5, 10, 24, 8, 9, 10, 4};
  int e2[] = {8, 4, 6, 2, 8, 5, 9, 7, 2, 8, 7, 9, 2, 10};

  // 1ra instancia
  if (atoi(argv[2]) == 16) {
     width = 3;
     numPieces = a1;
     totalPieces = 16;
     largePieces = a2;
     request = 4;
  // 2da instancia
  } else if (atoi(argv[2]) == 55) {
     width = 10;
     numPieces = b1;
     totalPieces = 55;
     largePieces = b2;
     request = 8;
  // 3ra instancia
  } else if (atoi(argv[2]) == 66) {
     width = 8;
     numPieces = c1;
     totalPieces = 66;
     largePieces = c2;
     request = 7;
  // 4ta instancia
  } else if (atoi(argv[2]) == 100) {
     width = 8;
     numPieces = d1;
     totalPieces = 100;
     largePieces = d2;
     request = 5;
  // 5ta instancia
  } else if (atoi(argv[2]) == 150) {
     width = 10;
     numPieces = e1;
     totalPieces = 150;
     largePieces = e2;
     request = 14;
  }

  int height = calcHeight(numPieces, request, largePieces);

  cout << "\nWidth = " << width << "\n";
  cout << "Height = " << height << "\n";

  list<Piece *> pieces;
  list<Piece *> piecesPart[request];
  Piece *P;
  Pattern * pat;
  Pattern* patOpt;
  Pattern* patOpt2;
  int count = 0;
  clock_t begin, end;
  
  for (i = 0; i < request; i++) {
    for (j = 0; j < numPieces[i]; j++) {
      count++;
      P = new Piece(largePieces[i]);
      piecesPart[i].push_back(P);
      pieces.push_back(P);
    }
  }

  pat = new Pattern(width, height);

  //pieces.sort(comparePieces);

  ///////////////////////////////////////////////
  // Patron Inicial
  ///////////////////////////////////////////////

  if (MH == "LS" || MH == "LSMM" || MH == "ILS" || MH == "SA") {

    cout << "\n Patron Incicial \n";
    pat = init_pattern(pieces, width, height);
    pat = pat->perturb();
    //pat->print();

    cout << "Altura Inicial = " << pat->heightMax << "\n";
    printf("Solucion Inicial = %d\n",pat->quality());
    cout << "Lineas Incial = " << pat->lines << "\n";
    cout << "Num Piezas = " << pat->num_pieces << "\n";
    cout << "\n";

  }

  // Llamada a las Metaheuristicas


  /////////////////////////////////////////////////////
  // LOCAL SEARCH
  /////////////////////////////////////////////////////

  if (MH == "LS") {

    begin = clock();
    patOpt = localSearch(pat);
    end = clock();
    //patOpt->print();

    cout << "Altura LS = " << patOpt->heightMax << "\n";
    printf("Solucion LS = %d\n",patOpt->quality());
    cout << "Lineas LS = " << patOpt->lines << "\n";
    cout << "Tiempo de Ejecucion: " << (double(diffclock(end,begin))) / 1000 << " seg"<< endl;
    cout << "\n";

    /////////////////////////////////////////////////////
    // LOCAL SEARCH - MEJOR MEJOR
    /////////////////////////////////////////////////////

  } else   if (MH == "LSMM") {

    begin = clock();
    patOpt = localSearchMejorMejor(pat);
    end = clock();
    //patOpt->print();

    cout << "Altura LS-MM = " << patOpt->heightMax << "\n";
    printf("Solucion LS-MM = %d\n",patOpt->quality());
    cout << "Lineas LS-MM = " << patOpt->lines << "\n";
    cout << "Tiempo de Ejecucion: " << (double(diffclock(end,begin))) / 1000 << " seg"<< endl;
    cout << "\n";

    /////////////////////////////////////////////////////
    // ILS
    /////////////////////////////////////////////////////

  } else   if (MH == "ILS") {

    begin = clock();
    patOpt = ILS(pat);
    end = clock();
    //patOpt->print();

    cout << "Altura ILS = " << patOpt->heightMax << "\n";
    printf("Solucion ILS = %d\n",patOpt->quality());
    cout << "Lineas ILS = " << patOpt->lines << "\n";
    cout << "Tiempo de Ejecucion: " << (double(diffclock(end,begin))) / 1000 << " seg"<< endl;
    cout << "\n";

    /////////////////////////////////////////////////////
    // GRASP
    /////////////////////////////////////////////////////

  } else   if (MH == "GRASP") {

    begin = clock();
    patOpt2 = GRASP(piecesPart,request,width,height);
    end = clock();
    //patOpt2->print();

    cout << "Altura GRASP = " << patOpt2->heightMax << "\n";
    printf("Solucion GRASP = %d\n",patOpt2->quality());
    cout << "Lineas GRASP = " << patOpt2->lines << "\n";
    cout << "Tiempo de Ejecucion: " << (double(diffclock(end,begin))) / 1000 << " seg"<< endl;
    cout << "\n";

    /////////////////////////////////////////////////////
    // SIMULATED ANNELING
    ////////////////////////////////////////////////////

  } else   if (MH == "SA") {

    begin = clock();
    patOpt = SA(pat);
    end = clock();
    //patOpt->print();

    cout << "Altura SA = " << patOpt->heightMax << "\n";
    printf("Solucion SA = %d\n",patOpt->quality());
    cout << "Lineas SA = " << patOpt->lines << "\n";
    cout << "Tiempo de Ejecucion: " << (double(diffclock(end,begin))) / 1000 << " seg"<< endl;
    cout << "\n";

    /////////////////////////////////////////////////////
    // Algoritmo Genetico
    /////////////////////////////////////////////////////

  } else   if (MH == "GA") {

    srand(time(NULL));

    begin = clock();
    Chromosome *optGA = GA(request,width,numPieces,largePieces);
    end = clock();

    printf("Solucion GA = %d\n",optGA->quality);
    cout << "Lineas GA = " << optGA->lines << "\n";
    cout << "Tiempo de Ejecucion: " << (double(diffclock(end,begin))) / 1000 << " seg"<< endl;
    cout << "\n";

  }
  
  cout << "\n";

  return 0;
}
