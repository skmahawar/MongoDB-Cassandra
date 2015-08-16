delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';


CREATE OR REPLACE FUNCTION state_groupbysum( state map<text, int>, datetime timestamp, amount int )
CALLED ON NULL INPUT
RETURNS map<text, int>
LANGUAGE java 
AS 'String date = (datetime.getYear()+1900)+"-"+datetime.getMonth()+"-"+datetime.getDate(); Integer count = (Integer) state.get(date);  if (count == null) count = amount; else count = count + amount; if(state.size() <= 5 ) state.put(date, count); return state; ' ;


CREATE OR REPLACE AGGREGATE groupbysum(timestamp, int) 
SFUNC state_groupbysum 
STYPE map<text, int> 
INITCOND {};



CREATE OR REPLACE FUNCTION state_groupbysum( state map<text, int>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, int>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); Integer count = (Integer) state.get(date);  if (count == null) count = Integer.parseInt(amount); else count = count +  Integer.parseInt(amount); state.put(date, count); return state;' ;


CREATE OR REPLACE AGGREGATE groupbysum(text, text) 
SFUNC state_groupbysum 
STYPE map<text, int> 
INITCOND {};-


select groupbysum(created, value) from warehouse limit 5;


CREATE OR REPLACE FUNCTION state_groupbyandsum2( state map<text, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); Double count = (Double) state.get(date);  if (count == null) count = Double.parseDouble(amount); else count = count +  Double.parseDouble(amount); state.put(date, count); return state; ' ;

CREATE OR REPLACE FUNCTION topFiveFinal ( state map<text, double> )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java AS 'while(true) { if(state.size() <= 5) return state; else state.remove(state.getString(0)); }; return state;';

'  for( Object key : state.keySet()) { if(state.size() <= 5) return state; else state.remove(key); };';

'for (Map.Entry<String, String> entry : state.entrySet()){ if(state.size()<=5) return state else state.remove(entry.getKey());} return state;'


CREATE OR REPLACE AGGREGATE groupbyandsum2(text, text) 
SFUNC state_groupbyandsum2 
STYPE map<text, double> 
FINALFUNC topFiveFinal
INITCOND {};

select groupbyandsum2(timestamp, value) from warehouse limit 5;
select groupbyandsum2(timestamp, value) from warehouse;


update warehouse set timestamp='24-06-2014 17:00' where customer='XYZ';


INSERT INTO users (id, name, email, amount, created, modified) VALUES(5b6962dd-3f90-4c93-8f61-eabfa4a803e2,'Suresh','suresh.mahawar@technocube.in',10, '2015-08-01 00:05+0000','2015-08-01 00:05+0000');
INSERT INTO users (id, name, email, amount, created, modified) VALUES(5b6962dd-3f90-4c93-8f61-eabfa4a803e3,'Ashish','ashish.mahawar@technocube.in',11, '2015-08-01 09:06+0000','2015-08-01 09:06+0000');
INSERT INTO users (id, name, email, amount, created, modified) VALUES(5b6962dd-3f90-4c93-8f61-eabfa4a803e4,'Himanshu','himanshu.mahawar@technocube.in',14, '2015-08-01 07:05+0000','2015-08-01 07:05+0000');
INSERT INTO users (id, name, email, amount, created, modified) VALUES(5b6962dd-3f90-4c93-8f61-eabfa4a803e5,'Ankur','ankur.mahawar@technocube.in',12, '2015-08-02 00:05+0000','2015-08-02 00:05+0000');
INSERT INTO users (id, name, email, amount, created, modified) VALUES(5b6962dd-3f90-4c93-8f61-eabfa4a803e6,'Nishant','nishant.mahawar@technocube.in',13, '2015-08-02 05:05+0000','2015-08-02 05:05+0000');





CREATE OR REPLACE FUNCTION state_groupbyandsum( state map<text, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java 
AS 'String date = datetime.substring(0,10); Double count = (Double) state.get(date);  if (count == null) count = Double.parseDouble(amount); else count = count +  Double.parseDouble(amount); if(state.size()) state.put(date, count); return state;' ;


CREATE OR REPLACE AGGREGATE groupbyandsum(text, text) 
SFUNC state_groupbyandsum
STYPE map<text, double>
INITCOND {};

select groupbyandsum(timestamp, value) from warehouse limit 5;
select groupbyandsum(timestamp, value) from warehouse;









CREATE KEYSPACE chris WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE warehouse (
	customer text primary key ,
	site text,
	asset text,
	timestamp text,
	controller text,
	frequency_of_collection text,
	meter_type text,
	unit_of_collection text,
	value text
);


copy warehouse (customer,site,asset,timestamp,controller,frequency_of_collection,meter_type,unit_of_collection,value) from 'exportfile-3.csv' with HEADER = TRUE;

copy warehouse from 'exportfile-3.csv' with HEADER = TRUE;





insert into warehouse(customer,asset,controller,frequency_of_collection,meter_type,timestamp,site,unit_of_collection,value) values('XYZ','Energy Meter 105','DC 1','98','MT RACK KWH', 'Faulkham Mills 1105','31-12-2014 24:00','kWh','8.9');






//begin test
CREATE OR REPLACE FUNCTION avgFinal ( state tuple<int,bigint> ) 
CALLED ON NULL INPUT 
RETURNS double 
LANGUAGE java AS 
  'double r = 0; if (state.getInt(0) == 0) return null; r = state.getLong(1); r/= state.getInt(0); return Double.valueOf(r);';

CREATE OR REPLACE AGGREGATE IF NOT EXISTS average ( int )
SFUNC avgState 
STYPE map<text,double> 
FINALFUNC top5Record 
INITCOND (0,0);  

//end test


"{'20-06-2014': 39.9117775, 
  '22-06-2014': 41.43167877, 
  '23-06-2014': 15.16696072, 
  '25-06-2014': 14.56181049, 
  '21-06-2014': 31.72406197, 
  '24-06-2014': 42.14431}"
