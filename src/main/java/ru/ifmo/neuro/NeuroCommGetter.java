package ru.ifmo.neuro;

public interface NeuroCommGetter {
    String getStringCommand(String input);
    void setSystemPrompt(String prompt);
}
