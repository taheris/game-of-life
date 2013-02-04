// import global variables for app, window object and d3 for use in module
var app = (function (app, window, d3) {
    // use ECMAScript 5 strict mode error checking
    "use strict";
    
    // display object holds state and methods for grid and cell size
    app.display = {
        gridWidth: Math.floor(0.95 * window.outerWidth),
        gridHeight: Math.floor(0.85 * window.outerHeight),
        cellDiameter: Math.floor(0.02 * Math.min(window.outerHeight, window.outerWidth)),
        
        cols: function () {
            return Math.floor(app.display.gridWidth / app.display.cellDiameter);
        },
        
        rows: function () {
            return Math.floor(app.display.gridHeight / app.display.cellDiameter);
        },
        
        centreCol: function () {
            return Math.floor(app.display.cols() / 2);
        },
        
        centreRow: function () {
            return Math.floor(app.display.rows() / 2);
        }
    };
    
    // game of life object using d3.js to render cells
    app.gameOfLife = {
        isRunning: false, // game animation state
        cellStates: [], // list of objects holding current cell states
        
        // standard game of life rules dictate 3 neighbours to create life
        // and 2 or 3 neighbours to sustain life, otherwise cell dies
        born: [3], // list of neighbour counts that will create life in cell
        live: [2, 3], // list of neighbour counts that will retain life in cell
        
        // replace cell states with a percentage that each cell is alive
        setRandom: function (probability) {
            // clear states and reset game of life rules
            app.gameOfLife.cellStates = [];
            app.gameOfLife.born = [3];
            app.gameOfLife.live = [2, 3];
            
            d3.range(app.display.cols()).forEach(function (x) {
                d3.range(app.display.rows()).forEach(function (y) {
                    var alive = Math.random() < probability;
                    
                    app.gameOfLife.cellStates.push({
                        "x": x, // column
                        "y": y, // row
                        "alive": alive, // is the cell alive
                        "lived": alive // has the cell ever lived
                    });
                });
            });
            
            app.gameOfLife.updateGrid();
        },
        
        // life is a list of strings for each row where # marks each alive column
        setAlive: function (life) {
            var rows = life.length;
            var cols = life[0].length;
            var startRow = app.display.centreRow() - Math.floor(rows / 2);
            var startCol = app.display.centreCol() - Math.floor(cols / 2);
            var gridRows = app.display.rows();
            
            app.layouts.empty();
            if (app.display.rows() < (rows + 2) || app.display.cols() < (cols + 2))
                return; // array too big for grid
            
            d3.range(rows).forEach(function (y) {
                var row = life[y].split(''); // split string by each char
                
                d3.range(cols).forEach(function (x) {
                    var item = (startCol + x) * gridRows + (startRow + y);
                    app.gameOfLife.cellStates[item].alive = (row.shift() === "#");
                });
            });
            
            app.gameOfLife.updateGrid();
        },
        
        // decide whether each cell lives or dies in the next generation
        nextGeneration: function () {
            var nextStates = [];
            
            d3.range(app.display.cols()).forEach(function (x) {
                d3.range(app.display.rows()).forEach(function (y) {
                    // shorthand for repeated use
                    var rows = app.display.rows();
                    var cols = app.display.cols();
                    
                    // wrap top cells with bottom, and left cells with right
                    var left = (x === 0 ? cols - 1 : x - 1);
                    var top = (y === 0 ? rows - 1 : y - 1);
                    var right = (x === cols - 1 ? 0 : x + 1);
                    var bottom = (y === rows - 1 ? 0 : y + 1);
                    
                    // use javascript property that true == 1 to count alive neighbours
                    var neighbours = app.gameOfLife.cellStates[left * rows + top].alive
                        + app.gameOfLife.cellStates[x * rows + top].alive
                        + app.gameOfLife.cellStates[right * rows + top].alive
                        + app.gameOfLife.cellStates[left * rows + y].alive
                        + app.gameOfLife.cellStates[right * rows + y].alive
                        + app.gameOfLife.cellStates[left * rows + bottom].alive
                        + app.gameOfLife.cellStates[x * rows + bottom].alive
                        + app.gameOfLife.cellStates[right * rows + bottom].alive;
                    
                    var alive; // cell alive in the next generation
                    if (app.gameOfLife.cellStates[x * rows + y].alive)
                        alive = app.gameOfLife.live.indexOf(neighbours) > -1;
                    else
                        alive = app.gameOfLife.born.indexOf(neighbours) > -1;
                    
                    nextStates.push({
                        "x": x,
                        "y": y,
                        "alive": alive,
                        "lived": alive || app.gameOfLife.cellStates[x * rows + y].lived
                    });
                });
            });
            
            app.gameOfLife.cellStates = nextStates; // update cellStates with next generation
        },
        
        // draw circles using svg for each game of life cell
        drawGrid: function () {
            // replace svg element in body with grid size
            d3.select("svg").remove();
            var life = d3.select('#life')
                .append('svg')
                .attr('width', app.display.gridWidth)
                .attr('height', app.display.gridHeight);
            
            // returns a function that will scale input by cellDiameter
            var xScale = d3.scale.linear()
                .domain([0, app.display.cols()])
                .rangeRound([0, app.display.cols() * app.display.cellDiameter]);
            
            // returns a function that will scale input by cellDiameter
            var yScale = d3.scale.linear()
                .domain([0, app.display.rows()])
                .rangeRound([0, app.display.rows() * app.display.cellDiameter]);
            
            var radius = app.display.cellDiameter / 2;
            
            life.selectAll("circle")
                .data(function() {
                    return app.gameOfLife.cellStates; // get cell objects
                })
                .enter()
                .append("circle") // create circle for each cell
                .attr("cx", function (data) { return xScale(data.x) + radius; }) // x centre
                .attr("cy", function (data) { return yScale(data.y) + radius; }) // y centre
                .attr("r", function (data) { return radius; }) // circle radius
                .on('mousedown', function (data) {
                    // invert alive state
                    var cell = data.x * app.display.rows() + data.y;
                    app.gameOfLife.cellStates[cell].alive = !app.gameOfLife.cellStates[cell].alive;
                    app.gameOfLife.updateGrid();
                })
                .classed('alive', function (data) { return data.alive; })
                .classed('lived', function (data) { return data.lived; });
        },
        
        // redraw grid with current cellStates
        updateGrid: function () {
            d3.selectAll("circle")
                .data(app.gameOfLife.cellStates)
                .classed('alive', function (data) { return data.alive; })
                .classed('lived', function (data) { return data.lived; });
        },
        
        // create next generation then redraw the grid
        animate: function () {
            app.gameOfLife.nextGeneration();
            app.gameOfLife.updateGrid();
            return !app.gameOfLife.isRunning; // return true to kill d3 timer
        }
    };
    
    // events object used for managing event listeners on the page
    app.events = {
        // create page event listeners
        addEvents: function () {
            document.getElementById("run").addEventListener("click", app.events.run);
            document.getElementById("step").addEventListener("click", app.events.step);
            document.getElementById("reset").addEventListener("click", app.events.reset);
            document.getElementById("zoom-in").addEventListener("click", app.events.zoomIn);
            document.getElementById("zoom-out").addEventListener("click", app.events.zoomOut);
            document.getElementById("layout").addEventListener("change", app.events.reset);
        },
        
        // dynamically build layout dropdown list
        addLayouts: function () {
            var layout = document.getElementById("layout");
        
            for (var key in app.layouts) {
                var node = document.createElement('option');
                node.text = key[0].toUpperCase() + key.slice(1);
                node.value = key;
                layout.appendChild(node);
            }
        },
        
        // start or stop the game
        run: function () {
            if (app.gameOfLife.isRunning) {
                app.gameOfLife.isRunning = false;
                document.getElementById("run").value = "Run";
            } else {
                app.gameOfLife.isRunning = true;
                document.getElementById("run").value = "Stop";
                // timer function runs animate repeatedly while game is running
                d3.timer(app.gameOfLife.animate);
            }
        },
        
        // redraw one generation only
        step: function () {
            app.gameOfLife.animate();
        },
        
        // regenerate cellStates using selected layout
        reset: function () {
            app.layouts[document.getElementById("layout").value]();
        },
        
        // increase the cell size no greater than 10% of the grid
        zoomIn: function () {
            if (app.display.cellDiameter < app.display.gridWidth * 0.10
                    && app.display.cellDiameter < app.display.gridHeight * 0.10) {
                app.display.cellDiameter = Math.floor(1.1 * app.display.cellDiameter);
                app.events.reset();
                app.gameOfLife.drawGrid();
            }
        },
        
        // decrease the cell size no smaller than 1% of the screen
        zoomOut: function () {
            var onePercent = 0.01 * Math.max(window.outerHeight, window.outerWidth);
            
            if (app.display.cellDiameter > onePercent) {
                app.display.cellDiameter = Math.floor(0.95 * app.display.cellDiameter);
                app.events.reset();
                app.gameOfLife.drawGrid();
            }
        }
    };
    
    // wait until the page has finished loading, then run callback function
    window.addEventListener('load', function () {
        app.events.addEvents();
        app.events.addLayouts();
        app.events.reset();
        app.gameOfLife.drawGrid();
    });
    
    return app;
})(app || {}, window, d3);
