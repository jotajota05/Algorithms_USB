
EXE1 = translator
EXE2 = solver
OBJS1 = sudoku2sat.o translator.o
OBJS2 = solver.o CNF_Form.o List.o

# release settings
#CFLAGS = -O2
#LFLAGS = -s

# debug settings
CFLAGS = -g -lm
LFLAGS = -g -lm

all: ${EXE1} ${EXE2} clean

${EXE1}: ${OBJS1} 
	gcc ${LFLAGS} -o $@ ${OBJS1}

${EXE2}: ${OBJS2} 
	gcc ${LFLAGS2} -o $@ ${OBJS2}

clean:
	-rm -f ${OBJS1} ${OBJS2}

clobber: clean
	-rm -f ${EXE1} ${EXE2}

