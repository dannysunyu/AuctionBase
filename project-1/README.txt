1. List your relations. Please specify all keys that hold on each relation. You need not specify 
attribute types at this stage.

AuctionUsers(UserID, Rating, Location, Country) 
UserID is the key.

Items(ItemID, Name, Buy_Price, Started, Ends, SellerID, Description) 
ItemID is the key.

Categories(ItemID, CategoryName) 

Bids(ItemID, BidderID, Time, Amount)
ItemID, BidderID, Time is the key.


2. List all completely nontrivial functional dependencies that hold on each relation, excluding 
those that effectively specify keys.

None

3. Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and 
start over, or explain why you feel it is advantageous to use non-BCNF relations.

Yes, they are.


4. List all nontrivial multivalued dependencies that hold on each relation, excluding those that are
also functional dependencies.

None.

5. Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start 
over, or explain why you feel it is advantageous to use non-4NF relations.

Yes, they are.







