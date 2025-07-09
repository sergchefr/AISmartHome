package ru.ifmo.console.commands;

import ru.ifmo.console.Icommand;
import ru.ifmo.console.Parameter;
import ru.ifmo.console.VerifierCommand;
import ru.ifmo.neuro.NeuroManager;
import ru.ifmo.neuro.RecognitionException;

public class DoCommand implements Icommand {
    @Override
    public String execute(String command) {


        try {
            return NeuroManager.getInstance().getCommand(command.substring(3));
        } catch (RecognitionException e) {
            return "ошибка распознавания";
        }
    }

    @Override
    public String getName() {
        return "do";
    }

    @Override
    public VerifierCommand getVerifierCommand() {

        return new Doverifier();
    }

    private class Doverifier extends VerifierCommand{

        public Doverifier() {
            super("do", "позволяет выполнять команды, написанные на человеческом языке", new Parameter[]{});
        }

        @Override
        public boolean verify(String com){
            return true;
        }
    }


}
