package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.toolbox.StringRequest;
import com.sendbird.android.MessagingChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessagingChannel;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

public class MyChatsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<MyChatsRowItem> chatsRowItemList;
    ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        chatsRowItemList = new ArrayList<>();
        final MyChatCustomAdapter adapter = new MyChatCustomAdapter(this, chatsRowItemList);

        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        // queryChannelList or queryMessagingChannelList
        MessagingChannelListQuery mChannelList = SendBird.queryMessagingChannelList();
        if (mChannelList.hasNext()) {
            mChannelList.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
                @Override
                public void onResult(List<MessagingChannel> channels) {
                    List<MessagingChannel.Member> listOfMembers;
                    List<String> listOfUserNames = new ArrayList<>();

                    for (int i = 0; i < channels.size(); i++) {
                        listOfMembers = channels.get(i).getMembers();
                        listOfUserNames.add(listOfMembers.get(i).getName());
                        Message m = channels.get(i).getLastMessage();
                        drawListRows(listOfMembers.get(i).getId(), channels.get(i).getLastMessage().getMessage(), listOfMembers.get(i).getName());
                        //Only works with size 1 atm, need to rethink for multiple chats
                    }
                    adapter.updateChatList(chatsRowItemList);
                    System.out.println(listOfUserNames);
                }

                @Override
                public void onError(int e) {

                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        final String userToMessageId = chatsRowItemList.get(position).userId;
        Intent i = new Intent(getApplicationContext(), MessagingActivity.class );
        i.putExtra("USER_TO_MESSAGE_ID", userToMessageId);
        startActivity(i);
    }

    public void drawListRows(String userId, String title, String userFullName){
        MyChatsRowItem item = new MyChatsRowItem(userId, title, userFullName);
        chatsRowItemList.add(item);
    }
}
