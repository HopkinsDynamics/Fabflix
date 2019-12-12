/* ====================================================================================================
   DATE:      06 APR 2018
   AUTHORS:   Kelly McKeown   David Hopkins
   UCInetIDs: KPMCKEOW        HOPKINSD
   ID#'s:     42806243        70404050
   CLASS:     CS 122B
   PROJECT:   #2
   ----------------------------------------------------------------------------------------------------
   DESCRIPTION:
   ====================================================================================================
 */

package fabflix.core;

import java.sql.*;

public class Checkout {
    public static Checkout co = new Checkout();

    public Checkout() { }

    public boolean checkout(Customer c, CreditCard cc) {
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        Customer checkCustomer = new Customer();

        // verify customer exists
        try {
            ps = Core.brain.getCon().prepareStatement(Queries.VERIFY_CUSTOMER_EXISTS);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getEmail());
            System.out.println("Trying Query: " + ps.toString());
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            System.out.println("Finished Query.");

            // build customer data
            if (rs.next()) {
                int resultid = rs.getInt(1);
                String resultFirstName = rs.getString(2);
                String resultLastName = rs.getString(3);
                String resultEmail = rs.getString(6);
                String resultCCid = rs.getString(4);
                String resultID = rs.getString(1);
                checkCustomer.setFirstName(resultFirstName);
                checkCustomer.setLastName(resultLastName);
                checkCustomer.setEmail(resultEmail);
                checkCustomer.setCcId(resultCCid);
                checkCustomer.setId(Integer.parseInt(resultID));
            }

            // if customer exists
            if (checkCustomer.getEmail().equals(c.getEmail())) {
                // verify cc info is the same
                if (checkCustomer.getCcId().equals(cc.getId())) {
                    // cc info matches, commit the sale
                    ShoppingCart sc = Core.brain.getShoppingCart(c.getEmail());
//                    Date saleTime = new Date(System.currentTimeMillis());
                    Timestamp saleTime = new Timestamp(System.currentTimeMillis());

//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    String saleDate = sdf.format(saleTime);
//                    Timestamp ts = new ();
//                    long saleTime = System.currentTimeMillis();

                    try {
                        ps = Core.getCon().prepareStatement(Queries.INSERT_SALE);
                        ps.setString(1, Integer.toString(checkCustomer.getId()));
                        ps.setTimestamp(3, saleTime);
                        for (int i = 0; i < sc.getMovies().size(); ++i) {
                            ps.setString(2, sc.getMovies().get(i).getId());
                            System.out.println("Trying Query: " + ps.toString());
                            ps.executeUpdate();
                            System.out.println("Finished Query.");
                        }
                        Core.brain.removeShoppingCart(c.getEmail());
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // cc info doesn't match
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
