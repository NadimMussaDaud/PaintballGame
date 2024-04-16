import java.util.Scanner;
import dataStructures.Array;
import dataStructures.Iterator;

public class Main {

    private static String FATAL_ERROR = "FATAL ERROR: Insufficient number of teams.";
    private static String INVALID_TEAM = "Team not created.";
    private static String INVALID_BUNKER = "Bunker not created.";
    private static String INVALID_COMMAND = "Invalid Command";
    private static String HELP_MESSAGE_FORMAT = "%s - %s\n";
    private static String QUIT_MESSAGE = "Bye.";
    private static Game game;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        processCommands(in);
        in.close();
    }

    private static void processCommands(Scanner in){
        Command command;
        do{
          command = getCommand(in);
          processCommand(command, in);
        }
        while( ! command.equals(Command.QUIT) );
    }

    private static void processCommand(Command command, Scanner in){
        switch(command){
                case GAME -> {
                    initGame(in);
                }
                case MOVE -> {
                    in.nextLine();
                }
                case CREATE -> {
                    in.nextLine();
                }
                case ATTACK -> {
                    in.nextLine();
                }
                case STATUS -> {
                    status();
                    in.nextLine();
                }
                case MAP -> {
                    printMap();
                    in.nextLine();
                }
                case BUNKERS -> {
                    bunkers();
                    in.nextLine();
                }
                case PLAYERS -> {
                    in.nextLine();
                }
                case HELP -> {
                    help();
                    in.nextLine();
                }
                case QUIT -> {
                    exitMessage();
                    in.nextLine();
                }
                case UNKNOWN -> {
                    invalidCommand();
                    in.nextLine();
                }
            }
    }

    private static void printMap() {
        String[][] map = game.map();
        int width = map.length;
        int height = map[0].length;
        System.out.printf("%d %d\n", width-1, height-1);
        
        //Prints the colummns indexes
        System.out.print("**");
        for(int i=1; i <= width-1; i++){
            System.out.printf("%d ", i);
        }
        System.out.println();

        for(int i = 1; i < height; i++){
            System.out.printf("%d ",i);
            for(int j = 1; j < width; j++){
                System.out.printf("%s ", map[j][i]);
            }
            System.out.println();
        }

    }

    private static void status(){
        Array<Array<String>> array = game.status();
        for(int i = 0; i < 3; i++){
            switch (i) {
                case 0 -> printDimensions(array.get(i));
                case 1 -> printBunkers(array.get(i));
                case 2 -> printTeams(array.get(i));
            }
        }
    }

    private static void bunkers(){
        Array<Bunker> bunkers = game.teamBunkers();
        Iterator<Bunker> it = bunkers.iterator();
        System.out.printf("%d bunkers:\n", bunkers.size());

        while (it.hasNext()) {
            Bunker b = it.next();
            System.out.printf("%s with %d coins in position (%d,%d)\n", b.getName(),b.getTreasure(), b.getX(), b.getY());
        }

    }
    

    private static void printTeams(Array<String> array) {

        int size = array.size();
        System.out.printf("%d teams:\n", size);

        for(int i = 0; i < size; i++){
            String format = (i == size-1) ? "%s\n" : "%s; ";
            System.out.printf(format, array.get(i), array.get(i+1));
        } 
    }

    private static void printBunkers(Array<String> array) {
        int size = array.size()/2;
        System.out.printf("%d bunkers:\n", size);

        for(int i = 0; i < size; i++){
            System.out.printf("%s (%s)\n", array.get(i*2), array.get((i*2)+1));
        }
        
    }

    private static void printDimensions(Array<String> array) {
        System.out.printf("%s %s\n", array.get(0), array.get(1));
    }
    

    private static void initGame(Scanner in) {
        int width = in.nextInt();
        int height = in.nextInt();
        int teams = in.nextInt();
        int bunkers = in.nextInt();

        game = new GameClass(width,height,teams,bunkers);
        
        //Read bunkers
        System.out.printf("%d bunkers:\n", bunkers);
        for(int i = 0 ; i < bunkers; i++ ){
            int x = in.nextInt();
            int y = in.nextInt();
            int treasure = in.nextInt();
            String name = in.nextLine().trim();
            if (validBunker(width, height, x, y, treasure, name))
                game.addBunker(x,y,treasure, name);
            else
                System.out.println(INVALID_BUNKER);
        }
        
        //Read teams
        System.out.printf("%d teams:\n", teams);
        for(int i = 0 ; i < teams; i++ ){
            String team = in.next();
            String bunker = in.nextLine().trim();

            if(ValidTeam(team, bunker)){
                game.addTeam(team, bunker);
            }else
                System.out.println(INVALID_TEAM);
        }
        
        if(!game.canPlay()){
            game = null;
            System.out.println(FATAL_ERROR);
        }
    }

    private static boolean ValidTeam(String team, String bunker) {
        if( game.hasTeam(team) || !game.hasBunker(bunker) || game.isOccupiedBunker(bunker) )
            return false;
        return true;

    }

    private static boolean validBunker(int width, int height,int x, int y, int treasure, String name) {
        if ( x < 0 || x > width || y < 0 || y > height || treasure <= 0)
            return false;
        if( game.hasBunker(name) || !game.isEmpty(x,y) )
            return false;

        return true;
    }

    private static void invalidCommand(){
        System.out.println(INVALID_COMMAND);
    }
    
    private static Command getCommand(Scanner in){
            try{
                String prompt = (game==null) ? "> " : String.format("%s> ", game.getTurnTeamName());
                System.out.print(prompt);

                String input = in.next().toUpperCase();
                return Command.valueOf(input);
            }
            catch(IllegalArgumentException e){
                return Command.UNKNOWN;
            }
    }

    private static void exitMessage(){
        System.out.println(QUIT_MESSAGE);
    }

    private static void help(){

        if(game==(null)){
            for (Command c: Command.values() ) {
                if(c.equals(Command.GAME) || c.equals(Command.HELP) || c.equals(Command.QUIT))
                    System.out.printf(HELP_MESSAGE_FORMAT,c.getName(),c.getMessage());
            }
        }
        else{
            for (Command c: Command.values() ) {
                System.out.printf(HELP_MESSAGE_FORMAT,c.getName(),c.getMessage());
            }
        }
    }
}

