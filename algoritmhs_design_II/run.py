import sys
import os
import subprocess as sub
import time

def prom(values):
    return sum(values, 0.0) / len(values)

def writeTable(NP,SI,SMH,T,name):
    file = open(name+"_"+str(NP[1])+".tex","w")
    header = ["\documentstyle[12pt]{article}\n","\\begin{document}\n","\\begin{table}\n","\caption{Tabla "+name+" con "+str(NP[1])+" piezas}\n","\centering\n","\\begin{tabular}{|c|c|c|c|}\n","\hline\n"]
    final = ["\end{tabular}\n","\end{table}\n","\end{document}\n"]
    file.writelines(header)
    file.writelines([" & {\\bf Sol Inicial} & {\\bf Sol Obtenida} & {\\bf Tiempo (seg)} \\\\\n","\hline\n"])
    for i in range(0,30):
        #file.writelines(["Ejec"+str(i+1)+" & "+str(SI[i])+" & "+str(SMH[i])+"  & "+str(T[i])+" \\\\\n","\hline\n"])
        file.writelines(["Ejec"+str(i+1)+" & -- & "+str(SMH[i])+"  & "+str(T[i])+" \\\\\n","\hline\n"])
    # file.writelines(["{\\bf Mejor} & {\\bf "+str(min(SI))+"} & {\\bf "+str(min(SMH))+"} & {\\bf "+str(min(T))+"} \\\\\n","\hline\n"])
    # file.writelines(["{\\bf Promedio} & {\\bf "+str(prom(SI))+"} & {\\bf "+str(prom(SMH))+"} & {\\bf "+str(prom(T))+"} \\\\\n","\hline\n"])
    # file.writelines(["{\\bf Peor} & {\\bf "+str(max(SI))+"} & {\\bf "+str(max(SMH))+"} & {\\bf "+str(max(T))+"} \\\\\n","\hline\n"])
    file.writelines(["{\\bf Mejor} & {\\bf -- } & {\\bf "+str(min(SMH))+"} & {\\bf "+str(min(T))+"} \\\\\n","\hline\n"])
    file.writelines(["{\\bf Promedio} & {\\bf -- } & {\\bf "+str(prom(SMH))+"} & {\\bf "+str(prom(T))+"} \\\\\n","\hline\n"])
    file.writelines(["{\\bf Peor} & {\\bf -- } & {\\bf "+str(max(SMH))+"} & {\\bf "+str(max(T))+"} \\\\\n","\hline\n"])
    file.writelines(final)


############################################################################
########################## Programa Principal ##############################
############################################################################

# Altura inicial =  result[11]
# Solucion Inicial =  result[15]
# Lineas Incial =  result[19]
# Num Piezas =  result[23]
# Tiempo MH =  result[-2]
# Lineas MH =  result[-6]
# Solucion MH =  result[-10]
# Altura MH =  result[-14]

# Se compila el programa
print "\nCompilando el CSP..."
sub.call("make",shell=True,stdout=sub.PIPE)

#Se crean los directorios para las soluciones
sub.call("mkdir Solutions",shell=True,stderr=sub.PIPE)

print "\nGenerando Solucion a la Instancia...\n"

numPiezas = []
solInic = []
solMH = []
tiempos = []
name = "GA"
      
#### Se corre el CSP con la Metaheristica ###
#############################################

for i in range(0,30):

    result = (os.popen("./CSP").read()).split()

    #numPiezas.append(int(result[23]))
    numPiezas.append(150);
    #solInic.append(int(result[15]))
    tiempos.append(float(result[-2]))
    solMH.append(int(result[-10]))

# print numPiezas
# print solInic
# print max(solInic)
# print min(solInic)
# print prom()
# print solMH
# print max(solMH)
# print min(solMH)
# print tiempos
# print max(tiempos)
# print min(tiempos)

#writeTable(numPiezas,solInic,solMH,tiempos,name)
writeTable(numPiezas,[],solMH,tiempos,name)

#####################################

sub.call("pdflatex ./"+name+"_"+str(numPiezas[1])+".tex",shell=True,stdout=sub.PIPE)
sub.call("mv "+name+"_"+str(numPiezas[1])+".tex Solutions/",shell=True,stderr=sub.PIPE)
sub.call("mv "+name+"_"+str(numPiezas[1])+".pdf Solutions/",shell=True,stderr=sub.PIPE)
# Se borran archivos innecesarios
sub.call("rm *.log *.aux",shell=True,stderr=sub.PIPE)
