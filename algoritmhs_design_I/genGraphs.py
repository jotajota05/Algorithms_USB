import matplotlib.pyplot as plt

inst = []
time = []
file = open("",'r')
while True:
    line = file.readline()
    if not line: break
    line_content = line.split()
    inst.append(int(line_content[0]))
    time.append(float(line_content[1]))
file.close()

plt.plot(it,error)
plt.title("Tiempo del Solver")
plt.xlabel("Instancias")
plt.ylabel("Tiempo")
plt.xlim(0,60)
plt.ylim(0,0.1)
plt.savefig("TiempoSolver")
