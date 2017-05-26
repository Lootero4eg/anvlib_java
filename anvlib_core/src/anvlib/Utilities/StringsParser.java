package anvlib.Utilities;

import java.util.HashMap;

public final class StringsParser
    {
        public static HashMap<String, String> ParseGroupedString(String groups_delimeter, String params_delimeter, String StringForParsing)
        {            
            HashMap<String, String> res = new HashMap();

            String[] first_pass = StringForParsing.split(groups_delimeter);
            String[] second_pass;
            if (first_pass.length > 0)
            {
                for (String first_val : first_pass)                
                {
                    second_pass = first_val.split(params_delimeter);
                    if (second_pass.length == 2)
                        res.put(second_pass[0], second_pass[1]);                    
                }
            }

            return res;
        }
    }
