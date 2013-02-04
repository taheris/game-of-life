// import app global variable for calling other objects
var app = (function (app) {
    // layouts object that creates patterns for grid
    app.layouts = {
        // cell alive with 15% probability
        random: function () {
            app.gameOfLife.setRandom(0.15);
        },
        
        // clear grid
        empty: function () {
            app.gameOfLife.setRandom(0);
        },
        
        // pattern that moves across the grid diagonally
        glider: function () {
            app.gameOfLife.setAlive([
                " # ",
                "  #",
                "###"
            ]);
        },
        
        // alternative glider pattern
        goose: function () {
            app.gameOfLife.setAlive([
                "###          ",
                "#         ## ",
                " #      ### #",
                "   ##  ##    ",
                "    #        ",
                "        #    ",
                "    ##   #   ",
                "   # # ##    ",
                "   # #  # ## ",
                "  #    ##    ",
                "  ##         ",
                "  ##         "
            ]);
        },
        
        // pattern that moves across the grid horizontally
        spaceship: function () {
            app.gameOfLife.setAlive([
                "  ## ",
                "## ##",
                "#### ",
                " ##  "
            ]);
        },
        
        // fires gliders
        gun: function () {
            app.gameOfLife.setAlive([
                "                        #           ",
                "                      # #           ",
                "            ##      ##            ##",
                "           #   #    ##            ##",
                "##        #     #   ##              ",
                "##        #   # ##    # #           ",
                "          #     #       #           ",
                "           #   #                    ",
                "            ##                      "
            ]);
        },
        
        // reflects incoming glider 180 degrees
        reflector: function () {
            app.gameOfLife.setAlive([
                "# #                                                 ",
                " ##                                                 ",
                " #                                                  ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                      #                             ",
                "                     # #                            ",
                "                     # #                            ",
                "                      #                             ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                                                    ",
                "                               ##                   ",
                "                               ##                   ",
                "                                                    ",
                "             ##                                     ",
                "            # #                                     ",
                "            #                                       ",
                "           ##                                       ",
                "                                          ##        ",
                "                                         #  #  ##   ",
                "                                         # #    #   ",
                "                      ##                  #     # ##",
                "                     # #                     ## # # ",
                "                     #                       #  #  #",
                "                    ##                    #    #  ##",
                "                                          #####     ",
                "                                                    ",
                "                                            ## #    ",
                "                                            # ##    "
            ]);
        },
        
        // grows at four corners filling space with still life
        spacefiller: function () {
            app.gameOfLife.setAlive([
                "                  #        ",
                "                 ###       ",
                "            ###    ##      ",
                "           #  ###  # ##    ",
                "          #   # #  # #     ",
                "          #    # # # # ##  ",
                "            #    # #   ##  ",
                "####     # #    #   # ###  ",
                "#   ## # ### ##         ## ",
                "#     ##     #             ",
                " #  ## #  #  # ##          ",
                "       # # # # # #     ####",
                " #  ## #  #  #  ## # ##   #",
                "#     ##   # # #   ##     #",
                "#   ## # ##  #  #  # ##  # ",
                "####     # # # # # #       ",
                "          ## #  #  # ##  # ",
                "             #     ##     #",
                " ##         ## ### # ##   #",
                "  ### #   #    # #     ####",
                "  ##   # #    #            ",
                "  ## # # # #    #          ",
                "     # #  # #   #          ",
                "    ## #  ###  #           ",
                "      ##    ###            ",
                "       ###                 ",
                "        #                  "
            ]);
        },
        
        // replicates life using highlife rules
        replicator: function () {
            app.gameOfLife.setAlive([
                "  ###",
                " #  #",
                "#   #",
                "#  # ",
                "###  "
            ]);
            app.gameOfLife.born = [3, 6];
            app.gameOfLife.live = [2, 3];
        },
        
        // generates an approximation of Sierpinski triangles
        sierpinski: function () {
            app.gameOfLife.setAlive([
                "#"
            ]);
            app.gameOfLife.born = [1];
            app.gameOfLife.live = [1, 2];
        },
    };
    
    return app;
})(app || {});
