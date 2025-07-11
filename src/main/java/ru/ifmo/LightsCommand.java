package ru.ifmo;

import ru.ifmo.console.Icommand;
import ru.ifmo.console.ParameterBuilder;
import ru.ifmo.console.VerifierCommand;
import ru.ifmo.console.VerifierCommandBuilder;
import ru.ifmo.neuro.RecognitionException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class LightsCommand implements Icommand {

    @Override
    public String execute(String command) {
        System.err.println(interprete(command));
        return "выполнено";
    }

    @Override
    public String getName() {
        return "lights";
    }

    @Override
    //TODO переделать команды для возможности динамического количества параметров
    public VerifierCommand getVerifierCommand() {
        return new VerifierCommandBuilder().setName(getName())
                .addParamameter(new ParameterBuilder().setName("brightness").setDescription("устанавливает яркость освещения").setLimitations("String").getParameter())
                .addParamameter(new ParameterBuilder().setName("delay").setDescription("устанавливает задержку выполнения").setLimitations("int:[0;43200]").getParameter())
                .addParamameter(new ParameterBuilder().setName("device_name").setDescription("имя устройства").setLimitations("String").getParameter())
                .setDescription("позволяет управлять светом")
                .build();
    }

    @Override
    public String interprete(String command) {
        try {
            System.out.println(command);
            HashMap<String, String> params = comparam(command);
            StringBuilder builder = new StringBuilder();
            int brightness = Integer.parseInt(params.get("brightness"));
            String deviceName = params.get("device_name");
            //System.out.println(deviceName);
            int delay = Integer.parseInt(params.get("delay"));

            if (brightness==0) builder.append("выключить свет на устройстве ").append(deviceName).append("\"");
            else if (brightness==100) builder.append("включить свет на устройстве ").append(deviceName);
            else builder.append("включить свет на устройстве ").append(deviceName).append(" яркостью ").append(brightness).append("/100");

            System.out.println(builder.toString());
            return builder.toString();

        } catch (Exception e) {
            return "ошибка в процессе интерпретации команды "+command;
        }

    }

    private HashMap<String, String> comparam(String command)throws RecognitionException {
        HashMap<String, String> params = new HashMap<>();
        String[] splitted = command.split(" ");
        for (String s : splitted) {
            if(!s.contains("=")) continue;
            params.put(s.split("=")[0].strip(), s.split("=")[1].strip());
        }
        Set<String> keys = params.keySet();
        if (!(keys.contains("device_name")&&keys.contains("brightness")&&keys.contains("delay")&&keys.size()==3))throw new RecognitionException("команда не парсится: "+command);
        return params;

    }
}
