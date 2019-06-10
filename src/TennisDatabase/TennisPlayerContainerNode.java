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
