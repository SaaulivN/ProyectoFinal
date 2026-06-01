import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n========================================");
        System.out.println("PROYECTO FINAL - GRAFO DIRIGÍDO ACÍCLICO");
        System.out.println("========================================");
        System.out.println(("\n- Ingresa la cantidad de vértices que tendrá el grafo: "));
        int cantVertices = scanner.nextInt();

        GrafoDirigidoAciclico grafo = new GrafoDirigidoAciclico(cantVertices);

        int opcionAElegir = 0;

        while (opcionAElegir != 6) {
            System.out.println("\nMENÚ DE OPCIONES");
            System.out.println("[1] Insertar aristas.");
            System.out.println("[2] Mostrar grafo");
            System.out.println("[3] Verificar si estan conectados");
            System.out.println("[4] Eliminar aristas del grafo");
            System.out.println("[5] Ordenamiento topológico");
            System.out.println("[6] Salir");
            System.out.print("Opción: ");
            opcionAElegir = scanner.nextInt();

            switch (opcionAElegir) {
                case 1:
                    System.out.println("Ingresa el vértice origen");
                    int origen = scanner.nextInt();
                    System.out.println("Ingresa el vértice destino");
                    int destino = scanner.nextInt();

                    boolean aceptable = grafo.insertarArista(origen, destino);
                    break;
                case 2:
                    grafo.mostrarEstructura();
                    break;
                case 3:
                    System.out.println("Ingresa el vértice A");
                    int nodoA = scanner.nextInt();
                    System.out.println("Ingresa el vértice b");
                    int nodoB = scanner.nextInt();

                    boolean estanConectados = grafo.conectados(nodoA, nodoB);
                    break;
                case 4:
                    grafo.eliminarAristas();
                    System.out.println("Aristas eliminadas.");
                    break;
                case 5:
                    System.out.println("Grafo ordenado:\n");
                    String ordenado = grafo.topologicalSort();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
            }
        }

    }
}