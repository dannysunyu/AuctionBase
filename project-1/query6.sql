select count(distinct SellerID)
from Items, Bid
where SellerID = BidderID;