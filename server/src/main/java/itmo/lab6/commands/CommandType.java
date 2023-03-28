package itmo.lab6.commands;

import itmo.lab6.commands.implemented.*;

import java.io.Serializable;

public enum CommandType implements Serializable {
    CLEAR(ClearCommand.class, "clear: clear the collection"),
    EXECUTE_SCRIPT(ExecuteScriptCommand.class, "execute_script <file_name>: read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode. USE RELATIVE PATHS."),
    EXIT(null, "exit: exit the program"),
    HELP(HelpCommand.class, "help: shows this message"),
    HISTORY(HistoryCommand.class, "history: output the last 7 commands (without their arguments)"),
    INFO(InfoCommand.class, "info: output to the standard output stream information about the collection (type initialization date number of elements etc.)"),
    INSERT(InsertCommand.class, "insert <id> {element}: add a new element with the specified key"),
    PRINT_ASCENDING(PrintAscendingCommand.class, "print_ascending: print the elements of the collection in ascending order"),
    PRINT_DESCENDING(PrintDescendingCommand.class, "print_descending: print the elements of the collection in descending order"),
    REMOVE_ALL_BY_MPAA_RATING(RemoveByMpaaRating.class, "remove_all_by_mpaa_rating <mpaaRating>: remove from the collection all elements whose mpaaRating field value is equivalent to the specified one"),
    REMOVE_GREATER(RemoveGreaterCommand.class, "remove_greater <Oscars>: remove from the collection all elements exceeding the specified"),
    REPLACE_IF_LOWER(ReplaceLowerCommand.class, "replace_if_lower <id> {element}: replace the value by key if the new value is less than the old one."),
    REMOVE_KEY(RemoveKeyCommand.class, "remove_key <id>: delete an element from the collection by its key"),
    SHOW(ShowCommand.class, "show: output to the standard output stream all elements of the collection in the string representation"),
    UPDATE(UpdateCommand.class, "update <id> {element}: update the value of a collection element whose id is equal to the specified"),
    DEFAULT(DefaultCommand.class, ""),
    SERVICE(ServiceCommand.class, "");
    private final Class<? extends Action> executableClass;
    private final String description;

    CommandType(Class<? extends Action> executableClass, String description) {
        this.executableClass = executableClass;
        this.description = description;
    }

    public Class<? extends Action> getExecutableClass() {
        return executableClass;
    }

    public String getDescription() {
        return description;
    }
}
