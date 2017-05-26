package anvlib.Classes.Base;

public class BaseCommonObject
{
    public int Id;
    public String Name;

    public BaseCommonObject()
    {
    }

    public BaseCommonObject(int id, String name)
    {
        Id = id;
        Name = name;        
    }

    public Object Clone()
    {
        BaseCommonObject obj = new BaseCommonObject(this.Id, this.Name);

        return obj;
    }
}
