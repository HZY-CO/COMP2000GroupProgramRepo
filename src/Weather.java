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
    private final float snowstormIgnitionMultiplier = 0.5f;
    private final float hellIgnitionMultiplier = 10f;

    private float currentLightningMultiplier;
    private float currentIgnitionMultiplier;

    private final String clearSkiesWeatherName = "Clear Skies.";
    private final String thunderstormWeatherName = "Thunder Storm!";
    private final String snowstormWeatherName = "Snow Storm!";
    private final String hellWeatherName = "Hell on Earth.";
    private String currentWeatherName;

    private float chanceOfClearSkies = 1f;
    private float chanceOfThunderstorm = 0.4f;
    private float chanceOfSnowstorm = 0.5f;
    private float chanceOfHell = 0.1f;

    public Weather(WeatherState state){
        this.state = state;
    }

    public Weather(WeatherState state, int ccs, int cts, int css, int ch){
        this.state = state;
        this.chanceOfClearSkies = ccs;
        this.chanceOfThunderstorm = cts;
        this.chanceOfSnowstorm = css;
        this.chanceOfHell = ch;
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
        return currentLightningMultiplier;
    }

    public float ReturnIgnitionMultiplier()
    {
        return currentIgnitionMultiplier;
    }

    public String ReturnWeatherName()
    {
        return currentWeatherName;
    }

    public void CycleWeather()
    {
        double weatherChance = Math.random();

        if(weatherChance < chanceOfClearSkies)
        {
            state = WeatherState.CLEAR_SKIES;
        }
        if(weatherChance < chanceOfThunderstorm)
        {
            state = WeatherState.THUNDERSTORM;
        }
        if(weatherChance < chanceOfSnowstorm)
        {
            state = WeatherState.SNOWSTORM;
        }
        if(weatherChance < chanceOfHell)
        {
            state = WeatherState.HELL;
        }

        UpdateMultipliers();
    }
}
