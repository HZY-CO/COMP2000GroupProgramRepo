# COMP2000 Forest Fire Simulator
### Members: 
Zeu Sul, Joshua Martin, [add your name]

___

### Design Choices:

1. Converted from each **Entity** class maintaining its own coordinates to **Forest** class keeping coordinates of all **Entity** in a grid.
    - Allows easier neighbour search for determining where the **Fire** will spread.
2. Removed **EntityManager** and implemented **TickableManager** to remove the forced inheritance hierachy.
___

### Current Classes and Patterns:

