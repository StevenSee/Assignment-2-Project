// Steven Causley
// CS-102, Spring 2019
// Assignment 1
package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class TennisDatabase implements TennisDatabaseInterface {

    private TennisMatchContainer matchContainer = new TennisMatchContainer();
    private TennisPlayerContainer playerContainer = new TennisPlayerContainer();

    //Scans input file and loads players and matches into respective containers
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

    @Override
    public void saveToFile(String fileName) throws TennisDatabaseException {
        PrintStream out;
        try {
            out = new PrintStream(fileName);
        } catch (FileNotFoundException e) {
            throw new TennisDatabaseException("ERROR: Input file not found.");
        }
        //TODO: Exporting

    }

    @Override
    public void reset() {
        if (this.playerContainer.getNumPlayers() == 0) {
            System.out.println("Player Container is empty. Nothing to delete");
        }
        if (this.matchContainer.getNumMatches() == 0) {
            System.out.println("Match Container is empty. Nothing to delete");
        }
        this.playerContainer = new TennisPlayerContainer();
        this.matchContainer = new TennisMatchContainer();
    }

    @Override
    public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
        return playerContainer.getPlayer(id);
    }

    @Override
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
        return playerContainer.getAllPlayers();
    }

    @Override
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        return playerContainer.getMatchesOfPlayer(playerId);

    }

    @Override
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        return matchContainer.getAllMatches();
    }

    //Adds player to playerContainer
    @Override
    public void insertPlayer(String id, String firstName, String lastName, int year, String country) throws TennisDatabaseException {
        try {
            TennisPlayer newPlayer = new TennisPlayer(id.toUpperCase(), firstName.toUpperCase(), lastName.toUpperCase(), year, country.toUpperCase());
            playerContainer.insertPlayer(newPlayer);
        } catch (TennisDatabaseException e) {
            throw new TennisDatabaseRuntimeException("Tennis Database Exception in insertPlayer");
        }

    }

    @Override
    public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException {
        playerContainer.deletePlayer(playerId);
    }

    //Adds match to matchContainer
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

    //splits the date into a String array for use with TennisMatch constructor
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
