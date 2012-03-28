create table AuctionUser (UserID int PRIMARY KEY, Rating int, Location text, Country text);

create table Items (ItemID int PRIMARY KEY, Name text, Buy_Price real, Started text, Ends text,
	Seller int, Description text);

create table Categories (ItemID int, CategoryName text);

create table Bid (ItemID int, Bidder int, Time text, Amount real, PRIMARY KEY (ItemID, Bidder,
	Time));