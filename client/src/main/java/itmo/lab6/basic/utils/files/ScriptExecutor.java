package itmo.lab6.basic.utils.files;

import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandFactory;
import itmo.lab6.commands.CommandType;
import itmo.lab6.commands.CommandUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptExecutor {
    private final ArrayList<Command> commandQue = new ArrayList<>();
    private final File scriptFile;
    private final ArrayDeque<File> filesMemory = new ArrayDeque<>();

    public ScriptExecutor(File scriptFile) {
        this.scriptFile = scriptFile;
    }

    public ArrayList<Command> getCommandList() {
        return commandQue;
    }

    private ScriptExecutor readScript(File scriptFile) {
        List<String> lines;
        try {
            lines = Files.readAllLines(scriptFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filesMemory.add(scriptFile);
        for (String line : lines) {
            String[] args = {};
            String[] lineSplit = line.split(" ");
            if (lineSplit.length > 1) {
                args = Arrays.copyOfRange(lineSplit, 1, lineSplit.length);
            }
            CommandType commandType = CommandUtils.getCommandType(lineSplit[0]);
            if (commandType == CommandType.EXECUTE_SCRIPT) {
                if (filesMemory.contains(new File(args[0]))) {
                    System.err.println("Recursive file execution. Skipping line: " + line);
                    continue;
                }
                if (FileUtils.isFileExist(args[0])) {
                    this.readScript(new File(args[0]));
                    continue;
                }
            }
            Command cmd = CommandFactory.createCommand(commandType, args);
            if (cmd != null) commandQue.add(cmd);
        }
        filesMemory.pop();
        return this;
    }

    public ScriptExecutor readScript() {
        return this.readScript(scriptFile);
    }
}
