package com.jamin.android.demo.ui.tab.item.model;

import com.jamin.simpedb.BaseModel;
import com.jamin.simpedb.DatabaseField;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 已下载音乐管理页面,对应数据结构.
 * 对应数据库的字段.自定义.
 */
@Entity(nameInDb = "template_audio") @DatabaseField(tableName = "template_audio")
public class DBTemplateAudioInfo extends BaseModel {

  @Id @Property(nameInDb = "id") @DatabaseField(columnName = "id", primaryKey = true) public String
      index;
  @Property(nameInDb = "category_index") @DatabaseField(columnName = "category_index") public String
      categoryIndex;
  @Property(nameInDb = "cover_url") @DatabaseField(columnName = "cover_url") public String coverUrl;
  @Property(nameInDb = "audio_url") @DatabaseField(columnName = "audio_url") public String audioUrl;
  @Property(nameInDb = "name") @DatabaseField(columnName = "name") public String name;
  @Property(nameInDb = "duration") @DatabaseField(columnName = "duration") public int duration;
  @Property(nameInDb = "author") @DatabaseField(columnName = "author") public String author;
  @Property(nameInDb = "album") @DatabaseField(columnName = "album") public String album;
  @Property(nameInDb = "new_flag") @DatabaseField(columnName = "new_flag") public String newFlag;
  @Property(nameInDb = "order") @DatabaseField(columnName = "order") public String order;

  @Generated(hash = 262030984)
  public DBTemplateAudioInfo(String index, String categoryIndex, String coverUrl, String audioUrl,
          String name, int duration, String author, String album, String newFlag, String order) {
      this.index = index;
      this.categoryIndex = categoryIndex;
      this.coverUrl = coverUrl;
      this.audioUrl = audioUrl;
      this.name = name;
      this.duration = duration;
      this.author = author;
      this.album = album;
      this.newFlag = newFlag;
      this.order = order;
  }

  @Generated(hash = 805032786) public DBTemplateAudioInfo() {
  }

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

  public String getIndex() {
    return this.index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getCategoryIndex() {
    return this.categoryIndex;
  }

  public void setCategoryIndex(String categoryIndex) {
    this.categoryIndex = categoryIndex;
  }

  public String getCoverUrl() {
    return this.coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getAudioUrl() {
    return this.audioUrl;
  }

  public void setAudioUrl(String audioUrl) {
    this.audioUrl = audioUrl;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDuration() {
    return this.duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAlbum() {
    return this.album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getNewFlag() {
    return this.newFlag;
  }

  public void setNewFlag(String newFlag) {
    this.newFlag = newFlag;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
}
