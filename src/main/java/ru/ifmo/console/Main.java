package ru.ifmo.console;

import ru.ifmo.LightsCommand;

public class Main {
    public static void main(String[] args) {
        CommandManagerImpl commandManager = CommandManagerImpl.getInstance();
        commandManager.addCommand(new LightsCommand());
        ConsoleIO.getInstance().start(commandManager);
        ConsoleIO.getInstance().print("goool");

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ConsoleIO.getInstance().print("goool");
        while (true) {
            Thread.onSpinWait();
        }
        //TODO починить консоль! она не работает!
    }
}