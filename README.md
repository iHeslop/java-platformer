# Java Platformer - Sky Warrior

## Demo 

https://github.com/iHeslop/java-platformer/assets/115535765/93b00ae6-8864-48e4-9626-a07eff06f0e0

---
## Description / Requirements

Sky Warrior is a basic 2D platformer built in Java. The goal of this project was to practice and utilize as many different features of Java to create and build a fun, simple and aesthetic game. Sky Warrior contains menu screens, pause screens, overlays, audio, music, sound effects, multiple levels and also basic animations. 

With this project, the plan was to practice and implement how to:

- Use Java classes correctly.
- Implement code abstraction.
- Create a simple Java game using JFrame and JPanel as a base.
- Design a good looking game within a self-created Java UI.
- Practice and implement using audio and image streams.
- Package a java project into a single jar file for implementation.
- Use keyboard and mouse inputs within Java
- Manage different game states
- Learn about threads

---

## Build Steps

The included 'java-platformer.jar' is a compiled project file. You can run this on any operating system with Java using the following terminal command: (Make sure to run this command from within the same folder as the jar file)

```
java --enable-preview -jar ./java-platformer.jar
```

---

## Design Goals / Approach

- I wanted to create a simple jumping game, where the player has to jump up to the top of the level to complete it.
- Use of collision detection, as well as player velocity and gravity were integral to the games overall design.
- I wanted to integrate a nice design aesthetic, across the level and menu screens.
- Whilst the concept of this application is relatively simple, I wanted to use clean coding practices as much as possible, including using contexts where necessary instead of prop drilling, as well as having organized and well thought out components that are easy to scale and update/change in the future if add any extra features.
- Design multiple overlays for the user within the game, including a pause screen, a level completed screen and a main menu screen.
- Incorporate sound effects and music into the game which can be controlled within the menus.
- Add dynamic movement to the player, including double jumps and crouching. (Add the ability to easily add more).
- I wanted to make everything as reusable as possible, so if there is anything i want to add in the future (e.g. more sound effects, more levels, different characters) it wouldn't be too difficult to do so. 
- I also wanted to keep everything within a tight scope, so the game is basic and works as intended. 

---

## Features

- **Player Movement:** Ability to move the player around the screen, with animated movements such as running, double jumping and crouching.
- **Menu:** Main menu screen, with the ability to play, update sound options or quit.
- **Overlays:** Pause screen as well as a level completed screen, with relevant buttons displayed on both. 
- **Multiple Levels:** Game has the ability to contain multiple levels (currently only contains two). 
- **Unique Level Mapping Design:** Use of R values (RGB) to draw and design level layouts. 
- **Sound:** Multiple different background tracks for menu and different levels. Also sound effects for jumping and entering door.
- **Aesthetic:** Pixelated japanese aesthetic for a good-looking simple game.

---

## Technologies:

- **Java**

---

## Known issues

- Bug 1: Sometimes if you fall too fast the game will register a block as solid too early, and you will float one tile above the floor.
- Bug 2: Currently, all tile hit box sizes are the same, but platforms are thinner, so the player will hit the invisible underside of a tile if jumping into a platform tile. 
- Bug 3: Current sound control is very buggy. Will look to incorporate a better approach. Potential use of an external library.

---

## Future Goals

- More levels
- Enemies
- Attacks
- Sliding ability
- Potential Horizontal scrolling and level size increase
- Health Bar
- Timer / Scoreboard for level clears
- Different characters
- Different level backgrounds

---

## Struggles

**A lot**. This was a super challenging project, but to summarize the main struggles: 
- Player movement. Figuring out how to move the player correctly (double jumping, crouching etc) and incorporate a proper collision detection system.
- How to create and draw levels properly and consistently. 
- Incorporating sound properly into the game.
- Packaging the project into a jar file correctly.
- Animations. Getting the player to animate correctly when in certain states. (e.g. Idle, running, jumping etc)

---

### Assets / References:

- Art by: https://pixelfrog-assets.itch.io/
- Character by: https://rvros.itch.io/
- Sound from: https://pixabay.com/
- Menu and Overlays by: https://www.kaaringaming.com/

---
