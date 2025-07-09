package ru.ifmo.neuro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SystemPromptCreator {
    private String systemprompt;
    private String resLocation;

    public SystemPromptCreator(String resLocation) {
        this.resLocation = resLocation;
        reloadPrompt();
    }

    public void reloadPrompt(){
        StringBuilder promt  = new StringBuilder();
        try {
            promt.append(loadFile(resLocation+ "prompt_instr.txt"))
                    .append("\n########\n Доступные команды с их описанием:\n").append(loadFile(resLocation+"Commands.xml")).append("\n#####\n Далее представлен список устройств. Category - категория устройства, то есть с какой командой его можно использовать")
                    .append(loadFile(resLocation+"device_list.xml"));
            systemprompt = promt.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadFile(String path) throws IOException{
        Path filePath = Paths.get(path);
        String filestr = Files.readString(filePath);
        if (filestr.isEmpty()) throw  new IOException("empty file");
        return filestr;
    }

    public String getSystemprompt() {
        return systemprompt;
    }
}
