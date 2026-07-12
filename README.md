## <img src="icon_game_64.png" alt="Icon" width="48" style="vertical-align: middle; margin-right: 10px;"/> Maze Builder

A 2D tower defense game built in Java using Swing and AWT. Place defensive structures to reroute enemies and stop them from reaching your base. Originally created as a personal project in 2020.

![Gameplay GIF](screenshots/gameplay.gif)

##	Features
- Pathfinding - Enemies recalculate their path to walk around your buildings.
- Multiple Building Types – Turrets (attack enemies), Walls (block paths), Banks (bonus gold).
- Wave System – Enemies of different types (walking, flying) spawn in increasingly difficult waves.
- Projectile Combat – Turrets fire projectiles at enemies.
- Custom Game Loop – A custom-made game loop

##	Project Structure
```
Maze-Builder/
├── src/
│   ├── Components/     # Main game window, rendering, game loop
│   ├── Levels/         # Map loading, wave spawning, pathfinding
│   ├── Buildings/      # Turrets, walls, banks
│   ├── Enemies/        # Enemy types (walking, flying)
│   ├── Enums/          # Enumerations for game constants
│   ├── Utility/        # Asset loading, animations, helpers
│   └── Assets/         # Images, icons, level data
└── screenshots/        # Gameplay screenshots
```