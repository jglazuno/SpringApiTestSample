package com.iu.spring.rest.nasa.sounds.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class Sound implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private String description;
  private String license;
  private String title;
  private String download_url;
  private long duration;
  private String last_modified;
  private String stream_url;
  private String tag_list;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLicense() {
    return license;
  }

  public void setLicense(String license) {
    this.license = license;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDownload_url() {
    return download_url;
  }

  public void setDownload_url(String download_url) {
    this.download_url = download_url;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public String getLast_modified() {
    return last_modified;
  }

  public void setLast_modified(String last_modified) {
    this.last_modified = last_modified;
  }

  public String getStream_url() {
    return stream_url;
  }

  public void setStream_url(String stream_url) {
    this.stream_url = stream_url;
  }

  public String getTag_list() {
    return tag_list;
  }

  public void setTag_list(String tag_list) {
    this.tag_list = tag_list;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
