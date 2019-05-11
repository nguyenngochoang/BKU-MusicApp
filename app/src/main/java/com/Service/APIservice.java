package com.Service;

public class APIservice {
    private static String base_url="https://minhnhut1610.000webhostapp.com/Server/";
    public static DataService getservice(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
