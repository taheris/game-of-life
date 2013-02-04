package com.taheris.gameoflife;

/**
 * <dl>
 *   <dt> Purpose:
 *   <dd> Defined Game of Life patterns
 *   
 *   <dt> Description:
 *   <dd> Let's the user choose from a random grid or previously set patterns
 * </dl>
 *
 * @author Shaun Taheri
 * @version 23 Jan 2013
 */

public enum GameOfLifeLayout {
    // cell alive with 15% probability
    RANDOM,
    
    // pattern that moves across the grid diagonally
    GLIDER (new String[] {
            " # ",
            "  #",
            "###"
    }),
    
    // alternative glider pattern
    GOOSE (new String[] {
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
    }),
    
    // pattern that moves across the grid horizontally
    SPACESHIP (new String[] {
            "  ## ",
            "## ##",
            "#### ",
            " ##  "
    }),
    
    // fires gliders
    GUN (new String[] {
            "                        #           ",
            "                      # #           ",
            "            ##      ##            ##",
            "           #   #    ##            ##",
            "##        #     #   ##              ",
            "##        #   # ##    # #           ",
            "          #     #       #           ",
            "           #   #                    ",
            "            ##                      "
    }),
    
    // reflects incoming glider 180 degrees
    REFLECTOR (new String[] {
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
    }),
    
    // grows at four corners filling space with still life
    SPACEFILLER (new String[] {
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
    }),
    
    // replicates life using highlife rules
    REPLICATOR (new int[] {3, 6}, new int[] {2, 3}, new String[] {
            "  ###",
            " #  #",
            "#   #",
            "#  # ",
            "###  "
    }),
    
    // generates an approximation of Sierpinski triangles
    SIERPINSKI (new int[] {1}, new int[] {1, 2}, new String[] {
            "#"
    });
    
    private final int[] born; // list of neighbour counts that will create life in cell
    private final int[] live; // list of neighbour counts that will retain life in cell
    private final String[] alive; // list for each row where '#' indicates alive column
    
    GameOfLifeLayout() {
        this(null);
    }
    
    GameOfLifeLayout(String[] alive) {
        // standard game of life rules dictate 3 neighbours to create life
        // and 2 or 3 neighbours to sustain life, otherwise cell dies
        this(new int[] {3}, new int[] {2, 3}, alive);
    }
    
    GameOfLifeLayout(int[] born, int[] live, String[] alive) {
        this.born = born;
        this.live = live;
        this.alive = alive;
    }
    
    public int[] getBorn() {
        return born;
    }
    
    public int[] getLive() {
        return live;
    }
    
    public String[] getAlive() {
        return alive;
    }
}
