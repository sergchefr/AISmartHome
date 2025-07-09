package ru.ifmo;

import ru.ifmo.console.Icommand;
import ru.ifmo.console.ParameterBuilder;
import ru.ifmo.console.VerifierCommand;
import ru.ifmo.console.VerifierCommandBuilder;

public class TableLampCommand implements Icommand {

    @Override
    public String execute(String command) {
        System.err.println("lamp on");
        return "выполнено";
    }

    @Override
    public String getName() {
        return "table_lamp";
    }

    @Override
    public VerifierCommand getVerifierCommand() {
        return new VerifierCommandBuilder().setName(getName())
                .addParamameter(new ParameterBuilder().setName("brightness").setDescription("устанавливает яркость освещения").setLimitations("String").getParameter())
                .addParamameter(new ParameterBuilder().setName("delay").setDescription("устанавливает задержку выполнения").setLimitations("int:[0;43200]").getParameter())
                .setDescription("позволяет управлять настольной лампой")
                .build();
    }
}
