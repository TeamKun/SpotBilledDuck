package net.kunmc.lab.spotbilledduck.command;

public class Main extends CommandBase {

    public Main() {
        super(CommandEnum.spotBilledDuck);
        children(
                new Mode(CommandEnum.mode),
                new Start(CommandEnum.start),
                new Stop(CommandEnum.stop),
                new Stop(CommandEnum.stop),
                new Stop(CommandEnum.showStatus),
                new Stop(CommandEnum.addParent),
                new Stop(CommandEnum.removeParent)
        );
    }
}
