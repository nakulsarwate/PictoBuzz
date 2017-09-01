package com.pictoreal.pictobuzz;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by row_hammer on 19/3/17.
 */

public class PopupWindowHandler {
    PopupWindow popupWindow;
    View popupView;
    Context context;
    /**
     * Use this class to stup the popupwindow wherever necessary
     * Use Case: When downloasing the any content from internet, this class can be used
     *
     *
     */
    public PopupWindowHandler(Context context)
    {
        this.context=context;
    }

    public void setPopUp()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)context
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.fragment_popupwindow_view, null);
        popupWindow = new PopupWindow(
                popupView,
               400,
                ActionBar.LayoutParams.WRAP_CONTENT);
        // popupWindow.showAsDropDown(btnOpenPopup, Gravity.CENTER, 0, 0);
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
    }

    public void dismissPopup()
    {
        if(popupWindow.isShowing())
        {
            popupWindow.dismiss();
        }

    }//[End dismissPopUp]
}
