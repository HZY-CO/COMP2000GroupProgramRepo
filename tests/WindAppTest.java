import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class WindAppTest {
    @Test
    void changeDirectionKeepsWindValuesWithinSupportedRanges() {
        Wind wind = new Wind(0, 0, 0);

        for (int i = 0; i < 100; i++) {
            wind.changeDirection(1000, -1000, 1000);
        }

        assertTrue(wind.getX() >= -100 && wind.getX() <= 100);
        assertTrue(wind.getY() >= -100 && wind.getY() <= 100);
        assertTrue(wind.getWindPower() >= 0 && wind.getWindPower() <= 100);
    }
}
