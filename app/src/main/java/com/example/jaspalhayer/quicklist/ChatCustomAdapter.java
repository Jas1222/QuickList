package com.example.jaspalhayer.quicklist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sendbird.android.model.Message;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaspalhayer on 29/02/2016.
 */
public class ChatCustomAdapter extends BaseAdapter {
    List<ChatRowItem> chatRowItemList;
    Context context;

//    ChatCustomAdapter(Context context, List<ChatRowItem> chatRowItemList){
//        this.chatRowItemList = chatRowItemList;
//        this.context = context;
//    }

    ChatCustomAdapter(Context context, List<ChatRowItem> chatRowItemList){
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
        TextView username;
        TextView message;
        TextView timestamp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null ) {
            convertView = mInflater.inflate(R.layout.chat_msg_item, null);
            holder = new ViewHolder();
            holder.message = (TextView)convertView.findViewById(R.id.chat_message_text);
            holder.username = (TextView)convertView.findViewById(R.id.chat_user_id);
            holder.timestamp = (TextView)convertView.findViewById(R.id.chat_timestamp);


//            holder.timestamp.setText((int)row_pos.timestamp);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        ChatRowItem row_pos = chatRowItemList.get(position);

        holder.message.setText(row_pos.message);
        holder.username.setText(row_pos.userId);

        return convertView;
    }

    public void updateChatList(List<ChatRowItem> chatItems){
        System.out.println("LIST THAT IS PASSED"+chatItems);
        System.out.println("BEFORE CLEAR: " + chatRowItemList);
        chatRowItemList.clear();
        chatRowItemList.addAll(chatItems);
        System.out.println("AFTER CLEAR AND ADD: "+chatRowItemList);

        this.notifyDataSetChanged();
    }


}
