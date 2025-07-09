package ru.ifmo.console;

public interface Icommand {
    String execute(String command);
    String getName();
    VerifierCommand getVerifierCommand();
}
