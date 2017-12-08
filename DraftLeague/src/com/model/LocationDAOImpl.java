package com.model;

import com.common.CountryDTO;
import com.common.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LocationDAOImpl implements LocationDAO {
    
    @Override
    public List<CountryDTO> getCountryList(boolean includeAll) {
        Connection con = null;
        List<CountryDTO> list = new ArrayList<>();
        try {
            con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from country "+(!includeAll ? " where disabled = 0 " : "" )+" order by name");
            while(rs.next()) {
                CountryDTO c = new CountryDTO();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setDisabled(rs.getBoolean(3));
                list.add(c);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException x) {
                }
            }
        }
        return list;
    }
    
    @Override
    public List<State> getStateList(int country, boolean includeAll) {
        Connection con = null;
        List<State> list = new ArrayList<>();
        try {
            con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select s.id, s.name, s.disabled, c.name from state s, country c where c.id = s.country_id and c.id="+country+(!includeAll ? " and s.disabled = 0 " : "" )+" order by s.name");
            while(rs.next()) {
                State s = new State();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setDisabled(rs.getBoolean(3));
                s.setCountryId(country);
                s.setCountryName(rs.getString(4));
                list.add(s);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException x) {
                }
            }
        }
        return list;
    }
}
