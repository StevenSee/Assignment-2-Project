Assignment 2
	by Steven Causley

Instructions:
	1. Create an input file with the following parameters. One entry per line, all players first, then all matches.
	    Note: The code automatically turns lowercase letters into capital letters.

		Player entry: 
			PLAYER/<PLAYERID>/<FIRST NAME>/<LAST NAME>/<birthyear yyyy>/<COUNTRY>
		Match entry:
			MATCH/<player1 id>/<player2 id>/<date yyyymmdd>/<TOURNAMENT>/<scores, separated by commas e.g. 6-3,6-7,6-21>
		
	2. Load the file by typing the file name into the text field for "Load From File" and click the button
	3. Export the file by typing the file name into the text field for "Save to File" and click the button.
	4. Click the "Reset Databse" button to wipe the database from the application. 
	5. Enter a player id under "Delete Player" and click the button to delete that player.
	6. On the ride side, fill the parameters for either a match or player and click the respective button to insert a match or player.
	    6a. Inserting a Match
	        1. Player 1 and 2 Id: An alphanumeric representation of the players
	        2. Year, Month, and Day: Numeric entries for these items
	        3. Tournament: Alphanumeric name for the tournament
	        4. Score: Comma separated score entries. Example: 4-6,4-6
        6b. Inserting a PLayer
            1. Player Id: An alphanumeric representation of the player
            2. First and Last Name: Names of the player
            3. Birth Year: Numeric year the player was born
            4. Country: Player's country of origin
	
The reasons for choosing an ArrayList over a LinkedList are as follows:
	-Superior performance with getters and setters
	-Better at dealing with initial high capacity
	-Faster at performing binary searches
	-Smaller memory requirement
	In general, ArrayList and LinkedList had very little differences in the context of this project, but the reasons listed above come from research that explains ArrayList's advantages.

Design Choices:
    -Called playerTableView.refresh() to refresh the table view because wins and losses were originally not being updated.
	
