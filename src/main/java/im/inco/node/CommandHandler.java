package im.inco.node;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Albert Veldman
 */
public class CommandHandler {
    /**
     * Scanner, reads user input
     */
    private Scanner scanner;

    /**
     * NodeMain, used to get UserData
     */
    private NodeMain node;

    /**
     * Sets up scanner
     *
     * @param node NodeMain object that created this instance
     *
     * @author Albert Veldman
     */
    public CommandHandler(NodeMain node) {
        this.node = node;
        scanner = new Scanner(System.in);
    }

    /**
     * Waits for input and processes it
     *
     * @author Albert Veldman
     */
    public void waitForInput() {
        NodeMain.printLine("Ready for input...");
        if(scanner.hasNextLine()) {
            processInput();
        }
    }

    /**
     * Asks the user which port should be used to receive messages/files and returns this.
     *
     * @param standardPort the standard port that is used to receive messages/files
     * @param usage port usage. Messages or files.
     *
     * @return returns port that user put in. Else return -1
     *
     * @author Albert Veldman
     */
    public int askPort(int standardPort, String usage) {
        NodeMain.printLine("Which port should be used to receive " + usage +
                "? (Press enter without a value if you want to use the standard port: " + standardPort + "): ");
        if(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            Pattern pat = Pattern.compile("[0-9]{4,5}");
            Matcher matcher = pat.matcher(input);
            if(input.isEmpty()) {
                return -1;
            }
            else if(matcher.matches()){
                return Integer.parseInt(input);
            }
            else {
                NodeMain.printLine("Input not valid, try again");
                return askPort(standardPort, usage);
            }
        }
        return -1;
    }

