package com.example.jaspalhayer.quicklist;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdEventHandler;
import com.sendbird.android.model.BroadcastMessage;
import com.sendbird.android.model.Channel;
import com.sendbird.android.model.FileLink;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessagingChannel;
import com.sendbird.android.model.ReadStatus;
import com.sendbird.android.model.SystemMessage;
import com.sendbird.android.model.TypeStatus;

public class MessagingActivity extends Activity {
    UserCredentialHandler userCredentialHandler;
    TextView messageReceivedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        userCredentialHandler = new UserCredentialHandler();
        Button bMsgJas = (Button)findViewById(R.id.bJas);
        Button bMsgTest = (Button)findViewById(R.id.bTest);

        messageReceivedText = (TextView)findViewById(R.id.message_text);
        SendBird.connect();

        bMsgJas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SendBird.joinMessaging();
                //  SendBird.join("72f92.testchannel");

                SendBird.startMessaging("jhayer36@gmail.com");

                SendBird.send("IF YOU SEE THIS IT WORKED");
            }
        });

        bMsgTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendBird.startMessaging("test@test.com");

                SendBird.send("IF YOU SEE THIS IT WORKED");
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
                messageReceivedText.setText(message.getMessage());
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
            public void onMessagingStarted(MessagingChannel messagingChannel) {

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
}
