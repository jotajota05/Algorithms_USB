CC = g++

OBJECTS = CSP.o Piece.o Pattern.o Chromosome.o

CSP: $(OBJECTS)
	$(CC) -g $(OBJECTS) -o CSP
	make clean
CSP.o:
	$(CC) -g -c CSP.cpp -o CSP.o
Piece.o:
	$(CC) -g -c Piece.cpp -o Piece.o
Pattern.o:
	$(CC) -g -c Pattern.cpp -o Pattern.o
Chromosome.o:
	$(CC) -g -c Chromosome.cpp -o Chromosome.o
clean:
	-rm -f *.o