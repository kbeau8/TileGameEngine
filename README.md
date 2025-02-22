**WELCOME TO PUZZLE BOBBLE!**

This game engine is designed to implement multiple different types of tile-based puzzle games on top of a base, 2D engine.

**Usage**
Check and update Java and Maven:

java -version
mvn -version

Run from LoginScreen.java

Class Hierarchy:

**Entities** - Interacts with the game map and other objects

Entity - Abstract class
Player - 1-2 players can be instantiated to play the game
Enemy - Some games may need the potential for AI enemies

**Input**

InputManager - Handles all user input, currently only keyboard

**Logic**

CollisionManager - Handles all events when objects/entities touch
GameRules - Abstract, implemented by specific games

**Profiles**

PlayerProfile - Creates basic player profiles for log in
ProfileManager - Stores and updates profiles

**Sounds**

SoundManager - Handles applying sound effects and bg music

**Tiles**

Tile - A single block which can have various looks/behaviours
TileMap - The entire game map consisting of different Tiles

Game - main engine, abstract and runs specific games on top of it
GameSelectionScreen - After logging in this contains the selection of games
GameState - Updates the game state after every tick (moves game forward)
LoginScreen - Starting screen to select a player profile
Renderer - Graphics driver for tiles
Timer - Implemented for turn-based and live competitive games
Vector2D - Handles movement and physics for player/objects
