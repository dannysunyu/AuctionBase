.separator <>
.import user.dat AuctionUser

.import items.dat Items
update Items set Buy_Price = null where Buy_Price = 'NULL';

.import bid.dat Bid

.import categories.dat Categories 