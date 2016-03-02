package com.example.jaspalhayer.quicklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.sendbird.android.MessagingChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessagingChannel;
import com.sendbird.android.model.User;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

public class MyChatsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<MyChatsRowItem> chatsRowItemList;
    ListView myListView;
    String myEmail;
    String recipientName;
    String recipientId;
    String recipientLastMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);
        chatsRowItemList = new ArrayList<>();
        final MyChatCustomAdapter adapter = new MyChatCustomAdapter(this, chatsRowItemList);
        setTitle("My Chats");

        UserCredentialHandler user = new UserCredentialHandler();
        myEmail = user.getUserEmail(getApplicationContext());

        myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(this);

        Toast loadingChatsToast = Toast.makeText(getApplicationContext(), "Loading Chats", Toast.LENGTH_SHORT);
       final  Toast noChatsFoundToast = Toast.makeText(getApplicationContext(), "No Chats Found", Toast.LENGTH_LONG);
        loadingChatsToast.show();

        // queryChannelList or queryMessagingChannelList
        MessagingChannelListQuery mChannelList = SendBird.queryMessagingChannelList();


        if (mChannelList.hasNext()) {
            mChannelList.next(new MessagingChannelListQuery.MessagingChannelListQueryResult() {
                @Override
                public void onResult(List<MessagingChannel> channels) {
                    if(channels.size() == 0){
                        noChatsFoundToast.show();
                    }
                    List<MessagingChannel.Member> listOfMembersPerChannel;
                    List<String> listOfUserNames = new ArrayList<>();
                    String name;

                    for (int i = 0; i < channels.size(); i++) {
                        listOfMembersPerChannel = channels.get(i).getMembers();

                        for (int j = 0; j < listOfMembersPerChannel.size(); j++) {
                            name = listOfMembersPerChannel.get(j).getId();
                            if (name.contains(myEmail)) {

                            } else {
                                listOfUserNames.add(listOfMembersPerChannel.get(j).getName());
                                recipientName = listOfUserNames.get(i);
                                recipientLastMsg = channels.get(i).getLastMessage().getMessage();
                                recipientId = listOfMembersPerChannel.get(j).getId();
                                  drawListRows(recipientId, recipientLastMsg, recipientName);

                              //  drawListRows(listOfUserNames.get(j), channels.get(i).getLastMessage().getMessage(), listOfMembersPerChannel.get(j).getName());
                            }

                        }

                        //Only works with size 1 atm, need to rethink for multiple chats
                    }
                    adapter.updateChatList(chatsRowItemList);
                }

                @Override
                public void onError(int e) {

                }
            });
        } else {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        final String userToMessageId = chatsRowItemList.get(position).userId;
        String recipientName = chatsRowItemList.get(position).userFullName;
        Intent i = new Intent(getApplicationContext(), MessagingActivity.class);
        i.putExtra("USER_TO_MESSAGE_ID", userToMessageId);
        i.putExtra("SENDER_NAME", recipientName);
        startActivity(i);
    }

    public void drawListRows(String userId, String title, String userFullName) {
        MyChatsRowItem item = new MyChatsRowItem(userId, title, userFullName);
        chatsRowItemList.add(item);
    }
}
