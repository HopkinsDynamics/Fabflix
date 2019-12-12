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

import java.util.ArrayList;

public class SearchParameters {
    private String bGenre;
    private String bTitle;
    private String sTitle;
    private String sYear;
    private String sDir;
    private String sStar;
    private String orderBy;
    private String order;
    private String limit;
    private String offset;
    private String params[];
    private int    limitInt;
    private int    offsetInt;

    public static final int MAX_PARAMS = 9;
    public static final String DEFAULT_LIMIT = "10";

    public SearchParameters(String bGenre, String bTitle, String sTitle, String sYear, String sDir, String sStar, String orderBy, String order, String limit, String offset) {
        System.out.println("BUILDING NEW SearchParameters OBJECT...");
        this.bGenre = bGenre;
        this.bTitle = bTitle;
        this.sTitle = sTitle;
        this.sYear = sYear;
        this.sDir = sDir;
        this.sStar = sStar;
        this.orderBy = orderBy;
        this.order = order;
        this.limit = limit;
        this.offset = offset;
        this.limitInt = Integer.parseInt(limit);
        this.offsetInt = Integer.parseInt(offset);
//        System.out.println("  bGenre  = " + bGenre);
//        System.out.println("  bTitle  = " + bTitle);
//        System.out.println("  sTitle  = " + sTitle);
//        System.out.println("  sYear   = " + sYear);
//        System.out.println("  sDir    = " + sDir);
//        System.out.println("  sStar   = " + sStar);
//        System.out.println("  orderBy = " + orderBy);
//        System.out.println("  order   = " + order);
//        System.out.println("  limit   = " + limit);
//        System.out.println("  offset  = " + offset);
//        System.out.println("DONE! Building params array in SearchParameters...");
        buildParams();
    }

    private void buildParams() {
//        System.out.println("buildParams()");
        ArrayList<String> params = new ArrayList<>();

        if (!bGenre.equals("null")) {
//            System.out.println("b.Genre != null --> adding");
            params.add(bGenre);
        }
        else {
//            System.out.println("b.Genre is null");
        }

        if (!bTitle.equals("null")) {
//            System.out.println("b.Title != null --> adding");
            params.add(bTitle + "%");
        }
        else {
//            System.out.println("b.Title is null");
        }

        if (!sTitle.equals("null")) {
//            System.out.println("s.Title != null --> adding");
            params.add("%" + sTitle + "%");
        }
        else {
//            System.out.println("s.Title is null");
        }

        if (!sYear.equals("null")) {
//            System.out.println("s.Year != null --> adding");
            params.add(sYear);
        }
        else {
//            System.out.println("s.Year is null");
        }

        if (!sDir.equals("null")) {
//            System.out.println("s.Dir != null --> adding");
            params.add("%" + sDir + "%");
        }
        else {
//            System.out.println("s.Dir is null");
        }

        if (!sStar.equals("null")) {
//            System.out.println("s.Star != null --> adding");
            params.add("%" + sStar + "%");
        }
        else {
//            System.out.println("s.Star is null");
        }

//        if (!orderBy.equals("null"))
//        {
//            System.out.println("s.orderBy != null --> adding");
//            params.add(orderBy);
//        }
//        else {
//            System.out.println("s.orderBy is null");
//        }

//        if (!order.equals("null")) {
//            System.out.println("s.order != null --> adding");
//            // set default to ASC
//            params.add(order);
//        }
//        else {
//            System.out.println("s.order is null --> set default to ASC");
//            params.add("ASC");
//        }

        if (!limit.equals("null)")) {
//            System.out.println("s.limit != null --> adding");
            // set default to 10
            params.add(limit);
        }
        else {
//            System.out.println("s.limit is null --> set default to 10");
            params.add(DEFAULT_LIMIT);
        }

        if (!offset.equals("null")) {
//            System.out.println("s.offset != null --> adding");
            params.add(offset);
        }
        else {
//            System.out.println("s.offset is null");
        }

        this.params = new String[params.size()];
//        System.out.println("params.size = " + params.size());
//        System.out.println("params[].length = " + this.params.length);
        for (int i = 0; i < this.params.length; ++i) {
//            System.out.println("params[" + i + "] = " + params.get(i));
            this.params[i] = params.get(i);
//            System.out.println("this.params[" + i + "] = " + this.params[i]);
        }

//        System.out.println("DONE! Printing...");
//        for (int i = 0; i < this.params.length; i++)
//            System.out.println("  " + this.params[i]);
    }

    public String[] getParams() {
        return params;
    }

    public String getbGenre() {
        return bGenre;
    }

    public void setbGenre(String bGenre) {
        this.bGenre = bGenre;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsDir() {
        return sDir;
    }

    public void setsDir(String sDir) {
        this.sDir = sDir;
    }

    public String getsStar() {
        return sStar;
    }

    public void setsStar(String sStar) {
        this.sStar = sStar;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getsYear() {
        return sYear;
    }

    public void setsYear(String sYear) {
        this.sYear = sYear;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public int getLimitInt() {
        return limitInt;
    }

    public void setLimitInt(int limitInt) {
        this.limitInt = limitInt;
    }

    public int getOffsetInt() {
        return offsetInt;
    }

    public void setOffsetInt(int offsetInt) {
        this.offsetInt = offsetInt;
    }
}
