package net.kunmc.lab.spotbilledduck.command;

public class Main extends CommandBase {

    public Main() {
        super(CommandEnum.spotBilledDuck);
        children(
                new Mode(CommandEnum.mode),
                new Start(CommandEnum.start),
                new Stop(CommandEnum.stop),
                new ShowStatus(CommandEnum.showStatus),
                new AddParent(CommandEnum.addParent),
                new RemoveParent(CommandEnum.removeParent)
        );
    }
}
