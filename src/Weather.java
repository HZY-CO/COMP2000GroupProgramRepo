public class Weather {
    public enum WeatherState {     
        CLEAR_SKIES,               
        THUNDERSTORM,               
        SNOWSTORM,
        HELL
    }
    private WeatherState state;

    private final float clearSkiesLightningMultiplier = 0f;
    private final float thunderstormLightningMultiplier = 2f;
    private final float snowstormLightningMultiplier = 1.3f;
    private final float hellLightningMultiplier = 4f;

    private final float clearSkiesIgnitionMultiplier = 0f;
    private final float thunderstormIgnitionMultiplier = 2f;
    private final float snowstormIgnitionMultiplier = 1f;
    private final float hellIgnitionMultiplier = 10f;

    private float currentLightningMultiplier;
    private float currentIgnitionMultiplier;

    private final String clearSkiesWeatherName = "Clear Skies.";
    private final String thunderstormWeatherName = "Thunder Storm!";
    private final String snowstormWeatherName = "Snow Storm!";
    private final String hellWeatherName = "Hell on Earth.";
    private String currentWeatherName;

    public Weather(WeatherState state){
        this.state = state;
    }

    private void UpdateMultipliers()
    {
        switch (state){
            case CLEAR_SKIES:
                currentLightningMultiplier = clearSkiesLightningMultiplier;
                currentIgnitionMultiplier = clearSkiesIgnitionMultiplier;
                currentWeatherName = clearSkiesWeatherName;
                return;
            case THUNDERSTORM:
                currentLightningMultiplier = thunderstormLightningMultiplier;
                currentIgnitionMultiplier = thunderstormIgnitionMultiplier;
                currentWeatherName = thunderstormWeatherName;
                return;
            case SNOWSTORM:
                currentLightningMultiplier = snowstormLightningMultiplier;
                currentIgnitionMultiplier = snowstormIgnitionMultiplier;
                currentWeatherName = snowstormWeatherName;
                return;
            case HELL:
                currentLightningMultiplier = hellLightningMultiplier;
                currentIgnitionMultiplier = hellIgnitionMultiplier;
                currentWeatherName = hellWeatherName;
                return;
        }
    }

    public float ReturnLightningMultiplier()
    {
        UpdateMultipliers();
        return currentLightningMultiplier;
    }

    public float ReturnIgnitionMultiplier()
    {
        UpdateMultipliers();
        return currentIgnitionMultiplier;
    }

    public String ReturnWeatherName()
    {
        UpdateMultipliers();
        return currentWeatherName;
    }
}
