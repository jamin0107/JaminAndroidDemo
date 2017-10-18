package com.jamin.android.demo.ui.tab;

import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.tab.item.MusicItem;
import com.jamin.android.demo.ui.tab.item.model.DBTemplateAudioInfo;
import com.jamin.android.demo.ui.tab.item.model.TemplateAudioInfo;
import com.jamin.android.demo.ui.tab.item.model.TemplateAudioInfoList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjieming on 2017/10/18.
 */

public class MusicHelper {

  public static List<BaseItem> bindDataFromAudioList(BaseActivity activity,
      TemplateAudioInfoList templateAudioInfoList) {
    if (templateAudioInfoList == null
        || templateAudioInfoList.audioInfoList == null
        || templateAudioInfoList.audioInfoList.size() == 0) {
      return null;
    }
    List<BaseItem> list = new ArrayList<>();
    for (TemplateAudioInfo templateAudioInfo : templateAudioInfoList.audioInfoList) {

      MusicItem item = new MusicItem(activity, templateAudioInfo2DBTemplateAudioInfo(templateAudioInfo));
      list.add(item);
    }
    return list;
  }

  ///**
  // * 将服务器的数据模型转换成本地的数据模型.
  // */
  //public static TemplateAudioInfoListLocal templateAudioInfoList2DBTemplateAudioInfoList(
  //    TemplateAudioInfoList templateAudioInfoList) {
  //  if (templateAudioInfoList == null) {
  //    return null;
  //  }
  //  TemplateAudioInfoListLocal templateAudioInfoListLocal = new TemplateAudioInfoListLocal();
  //  templateAudioInfoListLocal.count = templateAudioInfoList.count;
  //  templateAudioInfoListLocal.audioInfoList = new ArrayList<>();
  //  for (TemplateAudioInfo info : templateAudioInfoList.audioInfoList) {
  //    templateAudioInfoListLocal.audioInfoList.add(templateAudioInfo2DBTemplateAudioInfo(info));
  //  }
  //  return templateAudioInfoListLocal;
  //}




  public static DBTemplateAudioInfo templateAudioInfo2DBTemplateAudioInfo(
      TemplateAudioInfo templateAudioInfo) {
    if (templateAudioInfo == null) {
      return null;
    }
    DBTemplateAudioInfo dbTemplateAudioInfo = new DBTemplateAudioInfo();
    dbTemplateAudioInfo.index = templateAudioInfo.index;
    dbTemplateAudioInfo.categoryIndex = templateAudioInfo.categoryIndex;
    dbTemplateAudioInfo.coverUrl = templateAudioInfo.coverUrl;
    dbTemplateAudioInfo.audioUrl = templateAudioInfo.audioUrl;
    dbTemplateAudioInfo.name = templateAudioInfo.name;
    dbTemplateAudioInfo.duration = templateAudioInfo.duration;
    dbTemplateAudioInfo.author = templateAudioInfo.author;
    dbTemplateAudioInfo.album = templateAudioInfo.album;
    dbTemplateAudioInfo.newFlag = templateAudioInfo.newFlag;
    dbTemplateAudioInfo.order = templateAudioInfo.order;

    return dbTemplateAudioInfo;
  }
}
