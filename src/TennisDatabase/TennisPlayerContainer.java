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

    //Desc: Deletes node by id on the input BST (current root)
    //Input: Binary Search Tree
    //Output: BST after removal
    private TennisPlayerContainerNode deletePlayerNodeRec(TennisPlayerContainerNode currRoot, String id) throws TennisDatabaseRuntimeException {
        if (currRoot == null) {
            throw new TennisDatabaseRuntimeException("Deleted Failed, Player could not be found");
        } else {
            // 3-way comparison to understand how to proceed the search.
            int comparisonResult = currRoot.getPlayer().getId().compareTo(id);
            if (comparisonResult == 0) {
                return deleteNode(currRoot);
            } else if (comparisonResult < 0) {
                TennisPlayerContainerNode newRightChild = deletePlayerNodeRec(currRoot.getRight(), id);
                currRoot.setRight(newRightChild);
                return currRoot;
            } else {
                TennisPlayerContainerNode newLeftChild = deletePlayerNodeRec(currRoot.getLeft(), id);
                currRoot.setLeft(newLeftChild);
                return currRoot;
            }
        }
    }

    private TennisPlayerContainerNode deleteNode(TennisPlayerContainerNode currRoot) {
        //currRoot has no children
        if (currRoot.getLeft() == null && currRoot.getRight() == null) {
            return null;
        }
        //only has left child
        else if (currRoot.getLeft() != null && currRoot.getRight() == null) {
            return currRoot.getLeft();
        }
        //only has right child
        else if (currRoot.getRight() != null && currRoot.getLeft() == null) {
            return currRoot.getRight();
        }
        //symmetric, search for inorder successor swap
        else { //TODO: delete node for when node has children on both sides
            //find the inorder successor, leftmost node of the right subtree, get leftmost of right child
            TennisPlayerContainerNode leftMost = findLeftMost(currRoot.getRight());
            //perform the copy of content from the successor into currRoot (set and get players and matches)
            currRoot.setPlayer(leftMost.getPlayer());
            currRoot.setMatchList(leftMost.getMatchList());
            //delete the successor , if no children return null, if right child return that
            TennisPlayerContainerNode deletedNode = deleteLeftMostRec(currRoot.getRight());
            currRoot.setRight(deletedNode);
            return deletedNode;

            //return currRoot
        }
    }

    private TennisPlayerContainerNode deleteLeftMostRec(TennisPlayerContainerNode currRoot){
        if (currRoot == null) { return null;}
        else if (currRoot.getLeft() == null) {return currRoot.getRight();}
        else {
            TennisPlayerContainerNode newLeftNode = deleteLeftMostRec(currRoot.getRight());
            currRoot.setLeft(newLeftNode);
            return currRoot;
        }
    }

    private TennisPlayerContainerNode findLeftMost(TennisPlayerContainerNode currRoot) {
        if (currRoot.getLeft() == null) {
            return currRoot;
        }
        else {
            return findLeftMost(currRoot.getLeft());
        }
    }

    // Desc.: Search for a player in this container by id, and delete it with all his matches (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode playerNode = deletePlayerNodeRec(this.root, playerId);
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

        TennisPlayerContainerNode nodeP1 = getPlayerNodeRec(this.root, idPlayer1);
        TennisPlayerContainerNode nodeP2 = getPlayerNodeRec(this.root, idPlayer2);

        if ((nodeP1 != null) && (nodeP2 != null)) {
            nodeP1.insertMatch(match);
            nodeP2.insertMatch(match);
        } else {
            throw new TennisDatabaseException("Player could not be found for insertion");
        }

    }

    // Desc.: Returns copies (deep copies) of all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of a player.
    // Output: Throws a checked (critical) exception if the player (id) does not exists.
    //         Throws an unchecked (non-critical) exception if there are no matches (but the player id exists).
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode nodePlayer = getPlayerNodeRec(this.root, playerId);

        return nodePlayer.getMatches();
    }

    public TennisPlayer[] getAllPlayers() {
        TennisPlayerContainerIterator iterator = this.iterator();
        iterator.setInorder();
        TennisPlayer[] outputArray = new TennisPlayer[playerCount];
        int index = 0;
        while (iterator.hasNext()) {
            outputArray[index] = iterator.next();
            index++;
        }
        return outputArray;
    }
}
