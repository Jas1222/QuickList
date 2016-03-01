package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    UserCredentialHandler userCredentialHandler;
    ListView chatListView;

    List<ChatRowItem> chatRowItems;
    List<Message> messagesList;
    String APP_ID = "FDBEF958-BCF1-4A23-A20F-C4625D2E9C7A";
    String recipientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        userCredentialHandler = new UserCredentialHandler();
        SendBird.init(APP_ID);
        recipientId = getIntent().getStringExtra("USER_TO_MESSAGE_ID");
        SendBird.startMessaging(recipientId);

        Button bSendMsg = (Button) findViewById(R.id.button_send_message);
        final EditText inputText = (EditText) findViewById(R.id.chat_enter_message);

        final String userH = "h@h.com";
        final String userT = "test@test.com";

        chatRowItems = new ArrayList<>();
        final ChatCustomAdapter adapter = new ChatCustomAdapter(this, chatRowItems);

        chatListView = (ListView) findViewById(R.id.chat_listview);
        chatListView.setAdapter(adapter);
        chatListView.setOnItemClickListener(this);

        bSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newMsg = inputText.getText().toString();
                SendBird.send(newMsg);
                inputText.setText(null);
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
                drawListRows(message);
                adapter.updateChatList(chatRowItems);
                scrollMyListViewToBottom(adapter);
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
                        messagesList = (List<Message>) (List<?>) messageModels;
                        for (int i = 0; i < messageModels.size(); i++) {
                            drawListRows(messagesList.get(i));
                        }
                        adapter.updateChatList(chatRowItems);
                        scrollMyListViewToBottom(adapter);
                        SendBird.join(messagingChannel.getUrl());
                        SendBird.connect();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
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

    public void drawListRows(Message msg) {
        ChatRowItem item = new ChatRowItem(msg.getSenderName(), msg.getMessage(), msg.getTimestamp());
        chatRowItems.add(item);
    }

    private void scrollMyListViewToBottom(final ChatCustomAdapter adapter){
        chatListView.post(new Runnable(){
            @Override
            public void run(){
                chatListView.setSelection(adapter.getCount() - 1);
            }
        });
    }
}


