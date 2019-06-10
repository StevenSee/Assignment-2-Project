// Steven Causley
// CS-102, Spring 2019
// Assignment 2

package TennisDatabase;

class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface
{

    private TennisPlayerContainerNode right;
    private TennisPlayerContainerNode left;
    private TennisPlayer player;
    private SortedLinkedList<TennisMatch> list; // List of matches of this player.

    public TennisPlayerContainerNode(TennisPlayer inputPlayer)
    {
        this.right = null;
        this.left = null;
        this.player = inputPlayer;
        this.list = new SortedLinkedList<TennisMatch>();
    }

    public TennisPlayer getPlayer()
    {
        return this.player;
    }
    public void setPlayer(TennisPlayer player){
        this.player = player;
    }
    public TennisPlayerContainerNode getLeft()
    {
        return this.left;
    }
    public TennisPlayerContainerNode getRight()
    {
        return this.right;
    }

    public void setLeft(TennisPlayerContainerNode p)
    {
        this.left = p;
    }
    public void setRight(TennisPlayerContainerNode n)
    {
        this.right = n;
    }

    // Desc.: Insert a TennisMatch object (reference) into this node.
    // Input: A TennisMatch object (reference).
    // Output: Throws a checked (critical) exception if match cannot be inserted in this player list.
    @Override
    public void insertMatch(TennisMatch m) throws TennisDatabaseException
    {
        try
        {
            list.insert(m);
        } catch (Exception e)
        {
            throw new TennisDatabaseException("");
        }
    }

    // Desc.: Returns all matches of this player arranged in the output array (sorted by date, most recent first).
    // Output: Throws an unchecked (non-critical) exception if there are no matches for this player.
    @Override
    public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException
    {
        TennisMatch[] a = new TennisMatch[list.size()];//list.get()
        for(int i = 0; i < list.size();i++)
        {
            a[i] = list.get(i);
        }


        TennisMatch[] b = new TennisMatch[a.length];

        for (int i = 0; i < a.length; i++)
        {
            b[i] = new TennisMatch(a[i]);
        }
        return b;
    }

    public SortedLinkedList<TennisMatch> getMatchList(){
        return this.list;
    }

    public void setMatchList(SortedLinkedList<TennisMatch> matches){
        this.list = matches;
    }
}
