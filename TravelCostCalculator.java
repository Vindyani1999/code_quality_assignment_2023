import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TravelCostCalculator {
    static Map<String, Double> a = new HashMap<>();
    static Map<String, Double> b = new HashMap<>();
    static Map<String, Double> c = new HashMap<>();

    static void loadRates1(String file, Map<String, Double> rates) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(file)));
        String line; 
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            hotelRate.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
    }

    static void loadRates2(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            exchangeRate.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
    }

    static void loadRates3(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            flightCost.put(parts[0].toUpperCase(), Double.parseDouble(parts[1]));
        }
    }

    public static void main(String[] args) {
        try {
            loadRates1("data/hotel_rates.csv");
            loadRates2("data/exchange_rates.csv");
            loadRates3("data/flight_costs.csv");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your destination: ");
            String destination = reader.readLine().toUpperCase();

            double flightCost = flightCost.getOrDefault(destination, 0.0);
            double hotelCost = hotelRate.getOrDefault(destination, 0.0);

            System.out.print("Enter your stay duration in days: ");
            int stayDuration = Integer.parseInt(reader.readLine());
            hotelCost *= stayDuration;

            double totalCostUsd = flightCost + hotelCost;

            System.out.printf("Flight cost: USD %.2f\n", flightCost);
            System.out.printf("Hotel cost (%d days): USD %.2f\n", stayDuration, hotelCost);
            System.out.printf("Total: USD %.2f\n", totalCostUsd);

            String[] availableCurrencies = exchangeRate.keySet().toArray(new String[0]);
            System.out.print("Select your currency for final price estimation(" + String.join(", ", availableCurrencies) + "): ");
            String selectedCurrency = reader.readLine();

            double finalPriceLocalCurrency = totalCostUsd * exchangeRate.get(selectedCurrency);

            System.out.printf("Total in %s: %.2f\n", selectedCurrency, finalPriceLocalCurrency);
        } catch (IOException economy) {
            economy.printStackTrace();
        }
    }
}
