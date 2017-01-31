package serverside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ERIDataBase {
    private Connection connection;
    private Statement selectStmt;

    public ERIDataBase(String server,String database,String user,String password){
        selectStmt = null;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" + user + ";password=" + password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetActivationCodeData(int idforuser){
        Statement updateStmt = null;

        try{
            updateStmt = connection.createStatement();
            updateStmt.execute("update [eri40-dev].[dbo].[LoginActivations] set [IsActivated]=0 where [Id]=" + Integer.toString(idforuser));
            updateStmt.execute("update [eri40-dev].[dbo].[LoginActivations] set [OperationDate]=NULL where [Id]=" + Integer.toString(idforuser));
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                updateStmt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserActivationCode(int userid){
        String activationCode = null;
        try
        {
            selectStmt = connection.createStatement();
            ResultSet rs = selectStmt.executeQuery("select [ActivationCode] from [eri40-dev].[dbo].[LoginActivations] where [IsActivated]=0 and [Id]=" + Integer.toString(userid));
            while(rs.next()){
                activationCode = rs.getString(1);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                selectStmt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return activationCode;
    }

    protected void finalize(){
        if (connection != null){
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserLoginName(int userid){
        String loginName = null;
        try
        {
            selectStmt = connection.createStatement();
            ResultSet rs = selectStmt.executeQuery("select [LoginName] from [eri40-dev].[dbo].[Logins] where [Id]=" + Integer.toString(userid));
            while(rs.next()){
                loginName = rs.getString(1);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                selectStmt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return loginName;
    }

    public String getUserDomainName(int userid){
        String domainName = null;
        try
        {
            selectStmt = connection.createStatement();
            ResultSet rs = selectStmt.executeQuery("select [Name] from [eri40-dev].[dbo].[Domains] dm join [eri40-dev].[dbo].[Logins] lg on lg.[Domain_Id]=dm.[Id] where lg.[Id]=" + Integer.toString(userid));
            while(rs.next()){
                domainName = rs.getString(1);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                selectStmt.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return domainName;
    }
}
