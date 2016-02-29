package com.example.jaspalhayer.quicklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sendbird.android.MessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdEventHandler;
import com.sendbird.android.model.BroadcastMessage;
import com.sendbird.android.model.Channel;
import com.sendbird.android.model.FileLink;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessageModel;
import com.sendbird.android.model.MessagingChannel;
import com.sendbird.android.model.ReadStatus;
import com.sendbird.android.model.SystemMessage;
import com.sendbird.android.model.TypeStatus;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    UserCredentialHandler userCredentialHandler;
    ListView chatListView;
    List<ChatRowItem> chatRowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        userCredentialHandler = new UserCredentialHandler();
        Button bMsgJas = (Button)findViewById(R.id.bJas);
        Button bMsgTest = (Button)findViewById(R.id.bTest);
        Button bSendMsg = (Button)findViewById(R.id.button_send_message);
        final EditText inputText = (EditText)findViewById(R.id.chat_enter_message);

        final String userH = "h@h.com";
        final String userT = "test@test.com";

        chatRowItems = new ArrayList<>();
        final ChatCustomAdapter adapter = new ChatCustomAdapter(this, chatRowItems);
        chatListView = (ListView)findViewById(R.id.chat_listview);
        chatListView.setAdapter(adapter);
        chatListView.setOnItemClickListener(this);


        bMsgJas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SendBird.joinMessaging();
                //  SendBird.join("72f92.testchannel");

                SendBird.startMessaging(userH);
            }
        });

        bMsgTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendBird.startMessaging(userT);
            }
        });

        bSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputText.getText().toString();
                SendBird.send(message);
                adapter.notifyDataSetChanged();
            }
        });

        SendBird.setEventHandler(new SendBirdEventHandler() {
            @Override
            public void onConnect(Channel channel) {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onChannelLeft(Channel channel) {

            }

            @Override
            public void onMessageReceived(Message message) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onSystemMessageReceived(SystemMessage systemMessage) {

            }

            @Override
            public void onBroadcastMessageReceived(BroadcastMessage broadcastMessage) {

            }

            @Override
            public void onFileReceived(FileLink fileLink) {

            }

            @Override
            public void onReadReceived(ReadStatus readStatus) {

            }

            @Override
            public void onTypeStartReceived(TypeStatus typeStatus) {

            }

            @Override
            public void onTypeEndReceived(TypeStatus typeStatus) {

            }

            @Override
            public void onAllDataReceived(SendBird.SendBirdDataType sendBirdDataType, int i) {

            }

            @Override
            public void onMessageDelivery(boolean b, String s, String s1, String s2) {

            }

            @Override
            public void onMessagingStarted(final MessagingChannel messagingChannel) {

                SendBird.queryMessageList(messagingChannel.getUrl()).load(Long.MAX_VALUE, 30, 10, new MessageListQuery.MessageListQueryResult() {
                    @Override
                    public void onResult(List<MessageModel> messageModels) {
                         List<Message> messages = (List<Message>)(List<?>) messageModels;
                         for(int i = 0; i < messageModels.size(); i++){
                             drawListRows(messages.get(i));
                         }
                        adapter.notifyDataSetChanged();
                        SendBird.join(messagingChannel.getUrl());
                        SendBird.connect();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
               // SendBird.join(messagingChannel.getUrl());
               // SendBird.connect();
            }

            @Override
            public void onMessagingUpdated(MessagingChannel messagingChannel) {

            }

            @Override
            public void onMessagingEnded(MessagingChannel messagingChannel) {

            }

            @Override
            public void onAllMessagingEnded() {

            }

            @Override
            public void onMessagingHidden(MessagingChannel messagingChannel) {

            }

            @Override
            public void onAllMessagingHidden() {

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //final String member_list_id = rowItems.get(position).bookListId;

    }

    public void drawListRows(Message msg){
        ChatRowItem item = new ChatRowItem(msg.getSenderName(), msg.getMessage(), msg.getTimestamp());
        chatRowItems.add(item);
    }

    public void getPreviousMessages(final MessagingChannel messagingChannel, final ChatCustomAdapter adapter){
        SendBird.queryMessageList(messagingChannel.getUrl()).load(Long.MAX_VALUE, 30, 10, new MessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<MessageModel> messageModels) {
                List<Message> messages = (List<Message>)(List<?>) messageModels;
                for(int i = 0; i < messageModels.size(); i++){
                    drawListRows(messages.get(i));
                }
                adapter.notifyDataSetChanged();
                SendBird.join(messagingChannel.getUrl());
                SendBird.connect();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
