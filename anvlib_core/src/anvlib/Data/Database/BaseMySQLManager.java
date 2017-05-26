package anvlib.Data.Database;

public class BaseMySQLManager extends BaseDbManager
{
    public boolean connectViaSSL = false;    
    
    public BaseMySQLManager()
    {
        _DBDriver = "mysql";  
        _defaultPort = 3306;
        CheckConnectionType();        
    }
    
    @Override
    protected String GetFullConnectionString(String Server, String Login, String Password, String Database)
    {             
        CheckConnectionType();
        
        String res;        
        res = String.format(_connectionStringTemplate, _DBDriver, Server, _defaultPort, Database, Login, Password);

        return res;
    }
    
    private void CheckConnectionType()
    {
        if(connectViaSSL)
            _connectionStringTemplate = "jdbc:%s://%s:%d/%s?user=%s&password=%s";
        else
            _connectionStringTemplate = "jdbc:%s://%s:%d/%s?user=%s&password=%s&useSSL=false";
    }
}
