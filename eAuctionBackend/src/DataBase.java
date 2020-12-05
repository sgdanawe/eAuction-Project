import java.sql.*;

import org.json.*;

public class DataBase {
    Connection con;
    PreparedStatement stmt, state, mate, h, p;
    private ResultSet rs, rss, rset;


    public static int stringCompare(String str1, String str2) {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }
        if (l1 != l2) {
            return l1 - l2;
        } else {
            return 0;
        }
    }


    public DataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/eauction?autoReconnect=true&useSSL=false", "root", "bi@cb@@@sgdhgmm");
//here sonoo is database name, root is username and password
        } catch (Exception ex) {
            System.out.println("Error : " + ex);
        }
    }




    public JSONObject getBidCaller(String email) {
        JSONObject aobj = new JSONObject();
        try {
            String sqlquery = "SELECT * from bidcaller WHERE emailaddress = ?;";
            stmt = con.prepareStatement(sqlquery);
            stmt.setString(1, email);
            System.out.println(stmt.toString());
            rs = stmt.executeQuery();
            rs.next();
            aobj.put("email", rs.getString("emailaddress"));
            aobj.put("firstname", rs.getString("firstname"));
            aobj.put("lastname", rs.getString("lastname"));
            aobj.put("password", rs.getString("password"));
            aobj.put("username", rs.getString("username"));
            aobj.put("mobile", rs.getString("mobile"));
            aobj.put("balance", rs.getString("wallet"));
            System.out.println(aobj.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aobj;
    }


    public JSONObject getAuctioneer(String email) {
        JSONObject bobj = new JSONObject();
        try {
            String sqlquery = "SELECT * from auctioneer WHERE emailaddress = ?;";
            stmt = con.prepareStatement(sqlquery);
            stmt.setString(1, email);
            System.out.println(stmt.toString());
            rs = stmt.executeQuery();
            rs.next();
            bobj.put("email", rs.getString("emailaddress"));
            bobj.put("firstname", rs.getString("firstname"));
            bobj.put("lastname", rs.getString("lastname"));
            bobj.put("password", rs.getString("password"));
            bobj.put("username", rs.getString("username"));
            bobj.put("sauc", rs.getString("successfulauctions"));
            bobj.put("mobile", rs.getString("mobile"));
            bobj.put("balance", rs.getString("wallet"));
            bobj.put("fees", rs.getString("fees"));
            System.out.print(bobj.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bobj;
    }


    public JSONObject getBidder(String email) {
        JSONObject cobj = new JSONObject();
        try {
            String sqlquery = "SELECT * from bidder WHERE emailaddress = ?;";
            stmt = con.prepareStatement(sqlquery);
            stmt.setString(1, email);
            System.out.println(stmt.toString());
            rs = stmt.executeQuery();
            rs.next();
            cobj.put("email", rs.getString("emailaddress"));
            cobj.put("firstname", rs.getString("firstname"));
            cobj.put("lastname", rs.getString("lastname"));
            cobj.put("password", rs.getString("password"));
            cobj.put("username", rs.getString("username"));
            cobj.put("balance", rs.getString("wallet"));
            cobj.put("mobile", rs.getString("mobile"));
            System.out.println(cobj.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cobj;
    }


    public JSONObject setBidCaller(String[] f) {
        JSONObject eobj = new JSONObject();
        try {
            String sqlquery = "INSERT INTO bidcaller (firstname,lastname,emailaddress,password,username,mobile) VALUES (?,?,?,?,?,?);";
            stmt = con.prepareStatement(sqlquery);
            stmt.setString(1, f[0]);
            stmt.setString(2, f[1]);
            stmt.setString(3, f[2]);
            stmt.setString(4, f[3]);
            stmt.setString(5, f[4]);
            stmt.setString(6, f[5]);
            System.out.println(stmt.toString());
            stmt.execute();
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eobj;
    }


    public JSONObject setAuctioneer(String[] f) {
        JSONObject fobj = new JSONObject();
        try {
            String sqlquery = "INSERT INTO auctioneer (firstname,lastname,emailaddress,password,username,mobile,fees) VALUES (?,?,?,?,?,?,?);";
            stmt = con.prepareStatement(sqlquery);
            stmt.setString(1, f[0]);
            stmt.setString(2, f[1]);
            stmt.setString(3, f[2]);
            stmt.setString(4, f[3]);
            stmt.setString(5, f[4]);
            stmt.setString(6, f[5]);
            stmt.setString(7, f[6]);
            System.out.println(stmt.toString());
            stmt.execute();
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fobj;
    }


    public JSONObject setBidder(String[] f) {
        JSONObject gobj = new JSONObject();
        try {

            String sqlquery = "INSERT INTO bidder (firstname,lastname,emailaddress,password,username,wallet,mobile) VALUES (?,?,?,?,?,?,?);";
            stmt = con.prepareStatement(sqlquery);
            stmt.setString(1, f[0]);
            stmt.setString(2, f[1]);
            stmt.setString(3, f[2]);
            stmt.setString(4, f[3]);
            stmt.setString(5, f[4]);
            stmt.setString(6, "0");
            stmt.setString(7, f[5]);
            System.out.println(stmt.toString());
            stmt.execute();
            System.out.println("Inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gobj;
    }


    public JSONObject checkAuctioneer(String email, String username, String mobile) throws SQLException {
        JSONObject check = new JSONObject();
        String echeck = "SELECT * FROM auctioneer WHERE emailaddress=?;";
        String ucheck = "SELECT * FROM auctioneer WHERE username=?;";
        String mcheck = "SELECT * FROM auctioneer WHERE mobile=?;";
        state = con.prepareStatement(echeck);
        state.setString(1, email);
        System.out.println(state.toString());
        rs = state.executeQuery();
        mate = con.prepareStatement(ucheck);
        mate.setString(1, username);
        System.out.println(mate.toString());
        rss = mate.executeQuery();
        p = con.prepareStatement(mcheck);
        p.setString(1, mobile);
        System.out.println(p.toString());
        rset = p.executeQuery();
        if (rs.next()) {
            check.put("emailanswer", "emailconflict");
        } else {
            check.put("emailanswer", "emailpeace");
        }
        if (rss.next()) {
            check.put("useranswer", "userconflict");
        } else {
            check.put("useranswer", "userpeace");
        }
        if (rset.next()) {
            check.put("mobileanswer", "mobileconflict");
        } else {
            check.put("mobileanswer", "mobilepeace");
        }
        return check;
    }

    public JSONObject checkBidder(String email, String username, String mobile) throws SQLException {
        JSONObject check = new JSONObject();
        String echeck = "SELECT * FROM bidder WHERE emailaddress=?;";
        String ucheck = "SELECT * FROM bidder WHERE username=?;";
        String mcheck = "SELECT * FROM bidder WHERE mobile=?;";
        state = con.prepareStatement(echeck);
        state.setString(1, email);
        System.out.println(state.toString());
        rs = state.executeQuery();
        mate = con.prepareStatement(ucheck);
        mate.setString(1, username);
        System.out.println(mate.toString());
        rss = mate.executeQuery();
        p = con.prepareStatement(mcheck);
        p.setString(1, mobile);
        System.out.println(p.toString());
        rset = p.executeQuery();
        if (rs.next()) {
            check.put("emailanswer", "emailconflict");
        } else {
            check.put("emailanswer", "emailpeace");
        }
        if (rss.next()) {
            check.put("useranswer", "userconflict");
        } else {
            check.put("useranswer", "userpeace");
        }
        if (rset.next()) {
            check.put("mobileanswer", "mobileconflict");
        } else {
            check.put("mobileanswer", "mobilepeace");
        }
        return check;
    }

    public JSONObject checkBidcaller(String email, String username, String mobile) throws SQLException {
        JSONObject check = new JSONObject();
        String echeck = "SELECT * FROM bidcaller WHERE emailaddress=?;";
        String ucheck = "SELECT * FROM bidcaller WHERE username=?;";
        String mcheck = "SELECT * FROM bidcaller WHERE mobile=?;";
        state = con.prepareStatement(echeck);
        state.setString(1, email);
        System.out.println(state.toString());
        rs = state.executeQuery();
        mate = con.prepareStatement(ucheck);
        mate.setString(1, username);
        System.out.println(mate.toString());
        rss = mate.executeQuery();
        p = con.prepareStatement(mcheck);
        p.setString(1, mobile);
        System.out.println(p.toString());
        rset = p.executeQuery();
        if (rs.next()) {
            check.put("emailanswer", "emailconflict");
        } else {
            check.put("emailanswer", "emailpeace");
        }
        if (rss.next()) {
            check.put("useranswer", "userconflict");
        } else {
            check.put("useranswer", "userpeace");
        }
        if (rset.next()) {
            check.put("mobileanswer", "mobileconflict");
        } else {
            check.put("mobileanswer", "mobilepeace");
        }
        return check;
    }






    public void updateemail(String ut, String ei, String em) {
        try {
            if (stringCompare(ut, "a") == 0) {
                String p = "UPDATE bidcaller SET emailaddress=? WHERE emailaddress=?;";
                state = con.prepareStatement(p);
                state.setString(1, ei);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Email updated");
            } else if (stringCompare(ut, "b") == 0) {
                String p = "UPDATE auctioneer SET emailaddress=? WHERE emailaddress=?;";
                state = con.prepareStatement(p);
                state.setString(1, ei);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Auctioneer Email updated");
            } else {
                String p = "UPDATE bidder SET emailaddress=? WHERE emailaddress=?;";
                state = con.prepareStatement(p);
                state.setString(1, ei);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidder Email updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateusername(String ut, String nu, String em) throws SQLException {
        try {
            if (stringCompare(ut, "a") == 0) {
                String ert = "UPDATE bidcaller SET username=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nu);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Username updated");
            } else if (stringCompare(ut, "b") == 0) {
                String ert = "UPDATE auctioneer SET username=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nu);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Auctioneer Username updated");
            } else {
                String ert = "UPDATE bidder SET username=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nu);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidder Username updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public  void updatemobile(String ut,String nmn,String em){
        try {
            if (stringCompare(ut, "a") == 0) {
                String ert = "UPDATE bidcaller SET mobile=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nmn);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Mobile Number updated");
            } else if (stringCompare(ut, "b") == 0) {
                String ert = "UPDATE auctioneer SET mobile=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nmn);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Auctioneer Mobile Number updated");
            } else {
                String ert = "UPDATE bidder SET mobile=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nmn);
                state.setString(2, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidder Mobile Number updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updatename(String ut,String nfn,String nln,String em){
        try {
            if (stringCompare(ut, "a") == 0) {
                String ert = "UPDATE bidcaller SET firstname=?,lastname=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nfn);
                state.setString(2, nln);
                state.setString(3, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Mobile Number updated");
            } else if (stringCompare(ut, "b") == 0) {
                String ert = "UPDATE auctioneer SET firstname=?,lastname=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nfn);
                state.setString(2, nln);
                state.setString(3, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Auctioneer Mobile Number updated");
            } else {
                String ert = "UPDATE bidder SET firstname=?,lastname=? WHERE emailaddress=?;";
                state = con.prepareStatement(ert);
                state.setString(1, nfn);
                state.setString(2, nln);
                state.setString(3, em);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidder Mobile Number updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }














    public JSONObject getAuctioneerslist(){
        JSONObject list = new JSONObject();
        try{
            String data = "SELECT * FROM auctioneer;";
            state = con.prepareStatement(data);
            System.out.println(state);
            rs = state.executeQuery();
            int count = 0;
            while(rs.next()){
                list.put(String.valueOf(count),rs.getString("emailaddress"));
                ++count;
            }
            list.put("total",count);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }






    public JSONObject setItem(String[] i){
        JSONObject itemobject = new JSONObject();
        try{
                String add = "Insert into items (name,discription,auctioneer,bidcaller) values (?,?,?,?);";
                stmt = con.prepareStatement(add);
                stmt.setString(1, i[0]);
                stmt.setString(2, i[1]);
                stmt.setString(3, i[2]);
                stmt.setString(4, i[3]);
                System.out.println(stmt.toString());
                stmt.execute();
                System.out.println("Added");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return itemobject;
    }







    public JSONObject getaucitems(String aucemail){
        JSONObject list = new JSONObject();

        try{
            String data = "SELECT * FROM items where auctioneer=?;";
            state=con.prepareStatement(data);
            state.setString(1,aucemail);
            System.out.println(state.toString());
            rs = state.executeQuery();

            int count=0;
            while(rs.next()) {
                JSONArray yo = new JSONArray();
                yo.put(rs.getString("name"));
                yo.put(rs.getString("bidcaller"));
                list.put(String.valueOf(count), yo);
                ++count;
            }
            list.put("total",count);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }





    public JSONObject getiteminfo(String[] y){
        JSONObject iteminfo = new JSONObject();
        try{
            String start = "SELECT * FROM items WHERE name=? AND bidcaller=? AND auctioneer=?;";
            state = con.prepareStatement(start);
            state.setString(1,y[0]);
            state.setString(2,y[1]);
            state.setString(3,y[2]);
            System.out.println(state.toString());
            rs = state.executeQuery();
            rs.next();
            iteminfo.put("name",rs.getString("name"));
            iteminfo.put("description",rs.getString("discription"));
            iteminfo.put("bidcaller",rs.getString("bidcaller"));
            iteminfo.put("auctioneer",rs.getString("auctioneer"));
            iteminfo.put("id",rs.getString("id"));
            iteminfo.put("bp",rs.getString("baseprice"));
            iteminfo.put("ad",rs.getString("auctiondate"));
            iteminfo.put("at",rs.getString("auctiontime"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return iteminfo;
    }



    public void updateItem(String[] j){

        try {
            String k = "SELECT * FROM items WHERE id=?;";
            state= con.prepareStatement(k);
            state.setString(1,j[3]);
            System.out.println(state.toString());
            rs = state.executeQuery();
            rs.next();
                String check = "Update items SET id=?,baseprice=?,auctiondate=?,auctiontime=?,status=? WHERE name=? AND auctioneer=? AND bidcaller=?;";
                stmt = con.prepareStatement(check);
                stmt.setString(1, j[3]);
                stmt.setString(2, j[4]);
                stmt.setString(3, j[5]);
                stmt.setString(4, j[6]);
                stmt.setString(5, j[7]);
                stmt.setString(6, j[0]);
                stmt.setString(7, j[1]);
                stmt.setString(8, j[2]);
                System.out.println(stmt.toString());
                stmt.execute();
                System.out.println("Updated");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }










    public JSONObject eligibleitems(){
        JSONObject item = new JSONObject();
        try{
            String start = "SELECT * FROM items WHERE status=?;";
            state = con.prepareStatement(start);
            state.setString(1,"1");
            System.out.println(state.toString());
            rs = state.executeQuery();
            int count = 0;
            item.put("initial","y");
            while(rs.next()){
                JSONArray A = new JSONArray();
                A.put(rs.getString("name"));
                A.put(rs.getString("id"));
                item.put(String.valueOf(count),A);
                ++count;
            }
            item.put("total",count);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }




    public JSONObject getcompleteiteminfo(String id){
        JSONObject info = new JSONObject();
        try{
            String g = "SELECT * FROM items WHERE id=?;";
            state= con.prepareStatement(g);
            state.setString(1, String.valueOf(id));
            System.out.println(state.toString());
            rs = state.executeQuery();
            rs.next();
            info.put("name",rs.getString("name"));
            info.put("description",rs.getString("discription"));
            info.put("bidcaller",rs.getString("bidcaller"));
            info.put("auctioneer",rs.getString("auctioneer"));
            info.put("id",rs.getString("id"));
            info.put("bp",rs.getString("baseprice"));
            info.put("ad",rs.getString("auctiondate"));
            info.put("at",rs.getString("auctiontime"));
            info.put("status",rs.getString("status"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return info;
    }







    public JSONObject checkitem(String ID){
        JSONObject stat = new JSONObject();
        try{
            String chheck = "SELECT * FROM items WHERE id=?;";
            state=con.prepareStatement(chheck);
            state.setString(1,ID);
            System.out.println(state.toString());
            rs = state.executeQuery();
            if(rs.next()){
                stat.put("check","y");
            }else{
                stat.put("check","n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return stat;
    }



    public JSONObject acceptbid(String[] b){
        JSONObject bid = new JSONObject();
        try{
            String ch = "SELECT * FROM bid WHERE bidder=?;";
            state=con.prepareStatement(ch);
            state.setString(1,b[0]);
            System.out.println(state.toString());
            rs = state.executeQuery();
            if(rs.next()){
                String up = "UPDATE bid SET value=? WHERE bidder=? AND item=?;";
                stmt = con.prepareStatement(up);
                stmt.setString(1,b[2]);
                stmt.setString(2,b[0]);
                stmt.setString(3,b[1]);
                System.out.println(stmt.toString());
                stmt.execute();
                System.out.println("Updated");
            }else{
                String in = "INSERT INTO bid (bidder,item,value) VALUES (?,?,?);";
                mate = con.prepareStatement(in);
                mate.setString(1,b[0]);
                mate.setString(2,b[1]);
                mate.setString(3,b[2]);
                System.out.println(mate.toString());
                mate.execute();
                System.out.println("Inserted");
            }

            String ibids = "SELECT * FROM bid WHERE item=?;";
            h=con.prepareStatement(ibids);
            h.setString(1,b[1]);
            System.out.println(h.toString());
            rss=h.executeQuery();
            if(rss.next()){
                String hb = "SELECT MAX(value) FROM bid WHERE item=?;";
                p=con.prepareStatement(hb);
                p.setString(1,b[1]);
                System.out.println(p.toString());
                rset = p.executeQuery();
                rset.next();
                bid.put("highestbid",rset.getString("MAX(value)"));
            }else{
                bid.put("highestbid","baseprice");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bid;
    }





    public JSONObject getlivebidders(String ID){
        JSONObject lb=new JSONObject();
        try{
            String livb = "SELECT bidder,value FROM bid WHERE item=? ORDER BY value DESC;";
            mate = con.prepareStatement(livb);
            mate.setString(1,ID);
            System.out.println(mate.toString());
            rs = mate.executeQuery();
            int count=0;
            while(rs.next()){
                JSONArray A = new JSONArray();
                A.put(rs.getString("bidder"));
                A.put(rs.getString("value"));
                lb.put(String.valueOf(count),A);
                ++count;
            }
            lb.put("anyone",count);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lb;
    }







    public JSONObject recharge(String usertype,String email,String recharge){
        JSONObject k = new JSONObject();
        try{
            if(stringCompare(usertype,"a")==0) {
                String u = "UPDATE bidcaller SET wallet=wallet+? WHERE emailaddress=?;";
                mate = con.prepareStatement(u);
                mate.setString(1, recharge);
                mate.setString(2, email);
                System.out.println(mate.toString());
                mate.execute();
                System.out.println("Bidcaller Recharge successful");
            }else if(stringCompare(usertype,"b")==0){
                String u = "UPDATE auctioneer SET wallet=wallet+? WHERE emailaddress=?;";
                mate = con.prepareStatement(u);
                mate.setString(1, recharge);
                mate.setString(2, email);
                System.out.println(mate.toString());
                mate.execute();
                System.out.println("Auctioneer Recharge successful");
            }else if(stringCompare(usertype,"c")==0){
                String u = "UPDATE bidder SET wallet=wallet+? WHERE emailaddress=?;";
                mate = con.prepareStatement(u);
                mate.setString(1, recharge);
                mate.setString(2, email);
                System.out.println(mate.toString());
                mate.execute();
                System.out.println("Bidder Recharge successful");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return k;
    }






    public void changepassword(String usertype,String email,String p){
        try{
            if(stringCompare(usertype,"a")==0){
                String h = "UPDATE bidcaller SET password=? WHERE emailaddress=?;";
                state=con.prepareStatement(h);
                state.setString(1,p);
                state.setString(2,email);
                System.out.println(state.toString());
                state.execute();
                System.out.println("BidCallers Password Updated");
            }else if(stringCompare(usertype,"b")==0){
                String h = "UPDATE auctioneer SET password=? WHERE emailaddress=?;";
                state=con.prepareStatement(h);
                state.setString(1,p);
                state.setString(2,email);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Auctioneers Password Updated");
            }else if (stringCompare(usertype,"c")==0) {
                String h = "UPDATE bidder SET password=? WHERE emailaddress=?;";
                state = con.prepareStatement(h);
                state.setString(1, p);
                state.setString(2, email);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidders Password Updated");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public void setfees(String email,String fees){
        try{
            String y = "UPDATE auctioneer SET fees=? WHERE emailaddress=?;";
            mate=con.prepareStatement(y);
            mate.setString(1,fees);
            mate.setString(2,email);
            System.out.println(mate.toString());
            mate.execute();
            System.out.println("Fees set");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }





    public JSONObject results(String itemID){
        JSONObject result = new JSONObject();
        try{
            String h = "SELECT * FROM bid WHERE item=? ORDER BY value DESC;";
            state=con.prepareStatement(h);
            state.setString(1,itemID);
            System.out.println(state.toString());
            rs = state.executeQuery();
            int count=0;
            while(rs.next()){
                JSONArray W = new JSONArray();
                W.put(rs.getString("bidder"));
                W.put(rs.getString("value"));
                result.put(String.valueOf(count),W);
                ++count;
            }
            result.put("count",count);
            System.out.println("Results declared!!!");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }



    public JSONObject getbalance(String usertype,String email){
        JSONObject i = new JSONObject();
        try{
            if(stringCompare(usertype,"a")==0){
                String x = "SELECT * FROM bidcaller WHERE emailaddress=?;";
                state=con.prepareStatement(x);
                state.setString(1,email);
                System.out.println(state.toString());
                rs = state.executeQuery();
                rs.next();
                i.put("balance",rs.getString("wallet"));
            }else if(stringCompare(usertype,"b")==0){
                String x = "SELECT * FROM auctioneer WHERE emailaddress=?;";
                state=con.prepareStatement(x);
                state.setString(1,email);
                System.out.println(state.toString());
                rs = state.executeQuery();
                i.put("balance",rs.getString("wallet"));
            }else{
                String x = "SELECT * FROM bidder WHERE emailaddress=?;";
                state=con.prepareStatement(x);
                state.setString(1,email);
                System.out.println(state.toString());
                rs = state.executeQuery();
                i.put("balance",rs.getString("wallet"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }


    public void incresesuccess(String email){
        try{
            String z = "UPDATE auctioneer SET successfulauctions=successfulauctions+1 WHERE emailaddress=?;";
            mate=con.prepareStatement(z);
            mate.setString(1,email);
            System.out.println(mate.toString());
            mate.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public void updatestatus(String itemID,String status){
        try{
            String f = "UPDATE items SET status=? WHERE id=?;";
            state = con.prepareStatement(f);
            state.setString(1,status);
            state.setString(2,itemID);
            System.out.println(state.toString());
            state.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }



    public  void forgotpass(String ut,String np,String email){
        try{
            if(stringCompare(ut,"a")==0){
                String qwe = "UPDATE bidcaller SET password=? WHERE emailaddress=?;";
                state=con.prepareStatement(qwe);
                state.setString(1,np);
                state.setString(2,email);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Password Updated");
            }else if (stringCompare(ut,"b")==0){
                String qwe = "UPDATE auctioneer SET password=? WHERE emailaddress=?;";
                state=con.prepareStatement(qwe);
                state.setString(1,np);
                state.setString(2,email);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Password Updated");
            }else{
                String qwe = "UPDATE bidder SET password=? WHERE emailaddress=?;";
                state=con.prepareStatement(qwe);
                state.setString(1,np);
                state.setString(2,email);
                System.out.println(state.toString());
                state.execute();
                System.out.println("Bidcaller Password Updated");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataBase d = new DataBase();
    }

}

    /*
    ResultSet rs=stmt.executeQuery("select * from bidder");
            while (rs.next()){
                    System.out.println(rs.getString("username"));
                    }
                    */