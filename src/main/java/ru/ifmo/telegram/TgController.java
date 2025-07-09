package ru.ifmo.telegram;

import java.util.ArrayList;

public interface TgController {
    void sendMessage(int userid, String message);
    boolean getAcception(int userid, String Message);
    boolean getAcception(int userid, ArrayList<String> messages);
}
