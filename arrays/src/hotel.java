import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class hotel {
     static final int MAX_HOTELS = 7;
    static final int MAX_FLOORS = 15;
    static final int LIGHTS_PER_FLOOR = 3;
    static final double[] LIGHT_COSTS = {0.25, 0.50, 1.00}; // Halógena, LED, Multiled
    static Random random = new Random();

    public static void main(String[] args) {
        // Generar datos aleatorios para los hoteles
        int[][][] hotels = generateHotelData();

        // Calcular el total de horas encendidas y el gasto por hotel
        double[] totalCosts = calculateTotalCosts(hotels);
        int totalHours = calculateTotalHours(hotels);

        // Imprimir los resultados
        printResults(totalCosts, totalHours);

        // Imprimir el hotel que más ha gastado
        printMostExpensiveHotel(totalCosts);

        // Imprimir la hora más común de encendido
        printMostCommonHour(hotels);
        printPatternHours(hotels);
    }
    public static void printPatternHours(int[][][] hotels) {
    Map<Integer, Integer> hourCounts = new HashMap<>();
    for (int i = 0; i < MAX_HOTELS; i++) {
        for (int j = 0; j < MAX_FLOORS; j++) {
            for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                int hours = hotels[i][j][k] % 100;
                hourCounts.put(hours, hourCounts.getOrDefault(hours, 0) + 1);
            }
        }
    }

    List<Map.Entry<Integer, Integer>> list = new ArrayList<>(hourCounts.entrySet());
    list.sort(Map.Entry.comparingByValue());

    System.out.println("Las horas con patrones de uso más frecuentes son:");
    for (int i = list.size() - 1; i >= 0 && list.get(i).getValue() > 1; i--) {
        System.out.println("Hora " + list.get(i).getKey() + ": " + list.get(i).getValue() + " veces");
    }
}

    public static int[][][] generateHotelData() {
        int[][][] hotels = new int[MAX_HOTELS][MAX_FLOORS][LIGHTS_PER_FLOOR];
        for (int i = 0; i < MAX_HOTELS; i++) {
            for (int j = 0; j < MAX_FLOORS; j++) {
                for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                    // Generar tipo de luz y horas de uso aleatorias
                    int lightType = random.nextInt(3);
                    int hours = random.nextInt(25);
                    // Guardar datos en la matriz
                    hotels[i][j][k] = lightType * 100 + hours; // Codificar el tipo de luz y las horas en un solo número
                }
            }
        }
        return hotels;
    }

    public static double[] calculateTotalCosts(int[][][] hotels) {
        double[] totalCosts = new double[MAX_HOTELS];
        for (int i = 0; i < MAX_HOTELS; i++) {
            for (int j = 0; j < MAX_FLOORS; j++) {
                for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                    int lightType = hotels[i][j][k] / 100;
                    int hours = hotels[i][j][k] % 100;
                    totalCosts[i] += hours * LIGHT_COSTS[lightType];
                }
            }
        }
        return totalCosts;
    }

    public static int calculateTotalHours(int[][][] hotels) {
        int totalHours = 0;
        for (int i = 0; i < MAX_HOTELS; i++) {
            for (int j = 0; j < MAX_FLOORS; j++) {
                for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                    int hours = hotels[i][j][k] % 100;
                    totalHours += hours;
                }
            }
        }
        return totalHours;
    }

    public static void printResults(double[] totalCosts, int totalHours) {
        System.out.println("Total de horas encendidas: " + totalHours);
        for (int i = 0; i < MAX_HOTELS; i++) {
            System.out.println("Gasto total del hotel " + (i + 1) + ": " + totalCosts[i]);
        }
    }
    public static void printMostExpensiveHotel(double[] totalCosts) {
        int maxIndex = 0;
        for (int i = 1; i < MAX_HOTELS; i++) {
            if (totalCosts[i] > totalCosts[maxIndex]) {
                maxIndex = i;
            }
        }
        System.out.println("El hotel que más ha gastado es el hotel " + (maxIndex + 1) + " con un gasto de " + totalCosts[maxIndex]);
    }

    public static void printMostCommonHour(int[][][] hotels) {
        Map<Integer, Integer> hourCounts = new HashMap<>();
        for (int i = 0; i < MAX_HOTELS; i++) {
            for (int j = 0; j < MAX_FLOORS; j++) {
                for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                    int hours = hotels[i][j][k] % 100;
                    hourCounts.put(hours, hourCounts.getOrDefault(hours, 0) + 1);
                }
            }
        }

        int mostCommonHour = Collections.max(hourCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("La hora más común de encendido es: " + mostCommonHour);
    }
}
/*
 * public static int[][][] generateHotelData() {
    int[][][] hotels = new int[MAX_HOTELS][MAX_FLOORS][LIGHTS_PER_FLOOR];
    for (int i = 0; i < MAX_HOTELS; i++) {
        for (int j = 0; j < MAX_FLOORS; j++) {
            for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                hotels[i][j][k] = random.nextInt(24); // horas aleatorias de encendido
            }
        }
    }
    return hotels;
}

public static double[] calculateTotalCosts(int[][][] hotels) {
    double[] totalCosts = new double[MAX_HOTELS];
    for (int i = 0; i < MAX_HOTELS; i++) {
        for (int j = 0; j < MAX_FLOORS; j++) {
            for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                totalCosts[i] += hotels[i][j][k] * LIGHT_COSTS[random.nextInt(LIGHT_COSTS.length)];
            }
        }
    }
    return totalCosts;
}

public static int calculateTotalHours(int[][][] hotels) {
    int totalHours = 0;
    for (int i = 0; i < MAX_HOTELS; i++) {
        for (int j = 0; j < MAX_FLOORS; j++) {
            for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                totalHours += hotels[i][j][k];
            }
        }
    }
    return totalHours;
}

public static void printResults(double[] totalCosts, int totalHours) {
    System.out.println("Total de horas encendidas: " + totalHours);
    for (int i = 0; i < MAX_HOTELS; i++) {
        System.out.println("Hotel " + (i+1) + ": $" + totalCosts[i]);
    }
}

public static void printMostExpensiveHotel(double[] totalCosts) {
    int maxIndex = 0;
    for (int i = 1; i < MAX_HOTELS; i++) {
        if (totalCosts[i] > totalCosts[maxIndex]) {
            maxIndex = i;
        }
    }
    System.out.println("El hotel que más ha gastado es el hotel " + (maxIndex+1) + " con un gasto de $" + totalCosts[maxIndex]);
}

public static void printMostCommonHour(int[][][] hotels) {
    // Crear un mapa para contar las horas
    Map<Integer, Integer> hourCounts = new HashMap<>();
    for (int i = 0; i < MAX_HOTELS; i++) {
        for (int j = 0; j < MAX_FLOORS; j++) {
            for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                int hour = hotels[i][j][k];
                hourCounts.put(hour, hourCounts.getOrDefault(hour, 0) + 1);
            }
        }
    }
    // Encontrar la hora más común
    int mostCommonHour = Collections.max(hourCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    System.out.println("La hora más común de encendido es: " + mostCommonHour);
}

public static void printPatternHours(int[][][] hotels) {
    // Crear un mapa para contar las horas
    Map<Integer, Integer> hourCounts = new HashMap<>();
    for (int i = 0; i < MAX_HOTELS; i++) {
        for (int j = 0; j < MAX_FLOORS; j++) {
            for (int k = 0; k < LIGHTS_PER_FLOOR; k++) {
                int hour = hotels[i][j][k];
                hourCounts.put(hour, hourCounts.getOrDefault(hour, 0) + 1);
            }
        }
    }
    // Ordenar el mapa por valor en orden descendente
    List<Map.Entry<Integer, Integer>> list = new ArrayList<>(hourCounts.entrySet());
    list.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
    System.out.println("Las horas con patrones de uso más frecuentes son:");
    for (Map.Entry<Integer, Integer> entry : list) {
        System.out.println("Hora " + entry.getKey() + ": " + entry.getValue() + " veces");
    }
}

 */