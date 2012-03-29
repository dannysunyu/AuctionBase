select count(*)
from (select ItemID
	  from Categories
	  group by ItemID
	  Having count(*) = 4);