    /**
     * Displays the inco logo in ascii
     *
     * @author Albert Veldman
     */
    public void incoLogo() {
        String logo = "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNmdhhysssooooooooossyyhddmNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNmhysoooooooooooooooooooooooooooooshdmMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMMMMMMMMMMMMMMNdysoooooooooooooooooooooooooooooooooooooooshmMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMMMMMMMMMMMmhsoooooooooooooooooooooooooooooooooooooooooooooooydNMMMMMMMMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMMMMMMMMmhoooooooooooooooooooooooooooooooooooooooooooooooooooooosdNMMMMMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMMMMMNdsooooooooooooooooooooossssssssssssssssooooooooooooooooooooooymMMMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMMMNhoooooooooooooooooosssssssssssssssssssssssssssssooooooooooooooooosdMMMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMMMNhooooooooooooooossssssssssssssssssssssssssssssssssssssooooooooooooooosdMMMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMMNhoooooooooooooossssssssssssssssssssssssssssssssssssssssssssooooooooooooooymMMMMMMMMMMM\n" +
                "MMMMMMMMMMMMmsooooooooooooosssssssssssssssssssssssssssssssssssssssssssssssssooooooooooooohNMMMMMMMMM\n" +
                "MMMMMMMMMMMhoooooooooooossssssssssssssssssssssssssssssssssssssssssssssssssssssoooooooooooosmMMMMMMMM\n" +
                "MMMMMMMMMNyooooooooooossssssssssssssssssssssssssssssssssssssssssssssssssssssssssoooooooooooodMMMMMMM\n" +
                "MMMMMMMMmsooooooooooossssssssssyyddysssssshdddhsssssyddddysssssyhddsssssssssssssssooooooooooohMMMMMM\n" +
                "MMMMMMMmooooooooooossssssssssshmddMhsssssmNyyhMmssshMdyymMhsssmmdNNssssssssssssssssooooooooooohMMMMM\n" +
                "MMMMMMNooooooooooossssssssssssssshMhsssshMhsymmMhssMmsshNNNssssssNNsssssssssssssssssooooooooooohMMMM\n" +
                "MMMMMNsoooooooooosssssssssssssssshMhssssmMyyNhyMdsyMdsdmsmMssssssNNsssssssssssssssssssoooooooooodMMM\n" +
                "MMMMMyoooooooooossssssssssssssssshMhssssdMdNysyMdssMmmdssNMssssssNNssssssssssssssssssyhoooooooooomMM\n" +
                "MMMMmoooooooooosssssssssssssssssshMdssssyMNyssdMyssmMdssyMdssssssNNssssssssssssssydmmmdsooooooooosMM\n" +
                "MMMMsooooooooosssssssssssssssssmmmNmmmssshmmdmmyssssdNddNdsssshmmNNmmhsssssssydmmmhyoooooooooooooodM\n" +
                "MMMdoooooooooossssssssssssssssssssssssssssssysssssssssyysssssssssssssssssydmmdhsoooooooooooooooooosM\n" +
                "MMMyooooooooossssssssssssssssssyhddhysssssshdhyhdmmmmmmmdysssssshdhssshdmmhsooooooooooooooooooooooom\n" +
                "MMNoooooooooossssssssssssssssshMdyymMhsssmmdNMNhyooooooshmNdysmmdNNhmNdyoooooooooooooooooooooooooooh\n" +
                "MMmoooooooooosssssssssssssssssNNsshNNNsssyhNmyooooooooooooohNmhshNNhsoooooooooooooooooooooooooooooos\n" +
                "MMdooooooooossssssssssssssssssMmsdNymMsshNmsoooooooooooooooooyNNdsoooooooooooooooooooooooooooooooooo\n" +
                "MMhooooooooossssssssssssssssssMmmmssmMsmNyoooooydmNNmhoooooooooooooooooooooooooooooooooooooooooooooo\n" +
                "MMdooooooooossssssssssssssssssmMdssyMmNNsoooosmNhssmMmNdsooooooooooooooooooooooooooooooooooooooooooo\n" +
                "MMdooooooooosssssssssssssssssssmNmmNdmMsoooooNMmhsssmNmMmooooooooooooooooooooooooooooooooooooooooooo\n" +
                "MMmoooooooooossssssssssssssssssssssssNmooooooNmsssssdNdsoooooooooooooooooooooooooooooooooooooooooooy\n" +
                "MMMoooooooooosssssssssssssssssssyhhysNmoooooosNmysdNdsoooooooooooooooooooooooooooooooooooooooooooood\n" +
                "MMMyooooooooossssssssssssssssshNmmMhsdMsooooooohmNdsoooooooooooooooooooooooooooooooooooooooooooooooN\n" +
                "MMMmoooooooooossssssssssssssssssshMhssmNyoooooooosooooooooooooooooooooooooooooooooooooooooooooooooyM\n" +
                "MMMMyooooooooossssssssssssssssssshMhsssdNdsooooooooooooooooooooooooooooooooooooooooooooooooooooooomM\n" +
                "MMMMNoooooooooosssssssssssssssssshMhssssdMNdsoooooooooooooooooohmNNmdsoooooooooooooooooooooooooooyMM\n" +
                "MMMMMdoooooooooossssssssssssssssshMhssssyMMdooooooooooooooooosNMMMMMMMhooooooooooooooooooooooooosNMM\n" +
                "MMMMMMyoooooooooossssssssssssssdmmNmmmssdNhoooooooooooooooooohMMMMMMMMNooooooooooooooooooooooooomMMM\n" +
                "MMMMMMMyoooooooooosssssssssssssyyyyyyyyNmsooooooooooooooooooosNMMMMMMMNhysooooooooooooooooooooomMMMM\n" +
                "MMMMMMMmoooooooooossssssssssssssssssshNdoooooooooooooooooooooosdmNNNNMMMMNmdyooooooooooooooooodMMMMM\n" +
                "MMMMMMMyooooooooosssssssssssssssssssdNhooooooooooooooooooooooooooooosmMMMMMNhooooooooooooooosmMMMMMM\n" +
                "MMMMMMmoooooooooossssssssssssssssssdNyoooooooooooooooooooooooooooooooodMMNhsooooooooooooooosNMMMMMMM\n" +
                "MMMMMMsooooooooossssssssssssssssssdNyooooooooooooooooooooooooooooooooooydsoooooooooooooooohNMMMMMMMM\n" +
                "MMMMMmoooooooooosssssssssssssssssdNyoooooooooooooooooooooooooooooooooooooooooooooooooooosmMMMMMMMMMM\n" +
                "MMMMMsooooooooosssssssssssssssssdMyooooooooooooooooooooooooooooooooooooooooooooooooooosdMMMMMMMMMMMM\n" +
                "MMMMdoooooooooosssssssssooooooooyyooooooooooooooooooooooooooooooooooooooooooooooooooshNMMMMMMMMMMMMM\n" +
                "MMMMoooooooooosssooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooosdNMMMMMMMMMMMMMMM\n" +
                "MMMhooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooymMMMMMMMMMMMMMMMMMM\n" +
                "MMNoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooosdNMMMMMMMMMMMMMMMMMMMM\n" +
                "MMhoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooydNMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "MNooooooooooooooooooooosyhdmmNmhsoooooooooooooooooooooooooooooooooooosydmMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "MyoooooooooooooosyhdmNMMMMMMMMMMMMNdhysoooooooooooooooooooooooosyhdmNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "NooooooosyhdmmNMMMMMMMMMMMMMMMMMMMMMMMMMNNmddhhhyyyyyyhhhddmmNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                "ysyhdmNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n" +
                generateAscii("INCO-NODE-APPLICATION") + "\n" +
                generateAscii(node.getVersion()) + "\n" +
                "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n";
        System.out.println(logo);
    }

