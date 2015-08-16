
delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';

CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<timestamp, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<timestamp, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); 
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");

java.util.Date date1 = sdf.parse(date);

Double count = (Double) state.get(date1); 
 if (count == null) count = Double.parseDouble(amount);
  else count = count +  Double.parseDouble(amount); 
  state.put(date1, count);
   return state; ' ;

CREATE OR REPLACE FUNCTION topFiveFinal ( state map<timestamp, double> )
CALLED ON NULL INPUT
RETURNS map<timestamp, double>
LANGUAGE java AS ' return state;';

CREATE OR REPLACE AGGREGATE groupbyandsum(text, text) 
SFUNC state_groupbyandsum 
STYPE map<timestamp, double> 
FINALFUNC topFiveFinal
INITCOND {};























delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';

CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<timestamp, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<timestamp, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); 
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");

java.util.Date date1 = sdf.parse(date);

Double count = (Double) state.get(date1);  if (count == null) count = Double.parseDouble(amount); else count = count +  Double.parseDouble(amount); state.put(date1, count); return state; ' ;

CREATE OR REPLACE FUNCTION topFiveFinal ( state map<timestamp, double> )
CALLED ON NULL INPUT
RETURNS map<timestamp, double>
LANGUAGE java AS ' return state;';

CREATE OR REPLACE AGGREGATE groupbyandsum(text, text) 
SFUNC state_groupbyandsum 
STYPE map<timestamp, double> 
FINALFUNC topFiveFinal
INITCOND {};






























delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';

CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<text, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); Double count = (Double) state.get(date);  if (count == null) count = Double.parseDouble(amount); else count = count +  Double.parseDouble(amount); state.put(date, count); return state; ' ;

CREATE OR REPLACE FUNCTION topFiveFinal ( state map<text, double> )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java AS '

java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");

java.util.Date[] arrayOfDates = new java.util.Date[dates.length];
int index = 0;
    

for( Object key : state.keySet()){
	arrayOfDates[index] = sdf.parse(key.toString());
	index++;
}

Arrays.sort(arrayOfDates);

if(state.size()>5){
	for(index=arrayOfDates.length-1;index>=5;index--)
	{
		state.remove(sdf.format(arrayOfDates[index]));
	}
}

return state;';

CREATE OR REPLACE AGGREGATE groupbyandsum(text, text) 
SFUNC state_groupbyandsum 
STYPE map<text, double> 
FINALFUNC topFiveFinal
INITCOND {};









CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<varint, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<varint, double>
LANGUAGE java 
AS 'java.text.DateFormat df3 = new java.text.SimpleDateFormat("MM-dd-yyyy"); java.util.Date date = (df3.parse(datetime.substring(0,10))).Double; getTim count = (Double) state.get(date);  if (count == null) count = Double.parseDouble(amount); else count = count +  Double.parseDouble(amount); state.put(date, count); return state; ' ;







String date = datetime.substring(0,10);

Double count = (Double) state.get(date);
if (count == null)
   count = Double.parseDouble(amount);
else count = count +  Double.parseDouble(amount); 
     Integer size = state.size();  
     if(size<=4) {
     	state.put(date, count);
     }else{
     	if(state.containsKey(date)) state.put(date, count);
     }
return state;














Double count = (Double) state.get(date);
  if (count == null)
     count = Double.parseDouble(amount);
  else count = count +  Double.parseDouble(amount); 
       Integer size = state.size();  
       /*if(size<=4) {
        state.put(date, count);
       }else{
        if(state.containsKey(date)) state.put(date, count);
       }*/
       
       int dd = Integer.parseInt(date.substring(0,2));
    int mm = Integer.parseInt(date.substring(3,5));
    int yy=Integer.parseInt(date.substring(6,10));

    for(Object key : state.keySet())
    {
     String key1 = (String) key;

     int dd1 = Integer.parseInt(key1.substring(0,2));
     int mm1 = Integer.parseInt(key1.substring(3,5));
     int yy1=Integer.parseInt(key1.substring(6,10));



     if(yy>=yy1 && state.size()==5)
     {
      if(yy>=yy1 && mm >= mm1 && state.size()==5)
      {
       if(yy>=yy1 && mm >= mm1 && dd >= dd1 && state.size()==5)
       {
        state.remove(key1);
        state.put(date, amount);
       }
       else{
        state.put(date, amount);
       }
      }
      else{
       state.put(date, amount);
      }

     }
     else{
      state.put(date, amount);
     }
       
       
  return state;