delete from system.schema_aggregates where keyspace_name='chris';
delete from system.schema_functions where keyspace_name='chris';

CREATE FUNCTION state_group_and_total( state map<text, double>, datetime text, amount text )
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java AS '
String date = datetime.substring(0,10); 
Double count = (Double) state.get(date);
  if (count == null) count = Double.parseDouble(amount);
   else count = count +  Double.parseDouble(amount); 
   state.put(date, count); return state;
';

CREATE FUNCTION topFinal (state map<text, double>)
CALLED ON NULL INPUT
RETURNS map<text, double>
LANGUAGE java AS '
    java.util.Map<String, Double> inMap = new java.util.HashMap<String, Double>(),
                                  outMap = new java.util.HashMap<String, Double>();

    inMap.putAll(state);

    int topN = 5;
    for (int i = 1; i <= topN; i++) {
        double maxVal = -1;
        String moveKey = null;
        for (java.util.Map.Entry<String, Double> entry : inMap.entrySet()) {

            if (entry.getValue() > maxVal) {
                maxVal = entry.getValue();
                moveKey = entry.getKey();
            }
        }
        if (moveKey != null) {
            outMap.put(moveKey, maxVal);
            inMap.remove(moveKey);
        }
    }

    return outMap;
';


CREATE OR REPLACE AGGREGATE group_and_total(text, text) 
     SFUNC state_group_and_total 
     STYPE map<text, double> 
     FINALFUNC topFinal
     INITCOND {};

select group_and_total(timestamp, value) from warehouse;