# COMP2000 Forest Fire Simulator
### Members: 
Zeu Sul, Joshua Martin, Cooper Went, Edrick Han, Ansh Verma

___

### Design Choices:

1. Converted from each **Entity** class maintaining its own coordinates to **Forest** class keeping coordinates of all **Entity** in a grid.
    - Allows easier neighbour search for determining where the **Fire** will spread.
2. Removed **EntityManager** and implemented **TickableManager** to remove the forced inheritance hierachy.

### Moving Entities

`Movable` defines the movement capability independently of entity type. The current `Animal` is both `Movable` and `Tickable`, and delegates movement decisions to an `AnimalMovement`. The default `FireAvoidanceMovement` randomly selects a neighboring cell that does not reduce its distance from the nearest fire when possible; `RandomMovement` provides unconstrained random wandering. Movement implementations can be supplied when constructing an animal or changed later. Ten animals start at random forest positions. Each active animal moves to an in-bounds neighboring cell, including diagonals, each simulation tick. When deaths lower the living population below ten, replacements wait until all active fires and burning trees have burned out, then spawn at random positions along the forest edge. Animals may share cells with trees. Each animal is displayed as a gold marker and removed from the window when it dies in a fire.

`Simulation` owns the forest state and advances trees, fires, animals, weather, lightning, and simulation analytics on each tick. `App` delegates simulation work to it and remains responsible for the window and controls.

The UI controls and statistics are split into `ControlPanel` and `AnalyticsPanel`. The control panel manages grid dimensions and cell size, while the analytics panel refreshes its labels from the current `Simulation`.

Animal population statistics count active animals as alive and inactive animals as dead. Tree statistics count total trees and trees in the burning state. Both use parallel streams with custom collectors; each worker accumulates into its own `EnumMap`, which the collector combiner merges after processing. These parallel streams run after model updates and only read the entity states.

___

### Current Classes and Patterns:

### Running Tests

The tests use JUnit 5 without Maven. The JUnit Console Standalone JAR is included in
`lib/` and is also added to the VS Code Java project classpath.

```sh
mkdir -p bin
javac -cp "lib/junit-platform-console-standalone-1.11.4.jar" -d bin src/*.java tests/*.java
java -jar lib/junit-platform-console-standalone-1.11.4.jar execute --class-path bin --scan-class-path
```
