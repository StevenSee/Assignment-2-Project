//Steven Causley
//CS-192, Spring 2019
//Assignment 1

package TennisDatabase;

class TennisPlayerContainer implements TennisPlayerContainerInterface {
    private TennisPlayerContainerNode root;
    private int playerCount = 0;

    public TennisPlayerContainer() {
        this.root = null;
    }

    //Searches through nodes to find player. Throws exception if id does not exist
    /*
    public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode node = this.root;
        boolean idFound = false;
        int nodeIndex = 0;
        while ((nodeIndex < this.playerCount) && (node.getPlayer().getId().compareTo(id) < 0)) {
            node = node.getRight();
            nodeIndex++;
        }
        // Check if we found the node.
        if ((node != null) && (node.getPlayer().getId().equals(id))) {
            idFound = true;
        }

        if (idFound) {
            return node.getPlayer();
        } else {
            throw new TennisDatabaseRuntimeException("Player could not be found.");
        }
    }

     */

    //Searches for player with inputted id and creates an array of their matches
    public TennisMatch[] getPlayerMatches(String id) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode node = this.root;
        boolean idFound = false;
        int nodeIndex = 0;
        while ((nodeIndex < this.playerCount) && (node.getPlayer().getId().compareTo(id) < 0)) {
            node = node.getRight();
            nodeIndex++;
        }
        // Check if we found the node.
        if ((node != null) && (node.getPlayer().getId().equals(id))) {
            idFound = true;
        }

