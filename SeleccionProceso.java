import java.util.LinkedList;
import java.util.Stack;

public class SeleccionProceso {
  static int algoritmo, seleccion;

  public static void FIFO(Proceso[] memoria, Proceso[] memoriaSecundaria, Proceso procesoNuevo,
      LinkedList<Proceso> colaFIFO, LinkedList<Proceso> colaFIFOSec) {
    Proceso p = null;
    if (colaFIFO.isEmpty()) {
      // pilaLIFO.push(procesoNuevo);
      return;
    } else
      p = colaFIFO.poll();

    eliminarProceso(memoria, p);
    // Main.imprimirArreglo(memoria);
    switch (algoritmo) {
      case 1:
        eliminarProceso(memoriaSecundaria, procesoNuevo);
        Algoritmo.primerAjuste(memoria, procesoNuevo, memoriaSecundaria, colaFIFO, colaFIFOSec);
        Algoritmo.primerAjuste(memoriaSecundaria, p, memoria, colaFIFOSec, colaFIFO);
        // primerAjuste(memoriaSecundaria, p);
        break;
      case 2:
        eliminarProceso(memoriaSecundaria, procesoNuevo);
        Algoritmo.mejorAjuste(memoria, procesoNuevo, memoriaSecundaria, colaFIFO, colaFIFOSec);
        Algoritmo.mejorAjuste(memoriaSecundaria, p, memoria, colaFIFOSec, colaFIFO);
        break;
      case 3:
        eliminarProceso(memoriaSecundaria, procesoNuevo);
        Algoritmo.peorAjuste(memoria, procesoNuevo, memoriaSecundaria, colaFIFO, colaFIFOSec);
        Algoritmo.peorAjuste(memoriaSecundaria, p, memoria, colaFIFOSec, colaFIFO);
        break;
      default:
        System.out.println("Algoritmo no valido");
        break;
    }
  }

  public static void LIFO(Proceso[] memoria, Proceso[] memoriaSecundaria, Proceso procesoNuevo, Stack<Proceso> pilaLIFO,
      Stack<Proceso> pilaLIFOSec) {
    Proceso p = null;
    // if (memoria.length>=memoriaSecundaria.length) {
    if (pilaLIFO.isEmpty()) {
      // pilaLIFO.push(procesoNuevo);
      return;
    } else
      p = pilaLIFO.pop();
    // }
    // else
    // p = pilaLIFOSec.pop();

    eliminarProceso(memoria, p);
    switch (algoritmo) {
      case 1:
        // primerAjuste(memoriaSecundaria, p);
        eliminarProceso(memoriaSecundaria, procesoNuevo);
        Algoritmo.primerAjuste(memoria, procesoNuevo, memoriaSecundaria, pilaLIFO, pilaLIFOSec);
        Algoritmo.primerAjuste(memoriaSecundaria, p, memoria, pilaLIFOSec, pilaLIFO);
        break;
      case 2:
        eliminarProceso(memoriaSecundaria, procesoNuevo);
        Algoritmo.mejorAjuste(memoria, procesoNuevo, memoriaSecundaria, pilaLIFO, pilaLIFOSec);
        Algoritmo.mejorAjuste(memoriaSecundaria, p, memoria, pilaLIFOSec, pilaLIFO);
        break;
      case 3:
        eliminarProceso(memoriaSecundaria, procesoNuevo);
        Algoritmo.peorAjuste(memoria, procesoNuevo, memoriaSecundaria, pilaLIFO, pilaLIFOSec);
        Algoritmo.peorAjuste(memoriaSecundaria, p, memoria, pilaLIFOSec, pilaLIFO);
        break;
      default:
        System.out.println("Algoritmo no valido");
        break;
    }
  }

  public static void eliminarProceso(Proceso[] memoria, Proceso p) {
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        memoria[i] = null;
      }
    }
  }

}