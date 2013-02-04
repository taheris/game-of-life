Conway’s Game of Life is a zero-player game played on an infinite two dimensional grid, designed to show the evolution of complexity from simple initial states and rules. Each cell on the grid can be either dead or alive, and whether it is alive in the next state depends on the state of its eight neighbouring cells. The rules are as follows:

1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
2. Any live cell with two or three live neighbours lives on to the next generation.
3. Any live cell with more than three live neighbours dies, as if by overcrowding.
4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

This image shows some examples of how patterns evolve:

![Evolve](/evolve.png)

These simple rules result in the emergence of complexity, and give the notion that design and organisation can emerge in the absence of any governing body or supervision. It has indeed been proven that the Game of Life is a universal Turing machine, and therefore anything that is computable can be computed in the game.

I've included some pre-defined patterns with both implementations to view some of the more interesting patterns that have already been discovered. The most famous of which is the [Glider](http://en.wikipedia.org/wiki/Glider_%28Conway's_Life%29) pattern, which is the smallest [spaceship](http://en.wikipedia.org/wiki/Spaceship_%28CA%29) in the game, and travels diagonally with the pattern repeating every four iterations.
