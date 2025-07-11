package ru.ifmo.telegram.scenary;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface ScenaryElement {
    ScenaryElement getnext(Update update);
}
