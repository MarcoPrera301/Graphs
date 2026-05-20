import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        String filename = (args.length > 0) ? args[0] : "guategrafo.txt";

        Graph graph = GraphReader.read(filename);

        if(graph == null)
        {
            System.out.println("No se pudo cargar el grafo. Iniciando vacio.");
            graph = new Graph();
        }
        else
        {
            System.out.println("Grafo cargado desde: " + filename);
        }

        Algorithm floyd = new Algorithm(graph);

        System.out.println("\n=================Matriz de Adyacencia=================");
        System.out.println();
        graph.printMatrix();

        System.out.println("\n=================Floyd-Warshall (caminos mas cortos)=================");
        System.out.println();
        floyd.printDistMatrix();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while(running)
        {
            printMenu();
            String option = sc.nextLine().trim();

            if(option.equals("1"))
            {
                System.out.print("Ciudad origen  : ");
                String from = sc.nextLine().trim();

                System.out.print("Ciudad destino : ");
                String to = sc.nextLine().trim();

                int dist = floyd.getDistance(from, to);

                if(dist >= Graph.INF)
                {
                    System.out.println("No existe ruta entre " + from + " y " + to);
                }
                else
                {
                    List<String> path = floyd.getPath(from, to);
                    System.out.println("Distancia mas corta: " + dist + " km");
                    System.out.println("Ruta: " + String.join(" -> ", path));
                }
            }
            else if(option.equals("2"))
            {
                String center = floyd.getCenter();

                if(center == null)
                    System.out.println("El grafo esta vacio.");
                else
                    System.out.println("Centro del grafo: " + center);
            }
            else if(option.equals("3"))
            {
                System.out.println("a) Interrumpir trafico entre dos ciudades");
                System.out.println("b) Establecer conexion entre dos ciudades");
                String sub = sc.nextLine().trim().toLowerCase();

                if(sub.equals("a"))
                {
                    System.out.print("Ciudad origen  : ");
                    String from = sc.nextLine().trim();

                    System.out.print("Ciudad destino : ");
                    String to = sc.nextLine().trim();

                    boolean removed = graph.removeEdge(from, to);

                    if(removed)
                        System.out.println("Arco eliminado: " + from + " -> " + to);
                    else
                        System.out.println("No se encontro el arco.");
                }
                else if(sub.equals("b"))
                {
                    System.out.print("Ciudad origen  : ");
                    String from = sc.nextLine().trim();

                    System.out.print("Ciudad destino : ");
                    String to = sc.nextLine().trim();

                    System.out.print("Distancia (km) : ");
                    try
                    {
                        int km = Integer.parseInt(sc.nextLine().trim());
                        graph.addEdge(from, to, km);
                        System.out.println("Conexion agregada: " + from + " -> " + to + " = " + km + " km");
                    }
                    catch(NumberFormatException e)
                    {
                        System.out.println("Distancia invalida.");
                        continue;
                    }
                }
                else
                {
                    System.out.println("Opcion no reconocida.");
                    continue;
                }

                //recalcular rutas y centro
                floyd.run();

                System.out.println("\n=================Floyd-Warshall actualizado=================");
                floyd.printDistMatrix();
                System.out.println("Nuevo centro del grafo: " + floyd.getCenter());
            }
            else if(option.equals("4"))
            {
                running = false;
                System.out.println("Fin del programa.");
            }
            else
            {
                System.out.println("Opcion invalida.");
            }
        }

        sc.close();
    }

    private static void printMenu()
    {
        System.out.println("\n=================Menu=================");
        System.out.println("1. Ruta mas corta entre dos ciudades");
        System.out.println("2. Centro del grafo");
        System.out.println("3. Modificar el grafo");
        System.out.println("4. Salir");
        System.out.print("Opcion: ");
    }
}