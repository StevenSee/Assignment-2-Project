//Steven Causley
//CS-192, Spring 2019
//Assignment 1

package TennisDatabase;

class TennisPlayerContainer implements TennisPlayerContainerInterface {
    private TennisPlayerContainerNode root;
    private int playerCount;

    TennisPlayerContainer() {
        this.root = null;
        this.playerCount = 0;
    }

    // Desc.: Returns the number of players in this container.
    // Output: The number of players in this container as an integer.
    public int getNumPlayers() { return this.playerCount; }

    // Desc.: Returns an iterator object ready to be used to iterate this container.
    // Output: The iterator object configured for this container.
    public TennisPlayerContainerIterator iterator() { return new TennisPlayerContainerIterator(this.root); }

    // Desc.: Search for a player in this container by input id, and returns a copy of that player (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    public TennisPlayer getPlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode playerNode = getPlayerNodeRec(this.root, playerId);
        if (playerNode == null) { throw new TennisDatabaseRuntimeException("..."); } else {
            return playerNode.getPlayer();
        }
    }

    // Search for a player node by id, return the node if found, null otherwise.
    private TennisPlayerContainerNode getPlayerNodeRec(TennisPlayerContainerNode currRoot, String id) {
        if (currRoot == null) { return null; } else {
            // 3-way comparison to understand how to proceed the search.
            int comparisonResult = currRoot.getPlayer().getId().compareTo(id);
            if (comparisonResult == 0) {
                return currRoot;
            } else if (comparisonResult < 0) {
                return getPlayerNodeRec(currRoot.getRight(), id);
            } else {
                return getPlayerNodeRec(currRoot.getLeft(), id);
            }
        }
    }

    // Desc.: Search for a player in this container by id, and delete it with all his matches (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode nodePlayer = this.root;
        boolean playerFound = false;
        while (!playerFound) {
            if (nodePlayer.getPlayer().getId().compareTo(playerId) < 0) {
                nodePlayer = nodePlayer.getLeft();
            } else if (nodePlayer.getPlayer().getId().compareTo(playerId) > 0) {
                nodePlayer = nodePlayer.getRight();
            } else if (nodePlayer.getPlayer().getId().compareTo(playerId) == 0) {
                playerFound = true;
            } else {
                throw new TennisDatabaseRuntimeException("Player could not be found for match insertion");
            }
        }
    //TODO: Code player deletion
    }

    // Desc.: Insert a tennis player into this container.
    // Input: A tennis player.
    // Output: Throws a checked (critical) exception if player id is already in this container.
    //         Throws a checked (critical) exception if the container is full.
    public void insertPlayer(TennisPlayer player) throws TennisDatabaseException {
        this.root = insertPlayerRec(this.root, player);
        playerCount++;
    }

    // ...
    private TennisPlayerContainerNode insertPlayerRec(TennisPlayerContainerNode currRoot, TennisPlayer player)
            throws TennisDatabaseException {
        if (currRoot == null) {
            return new TennisPlayerContainerNode(player);
        } else {
            // 3-way comparison to understand how to proceed the search.
            int comparisonResult = currRoot.getPlayer().compareTo(player);
            if (comparisonResult == 0) {
                throw new TennisDatabaseException("...");
            } else if (comparisonResult < 0) {
                currRoot.setRight(insertPlayerRec(currRoot.getRight(), player));
                return currRoot;
            } else {
                currRoot.setLeft(insertPlayerRec(currRoot.getLeft(), player));
                return currRoot;
            }
        }
    }

    // Desc.: Insert a tennis match into the lists of both tennis players of the input match.
    // Input: A tennis match.
    // Output: Throws a checked (critical) exception if the insertion is not fully successful.
    public void insertMatch(TennisMatch match) throws TennisDatabaseException {
        String idPlayer1 = match.getIdPlayer1();
        String idPlayer2 = match.getIdPlayer2();

        TennisPlayerContainerNode nodeP1 = this.root;
        boolean p1Found = false;
        TennisPlayerContainerNode nodeP2 = this.root;
        boolean p2Found = false;
        while (!p1Found) {
            if (nodeP1.getPlayer().getId().compareTo(idPlayer1) > 0) {
                nodeP1 = nodeP1.getLeft();
            } else if (nodeP1.getPlayer().getId().compareTo(idPlayer1) < 0) {
                nodeP1 = nodeP1.getRight();
            } else if (nodeP1.getPlayer().getId().compareTo(idPlayer1) == 0) {
                p1Found = true;
            } else {
                throw new TennisDatabaseException("Player1 could not be found for match insertion");
            }
        }
        while (!p2Found) {
            if (nodeP2.getPlayer().getId().compareTo(idPlayer2) > 0) {
                nodeP2 = nodeP2.getLeft();
            } else if (nodeP2.getPlayer().getId().compareTo(idPlayer2) < 0) {
                nodeP2 = nodeP2.getRight();
            } else if (nodeP2.getPlayer().getId().compareTo(idPlayer2) == 0) {
                p2Found = true;
            } else {
                throw new TennisDatabaseException("Player2 could not be found for match insertion");
            }
        }
        if (p1Found && p2Found) {
            nodeP1.insertMatch(match);
            nodeP2.insertMatch(match);
        }


    }

    // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of a player.
    // Output: Throws a checked (critical) exception if the player (id) does not exists.
    //         Throws an unchecked (non-critical) exception if there are no matches (but the player id exists).
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode nodePlayer = this.root;
        boolean playerFound = false;
        while (!playerFound) {
            if (nodePlayer.getPlayer().getId().compareTo(playerId) < 0) {
                nodePlayer = nodePlayer.getLeft();
            } else if (nodePlayer.getPlayer().getId().compareTo(playerId) > 0) {
                nodePlayer = nodePlayer.getRight();
            } else if (nodePlayer.getPlayer().getId().compareTo(playerId) == 0) {
                playerFound = true;
            } else {
                throw new TennisDatabaseRuntimeException("Player could not be found for match insertion");
            }
        }
        if (playerFound) {
            return nodePlayer.getMatches();
        } else {
            throw new TennisDatabaseRuntimeException("Player could not be found for match insertion");
        }
    }

    public TennisPlayer[] getAllPlayers(){
        TennisPlayerContainerIterator iterator = iterator();
        iterator.setInorder();
        TennisPlayer[] outputArray = new TennisPlayer[playerCount];
        int index = 0;
        while (iterator.hasNext()){
            outputArray[index] = iterator.next();
        }
        return outputArray;
    }
}
