import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n========================================");
        System.out.println("PROYECTO FINAL - GRAFO DIRIGÍDO ACÍCLICO");
        System.out.println("========================================");
        System.out.println("\n- Ingresa la cantidad de vértices que tendrá el grafo: ");
        int cantVertices = scanner.nextInt();

        GrafoDirigidoAciclico grafo = new GrafoDirigidoAciclico(cantVertices);

        int opcionAElegir = 0;

        while (opcionAElegir != 7) {
            System.out.println("\nMENÚ DE OPCIONES");
            System.out.println("[1] Insertar arista (por índice)");
            System.out.println("[2] Insertar arista (por dato)");
            System.out.println("[3] Mostrar grafo");
            System.out.println("[4] Verificar si estan conectados");
            System.out.println("[5] Eliminar aristas del grafo");
            System.out.println("[6] Ordenamiento topológico");
            System.out.println("[7] Salir");
            System.out.print("Opción: ");
            opcionAElegir = scanner.nextInt();

            switch (opcionAElegir) {
                case 1:
                    System.out.println("Ingresa el ÍNDICE del vértice origen:");
                    int origenIdx = scanner.nextInt();
                    System.out.println("Ingresa el ÍNDICE del vértice destino:");
                    int destinoIdx = scanner.nextInt();
                    grafo.insertarArista(origenIdx, destinoIdx);
                    break;
                case 2:
                    System.out.println("Ingresa el DATO del vértice origen:");
                    int origenDato = scanner.nextInt();
                    System.out.println("Ingresa el DATO del vértice destino:");
                    int destinoDato = scanner.nextInt();
                    grafo.insertarAristaPorDato(origenDato, destinoDato);
                    break;
                case 3:
                    grafo.mostrarEstructuraBien();
                    break;
                case 4:
                    System.out.println("Ingresa el ÍNDICE del vértice A:");
                    int nodoA = scanner.nextInt();
                    System.out.println("Ingresa el ÍNDICE del vértice B:");
                    int nodoB = scanner.nextInt();
                    grafo.conectados(nodoA, nodoB);
                    break;
                case 5:
                    grafo.eliminarAristas();
                    System.out.println("Aristas eliminadas.");
                    break;
                case 6:
                    String ordenado = grafo.topologicalSort();
                    System.out.println("\nGrafo ordenado:");
                    System.out.println(ordenado);
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    break;
            }
        }
        scanner.close();
    }
}