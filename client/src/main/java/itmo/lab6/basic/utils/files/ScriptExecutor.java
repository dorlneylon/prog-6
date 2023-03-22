package itmo.lab6.basic.utils.files;

import itmo.lab6.basic.baseclasses.Movie;
import itmo.lab6.commands.Command;
import itmo.lab6.commands.CommandFactory;
import itmo.lab6.commands.CommandType;
import itmo.lab6.commands.CommandUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import static itmo.lab6.commands.CommandFactory.parseMovie;

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
        // создадим каунтер для пропуска лишних строк
        int skipLines = 0;
        List<String> lines;
        try {
            lines = Files.readAllLines(scriptFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filesMemory.add(scriptFile);
        for (String line : lines) {
            if (skipLines > 0) {
                skipLines--;
                continue;
            }
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
            if (List.of(CommandType.INSERT, CommandType.UPDATE, CommandType.REPLACE_IF_LOWER).contains(commandType)) {
                if (args.length < 1) {
                    System.err.println("Not enough arguments for command " + commandType);
                    continue;
                }
                String[] movieArgs = lines.subList(lines.indexOf(line) + 1, lines.indexOf(line) + 14).toArray(new String[0]);
                skipLines = 13;
                args = new String[movieArgs.length + 1];
                args[0] = lineSplit[1];
                System.arraycopy(movieArgs, 0, args, 1, movieArgs.length);
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
