package net.kunmc.lab.spotbilledduck.command;

public class Main extends CommandBase {

    public Main() {
        super(CommandEnum.SPOTBILLEDDUCK);
        children(
                new Mode(CommandEnum.MODE),
                new Start(CommandEnum.START),
                new Stop(CommandEnum.STOP)
        );
    }
}
