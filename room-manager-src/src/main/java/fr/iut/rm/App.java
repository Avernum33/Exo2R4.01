package fr.iut.rm;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.persist.jpa.JpaPersistOptions;
import fr.iut.rm.control.ControlRoom;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Entry point for command-line program. It's mainly a dumb main static method.
 */
public final class App {
    /**
     * quit constant
     */
    private static final String QUIT = "q";
    /**
     * help constant
     */
    private static final String HELP = "h";
    /**
     * creat constant
     */
    private static final String CREATE = "c";
    /**
     * list constant
     */
    private static final String LIST = "l";
    /**
     * delete constant
     */
    private static final String DELETE = "d";

    /**
     * standard logger
     */
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    /**
     * the available options for CLI management
     */
    private final Options options = new Options();

    @Inject
    ControlRoom cr;

    /**
     * Invoked at module initialization time
     */
    public App() {
        // build options command line options
        options.addOption(Option.builder(LIST)
            .desc("List all rooms")
            .build());

        options.addOption(Option.builder(CREATE)
            .hasArgs()
            .desc("Create new room")
            .build());

        options.addOption(Option.builder(HELP)
            .desc("Display help message")
            .build());

        options.addOption(Option.builder(QUIT)
            .desc("Quit the program")
            .build());

        options.addOption(Option.builder(DELETE)
            .hasArg()
            .desc("Delete room")
            .build());
    }

    /**
     * Main program entry point
     *
     * @param args main program args
     */
    public static void main(final String[] args) {
        logger.info("Room-Manager version {} started", Configuration.getVersion());
        logger.debug("create guice injector");
        Injector injector = Guice.createInjector(
            new JpaPersistModule(
                "room-manager",
                JpaPersistOptions.builder()
                    .setAutoBeginWorkOnEntityManagerCreation(true)
                    .build()
            ),
            new MainModule()
        );
        logger.info("starting persistence service");
        PersistService ps = injector.getInstance(PersistService.class);
        ps.start();

        App app = injector.getInstance(App.class);

        app.showHelp();
        app.run();

        logger.info("Program finished");
        System.exit(0);
    }

    /**
     * Displays help message
     */
    private void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("room-manager.jar", options);
    }

    public void run() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        Scanner sc = new Scanner(System.in);
        do {
            String str = sc.nextLine();
            try {
                cmd = parser.parse(options, str.split(" "));
                if (cmd.hasOption(HELP)) {
                    showHelp();
                } else if (cmd.hasOption(LIST)) {
                    cr.showRooms();
                } else if (cmd.hasOption(CREATE)) {
                    String val[] = cmd.getOptionValues(CREATE);
                    String name = val[0];
                    String description = val[1];
                    if (name != null && !name.isEmpty()) {
                        cr.createRoom(name,description);
                    }
                }   else if (cmd.hasOption(DELETE)) {
                    String val[] = cmd.getOptionValues(DELETE);
                    String name = val[0];
                    if (name != null && !name.isEmpty()) {
                        cr.deleteRoom(name);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
                showHelp();
            }
        } while (!cmd.hasOption(QUIT));
    }
}