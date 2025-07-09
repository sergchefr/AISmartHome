package ru.ifmo;
import ru.ifmo.console.CommandManagerImpl;
import ru.ifmo.console.ConsoleIO;
import ru.ifmo.console.commands.DoCommand;
import ru.ifmo.neuro.NeuroManager;
import ru.ifmo.neuro.RecognitionException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        NeuroManager neuroManager = NeuroManager.getInstance();

        CommandManagerImpl commandManager = CommandManagerImpl.getInstance();
        commandManager.addCommand(new DoCommand());
        ConsoleIO.getInstance().start(commandManager);



        while (true) {
            Thread.onSpinWait();
        }

    }



}
