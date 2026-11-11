# COMP2000 Forest Fire Simulator
### Members: 
Zeu Sul, Joshua Martin, Cooper Went, Edrick Han, Ansh Verma

___

### Design Choices:

1. Converted from each **Entity** class maintaining its own coordinates to **Forest** class keeping coordinates of all **Entity** in a grid.
    - Allows easier neighbour search for determining where the **Fire** will spread.
2. Removed **EntityManager** and implemented **TickableManager** to remove the forced inheritance hierachy.
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
