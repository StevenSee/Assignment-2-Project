// Steven Causley
// CS-102, Spring 2019
// Assignment 2
package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

public class TennisDatabase implements TennisDatabaseInterface {

    private TennisMatchContainer matchContainer = new TennisMatchContainer();
    private TennisPlayerContainer playerContainer = new TennisPlayerContainer();

    // Desc.: Loads data from file following the format described in the specifications.
    // Output: Throws an unchecked (non-critical) exception if the loading is not fully successfull.
    //         Throws a checked (critical) exception if the file (file name) does not exist.
    @Override
    public void loadFromFile(String fileName) throws TennisDatabaseException, TennisDatabaseRuntimeException {

        Scanner fileScan;


        //check that file is valid
        try {
            File inFile = new File(fileName);
            fileScan = new Scanner(inFile).useDelimiter("[\\r\\n]+");
        } catch (FileNotFoundException e) {
            throw new TennisDatabaseException("ERROR: Input file not found.");
        }


        while (fileScan.hasNextLine()) {
            String inString = fileScan.nextLine();
            Scanner inScan = new Scanner(inString).useDelimiter("[\\r\\n/]");
            String token = inScan.next().toUpperCase();

            if (token.equals("PLAYER")) {
                String id = inScan.next().toUpperCase();
                String firstName = inScan.next().toUpperCase();
                String lastName = inScan.next().toUpperCase();
                int year = inScan.nextInt();
                String country = inScan.next().toUpperCase();
                insertPlayer(id, firstName, lastName, year, country);

            }
            if (token.equals("MATCH")) {
                String playerId1 = inScan.next().toUpperCase();
                String playerId2 = inScan.next().toUpperCase();
                int[] date = splitDate(inScan.next());
                String location = inScan.next().toUpperCase();
                String score = inScan.next().toUpperCase();

                insertMatch(playerId1, playerId2, date[0], date[1], date[2], location, score);

            }
            //Ignore lines without PLAYER or MATCH
        }

    }


    // Desc.: Saves data to file following the format described in the specifications.
    // Output: Throws a checked (critical) exception if the file open for writing fails.
    @Override
    public void saveToFile(String fileName) throws TennisDatabaseException {
        PrintStream out;
        try {
            out = new PrintStream(fileName);
        } catch (FileNotFoundException e) {
            throw new TennisDatabaseException("ERROR: Output file not found.");
        }

        //print players
        TennisPlayerContainerIterator playerIterator = playerContainer.iterator();
        playerIterator.setInorder();

        while (playerIterator.hasNext()) {
            out.println(playerIterator.next().getExport());
        }
        Iterator<TennisMatch> matchIterator = matchContainer.iterator();

        while (matchIterator.hasNext()) {
            out.println((matchIterator.next().getExport()));
        }

    }

    // Desc.: Resets the database, making it empty.
    //Output: Throws non-critical exception if containers are already empty
    @Override
    public void reset() throws TennisDatabaseRuntimeException{
        if (this.playerContainer.getNumPlayers() == 0) {
            throw new TennisDatabaseRuntimeException("Player Container is empty. Nothing to delete");
        }
        if (this.matchContainer.getNumMatches() == 0) {
            throw new TennisDatabaseRuntimeException("Match Container is empty. Nothing to delete");
        }
        this.playerContainer = new TennisPlayerContainer();
        this.matchContainer = new TennisMatchContainer();
    }

    // Desc.: Search for a player in the database by input id, and returns a copy of that player (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    @Override
    public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
        return playerContainer.getPlayer(id);
    }

    // Desc.: Returns copies (deep copies) of all players in the database arranged in the output array (sorted by id, alphabetically).
    // Output: Throws an unchecked (non-critical) exception if there are no players in the database.
    @Override
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
        return playerContainer.getAllPlayers();
    }

    @Override
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        return playerContainer.getMatchesOfPlayer(playerId);

    }

    // Desc.: Returns copies (deep copies) of all matches in the database arranged in the output array (sorted by date, most recent first).
    // Output: Throws an unchecked (non-critical) exception if there are no matches in the database.
    @Override
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        return matchContainer.getAllMatches();
    }

    // Desc.: Insert a player into the database.
    // Input: All the data required for a player.
    // Output: Throws a checked (critical) exception if player id is already in the database.
    @Override
    public void insertPlayer(String id, String firstName, String lastName, int year, String country) throws TennisDatabaseException {
        try {
            TennisPlayer newPlayer = new TennisPlayer(id.toUpperCase(), firstName.toUpperCase(), lastName.toUpperCase(), year, country.toUpperCase());
            playerContainer.insertPlayer(newPlayer);
        } catch (TennisDatabaseException e) {
            throw new TennisDatabaseRuntimeException("Tennis Database Exception in insertPlayer");
        }

    }

    // Desc.: Search for a player in the database by id, and delete it with all his matches (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    @Override
    public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException {
        matchContainer.deleteMatchesOfPlayer(playerId);
        playerContainer.deletePlayer(playerId);

    }

    // Desc.: Insert a match into the database.
    // Input: All the data required for a match.
    // Output: Throws a checked (critical) exception if a player does not exist in the database.
    //         Throws a checked (critical) exception if the match score is not valid.
    @Override
    public void insertMatch(String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score) throws TennisDatabaseException {
        TennisMatch m = new TennisMatch(idPlayer1, idPlayer2, year, month, day, tournament, score);


        try {
            matchContainer.insertMatch(m);
            playerContainer.insertMatch(m);
        } catch (TennisDatabaseRuntimeException e) {
            throw new TennisDatabaseRuntimeException("Error: Invalid Match. Match not added");
        }

        if (m.getWinner() == 1) {
            getPlayer(m.getIdPlayer1()).addWin();
            getPlayer(m.getIdPlayer2()).addLoss();
        } else {
            getPlayer(m.getIdPlayer1()).addLoss();
            getPlayer(m.getIdPlayer2()).addWin();
        }

    }

    //Splits the date into a String array for use with TennisMatch constructor
    public int[] splitDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);

        int[] output = new int[3];
        output[0] = Integer.parseInt(year);
        output[1] = Integer.parseInt(month);
        output[2] = Integer.parseInt(day);

        return output;
    }


}
