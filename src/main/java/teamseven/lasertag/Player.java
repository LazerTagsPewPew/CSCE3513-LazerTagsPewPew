package teamseven.lasertag;

public class Player 
{
    int id_num;
    String first_name;
    String last_name;
    String codename;
    Player(int id, String fname, String lname, String sname)
    {
        id_num = id;
        first_name = fname;
        last_name = lname;
        codename = sname;
    }
}
