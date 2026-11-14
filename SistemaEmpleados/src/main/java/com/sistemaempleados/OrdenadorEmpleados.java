
package com.sistemaempleados;

import com.sistemaempleados.modelo.Empleado;
import java.util.ArrayList;
import java.util.List;

public class OrdenadorEmpleados {

    // Ordenar empleados por codigo
    public List<Empleado> ordenarPorCodigo(List<Empleado> empleados) {

        List<Empleado> lista = new ArrayList<>(empleados);

        for (int i = 0; i < lista.size() - 1; i++) {

            for (int j = i + 1; j < lista.size(); j++) {

                if (lista.get(i).getCodigo().compareToIgnoreCase(
                        lista.get(j).getCodigo()) > 0) {

                    Empleado auxiliar = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, auxiliar);
                }
            }
        }

        return lista;
    }

    // Ordenar empleados por nombre
    public List<Empleado> ordenarPorNombre(List<Empleado> empleados) {

        List<Empleado> lista = new ArrayList<>(empleados);

        for (int i = 0; i < lista.size() - 1; i++) {

            for (int j = i + 1; j < lista.size(); j++) {

                if (lista.get(i).getNombreCompleto().compareToIgnoreCase(
                        lista.get(j).getNombreCompleto()) > 0) {

                    Empleado auxiliar = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, auxiliar);
                }
            }
        }

        return lista;
    }

    // Ordenar empleados por tipo
    public List<Empleado> ordenarPorTipo(List<Empleado> empleados) {

        List<Empleado> lista = new ArrayList<>(empleados);

        for (int i = 0; i < lista.size() - 1; i++) {

            for (int j = i + 1; j < lista.size(); j++) {

                if (lista.get(i).getTipo().compareToIgnoreCase(
                        lista.get(j).getTipo()) > 0) {

                    Empleado auxiliar = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, auxiliar);
                }
            }
        }

        return lista;
    }
}
