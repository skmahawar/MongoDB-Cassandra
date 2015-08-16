
delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';

CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<text, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10);
  Double count = Double.parseDouble((String)state.get(date));
  if (count == null)
     count = Double.parseDouble(amount);
  else count = count +  Double.parseDouble(amount); 

    int dd = Integer.parseInt(datetime.substring(0,2));
    int mm = Integer.parseInt(datetime.substring(3,5));
    int yy=Integer.parseInt(datetime.substring(6,10));

    if(state.isEmpty()){
      state.put(date, count);
    }else{
      if(state.size() < 5){
        state.put(date, count);
      }else{
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
              state.put(date, count);
              break;
             }
            }
           }
         }
        }
    }
    
  return state;' ;


CREATE OR REPLACE AGGREGATE groupbyandsum(text, text) 
SFUNC state_groupbyandsum
STYPE map<text, double>
INITCOND {};

select groupbyandsum(timestamp, value) from warehouse;


"{'20-06-2014': 39.9117775, 
  '22-06-2014': 41.43167877,
  '23-06-2014': 15.16696072, 
  '25-06-2014': 14.56181049, 
  '21-06-2014': 31.72406197}"

  "{'20-06-2014': 39.9117775, 
  '22-06-2014': 41.43167877, 
  '23-06-2014': 15.16696072, 
  '25-06-2014': 45.88895321, 
  '21-06-2014': 31.72406197}"

