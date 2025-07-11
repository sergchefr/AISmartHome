package ru.ifmo;
import ru.ifmo.console.CommandManagerImpl;
import ru.ifmo.console.ConsoleIO;
import ru.ifmo.console.commands.DoCommand;
import ru.ifmo.neuro.NeuroController;
import ru.ifmo.telegram.TgController;
import ru.ifmo.telegram.TgManager;

public class Main {
    public static void main(String[] args){
        NeuroController neuroManager = NeuroController.getInstance();

        CommandManagerImpl commandManager = CommandManagerImpl.getInstance();
        commandManager.addCommand(new DoCommand());
        commandManager.addCommand(new LightsCommand());
        ConsoleIO.getInstance().start(commandManager);


        String apikey = System.getenv("TG_API_KEY");
        TgController tgBot = new TgManager(apikey);
        tgBot.start();

        while (true) {
            Thread.onSpinWait();
        }

    }



}
