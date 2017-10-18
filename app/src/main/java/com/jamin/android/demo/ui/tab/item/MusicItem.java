package com.jamin.android.demo.ui.tab.item;

import android.widget.TextView;
import com.jamin.android.demo.R;
import com.jamin.android.demo.adapter.BaseHolder;
import com.jamin.android.demo.adapter.BaseItem;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.android.demo.ui.tab.item.model.DBTemplateAudioInfo;

/**
 * Created by wangjieming on 2017/10/18.
 */

public class MusicItem extends BaseItem<DBTemplateAudioInfo> {

  public MusicItem(BaseActivity activity, DBTemplateAudioInfo templateAudioInfo) {
    super(activity, templateAudioInfo);
  }

  @Override public int getLayoutId() {
    return R.layout.list_item_music;
  }

  @Override public void onBindView(BaseHolder holder, int position) {
    DBTemplateAudioInfo templateAudioInfo = getItemData();
    if (templateAudioInfo == null) {
      return;
    }
    TextView nameTV = holder.findViewById(R.id.music_item_name);
    TextView timeTV = holder.findViewById(R.id.music_item_time);
    nameTV.setText(templateAudioInfo.getName());
    timeTV.setText(secToTime(templateAudioInfo.getDuration() / 1000));
  }

  private static String secToTime(int time) {
    String timeStr;
    int hour;
    int minute;
    int second;
    if (time <= 0) {
      return "00:00";
    } else {
      minute = time / 60;
      if (minute < 60) {
        second = time % 60;
        timeStr = unitFormat(minute) + ":" + unitFormat(second);
      } else {
        hour = minute / 60;
        if (hour > 99) return "99:59:59";
        minute = minute % 60;
        second = time - hour * 3600 - minute * 60;
        timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
      }
    }
    return timeStr;
  }

  private static String unitFormat(int i) {
    String retStr;
    if (i >= 0 && i < 10) {
      retStr = "0" + Integer.toString(i);
    } else {
      retStr = "" + i;
    }
    return retStr;
  }
}
