package anvlib.Data.Database;

public class BasePostgresSQLManager extends BaseDbManager
{
    public BasePostgresSQLManager()
    {
        _DBDriver = "postgres";  
        _defaultPort = 5432;
        _connectionStringTemplate = "jdbc:%s://%s/%d";
    }
}
