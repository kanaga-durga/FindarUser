package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Adapter.CustomRecyclearAdapterChat;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.Chat_GS;

import org.findar.user.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    UICommon uicommon;
    TextView textCompany;
    String TAG = "ChatActivity";
    Activity activity;
    List<Chat_GS> chatGsList;
    RecyclerView recycleViewChat;
    ImageView imageViewSend;
    EditText editTextArea;
    CustomRecyclearAdapterChat mAdapterChatHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activity = ChatActivity.this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,ChatActivity.this);

        textCompany = findViewById(R.id.textCompany);
        textCompany.setText(Constant.COMPANYNAME);

        recycleViewChat = findViewById(R.id.recycleViewChat);

        imageViewSend = findViewById(R.id.imageViewSend);
        editTextArea = findViewById(R.id.editTextArea);

        GetChatHistory();

        imageViewSend.setOnClickListener(v->{
            View view = this.getCurrentFocus();
            String message;
            message = editTextArea.getText().toString();
            if(!message.equalsIgnoreCase("")) {
                uicommon.hideKeyboardFrom(ChatActivity.this,view);
                GoToChatAction(Constant.JOBID, Constant.USER_ID, Constant.COMPANYID, message);
            }
            else
                Toast.makeText(getApplicationContext(),R.string.chatError,Toast.LENGTH_LONG).show();
        });

    }

    /*************************** Get Chat History ***************************/
    @SuppressLint("SetTextI18n")
    private void GetChatHistory() {
        System.out.println("Constant.JOBID"+Constant.JOBID);
        System.out.println("Constant.USER_ID"+Constant.USER_ID);
        System.out.println("Constant.COMPANYID"+Constant.COMPANYID);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.P_JOBID, Constant.JOBID);
        params.put(Constant.P_FROMID, Constant.USER_ID);
        params.put(Constant.P_TOID, Constant.COMPANYID);
        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_ChatList" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    chatGsList = new ArrayList<Chat_GS>();

                    mAdapterChatHistory = new CustomRecyclearAdapterChat(ChatActivity.this, chatGsList);
                    recycleViewChat.setAdapter(mAdapterChatHistory);

                    if (status.equals("1")) {

                        JSONArray result_chat = jsonObject.getJSONArray("result");
                        if(result_chat.length()!=0) {
                            for (int i = 0; i < result_chat.length(); i++) {
                                Chat_GS chat_gs = new Chat_GS();
                                JSONObject chatRecord = result_chat.getJSONObject(i);
                                System.out.println("Result_ HistoryJobID" + chatRecord.getString("message"));
                                chat_gs.setId(chatRecord.getString("id"));
                                chat_gs.setJob_id(chatRecord.getString("job_id"));
                                chat_gs.setFrom_id(chatRecord.getString("from_id"));
                                chat_gs.setTo_id(chatRecord.getString("to_id"));
                                chat_gs.setMessage(chatRecord.getString("message"));
                                chat_gs.setAttachment(chatRecord.getString("attachment"));
                                chat_gs.setState1(chatRecord.getString("state1"));
                                chat_gs.setState2(chatRecord.getString("state2"));
                                chat_gs.setViewed(chatRecord.getString("viewed"));
                                chat_gs.setType(chatRecord.getString("type"));
                                chat_gs.setCreated_at(chatRecord.getString("created_at"));
                                chat_gs.setName(chatRecord.getString("name"));
                                chatGsList.add(chat_gs);
                            }
                        }

                        recycleViewChat.setHasFixedSize(true);
                        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
                        recycleViewChat.setLayoutManager(layoutManagerSubProblems);
                        mAdapterChatHistory = new CustomRecyclearAdapterChat(ChatActivity.this, chatGsList);
                        recycleViewChat.setAdapter(mAdapterChatHistory);
                        mAdapterChatHistory.notifyDataSetChanged();
                        recycleViewChat.scrollToPosition(chatGsList.size() - 1);
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_OrderHistory" + e);
                }
            }
        }, activity, Constant.CHATLIST, params, true);
    }

    private void GoToChatAction(String jobId, String fromId, String toId,String msg) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.P_JOBID, jobId);
        params.put(Constant.P_FROMID, fromId);
        params.put(Constant.P_TOID, toId);
        params.put(Constant.P_MESSAGE, msg);

       System.out.println("Constant.P_JOBID"+ jobId);
       System.out.println("Constant.P_FROMID"+fromId);
       System.out.println("Constant.P_TOID"+toId);
       System.out.println("Constant.P_MESSAGE"+msg);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_ChatAction" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        editTextArea.setText("");
                        GetChatHistory();

                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException exception) {
                    System.out.println("Exception ::"+exception);

                }
            }
        }, activity, Constant.CHATACTION, params, true);
    }

}