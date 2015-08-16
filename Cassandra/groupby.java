  String date = datetime.substring(0,10);
  Double count = Double.parseDouble((String)state.get(date));
  if (count == null)
     count = Double.parseDouble(amount);
  else count = count +  Double.parseDouble(amount); 

    int dd = Integer.parseInt(datetime.substring(0,2));
    int mm = Integer.parseInt(datetime.substring(3,5));
    int yy = Integer.parseInt(datetime.substring(6,10));

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
    
  return state;