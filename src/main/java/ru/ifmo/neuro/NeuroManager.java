package ru.ifmo.neuro;

public class NeuroManager {
    private static NeuroManager instance;
    private NeuroCommGetter ai;

    private NeuroManager(){
        ai = new Mistral();
        reloadSystemPrompt();
        System.out.println();
    }

    public String getCommand(String input)throws RecognitionException {
        String ans = ai.getStringCommand(input);
        if (ans.equals("ERROR"))throw new RecognitionException(input);
        return ans;
    }

    public void reloadSystemPrompt(){
        String systemprompt= new SystemPromptCreator("C:\\Users\\Сергей\\IdeaProjects\\AISmartHome\\src\\main\\resources\\").getSystemprompt();
        ai.setSystemPrompt(systemprompt);
    }


    public static NeuroManager getInstance() {
        if(instance == null) instance = new NeuroManager();
        return  instance;
    }
}
