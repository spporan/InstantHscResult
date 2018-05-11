package com.poran.instanthscresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by poran on 27-Dec-17.
 */

public class CustomList extends BaseAdapter {


Context context;
    ArrayList<String> messageList=new ArrayList<>();
    ArrayList <String> timeList=new ArrayList<>();
    LayoutInflater inflter;

    public CustomList(Context applicationContext, ArrayList messageList,ArrayList timeList) {

        this.messageList = messageList;
        this.timeList = timeList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_view_item, null);
        TextView messageView = (TextView) view.findViewById(R.id.messageview);
        TextView timeView = (TextView) view.findViewById(R.id.timeView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        messageView.setText(messageList.get(i));
        timeView.setText(timeList.get(i));
        return view;
    }




}
