import sys
import os
import subprocess as sub
import time

# Devuelve la fila de la casilla
def getRow(literal,n): 
    return ((literal-1) / n) / n

# Devuelve la columna de la casilla
def getCol(literal,n) :
    return ((literal -1) / n) % n

# Devuelve el valor de la casilla
def getValue(literal,n) :
    return 1 + ((literal -1) % n)

# Escribe la solucion Sudoku en archivo .PDF
def writeSudoku(name,out,n):
    file = open(name,"w")
    header = ["\documentstyle[12pt]{article}\n","\\begin{document}\n","\\begin{table}\n","\caption{Resolucion del Sudoku}\n","\centering\n","\\Huge\n","\\begin{tabular}{|c|c|c|c|c|c|c|c|c|}\n","\hline\n"]
    final = ["\end{tabular}\n","\end{table}\n","\end{document}\n"]
    file.writelines(header)
    for i in range(0,n):
        line = out[(i*n):(int(n*i)+n)]
        if (i<3 or i>5):
            file.writelines(["{\\bf "+line[0]+"} & {\\bf "+line[1]+"} & {\\bf "+line[2]+"} & "+line[3]+"  & "+line[4]+"  & "+line[5]+"  & {\\bf "+line[6]+"} & {\\bf "+line[7]+"} & {\\bf "+line[8]+"} \\\\\n","\hline\n"])
        else:
            file.writelines([line[0]+" & "+line[1]+" & "+line[2]+" & {\\bf "+line[3]+"} & {\\bf "+line[4]+"} & {\\bf "+line[5]+"} & "+line[6]+" & "+line[7]+" & "+line[8]+" \\\\\n","\hline\n"])
    file.writelines(final)


############################################################################
########################## Programa Principal ##############################
############################################################################


input_file = open("InstanciasSudoku.txt","r")
run_print = "pdflatex solution.tex"
count = 0

# Se compila el traductor
print "\nCompilando el traductor..."
sub.call("make",shell=True,stdout=sub.PIPE)

#Se crean los directorios para las soluciones
sub.call("mkdir Solutions",shell=True,stderr=sub.PIPE)
sub.call("mkdir TranslationsSAT",shell=True,stderr=sub.PIPE)

print "\nGenerando Soluciones a las Instancias...\n"

# Ciclo para correr el algoritmo con todas las instancias de entrada
while True:

    count += 1

    # Se corre el traductor con la Instancia leida
    inst_in_orig = input_file.readline()
    if not inst_in_orig: 
        break    
    inst_in_orig = inst_in_orig.replace(" ","_")
    run_translator = "./translator "+inst_in_orig[0:-1]+" out.cnf"
    os.system(run_translator) # Llamada al sistema del traductor

    # Se copian las traducciones a la carpeta Translations
    sub.call("cp out.cnf ./TranslationsSAT/out"+str(count)+".cnf",shell=True,stdout=sub.PIPE)
    
    # Se lee la traduccion obtenida en formato CNF
    f = open("out.cnf","r")
    while True:
        line = f.readline()
        if not line: break
        segments = line.split()
        if segments[0] == "p":
            n = int(pow(int(segments[2]),1.0/3)) + 1
            break
        
    #### Se corre el SATsolver Propio ###
    #####################################

    #start = time.time()
    run_solver = "./solver out.cnf"
    #end = time.time()
    #timeexec = end - start
    result = (os.popen(run_solver).read()).split() # LLamada al Solver Propio

#Caso de no tener Solucion
    if result[10]=="UNSAT":
        solution = ["0" for i in range(0,81)]
        print "\nSolucion: "+str(solution)+"\n"

# Caso en que se encuentre solucion
    else:
        print "Instancia"+str(count)+" = "+inst_in_orig[2:-1]
        #print "Tiempo de ejecucion = "+str(timeexec)
        variables = [i for i in result[12:93] if int(i)>0]
        solution = ""
        for i in variables:
            solution+=str(getValue(int(i),n))
        
        #Se imprime la solucion en .PDF y se guarda en la carpeta Solutions
        writeSudoku("solution"+str(count)+"Solver.tex",solution,n)
        print "Solucion Solver = "+solution+"\n"
        run_print = "pdflatex solution"+str(count)+"Solver.tex" # Llamada al sistema de Latex
        sub.call(run_print,shell=True,stdout=sub.PIPE)
        sub.call("mv solution"+str(count)+"Solver.pdf Solutions/",shell=True,stderr=sub.PIPE)

    #####################################


    #### Se corre el SATsolver zchaff ###
    #####################################

    run_zchaff = "./zchaff64/zchaff out.cnf 100"
    result = (os.popen(run_zchaff).read()).split() # LLamada al sistema del zchaff
    
# Caso de no tener Solucion
    if result[-1]=="UNSAT":
        solution = ["0" for i in range(0,81)]

# Caso en que se encuentre solucion
    else:
        print "Instancia"+str(count)+" = "+inst_in_orig[2:-1]
        variables = [i for i in result[17:746] if int(i)>0]
        solution = ""
        for i in variables:
            solution+=str(getValue(int(i),n))
    
        #Se imprime la solucion en .PDF y se guarda en la carpeta Solutions
        writeSudoku("solution"+str(count)+"ZChaff.tex",solution,n)
        print "Solucion ZChaff = "+solution+"\n"
        run_print = "pdflatex solution"+str(count)+"ZChaff.tex" # Llamada al sistema de Latex
        sub.call(run_print,shell=True,stdout=sub.PIPE)
        sub.call("mv solution"+str(count)+"ZChaff.pdf Solutions/",shell=True,stderr=sub.PIPE)

    #####################################


# Se borran archivos innecesarios
sub.call("rm *.log *.aux *.tex *.cnf",shell=True,stderr=sub.PIPE)

print "\nLas Soluciones en el tablero puede encontrarlas en el directorio Solutions"
print "\nLas traducciones a SAT puede encontrarlas en el directorio Translations\n"
