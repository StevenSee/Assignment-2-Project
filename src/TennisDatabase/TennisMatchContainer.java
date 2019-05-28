// Steven Causley
// CS-102, Spring 2019
// Assignment 1
package TennisDatabase;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;

public class TennisMatchContainer implements TennisMatchContainerInterface
{
    //private TennisMatch[] matchArray;
    //private int matchCount;//number of matches in array (logical size)
    //private int maxMatches = 2; //max number of matches in array

    private ArrayList<TennisMatch> matchList;
    //private LinkedList<TennisMatch> matchList;

    public TennisMatchContainer()
    {
        this.matchList = new ArrayList<TennisMatch>();
        //this.matchList = new LinkedList<TennisMatch>();
    }

    // Desc.: Returns the number of matches in this container.
    // Output: The number of matches in this container as an integer.
    public int getNumMatches() { return this.matchList.size(); }

    // Desc.: Returns an iterator object ready to be used to iterate this container.
    // Output: The iterator object configured for this container.
    public Iterator<TennisMatch> iterator() { return this.matchList.iterator(); }

    // Desc.: Insert a tennis match into this container.
    // Input: A tennis match.
    // Output: Throws a checked (critical) exception if the container is full.
    public void insertMatch( TennisMatch m ) throws TennisDatabaseException {
        // Find the point of insertion.
        int point = 0;
        while ((point < this.matchList.size() ) && (this.matchList.get(point).compareTo(m) > 0))
        {
            point++;
        }
        this.matchList.add( point, m );
    }

    // Desc.: Returns all matches in the database arranged in the output array (sorted by date, most recent first).
    // Output: Throws an exception if there are no matches in this container.
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException { return null; }

    // Desc.: Returns all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of the tennis player.
    // Output: Throws an unchecked (non-critical) exception if there are no tennis matches in the list.
    public TennisMatch[] getMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException { return null; }

    // Desc.: Delete all matches of a player by id (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no match with that input id.
    public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException {}

    /*@Override
    public int getNumMatches() {
        return 0;
    }

    @Override
    public Iterator<TennisMatch> iterator() {
        return null;
    }

    //Adds inputted match into the container
    @Override
    public void insertMatch(TennisMatch m) throws TennisDatabaseException
    {
        if (this.matchCount == maxMatches)
        {
            TennisMatch[] newArray = new TennisMatch[this.matchArray.length * 2];
            for (int i = 0; i < this.matchCount; i++)
            {
                newArray[i] = this.matchArray[i];
            }
            this.matchArray = newArray;
            this.maxMatches = this.matchArray.length;
        }

        int point = 0;
        while ((point < this.matchCount) && (this.matchArray[point].compareTo(m) > 0))
        {
            point++;
        }
        if (point < this.matchCount)
        {
            for (int i = this.matchCount - 1; i >= point; i--)
            {
                this.matchArray[i + 1] = this.matchArray[i];
            }
        }
        this.matchArray[point] = m;
        this.matchCount++;
    }

    @Override
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException
    {
        return matchArray;
    }

    //Unused method. Returns array of matches with playerId
    @Override
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException
    {

        int matches = 0;
        for (int i = 0; i< this.matchCount; i++)
        {
            if (this.matchArray[i].getIdPlayer1().equals(playerId) || this.matchArray[i].getIdPlayer2().equals(playerId))
            {
                matches++;
            }
        }
        TennisMatch[] output = new TennisMatch[matches];
        int outputIndex = 0;
        for (int i =0; i < matchArray.length; i++)
        {
            if (this.matchArray[i].getIdPlayer1().equals(playerId) || this.matchArray[i].getIdPlayer2().equals(playerId))
            {
                output[outputIndex] = matchArray[i];
                outputIndex++;
            }
        }


        return output;
    }

    @Override
    public void deleteMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {

    }

    public int getMatchCount()
    {
        return matchCount;
    }*/

}
