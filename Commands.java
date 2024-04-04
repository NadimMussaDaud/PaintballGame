public enum Commands {
    
        GAME("game", "Create a new game"),
        MOVE("move", "Move a player"),
        CREATE("create", "Create a player in a bunker"),
        ATTACK("attack", "Attack with all players of the current team"),
        STATUS("status", "Show the current state of the game"),
        MAP("map", "Show the map of the current team"),
        BUNKERS("bunkers", "List the bunkers of the current team, by the order they were seized"),
        PLAYERS("players", "List the active players of the current team, by the order they were created"),
        HELP("help", "Show available commands"),
        QUIT("quit", "End program execution");
    
        private String name, message;

        Commands(String name, String message) {
            this.name = name;
            this.message = message;
        }
    
        public String getNome() {
            return name;
        }
        public String getMessage(){
            return message;
        }  

        public static Commands getCommandByName(String name) {
            for (Commands command : Commands.values()) {
                if (command.getNome().equalsIgnoreCase(name)) {
                    return command;
                }
            }
            throw new IllegalArgumentException("Comando inválido: " + name);
        }
    
}