    /**
     * Creates a 100 character ascii with the current version in the middle
     *
     * @return 100 character ascii with version in the middle
     *
     * @author Albert Veldman
     */
    private String generateAscii(String text) {
        String ascii = "";
        int amountOfHyphen = 6;
        int size = text.length() + amountOfHyphen;
        int leftAmount = (int)Math.floor((100 - size)/2);
        int rightAmount = 100 - size - leftAmount;
        System.out.println(leftAmount);
        System.out.println(rightAmount);

        for(int i = leftAmount; i > 0; i--) {
            ascii += "M";
        }
        ascii += ("---" + text + "---");
        for(int i = rightAmount; i > 0; i--) {
            ascii += "M";
        }
        return ascii;
    }

    /**
     * Determines input and sends it to the correct processing method.
     *
     * @author Albert Veldman
     */
    private void processInput() {
        String input = scanner.nextLine();
        switch(input.toLowerCase()) {
            case "/help":
                printHelp();
                break;
            case "/unregister":
                node.unregisterNode();
                break;
            case "/register":
                node.setUserData();
                node.registerNode();
                break;
            case "/login":
                node.loginNode();
                break;
            case "/logout":
                node.logoutNode();
                break;
            case "/nickname":
                NodeMain.printLine("Nickname: " + node.getNickName());
                break;
            case "/messageport":
                NodeMain.printLine("Message port: " + node.getMessagePort());
                break;
            case "/fileport":
                NodeMain.printLine("File port: " + node.getFilePort());
                break;
            case "/relay":
                node.printRelay();
                break;
            case "/shutdown":
                node.shutdownNode();
                break;
            case "/inco":
                incoLogo();
                break;
            case "/version":
                node.printVersion();
                break;
            case "/help unregister":
            case "/help register":
            case "/help login":
            case "/help logout":
            case "/help messageport":
            case "/help fileport":
            case "/help nickname":
            case "/help relay":
            case "/help shutdown":
            case "/help version":
                printHelpCommand(input);
                break;
            default:
                NodeMain.printLine("!Invalid Command! /help for available commands");
                break;
        }
        if(!input.toLowerCase().equals("/shutdown"))
            waitForInput();
    }

    /**
     * prints all available commands to the console
     *
     * @author Albert Veldman
     */
    private void printHelp() {
        NodeMain.printLine("Available Commands:");
        NodeMain.printLine("- /unregister");
        NodeMain.printLine("- /register");
        NodeMain.printLine("- /logout");
        NodeMain.printLine("- /login");
        NodeMain.printLine("- /messageport");
        NodeMain.printLine("- /fileport");
        NodeMain.printLine("- /nickname");
        NodeMain.printLine("- /relay");
        NodeMain.printLine("- /shutdown");
        NodeMain.printLine("- /version");
        NodeMain.printLine("- /help");
        NodeMain.printLine("- /help <command>");
    }

    /**
     * Prints explanation of the given command.
     *
     * @param command command that needs explanation
     *
     * @author Albert Veldman
     */
    private void printHelpCommand(String command) {
        switch(command.toLowerCase()) {
            case "/help unregister":
                NodeMain.printLine("Unregister: Unregisters this Node from the Graph. Removing this Node from the network.");
                break;
            case "/help register":
                NodeMain.printLine("Register: Registers this Node at the Graph. Adding this Node to the network.");
                NodeMain.printLine("This is done automatically the first time this software is started.");
                break;
            case "/help logout":
                NodeMain.printLine("Logout: Logs this node out at the Graph. Disabling this node in the network.");
                break;
            case "/help login":
                NodeMain.printLine("Login: Logs this node in at the Graph. Enabling this node in the network.");
                NodeMain.printLine("The Node is automatically logged in when it's registered.");
                break;
            case "/help nickname":
                NodeMain.printLine("Shows this node's current nickname.");
                break;
            case "/help fileport":
                NodeMain.printLine("Shows this node's current port used to receive files.");
                break;
            case "/help messageport":
                NodeMain.printLine("Shows this node's current port used to receive messages.");
                break;
            case "/help relay":
                NodeMain.printLine("Shows all endpoints that use this node as a relay.");
                break;
            case "/help shutdown":
                NodeMain.printLine("Shuts down the application. This can take a few seconds.");
                break;
            case "/help version":
                NodeMain.printLine("Shows the current node software version.");
                break;
            default:
                NodeMain.printLine("!Invalid /help command!");
                break;
        }
    }
}