package com.jamin.android.demo.ui.tab.item.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 *
 * @author luc
 */

public class TemplateAudioInfoList {
  @SerializedName("allcount") public String count;
  @SerializedName("audiolist") public List<TemplateAudioInfo> audioInfoList;

  @Override public String toString() {
    return "TemplateAudioInfoList{"
        + "count='"
        + count
        + '\''
        + ", audioInfoList="
        + audioInfoList
        + '}';
  }
}
