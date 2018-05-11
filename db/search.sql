CREATE FUNCTION search (words varchar) RETURNS varchar AS $$
DECLARE
item1 item;
ids varchar;
word varchar;
ws varchar[];

BEGIN
ws=string_to_array(words, ' ');
for  item1 in select * from item loop
foreach word in ARRAY ws loop
if (array_length(regexp_matches(item1.name, word), 1)>0) 
 then ids=concat(ids, item1.id, ' '); exit;
 elseif (array_length(regexp_matches(item1.producer, word), 1)>0)
  then ids=concat(ids, item1.id, ' '); exit;
end if;
end loop;
end loop;
return ids; 
END;
$$ LANGUAGE plpgsql;