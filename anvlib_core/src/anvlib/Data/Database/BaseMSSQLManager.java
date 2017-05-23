/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anvlib.Data.Database;

/**
 *
 * @author crash
 */
public class BaseMSSQLManager extends BaseDbManager
{
    public BaseMSSQLManager()
    {
        _DBDriver = "sqlserver";
        //_connectionStringTemplate = "jdbc:%s://%s:1433;databaseName=%s";        
        _connectionStringTemplate = "jdbc:%s://%s:1433;username=%s;password=%s;databaseName=%s";        
    }
}
