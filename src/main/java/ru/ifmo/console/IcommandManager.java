package ru.ifmo.console;

public interface IcommandManager {

    void execute(String command);
    VerifierCommand getVerifierCommand(String comName);
    void addAnswer(String answer);
    String getAnswers();
    String getInterpretation(String Command);
    String help(String command);
    String help();




}
