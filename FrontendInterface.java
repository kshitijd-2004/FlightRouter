   public interface FrontendInterface {


        // Constructor Parameters: An instance of the Backend and a scanner object
        // Initialize scanner object and backend instance

        /**
         * Starts the main command loop for the user, allowing them to interact with the application.
         */
        void startApplication();

        /**
         * Prompts the user to specify and load a data file.
         */
        void loadFile();

        /**
         * Displays statistics about the dataset, including the number of airports,
         * the number of flights, and the total number of miles.
         */
        void showStats();

        /**
         * Asks the user for a start and destination airport, then lists the shortest route
         * between those airports, including all airports on the way, the distance for each segment,
         * and the total number of miles from the start to the destination airport.
         *
         * @param start - Start airport
         * @param destination - Destination airport
         *
         */
        void showRouteInfo(String start, String destination);

        /**
         * Exits the application.
         */
        void exitApplication();
    }


