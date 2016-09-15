# Mazing
A maze game android app.

## Features
In this app you can:
- Solve the maze with your finger
- Generate new (and different) mazes to solve

## What it looks like
The following screenshots show the app when it first opens, 
when the maze is half-way solved 
and after the level was changed to level 1 and the maze was solved by the user:

<img src="https://raw.githubusercontent.com/mastershif/Mazing/master/Screenshot_20160915-212952.png" alt-text="Just opened" width="250">
<img src="https://raw.githubusercontent.com/mastershif/Mazing/master/Screenshot_20160915-213006.png" alt-text="Half solved opened" width="250">
<img src="https://raw.githubusercontent.com/mastershif/Mazing/master/Screenshot_20160915-213036.png" alt-text="Level 1 solved" width="250">

## How It Works 

The maze is built based on an NxN grid, represented by a graph and 2 arrays of vertical and horizontal walls.
The starting maze is of size 10x10, which means the area of the maze on the screen is divided to 10x10 cells. 
This app uses 2 modified graph search algorithms to build the maze: Depth First Search and Breadth First Search.

A modified (randomized) version of Depth First Search is used to build the maze.

Breadth First Search is used to find the longest path in the maze, which becomes the maze's solution.

The app can generate virtually endless number of mazes.

## Planned Features

1. Change level - make the maze easier or more difficult by decreasing/increasing the size of the maze. The actual space the maze takes on the screen will not change, but the given area of the maze will be divided to less/more cells.
2. Print maze - print the maze so you can solve it on paper. 

## License
```
Copyright 2016 Shif Ben Avraham

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
