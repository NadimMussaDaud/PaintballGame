public class TeamClass implements Team{
    
    private String name, bunker;

    public TeamClass(String name, String bunker){
        this.bunker = bunker;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBunker() {
        return bunker;
    }
}
