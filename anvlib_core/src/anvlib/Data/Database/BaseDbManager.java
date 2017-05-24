package anvlib.Data.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

/* Этот класс нужен только для других базовых классов
так что используй базовые классы 
типа BaseMSSQLManager и BaseOracleManager*/
public abstract class BaseDbManager
{
    protected Connection _conn;
    protected Statement _stmt;
    protected CallableStatement _cstmt;
    protected ResultSet _rs;  
    protected boolean _connected = false; 
    /*protected DbParameter _param;
        protected DbDataAdapter _DA;
        protected DbDataReader _DR;        
        protected DataTable _dt;
        protected DataRow _row;
        protected DataColumn _column;
        protected DbTransaction _transaction;*/
    protected String _systables_prefix; //--префикс системных таблиц
    protected String _parameters_prefix;//--префикс для параметров, в MySQL и MSSQL отличаются, возможно в оракле тоже
    /*protected String _TransactionStartCmd;//--команда начала транзакции
    protected String _TransactionEndCmd;//--комнад окончания транзакции*/
    protected String _connectionString;//--строка соединения
    protected String _connectionStringTemplate = "jdbc:%s://%s:%d/%s";
    protected String _DBDriver = "nulldriver";
    protected int _defaultPort = 0;
    protected String _owner;//--владелец объекта
    protected char _open_bracket;//--для избежания проблем с системными полями
    protected char _close_bracket;//--для избежания проблем с системными полями

    protected int _last_error = 0;

    //--Может использоваться не только для ошибок! Но и когда надо напечатать какое-то сообщение из методов менеджера
    /*protected IPrintMessageSystem MessagePrinter;
        protected ErrorMessageManager ErrorsManager = new ErrorMessageManager();*/
    //--Конструктор
    public BaseDbManager()
    {
    }
    
    // Состоянние "Соединения с сервером"        
    //public boolean Connected;

    public boolean Connected()
    {
        try
        {
            if (!_conn.isClosed() || _conn != null)
            {
                return true;
            }
        } catch (SQLException e)
        {
            return false;
        }
        return false;
    }
    
    // Событие возникающее, при ошибке запуска команды, ридера или датаадаптера
    // К сожалениею, пока вызывается только в потомках!       
    //public event EventHandler ExecutionException;
        
    // Владелец схемы данных    
    public String SchemaOwner;
        
    // Установить соединение с сервером                
    public void Connect(String ConnectionString)
    {
        if (_conn == null)
        {     
            _connectionString = ConnectionString;
            try
            {
                _conn = DriverManager.getConnection(_connectionString);
            } catch (SQLException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    
    // Установить соединение с сервером                
    public void Connect(String Server, String Login, String Password, String Database)
    {
        if (_conn == null)
        {     
            _connectionString = GetFullConnectionString(Server, Login, Password, Database);
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
    
    /*protected String GetShortConnectionString(String Server, String Database)
    {
        String res;

        res = String.format(_connectionStringTemplate, _DBDriver, Server, _defaultPort, Database);

        return res;
    }*/
    
    protected String GetFullConnectionString(String Server, String Login, String Password, String Database)
    {
        String res;
        res = String.format(_connectionStringTemplate, _DBDriver, Server, _defaultPort, Login, Password, Database);
        
        return res;
    }
    
    // Код ошибки выполнения запроса. Если значнеие 0 - значит ошибки нет.    
    public int LastError;
    
    // Разъединить соединение с сервером
    public void Disconnect()
    {
        if (Connected())
        {
            try
            {
                _conn.close();
            } catch (SQLException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    
    // Переподключение к серверу
    public void Reconnect()
    {
        if (_conn != null)
        {
            if (Connected())            
                Disconnect();
            _conn = null;

            Connect(_connectionString);
        }
    }
               
    protected Statement ExecuteNonQuery(String CmdText)
    {
        try
        {
            _stmt = _conn.createStatement();
            _stmt.execute(CmdText);                    
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        
        return _stmt;
    }
    
    protected ResultSet ExecuteReader(String CmdText)
    {
        try
        {
            _stmt = _conn.createStatement();
            _rs = _stmt.executeQuery(CmdText);                    
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        
        return _rs;
    }
    
    // Insert, Update, Delete
    protected int ExecuteUpdateQuery(String CmdText)
    {
        int res = 0;
        try
        {
            _stmt = _conn.createStatement();
            res = _stmt.executeUpdate(CmdText);
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return res;
    }
    
    protected CallableStatement CreateCallableStatement(String ProcedureName, int ParsCount)
    {
        try
        {
            String pars = "";
            String preparedCall = "{call %s(%s)}";
            for (int i = 0; i < ParsCount; i++)
                pars += "?,";            

            if (ParsCount > 0)            
                pars = pars.substring(0, pars.length() - 1);            

            _cstmt = _conn.prepareCall(String.format(preparedCall, ProcedureName, pars));
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }

        return _cstmt;
    }
    
    public void ChangeDefaultServerPort(int Port)
    {
        _defaultPort = Port;
    }
}
