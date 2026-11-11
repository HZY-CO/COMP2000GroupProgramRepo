import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LightningTest {
    @Test
    void capsWeatherAdjustedIgnitionChanceAtOne() {
        double ignitionChance = Simulation.clampProbability(0.6 * 10);
        Forest forest = new Forest(1, 1);

        assertEquals(1.0, ignitionChance);
        assertDoesNotThrow(() ->
            new Lightning(new Position(0, 0), 1, 0.8, ignitionChance, 10, forest)
        );
    }
}
