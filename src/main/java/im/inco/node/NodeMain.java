package im.inco.node;

import im.inco.shared.components.cryptography.KeyGenerator;
import im.inco.shared.components.receivehandler.ReceiveHandler;
import im.inco.shared.components.receivehandler.interfaces.IReceive;
import im.inco.shared.components.register.LoginService;
import im.inco.shared.components.register.RegisterService;
import im.inco.shared.components.register.exceptions.KeySetNotSetException;
import im.inco.shared.components.register.exceptions.RoleNotSetException;
import im.inco.shared.components.register.exceptions.TokenNotSetException;
import im.inco.shared.components.register.exceptions.UserDataNotSetException;
import im.inco.shared.components.register.interfaces.ILogin;
import im.inco.shared.components.register.interfaces.IRegisterOnGraph;
import im.inco.shared.components.relay.ProcessRelay;
import im.inco.shared.components.utils.KeySet;
import im.inco.shared.components.utils.UserData;
import im.inco.shared.components.utils.DesktopLocalStorageManager;
import im.inco.shared.components.utils.UserConfig;
import im.inco.shared.components.utils.VertexRole;
import im.inco.shared.components.utils.api.exceptions.APIHandlerException;
import im.inco.shared.components.utils.interfaces.ILocalStorageManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Albert Veldman
 */
public class NodeMain {
    /**
     * Standard Ports used to receive Messages/Files
     */
    private final int standardMessagePort = 1312;
    private final int standardFilePort = 1337;

    /**
     * The logger
     */
    private static final Logger LOGGER = Logger.getLogger(NodeMain.class.getName());

    /**
     * LocalStorageManager used to create Userdata
     */
    private ILocalStorageManager storageManager;

    /**
     * UserData object used to initialize stuff
     */
    private UserData userData;

    /**
     * ReceiveHandler that initializes listeners.
     */
    private IReceive receiveHandler = new ReceiveHandler(null);

    /**
     * ReceiveHandler thread
     */
    private Thread receiveHandlerThread;

    /**
     * the commandHandler
     */
    private CommandHandler commandHandler;

    /**
     * Application's starting point. Creates a new NodeMain object.
     *
     * @param args
     *
     * @author Albert Veldman
     */
    public static void main(String[] args) {
        NodeMain main = new NodeMain();
    }

