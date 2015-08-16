
delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';

CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<text, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); 

Double count = (Double) state.get(date); 

 if (count == null) count = Double.parseDouble(amount);
 else 
 	count = count +  Double.parseDouble(amount); 
 state.put(date, count);
  return state; ' ;

CREATE OR REPLACE FUNCTION topFiveFinal ( state map<text, double> )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java AS '';

CREATE OR REPLACE AGGREGATE groupbyandsum(text, text) 
SFUNC state_groupbyandsum 
STYPE map<text, double> 
FINALFUNC topFiveFinal
INITCOND {};