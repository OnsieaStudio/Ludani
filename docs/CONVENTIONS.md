# Java source code conventions of this project

- Package prefixes: fr.onsiea.engine
- Getter | setter methods without "get..." and "set..."
  - void foo(int foo) [...]
  - int foo() [...]

# Engine conventions

- Onsiea Engine is disived in different parts, containing all the sources of a particular theme
  - Game ; the logic and rules of a game or several
  - Core ; common part between Client, Game and Server, engine systems, colisions, physiques ...
  - Client ; graphics, sounds, ...
  - Server ; necessary to operate a server of this game engine

## Game vs core

- The Onsiea Engine is a tool for creating a game. A game's logic and rules allow it to be created, to govern its operation, it is a bit like the finished product that the engine allows, it is also a good way to test the engine. The engine does not need specific game logic, while the game logic needs the or motor to run.
- You have to see the motor as a big mechanism that takes game logic as input, to make it work for the duration of the session and send back out if everything went well or not, various information ... While the game can't do anything alone.

## Core

- Will be automatically present in the two types of executables of a game on the client or server side

## Server vs client

- the client will indeed be able to host part of a game (for example a local host), despite everything the server part could have additional tools and be more specialized in the field.
- The separate server part also allows you to create a multiplayer session of a game without necessarily owning and launching the client part with the resources and content that this implies, which is always an advantage in terms of the weight of the executable and performance !

## Client

- the client part will can be modulated, allowing the choice to use either OpenGL or Vulkan, it will even be possible to have only the source codes of the module of one or the other
