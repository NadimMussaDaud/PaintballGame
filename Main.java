import java.util.Scanner;

public class Main {

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
                    in.nextLine();
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
                    in.nextLine();
                }
                case MAP -> {
                    in.nextLine();
                }
                case BUNKERS -> {
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

    private static void initGame(Scanner in) {
        int witdh = in.nextInt();
        int height = in.nextInt();
        int teams = in.nextInt();
        int bunkers = in.nextInt();

        game = new Game(witdh,height,teams,bunkers);
        
        //Read bunkers
        System.out.printf("%d bunkers:\n", bunkers);
        for(int i = 0 ; i < bunkers; i++ ){
            game.addBunker(in.nextInt(),in.nextInt(), in.nextInt(), in.nextLine().trim());
        }
        
        //Read teams
        System.out.printf("%d teams:\n", teams);
        for(int i = 0 ; i < teams; i++ ){
            game.addTeam(in.next(), in.nextLine().trim());
        }
    }

    private static void invalidCommand(){
        System.out.println(INVALID_COMMAND);
    }
    
    private static Command getCommand(Scanner in){
            try{
                String prompt = (game==null) ? "> " : "teamName> ";
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

