select count(ItemID)
from Category
group by ItemID
having count(*) = 4;