package com.jamin.android.demo.ui.tab.item.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/21.
 *
 * @author luc
 */

public class TemplateAudioInfo {
  @SerializedName("audioid") public String index;
  @SerializedName("classid") public String categoryIndex;
  @SerializedName("coverurl") public String coverUrl;
  @SerializedName("audiourl") public String audioUrl;
  public String name;
  public int duration;
  @SerializedName("auther") public String author;
  public String album;
  @SerializedName("newflag") public String newFlag;
  @SerializedName("orderno") public String order;

  @Override public String toString() {
    return "TemplateAudioInfo{"
        + "index='"
        + index
        + '\''
        + ", categoryIndex='"
        + categoryIndex
        + '\''
        + ", coverUrl='"
        + coverUrl
        + '\''
        + ", audioUrl='"
        + audioUrl
        + '\''
        + ", name='"
        + name
        + '\''
        + ", duration='"
        + duration
        + '\''
        + ", author='"
        + author
        + '\''
        + ", album='"
        + album
        + '\''
        + ", newFlag='"
        + newFlag
        + '\''
        + ", order='"
        + order
        + '\''
        + '}';
  }
}
