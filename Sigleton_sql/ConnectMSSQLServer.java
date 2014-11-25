package Sigleton_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectMSSQLServer {

    ArrayList<estudiante> estudiantes = new ArrayList<>();

    Statement statement;

    public void dbConnect(String db_connect_string,
            String db_userid,
            String db_password) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(db_connect_string,
                    db_userid, db_password);
            System.out.println("connected");
            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<estudiante> getEstudiantes() throws SQLException {
        String queryString
                = "SELECT [Nombre]\n"
                + "      ,[Apellido]\n"
                + "      ,[Carne]\n"
                + "      ,[NotaPromedio]\n"
                + "  FROM [dbo].[Lista]\n"
                + "";
        ResultSet rs = statement.executeQuery(queryString);
        while (rs.next()) {
            estudiantes.add(new estudiante(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return estudiantes;

    }


    private ConnectMSSQLServer() {
        dbConnect("jdbc:sqlserver://poosql.cloudapp.net:57500", "test3",
                "Admin123");
    }
    private static ConnectMSSQLServer instance = null;
    
    public static ConnectMSSQLServer getInstance() {
        if (instance == null) {
            instance = new ConnectMSSQLServer();
        }
        return instance;
    }

}