    /**
     * Calls Initialize, and sets up and starts a ReceiveHandler thread
     *
     * @author Albert Veldman
     */
    public NodeMain () {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] %4$s: %5$s%n");
        commandHandler = new CommandHandler(this);
        commandHandler.incoLogo();
        printLine("INFO: Starting Initialize...");
        initialize();
        printLine("INFO: Initialized config.");
        //registerNode();
        printLine("Nickname: " + userData.getNickname());
        printLine("INFO: Initializing receive service...");
        setupReceiveService();
        printLine("INFO: Initialized receive service.");
        printLine("INFO: Done Initializing.");
        try {
            storageManager.writeUserData(userData);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        }
        mainProgram();
    }

    /**
     * Prints the received message with a timestamp in front of it
     *
     * @param message message that has to be displayed on the console
     *
     * @author Albert Veldman
     */
    public static void printLine(String message) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = ("[" + df.format(calendar.getTime()) + "] ");

        System.out.println(time + message);
    }

    /**
     * Call registerService to register node at graph
     *
     * @author Albert Veldman
     */
    protected void registerNode() {
        if(userData.getNickname() == null) {
            printLine("INFO: Registering Node...");
            try {
                IRegisterOnGraph registerService = new RegisterService();
                registerService.register();
                printLine("INFO: Node Registered.");
                userData.setOnline(true);
                storageManager.writeUserData(userData);
            } catch (IOException exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            } catch (KeySetNotSetException exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            } catch (APIHandlerException exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            } catch (RoleNotSetException exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            } catch (UserDataNotSetException exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            }
        }
        else {
            printLine("INFO: Node already registered.");
        }
    }

    /**
     * Call registerService to unregister node at graph
     *
     * @author Albert Veldman
     */
    protected void unregisterNode() {
        printLine("INFO: Unregistering Node...");
        try {
            IRegisterOnGraph registerService = new RegisterService();
            registerService.unregister();
            printLine("INFO: Node Unregistered");
            storageManager.writeUserData(userData);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        } catch (TokenNotSetException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        } catch (APIHandlerException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        } catch (UserDataNotSetException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }

    /**
     * Call LoginService to logoff node at graph
     *
     * @author Albert Veldman
     */
    protected void logoutNode() {
        printLine("INFO: Logging out Node...");
        if(userData.getNickname() != null) {
            try {
                ILogin logoff = new LoginService();
                logoff.logoff();
                storageManager.writeUserData(userData);
                printLine("INFO: Node logged out.");
            } catch (APIHandlerException exception) {
                LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
            } catch (IOException exception) {
                LOGGER.log(Level.WARNING, exception.getMessage(), exception);
            }
        }
        else {
            printLine("INFO: Node not registered");
        }
    }

    /**
     * Call LoginService to login node at graph
     *
     * @author Albert Veldman
     */
    protected void loginNode() {
        printLine("INFO: Logging in Node...");
        try {
            ILogin login = new LoginService();
            login.login();
            storageManager.writeUserData(userData);
            printLine("INFO: Node Logged in.");
        } catch (APIHandlerException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }

    /**
     * Prints all connected endpoints
     *
     * @author Albert Veldman
     */
    protected void printRelay() {
        ArrayList<String> nicknames = ProcessRelay.getConnectedNicknames();
        if(!nicknames.isEmpty()) {
            for (String nickname : nicknames) {
                printLine("- " + nickname);
            }
        } else {
            printLine("No connected endpoints.");
        }
    }

    /**
     * Starts shutdown by interrupting receiveHandlerThread
     *
     * @author Albert Veldman
     */
    protected void shutdownNode() {
        printLine("INFO: Initiated shutdown...");
        logoutNode();
        printLine("INFO: Shutting down receiver...");
        receiveHandlerThread.interrupt();
        waitForShutdown();
    }

    /**
     * Sets available userdata
     *
     * @author Albert Veldman
     */
    protected void setUserData() {
        if(userData.getKeySet() == null) {
            KeySet keySet = KeyGenerator.toKeySet(KeyGenerator.generateRSAKeyPair());
            userData.setKeySet(keySet);
        }
        if(userData.getNickname() == null) {
            userData.setMessagePort(askMessagePort());
            userData.setFilePort(askFilePort());
        }
        userData.setRelayPath(System.getProperty("user.dir").concat(File.separator + "Relay"));
        userData.setEndpointPath(System.getProperty("user.dir").concat(File.separator + "Endpoint"));
        userData.setTempPath(System.getProperty("user.dir").concat(File.separator + "Temp"));
        userData.setRole(VertexRole.NODE);
    }

    /**
     * Prints the version number
     *
     * @author Albert Veldman
     */
    protected void printVersion() {
        printLine("Node version: " + getVersion());
    }

    /**
     * Stars receivehandler which starts listeners.
     *
     * @author Albert Veldman
     */
    private void setupReceiveService() {
        receiveHandler.setupHandler();
        receiveHandlerThread = new Thread(receiveHandler);
        receiveHandlerThread.start();
    }

    /**
     * Calls CommandHandler and waits for input
     *
     * @author Albert Veldman
     */
    private void mainProgram() {
        printLine("/help for available commands");
        commandHandler.waitForInput();
    }

    /**
     * Initializes UserData and sets it using setUserData
     *
     * @author Albert Veldman
     */
    private void initialize() {
        try {
            initializeUserData();
        } catch (IOException exception) {
            LOGGER.log(Level.INFO, "INFO: No userdata found! Creating new userdata...");
        }
        setUserData();
    }

    /**
     * Creates a new userdata object using the LocalStorageManager
     *
     * @throws IOException
     *
     * @author Albert Veldman
     */
    private void initializeUserData() throws IOException {
        storageManager = new DesktopLocalStorageManager();
        UserConfig.getInstance(storageManager).setUserData(storageManager.readUserData());
        userData = UserConfig.getInstance().getUserData();
    }

    /**
     * Ask user if they want to use the standard port or want to choose one themself
     *
     * @return Standard message port or message port that user specified
     *
     * @author Albert Veldman
     */
    private int askMessagePort() {
        int messagePort = commandHandler.askPort(standardMessagePort, "messages");
        if(messagePort == -1) {
            return standardMessagePort;
        }
        else return messagePort;
    }

    /**
     * Ask user if they want to use the standard port or want to choose one themself
     *
     * @return Standard file port or file port that user specified
     *
     * @author Albert Veldman
     */
    private int askFilePort() {
        int filePort = commandHandler.askPort(standardFilePort, "files");
        if(filePort == -1) {
            return standardFilePort;
        }
        else
            return filePort;
    }

    /**
     * Waits until receiver thread is shutdown and closes the application
     *
     * @author Albert Veldman
     */
    private void waitForShutdown() {
        while(receiveHandlerThread.isAlive()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get messageport from userdata and returns it.
     *
     * @return messageport
     *
     * @author Albert Veldman
     */
    public int getMessagePort() {
        return userData.getMessagePort();
    }

    /**
     * Get fileport from userdata and returns it.
     *
     * @return fileport
     *
     * @author Albert Veldman
     */
    public int getFilePort() {
        return userData.getFilePort();
    }

    /**
     * Get nickname from userdata and returns it.
     *
     * @return nickname
     *
     * @author Albert Veldman
     */
    public String getNickName() {
        return userData.getNickname();
    }

    /**
     * Get version number from manifest
     *
     * @return node version
     *
     * @author Albert Veldman
     */
    public String getVersion() {
        String version = getClass().getPackage().getImplementationVersion();
        return version;
    }
}