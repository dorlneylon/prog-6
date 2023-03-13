package itmo.lab6.commands.implemented;

import itmo.lab6.basic.auxiliary.Command;
import itmo.lab6.commands.CommandHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static itmo.lab6.basic.baseclasses.Movie.*;

public class HelpCommand extends AbstractCommand {
    CommandHandler handler;

    public HelpCommand(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void execute() throws IOException {
        String s =
                prcr + "help:" + whcr + " output help for available commands\n" +
                prcr + "info:" + whcr + " output to the standard output stream information about the collection (type, initialization date, number of elements, etc.)\n" +
                prcr + "show:" + whcr + " output to the standard output stream all elements of the collection in the string representation\n" +
                prcr + "insert " + blcr + "<id> {element}:" + whcr + " add a new element with the specified key\n" +
                prcr + "update " + blcr + "<id> {element}:" + whcr + " update the value of a collection element whose id is equal to the specified\n" +
                prcr + "remove_key" + blcr +" <id>:" + whcr + " delete an element from the collection by its key\n" +
                prcr + "clear:" + whcr + " clear the collection\n" +
                prcr + "execute_script" + blcr + " <file_name>:" + whcr + " read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode. USE RELATIVE PATHS.\n" +
                prcr + "exit:" + whcr + " terminate the program (without saving to a file)\n" +
                prcr + "remove_greater" + blcr + " <Oscars>:" + whcr + " remove from the collection all elements exceeding the specified\n" +
                prcr + "history:" + whcr + " output the last 7 commands (without their arguments)\n" +
                prcr + "replace_if_lower" + blcr + " <id> {element}:" + whcr + " replace the value by key if the new value is less than the old one.\n" +
                prcr + "remove_all_by_mpaa_rating" + blcr + " <mpaaRating>:" + whcr + " remove from the collection all elements whose mpaaRating field value is equivalent to the specified one\n" +
                prcr + "print_ascending:" + whcr + " print the elements of the collection in ascending order\n" +
                prcr + "print_descending:" + whcr + " print the elements of the collection in descending order";
        handler.getChannel().send(ByteBuffer.wrap(s.getBytes()), handler.getAddress());
    }
}
