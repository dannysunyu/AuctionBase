select ItemID
from Items
where Currently 
	in (select max(Currently) 
	    from Items);
