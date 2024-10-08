import java.util.Scanner;
import dataStructures.Array;
import dataStructures.ArrayClass;
import dataStructures.Iterator;

public class Main {

    private static final String INVALID_POSITION = "Invalid Position.";
    private static final String EMPTY = "";
    private static final String NO_COINS = "Insufficient coins for recruitment.";
    private static final String NO_PLAYER = "No player in that position.";
    private static final String INVALID_MOVE = "Invalid move.";
    private static final String BUNKER_OCCUPIED = "Bunker not free.";
    private static final String BUNKER_ILLEGALY_INVADED = "Bunker illegally invaded.";
    private static final String NON_EXISTENT_BUNKER = "Non-existent bunker.";
    private static final String NON_EXISTENT_PLAYER = "Non-existent player type.";
    private static final String FATAL_ERROR = "FATAL ERROR: Insufficient number of teams.";
    private static final String INVALID_TEAM = "Team not created.";
    private static final String INVALID_BUNKER = "Bunker not created.";
    private static final String INVALID_COMMAND = "Invalid command.";
    private static final String HELP_MESSAGE_FORMAT = "%s - %s\n";
    private static final String QUIT_MESSAGE = "Bye.";
    private static final String PLAYER_CREATED = "%s player created in %s\n";
    private static final String PLAYER_INFO = "%s player in position (%d, %d)\n";
    private static final String PLAYER_FORMAT = "%d players:\n" ;
    private static final String NO_PLAYERS = "Without players.";
    private static final String NO_BUNKERS = "Without bunkers.";
    private static final String WINNER_MESSAGE = "Winner is %s.\n";
    private static final String RED = GameClass.RED;
    private static final String BLUE = GameClass.BLUE;
    private static final String GREEN = GameClass.GREEN;
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
                        if(game != null){
                            move(in);
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
                    }
                    case CREATE -> {
                        if(game != null){
                            create(in);
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
                    }
                    case ATTACK -> {
                        if(game != null){
                            attack();
                            in.nextLine();
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
                    }
                    case STATUS -> {
                        if(game != null){
                            status();
                            in.nextLine();
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
                    }
                    case MAP -> {
                        if(game != null){
                            printMap();
                            in.nextLine();
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
                    }
                    case BUNKERS -> {
                        if(game != null){
                            bunkers();
                            in.nextLine();
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
                    }
                    case PLAYERS -> {
                        if(game != null){
                            in.nextLine();
                            players();
                        }
                        else{
                            in.nextLine();
                            invalidCommand();
                        }
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
                        in.nextLine();
                        invalidCommand();
                    }
                }
            
    }

    private static void attack() {
        String[][] map = game.attack();
        String winner = game.getWinner();

        if(map == null){
            System.out.println("All players eliminated.");
        }
        if(map != null){
            int width = map.length;
            int height = map[0].length;
            System.out.printf("%d %d\n", width-1, height-1);
            
            //Prints the colummns indexes
            System.out.print("**");
            for(int i=1; i <= width-1; i++){
                String str = (i == width-1) ? "%d" : "%d ";
                System.out.printf(str, i);
                
            }
            System.out.println();

            for(int i = 1; i < height; i++){
                String str1 = (i == width-1) ? "%d " : "%d ";
                System.out.printf(str1,i);
                for(int j = 1; j < width; j++){
                    String str2 = (j == width-1) ? "%s\n" : "%s ";
                    System.out.printf(str2, map[j][i]);
                }
            }
        }
        if(winner != null){
            game = null;
            System.out.printf(WINNER_MESSAGE,winner);
        }
    }

    private static void move(Scanner in) {
        int x = in.nextInt();
        int y = in.nextInt();
        Player p = game.getPlayer(x, y);
        String dir1 = in.next();
        Array<String> directions = new ArrayClass<>();
        directions.insertLast(dir1);
        
        String dir2,dir3;
        String dir23 = in.nextLine().trim(); 
        boolean hasDir23 = !dir23.equals(EMPTY);

        if(hasDir23){
            dir2 = dir23.split(" ")[0];
            dir3 = dir23.split(" ")[1];

            directions.insertLast(dir2);
            directions.insertLast(dir3);
        }

        if(!game.isPosition(x,y)){
            System.out.println(INVALID_POSITION);
            game.changeTurns();
        } else if(!game.hasPlayer(x,y)){
            System.out.println(NO_PLAYER);
            game.changeTurns();
        } else if(!p.getType().equals(RED) && hasDir23){
            System.out.println(INVALID_MOVE);
            game.changeTurns();
        } 
        else{
            
                    Iterator<String> it = game.move(p,directions);
                    while (it.hasNext()) {
                        System.out.println(it.next());
                    }
                    String winner = game.getWinner();
                    if(winner != null)
                    {
                        game = null;
                        System.out.printf(WINNER_MESSAGE,winner);
                    }
                        
        }
    }

    private static void players() {
        Array<Player> array = game.players();
        int size = array.size();
        if(size > 0){
            System.out.printf(PLAYER_FORMAT, size);
            Iterator<Player> it = array.iterator();
            while (it.hasNext()) {
                Player p = it.next();
                System.out.printf(PLAYER_INFO,p.getType(), p.getX(), p.getY());
            }
        }else 
            System.out.println(NO_PLAYERS);
    }

    private static void create(Scanner in) {
        String type = in.next();
        String bunker = in.nextLine().trim();

        if(!type.equals(GREEN) && !type.equals(BLUE) && !type.equals(RED)){
            System.out.println(NON_EXISTENT_PLAYER);
        }
        else if(!game.hasBunker(bunker)){
            System.out.println(NON_EXISTENT_BUNKER);
        }else if(!game.belongsTo(bunker)){
            System.out.println(BUNKER_ILLEGALY_INVADED);
        }
        else if(game.isOccupiedBunker(bunker)){
            System.out.println(BUNKER_OCCUPIED);
        }
        else if(!game.hasFunds(bunker,type)){
            System.out.println(NO_COINS);
        }else
        {
            game.create(bunker, type);
            System.out.printf(PLAYER_CREATED,type,bunker);
        }
    
        game.changeTurns();
        
        
    }

    private static void printMap() {
        String[][] map = game.map();
        int width = map.length;
        int height = map[0].length;
        System.out.printf("%d %d\n", width-1, height-1);
        
        //Prints the colummns indexes
        System.out.print("**");
        for(int i=1; i <= width-1; i++){
            String str = (i == width-1) ? "%d" : "%d ";
            System.out.printf(str, i);
            
        }
        System.out.println();

        for(int i = 1; i < height; i++){
            String str1 = (i == width-1) ? "%d " : "%d ";
            System.out.printf(str1,i);
            for(int j = 1; j < width; j++){
                String str2 = (j == width-1) ? "%s\n" : "%s ";
                System.out.printf(str2, map[j][i]);
            }
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
        
        if(it.hasNext()){
            System.out.printf("%d bunkers:\n", bunkers.size());

            while (it.hasNext()) {
                Bunker b = it.next();
                System.out.printf("%s with %d coins in position (%d, %d)\n", b.getName(),b.getTreasure(), b.getX(), b.getY());
            }
        }else{
            System.out.println(NO_BUNKERS);
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
            
            if(i == teams-1 && !game.canPlay()){
                    System.out.println(FATAL_ERROR);
                    game = null;
                    break;
            }
        }
    }


    private static boolean ValidTeam(String team, String bunker) {
        if( game.hasTeam(team) || !game.hasBunker(bunker) || game.bunkerWithTeam(bunker))
            return false;
        return true;

    }

    private static boolean validBunker(int width, int height,int x, int y, int treasure, String name) {
        if ( !game.isPosition(x, y) || treasure <= 0){
            return false;
        }
        if( game.hasBunker(name) || !game.isEmpty(x,y) ){
            return false;
        }
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
        game = null;
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
                if(!c.equals(Command.UNKNOWN))
                    System.out.printf(HELP_MESSAGE_FORMAT,c.getName(),c.getMessage());
            }
        }
    }

}

