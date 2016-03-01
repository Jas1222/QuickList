package com.example.jaspalhayer.quicklist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaspalhayer on 29/02/2016.
 */
public class MyChatCustomAdapter extends BaseAdapter {
    List<MyChatsRowItem> chatRowItemList;
    Context context;

//    ChatCustomAdapter(Context context, List<ChatRowItem> chatRowItemList){
//        this.chatRowItemList = chatRowItemList;
//        this.context = context;
//    }

    MyChatCustomAdapter(Context context, List<MyChatsRowItem> chatRowItemList){
        this.chatRowItemList = new ArrayList<>(chatRowItemList);
        this.context = context;
    }

    @Override
    public int getCount(){
        return chatRowItemList.size();
    }

    @Override
    public Object getItem(int position){
        return chatRowItemList.get(position);
    }

    @Override
    public long getItemId(int position){
        return chatRowItemList.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView userId;
        TextView bookTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null ) {
            convertView = mInflater.inflate(R.layout.chat_msg_item, null);
            holder = new ViewHolder();
            holder.bookTitle = (TextView)convertView.findViewById(R.id.chat_message_text);
            holder.userId = (TextView)convertView.findViewById(R.id.chat_user_id);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        MyChatsRowItem row_pos = chatRowItemList.get(position);

        holder.bookTitle.setText(row_pos.bookTitle);
        holder.userId.setText(row_pos.userId);

        return convertView;
    }

    public void updateChatList(List<MyChatsRowItem> chatItems){
        chatRowItemList.clear();
        chatRowItemList.addAll(chatItems);
        this.notifyDataSetChanged();
    }
}
