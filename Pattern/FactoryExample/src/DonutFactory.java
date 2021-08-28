import java.util.HashMap;
import java.util.Map;

public class DonutFactory {
    private Map<DonutTypes, Integer> history = new HashMap<>();

    public Map<DonutTypes, Integer> getHistory() {
        return history;
    }

    public Donut getDonut(DonutTypes donutTypes) {
        Donut result = null;
        switch (donutTypes) {
            case CHERRY -> {
                result = new CherryDonut();
                counting(donutTypes);
            }
            case CHOCOLATE -> {
                result = new ChocolateDonut();
                counting(donutTypes);
            }
            case NUT -> {
                result = new NutDonut();
                counting(donutTypes);
            }
            default -> throw new IllegalArgumentException("Wrong donut type: " + donutTypes);
        }
        return result;
    }

    private void counting(DonutTypes donutTypes) {
        int count = 0;
        if (history.containsKey(donutTypes)) {
            count = history.get(donutTypes);
        }
        count++;
        history.put(donutTypes, count);
    }

    public void printHistory() {
        for (DonutTypes donutTypes : history.keySet()) {
            System.out.printf("%s - %d%n", donutTypes, history.get(donutTypes));
        }
    }
}
