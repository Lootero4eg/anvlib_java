package anvlib.Data.Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseOracleManager extends BaseDbManager
{
    public boolean connectViaSID = false;
    
    public BaseOracleManager()
    {        
        _DBDriver = "oracle";  
        _defaultPort = 1521;
        CheckConnectionType();
    }                            
    
    @Override
    public void Connect(String Server, String Login, String Password, String ServiceNameOrSID)
    {
        if (_conn == null)
        {     
            _connectionString = GetFullConnectionString(Server, Login, Password, ServiceNameOrSID);
            _connected = true;
            try
            {                
                _conn = DriverManager.getConnection(_connectionString);
            } catch (SQLException e)
            {
                if (MessagePrinter != null)
                    MessagePrinter.PrintMessage(e.getMessage(), "Error", 1, 1);
            }
        }
    }
    
    @Override
    public String GetFullConnectionString(String Server, String Login, String Password, String ServiceNameOrSID)
    {                
        String res;
        CheckConnectionType();
        res = String.format(_connectionStringTemplate, _DBDriver, Login, Password, Server, _defaultPort, ServiceNameOrSID);

        return res;
    }
    
    private void CheckConnectionType()
    {
        if(!connectViaSID)
            _connectionStringTemplate = "jdbc:%s:thin:%s/%s@%s:%d:%s";
        else
            _connectionStringTemplate = "jdbc:%s:thin:%s/%s@%s:%d/%s";
    }
}
