public class Proceso {
  int tamano;
  int quantum;
  int id;

  Proceso(int tamano, int quantum, int id) {
    this.tamano = tamano;
    this.quantum = quantum;
    this.id = id;
  }

  @Override
  public String toString() {
    return "Proceso: " + id + " Tamaño: " + tamano + " Quantum: " + quantum;
  }

}