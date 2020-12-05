import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;
import java.io.*;

import static spark.Spark.*;

public class Main {
public static void main (String[] args){
    DataBase d = new DataBase();
    System.out.println("hello");




    get("/bidder", (req, res) -> {
        String c =  d.getBidder(req.queryParams("email")).toString();
        System.out.println("response: "+c);
        return c;
    });

    get("/bidcaller", (request, response) -> {
        String  a=  d.getBidCaller(request.queryParams("email")).toString();
        System.out.println("response: "+a);
        return a;
    });

    get("/auctioneer", (request, response) -> {
        String b = d.getAuctioneer(request.queryParams("email")).toString();
        System.out.println("response: "+b);
        return b;
    });




    get("/bidder/signup", ((req, response) -> {
        String[] f = new String[6];
        f[0]=req.queryParams("firstname");
        f[1]=req.queryParams("lastname");
        f[2]=req.queryParams("email");
        f[3]=req.queryParams("password");
        f[4]=req.queryParams("username");
        f[5]=req.queryParams("mobile");
        d.setBidder(f);
        return "Sign Up Successful!!!!";
    }));

    get("/bidcaller/signup", ((req, response) -> {
        String[] f = new String[6];
        f[0]=req.queryParams("firstname");
        f[1]=req.queryParams("lastname");
        f[2]=req.queryParams("email");
        f[3]=req.queryParams("password");
        f[4]=req.queryParams("username");
        f[5]=req.queryParams("mobile");

        d.setBidCaller(f);
        return "Sign Up Successful!!!!";
    }));

    get("/auctioneer/signup", ((req, response) -> {
        String[] f = new String[7];
        f[0]=req.queryParams("firstname");
        f[1]=req.queryParams("lastname");
        f[2]=req.queryParams("email");
        f[3]=req.queryParams("password");
        f[4]=req.queryParams("username");
        f[5]=req.queryParams("mobile");
        f[6]=req.queryParams("fees");
        d.setAuctioneer(f);
        return "Sign Up Successful!!!!";
    }));





    get("/auctioneer/signup/check",((request, response) -> {
        String chup = d.checkAuctioneer(request.queryParams("email"),request.queryParams("username"),request.queryParams("mobile")).toString();
        return chup;
    }));

    get("/bidder/signup/check",((request, response) -> {
        String chup = d.checkBidder(request.queryParams("email"),request.queryParams("username"),request.queryParams("mobile")).toString();
        return chup;
    }));

    get("/bidcaller/signup/check",((request, response) -> {
        String chup = d.checkBidcaller(request.queryParams("email"),request.queryParams("username"),request.queryParams("mobile")).toString();
        return chup;
    }));





    get("/bidcaller/auctioneerlist", (request, response) -> {
        String b = d.getAuctioneerslist().toString();
        System.out.println("response: "+b);
        return b;
    });





    get("/bidcaller/item",((request, response) -> {
        String[] i = new String[4];
        i[0]=request.queryParams("name");
        i[1]=request.queryParams("description");
        i[2]=request.queryParams("auctioneer");
        i[3]=request.queryParams("bidcalleremail");
        d.setItem(i);
        return i;
    }));





    get("/auctioneer/getitemlist",((request, response) -> {
        String e = d.getaucitems(request.queryParams("email")).toString();
        return  e;
    }));




    get("/iteminfo",(request, response) -> {
        String[] y = new String[3];
        y[0] = request.queryParams("name");
        y[1] =request.queryParams("bidcaller");
        y[2] = request.queryParams("auctioneer");
        String itemdata=d.getiteminfo(y).toString();
        return itemdata;
    });





    get("/auctioneer/item", (request, response) -> {
        String[] j = new String[8];
        j[0]=request.queryParams("itemname");
        j[1]=request.queryParams("auctioneer");
        j[2]=request.queryParams("bidcaller");
        j[3]=request.queryParams("itemID");
        j[4]=request.queryParams("baseprice");
        j[5]=request.queryParams("auctiondate");
        j[6]=request.queryParams("auctiontime");
        j[7]=request.queryParams("status");
        System.out.println("updated");
        return ("Updated");
    });







    get("/eligibleitems",(request, response) -> {
        String eitems = d.eligibleitems().toString();
        return eitems;
    });





    get("/getcompleteiteminfo",(request, response) -> {
        String id = d.getcompleteiteminfo(request.queryParams("id")).toString();
        return id;
    });





    get("/item/check",(request, response) -> {
        String status = d.checkitem(request.queryParams("ID")).getString("check");
        return status;
    });





    get("/bidder/bid",(request, response) -> {
        String[] b = new String[3];
        b[0]=request.queryParams("bidderemail");
        b[1]=request.queryParams("itemid");
        b[2]=request.queryParams("bid");
        String hb = d.acceptbid(b).toString();
        return hb;
    });


    get("/auction/livebidders",(request, response) -> {
        String lllb=d.getlivebidders(request.queryParams("itemid")).toString();
        System.out.println(lllb);
        System.out.println("Printed");
        return lllb;
    });



    get("/user/recharge",(request, response) -> {
        String usertype = request.queryParams("usertype");
        String email = request.queryParams("email");
        String recharge = request.queryParams("recharge");
        d.recharge(usertype,email,recharge);
        return ("Recharge Successful");
    });




    get("/user/changepassword",(request, response) -> {
        d.changepassword(request.queryParams("usertype"),request.queryParams("email"),request.queryParams("newpassword"));
        return ("Password Updated");
    });






    get("/auctioneer/setfees",(request, response) -> {
        d.setfees(request.queryParams("email"),request.queryParams("fees"));
        return ("Fees set successfully");
    });



    get("/results",(request, response) -> {
        String string = d.results(request.queryParams("itemid")).toString();
        return string;
    });



    get("/getbalance",(request, response) -> {
        String balance = d.getbalance(request.queryParams("usertype"),request.queryParams("email")).getString("balance");
        return balance;
    });



    get("/increasesuccess",(request, response) -> {
        d.incresesuccess(request.queryParams("email"));
        return ("increased");
    });


    get("/changestatus",(request, response) -> {
        d.updatestatus(request.queryParams("id"),request.queryParams("status"));
        return ("status changed");
    });





    get("/user/updateemail",(request, response) -> {
        d.updateemail(request.queryParams("usertype"),request.queryParams("nemail"),request.queryParams("oemail"));
        return ("email updated");
    });


    get("/user/updateusername",(request, response) -> {
        d.updateusername(request.queryParams("usertype"),request.queryParams("nusername"),request.queryParams("email"));
        return ("username updated");
    });


    get("/user/updatemobile",(request, response) -> {
        d.updatemobile(request.queryParams("usertype"),request.queryParams("nmobile"),request.queryParams("email"));
        return ("mobile number updated");
    });


    get("/user/updatename",(request, response) -> {
        d.updatename(request.queryParams("usertype"),request.queryParams("nfirstname"),request.queryParams("nlastname"),request.queryParams("email"));
        return ("email updated");
    });



    get("/user/forgotpassword",(request, response) -> {
        d.forgotpass(request.queryParams("usertype"),request.queryParams("npassword"),request.queryParams("email"));
        return ("Password changed!!!");
    });

    }
}
