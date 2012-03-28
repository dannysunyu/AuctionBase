select count(distinct SellerID)
from Items, AuctionUser
where Items.SellerID = AuctionUser.UserID 
	and Rating > 1000; 