        if (idFound) {
            return node.getMatches();
        } else {
            throw new TennisDatabaseRuntimeException("Player could not be found.");
        }
    }

    //adds new player and player container node
    /*
    @Override
    public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
        if (!dupePlayerCheck(p)) {
            // Special case: list empty, no need to consider sorting, insert at front.
            if (this.playerCount == 0) {
                // Create a sorted doubly-linked circular list with only 1 node.
                TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p);
                newNode.setRight(newNode);
                newNode.setLeft(newNode);
                this.root = newNode;
                this.playerCount++;
            } else {
                // List not empty: find the point of insertion.
                TennisPlayerContainerNode currNode = this.root;
                TennisPlayerContainerNode prevNode = currNode.getLeft();
                int currNodeIndex = 0;
                while ((currNodeIndex < this.playerCount) &&
                        (currNode.getPlayer().compareTo(p) < 0)) {
                    prevNode = currNode;
                    currNode = currNode.getRight();
                    currNodeIndex++;
                }
                // Here, "currNode" and "prevNode" point at the 2 sides of the insertion point.
                // Perform insertion.
                TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(p);
                newNode.setRight(currNode);
                newNode.setLeft(prevNode);
                prevNode.setRight(newNode);
                currNode.setLeft(newNode);
                this.playerCount++;
                // Special case: insertion point at front.
                if (currNodeIndex == 0) {
                    this.root = newNode;
                }
            }
        } else {
            throw new TennisDatabaseException("Duplicate Player Detected. Insertion Failed");
        }
    }
     */

    //Adds match to proper player nodes
    @Override
    public void insertMatch(TennisMatch m) throws TennisDatabaseException {
        // Extract the ids of player1 and player2 of the input match "m"
        String idPlayer1 = m.getIdPlayer1();
        String idPlayer2 = m.getIdPlayer2();
        // Search the node associated with player1, by id.
        TennisPlayerContainerNode nodeP1 = this.root;
        boolean p1Found = false;
        int nodeP1Index = 0;
        while ((nodeP1Index < this.playerCount) && (nodeP1.getPlayer().getId().compareTo(idPlayer1) < 0)) {
            nodeP1 = nodeP1.getRight();
            nodeP1Index++;
        }
        // Check if we found the node of player1.
        if ((nodeP1 != null) && (nodeP1.getPlayer().getId().equals(idPlayer1))) {
            p1Found = true;
        }
        // Search the node associated with player2, by id.
        TennisPlayerContainerNode nodeP2 = this.root;
        boolean p2Found = false;
        int nodeP2Index = 0;
        while ((nodeP2Index < this.playerCount) && (nodeP2.getPlayer().getId().compareTo(idPlayer2) < 0)) {
            nodeP2 = nodeP2.getRight();
            nodeP2Index++;
        }
        // Check if we found the node of player2.
        if ((nodeP2 != null) && (nodeP2.getPlayer().getId().equals(idPlayer2))) {
            p2Found = true;
        }
        // ...
        if (p1Found && p2Found) {
            // Insert match "m" into the node of player1.
            nodeP1.insertMatch(m);
            // Insert match "m" into the node of player2.
            nodeP2.insertMatch(m);
        } else {
            throw new TennisDatabaseException("");
        }
    }

    @Override
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
        TennisPlayer[] output = new TennisPlayer[playerCount];
        TennisPlayerContainerNode node = root;

        output[0] = root.getPlayer();
        node = node.getRight();

        for (int i = 1; i < playerCount; i++) {
            output[i] = node.getPlayer();
            node = node.getRight();
        }

        return output;
    }

    @Override
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseException, TennisDatabaseRuntimeException {
        TennisPlayerContainerNode node = this.root;
        boolean idFound = false;
        int nodeIndex = 0;
        while ((nodeIndex < this.playerCount) && (node.getPlayer().getId().compareTo(playerId) < 0)) {
            node = node.getRight();
            nodeIndex++;
        }
        // Check if we found the node.
        if ((node != null) && (node.getPlayer().getId().equals(playerId))) {
            idFound = true;
        }

        if (idFound) {
            return node.getMatches();
        } else {
            throw new TennisDatabaseRuntimeException("Player could not be found.");
        }
    }

    //checks if inputted player already exists
    public boolean dupePlayerCheck(TennisPlayer inPlayer) {
        TennisPlayerContainerNode node = this.root;
        for (int i = 0; i < playerCount; i++) {
            if (node.getPlayer().getId().equals(inPlayer.getId())) {
                return true;
            }
        }
        return false;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public TennisPlayer getPlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode currNode = getPlayerNodeRec(this.root, playerId);
        if (currNode != null) {
            return new TennisPlayer(currNode.getPlayer());
        } else {
            throw new TennisDatabaseRuntimeException("Search Failed");
        }
    }

    private TennisPlayerContainerNode getPlayerNodeRec(TennisPlayerContainerNode currRoot, String playerId) {

        if (currRoot == null) {
            return null;
        } else {
            if (currRoot.getPlayer().getId().equals(playerId)) {
                return currRoot;
            } else if (currRoot.getPlayer().getId().compareTo(playerId) < 0) {
                return getPlayerNodeRec(currRoot.getRight(), playerId);
            } else {
                return getPlayerNodeRec(currRoot.getLeft(), playerId);
            }
        }
    }

    public void insertPlayer(TennisPlayer player) throws TennisDatabaseException {
        insertPlayerRec(this.root, player);
        this.playerCount++;
    }

    public TennisPlayerContainerNode insertPlayerRec(TennisPlayerContainerNode currRoot, TennisPlayer player) throws TennisDatabaseException {
        if (currRoot == null) {
            TennisPlayerContainerNode newNode = new TennisPlayerContainerNode(player);
            return newNode;
        } else {
            //BST not empty
            int compareResult = currRoot.getPlayer().getId().compareTo(player.getId());
            if (compareResult == 0) {
                throw new TennisDatabaseException("Error");
            } else if (compareResult < 0) {
                //insertPlayerRec(currRoot.getRight(), player);
                TennisPlayerContainerNode newRightChild = insertPlayerRec(currRoot.getRight(), player);
                currRoot.setLeft(newRightChild);
                return currRoot;
            } else {
                //insertPlayerRec(currRoot.getLeft(), player);
                TennisPlayerContainerNode newLeftChild = insertPlayerRec(currRoot.getLeft(), player);
                currRoot.setLeft(newLeftChild);
                return currRoot;
            }
        }
    }
}