package com.cyl.ctrbt.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cyl.ctrbt.openai.ChatGPTUtil;
import com.cyl.ctrbt.openai.entity.chat.Message;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequestMapping("/ding-talk")
@RestController
public class DingTalkController {

  @Autowired
  private ChatGPTUtil chatGPTUtil;

  @RequestMapping("/receive")
  public String helloRobots(@RequestBody(required = false) JSONObject json) {
    System.out.println(JSONUtil.toJsonStr(json));
    String content = json.getJSONObject("text").get("content").toString().replaceAll(" ", "");
    String sessionWebhook = json.getStr("sessionWebhook");
    DingTalkClient client = new DefaultDingTalkClient(sessionWebhook);
    if ("text".equals(json.getStr("msgtype"))) {
      text(client,content);
    }
    return null;
  }

  private void text(DingTalkClient client,String content) {
    try {
      OapiRobotSendRequest request = new OapiRobotSendRequest();
      request.setMsgtype("text");
      OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
      Message message = chatGPTUtil.chat(content, "dingtalk");
      text.setContent(message.getContent());
      request.setText(text);
      OapiRobotSendResponse response = client.execute(request);
      System.out.println(response.getBody());
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }
}
