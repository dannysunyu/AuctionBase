select count(distinct CategoryName)
from Categories, Bid
where Categories.ItemID = Bid.ItemID and Amount > 100;