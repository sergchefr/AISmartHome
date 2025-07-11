package ru.ifmo.neuro;

public class NeuroController {
    private static NeuroController instance;
    private NeuroCommGetter ai;

    private NeuroController(){
        ai = new Mistral();
        reloadSystemPrompt();
        System.out.println();
    }

    public String getCommand(String input)throws RecognitionException {
        String ans = ai.getStringCommand(input);
        System.out.println(ans);
        System.out.println("");
        if (ans.equals("ERROR"))throw new RecognitionException(input);
        return ans;
    }

    public void reloadSystemPrompt(){
        String systemprompt= new SystemPromptCreator("C:\\Users\\Сергей\\IdeaProjects\\AISmartHome\\src\\main\\resources\\").getSystemprompt();
        ai.setSystemPrompt(systemprompt);
    }


    public static NeuroController getInstance() {
        if(instance == null) instance = new NeuroController();
        return  instance;
    }
}
