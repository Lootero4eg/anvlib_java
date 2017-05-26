package anvlib.Data.Database;

public class BasePostgresSQLManager extends BaseDbManager
{
    public BasePostgresSQLManager()
    {
        _DBDriver = "postgresql";  
        _defaultPort = 5432;
        _connectionStringTemplate = "jdbc:%s://%s:%d/%s?user=%s&password=%s";
    }
    
    @Override
    protected String GetFullConnectionString(String Server, String Login, String Password, String Database)
    {                
        String res;        
        res = String.format(_connectionStringTemplate, _DBDriver, Server, _defaultPort, Database, Login, Password);

        return res;
    }
}
