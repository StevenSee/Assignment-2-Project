// Steven Causley
// CS-102, Spring 2019
// Assignment 2
package TennisDatabase;

import java.util.Iterator;
import java.util.ArrayList;

class TennisMatchContainer implements TennisMatchContainerInterface
{

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
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        TennisMatch[] outputArray = new TennisMatch[getNumMatches()];

        for(int i = 0; i < outputArray.length; i++){
            outputArray[i] = matchList.get(i);
        }

        return outputArray;
    }

    // Desc.: Returns all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of the tennis player.
    // Output: Throws an unchecked (non-critical) exception if there are no tennis matches in the list.
    public TennisMatch[] getMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException {
        int numMatches = 0;

        for (int i=0; i < matchList.size(); i++){
            if (matchList.get(i).getIdPlayer1().equals(playerId) || matchList.get(i).getIdPlayer2().equals(playerId)){
                numMatches++;
            }
        }

        TennisMatch[] outputArray = new TennisMatch[numMatches];
        int outputIndex = 0;

        for (int i = 0; i < matchList.size(); i++) {
            if (matchList.get(i).getIdPlayer1().equals(playerId) || matchList.get(i).getIdPlayer2().equals(playerId)){
                outputArray[outputIndex] = matchList.get(i);
                outputIndex++;
            }
        }
        return  outputArray;
    }

    // Desc.: Delete all matches of a player by id (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no match with that input id.
    public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException {
        int index = 0;
        while (index < matchList.size()) {
            if (matchList.get(index).getIdPlayer1().equals(playerId) || matchList.get(index).getIdPlayer2().equals(playerId)){
                matchList.remove(index);
            } else {
                index += 1;
            }
        }
    }


}
