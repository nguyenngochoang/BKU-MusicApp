package com.pankaj.mail_in_background;





import android.os.AsyncTask;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.firebase.ForgetPasswordActivity;
import com.example.firebase.R;


/**


 * Created by GsolC on 2/24/2017.


 */





public class LongOperation extends AsyncTask<Void, Void, String>  {

    private String body;
    private String reciever;
    private  String subject;
    public LongOperation (String reciever,String body){
        this.body = body;
        this.reciever = reciever;
        subject = "Lấy lại mật khẩu";
    }
    @Override
    public String doInBackground(Void... params) {
        try {
            com.pankaj.mail_in_background.GMailSender sender = new com.pankaj.mail_in_background.GMailSender("nngochoangnguyen@gmail.com", "Nguyenngochoang98");

            sender.sendMail(subject,
                    body,"nngochoangnguyen@gmail.com",
                    reciever);


        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return "Email Not Sent";
        }
        return "Email Sent";

    }





    @Override
    protected void onPostExecute(String result) {

        Log.e("LongOperation",result+"");

    }





    @Override


    protected void onPreExecute() {


    }





    @Override


    protected void onProgressUpdate(Void... values) {


    }


}
