package com.pictoreal.pictobuzz;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by row_hammer on 30/12/16.
 *
 */

public class PostDetails
{
    private static String TAG="POSTDETAILS";
    //Check if  this class is present
    //This class will store data about each post
    private String Image,Title,Description,Other,Url;
    private Long NoticeNumber;
    ArrayList<String> Image1,FileUrl;
    private String DateofUpload,uid;
    public PostDetails()
    {

    }

     public PostDetails(ArrayList<String> Image1, String Image, String Title, String Description, String DateofUpload,  String Other, String Url, String NoticeNumber, String uid,ArrayList<String> FileUrl) {
         try {
             Log.i(TAG, "VIEW IMAGE " + String.valueOf(Image1));
             Log.i(TAG, "VIEW Template " + Image);
             Log.i(TAG, "VIEW TITLE " + Title);
             Log.i(TAG, "VIEW DESCRIPTION " + Description);
             Log.i(TAG, "VIEW DATE" + DateofUpload);
             Log.i(TAG, "VIEW NOTICE NUMBER " + NoticeNumber);
             Log.i(TAG, "VIEW OTHER " + Other);
             Log.i(TAG, "VIEW FILEURL" + FileUrl.toString());

             this.Description = Description;
             this.Image = Image;
             this.Title = Title;
             this.Other = Other;
             this.Url = Url;
             this.NoticeNumber = Long.valueOf(NoticeNumber);
             this.DateofUpload = DateofUpload;
             this.uid = uid;
             this.Image1 = Image1;
             this.FileUrl=FileUrl;

         } catch (NullPointerException e) {
             Log.i(TAG, "NULL POINTER EXCEPTION");

         }
     }
        public PostDetails(String Title, String Description, String NoticeNumber,String DateofUpload) {
            try {
                Log.i(TAG, "VIEW TITLE " + Title);
                Log.i(TAG, "VIEW DESCRIPTION " + Description);
                Log.i(TAG, "VIEW DATE" + DateofUpload);
                Log.i(TAG, "VIEW NOTICE NUMBER " + NoticeNumber);

                this.Description = Description;
                this.Title = Title;
                this.NoticeNumber = Long.valueOf(NoticeNumber);
                this.DateofUpload = DateofUpload;
            }   catch (NullPointerException e) {
                Log.i(TAG, "NULL POINTER EXCEPTION");

            }
        }




    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }


    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Long getNoticeNumber() {
        return NoticeNumber;
    }

    public void setNoticeNumber(Long noticeNumber) {
        NoticeNumber = noticeNumber;
    }
    /*public void setDate(String Date) {
        this.Date = Date;
    }

    public String getUrl(){
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getDate (){
        return Date;
    }



    public String getYear (){
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }*/
}
