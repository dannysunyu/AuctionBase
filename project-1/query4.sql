select ItemID
from Bid
where Amount in 
	(select max(Amount)
     from Bid);
