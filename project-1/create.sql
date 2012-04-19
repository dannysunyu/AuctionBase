create table AuctionUser (
    UserID int PRIMARY KEY, 
    Rating int, 
    Location text, 
    Country text
);

create table Items (
    ItemID int PRIMARY KEY, 
    Name text, 
    Currently real,
    Buy_Price real, 
    First_Bid real,
    Started text, 
    Ends text,
    SellerID int, 
    Description text
);

create table Categories (
    ItemID int, 
    CategoryName text
);

create table Bid (
    ItemID int, 
    BidderID int, 
    Time text, 
    Amount real, 
    PRIMARY KEY (ItemID, BidderID, Time)
);