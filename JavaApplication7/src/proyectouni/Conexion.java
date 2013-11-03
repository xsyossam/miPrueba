/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectouni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author YOSS
 */
public class Conexion {
    private String bd = "db_programacion";
    private String login = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost/"+bd;
    private String driver = "com.mysql.jdbc.Driver";
    private Connection conn = null;

    public Conexion() {
        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(url,login,password);
            if(conn!=null) {
                System.out.println("CONECCION A BASE DE DATOS EXITOSA");
            }             
        } catch (ClassNotFoundException e) {
            System.out.println(e);   
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public Connection getConnection(){
            return this.conn;
    } 
    
    public Object[][] select(String table, String fields, String where){
        int registros =0;
        String Colname[] = fields.split((","));
        //Consultas SQL
        String q0= "SELECT"+fields+"FROM"+table;
        String q1= "SELECT count(*) as total FROM"+table;
        if (where!=null) {
            q0+="WHERE"+where;
            q1+="WHERE"+where;
        }
        try {   
            PreparedStatement pstm = conn.prepareStatement(q1);
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();           
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        //se crea uan matriz con tantas filas como yo necesite 
        
        
        Object data[][] = new String[registros][fields.split(",").length];
               
        try {
            PreparedStatement pstm;
            pstm = conn.prepareStatement(q0);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {            
                for (int j = 0; j < fields.split(",").length-1; j++) {
                    data[i][j] = res.getString(Colname[j].trim());
                }
                i++;
                res.close();
            }            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return  data;        
    }
}
        
        
