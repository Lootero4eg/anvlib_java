package anvlib.Classes.PrintMessageSystems;

public class ConsolePrintMessageSystem implements anvlib.Interfaces.IPrintMessageSystem
{
    @Override
    public void PrintMessage(String Msg)
    {
        System.err.println(Msg); 
    }
    
    @Override
    public void PrintMessage(String Msg, String WindowTitle, int Buttons, int Icon)
    {
        PrintMessage(Msg);
    }
}
