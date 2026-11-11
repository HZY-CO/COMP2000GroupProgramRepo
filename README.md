# Forest Fire Simulation

A Java-based forest fire simulation built for COMP2000. The project models a dynamic forest grid where trees can grow, burn, and regenerate under changing environmental conditions such as wind, lightning, and weather. It includes a Swing interface, animal movement, and a set of JUnit-based tests covering the main simulation systems.

## Team

- Zeu Sul
- Joshua Martin
- Cooper Went
- Edrick Han
- Ansh Verma

## Project Overview

This simulation creates a forest represented as a grid of cells. Each cell can contain a tree, rock, or empty space, and is affected by environmental factors and fire spread logic. The program tracks tree states, fire propagation, animal behaviour, and analytics while updating the simulation in real time.

### Key features

- Forest grid and cell-based simulation
- Fire spread driven by neighbouring cells and weather conditions
- Wind influence on spread and fire intensity
- Lightning events and random ignition events
- Dynamic weather cycles
- Animal agents moving through the forest
- Interactive graphical window and control panel

## Project structure

```text
.
├── lib/                  # JUnit console library and dependencies
├── src/                  # Java source files
│   ├── App.java          # Entry point for the graphical simulation
│   ├── Simulation.java   # Core simulation loop and environment behaviour
│   ├── Forest.java       # Forest representation and neighbour lookup
│   ├── Tree.java         # Tree state and burning behaviour
│   ├── Fire.java         # Fire logic and propagation
│   ├── Animal.java       # Animal lifecycle and movement
│   ├── Weather.java      # Weather state transitions
│   ├── Wind.java         # Wind strength and change behaviour
│   └── ...
├── tests/                # JUnit test classes
├── README.md             # Project overview and usage guide
├── .classpath            # Java classpath configuration
├── .project              # Eclipse project configuration
└── .gitignore
```

## Requirements

- Java JDK
- A terminal with access to `javac` and `java`

## Running the simulation

From the project root, compile and run the application:

```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin App
```

This launches the forest fire simulation in the Swing interface.

## Running tests

The project uses JUnit 5 without Maven. The console standalone JAR is already included in the `lib/` directory.

```bash
mkdir -p bin
javac -cp "lib/junit-platform-console-standalone-1.11.4.jar" -d bin src/*.java tests/*.java
java -jar lib/junit-platform-console-standalone-1.11.4.jar execute --class-path bin --scan-class-path
```

## Notes

- The simulation is designed for visual experimentation and educational demonstration.
- Fire spread, weather, and flora behaviour are intentionally modelled as simplified systems rather than exact ecological simulation.
- The project is currently structured as a desktop Java application rather than a build-tool-based project.

## Summary

This project combines object-oriented design, simulation logic, and graphical presentation to model a realistic but simplified ecosystem under wildfire conditions. It provides a practical example of how environmental systems, entity behaviour, and UI-driven analytics can be combined in a Java application.
