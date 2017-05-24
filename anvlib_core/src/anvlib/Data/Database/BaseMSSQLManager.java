package anvlib.Data.Database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseMSSQLManager extends BaseDbManager
{
    public BaseMSSQLManager()
    {
        _DBDriver = "sqlserver";
        _defaultPort = 1433;               
        _connectionStringTemplate = "jdbc:%s://%s:%d;username=%s;password=%s;databaseName=%s;MultipleActiveResultSets=true;";        
    }
    
    public void Connect(String Server, String Login, String Password, String Database, String ApplicationName)
    {
        if (_conn == null)
        {     
            _connectionString = GetFullConnectionString(Server, Login, Password, Database) + "applicationName=" + ApplicationName;
            _connected = true;
            try
            {                
                _conn = DriverManager.getConnection(_connectionString);
            } catch (SQLException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    
    // Подключиться под пользователем Windows
    public void ConnectAsWindowsUser(String srvname, String database)
    {
        Connect(GetWindowsUserConnection(srvname, database, null));
    }
    
    public void ConnectAsWindowsUser(String srvname, String database, String ApplicationName)
    {
        Connect(GetWindowsUserConnection(srvname, database, ApplicationName));
    }
        
    protected String GetWindowsUserConnection(String Server, String Database, String AppName)
    {
        String res;
        String template = "jdbc:%s://%s:%d;integratedSecurity=true;databaseName=%s;MultipleActiveResultSets=true;";
        if (AppName == null || AppName.isEmpty())
            res = String.format(template, _DBDriver, Server, _defaultPort, Database);
        else
            res = String.format(template + "applicationName=" + AppName, _DBDriver, Server, _defaultPort, Database);

        return res;
    }
}
