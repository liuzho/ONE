
package com.liuzh.one.bean.read;

import com.liuzh.one.bean.Author;
import com.liuzh.one.bean.ShareList;
import com.liuzh.one.bean.Tag;

import java.util.List;

public class ReadData {

    public String content_id;
    public String hp_title;
    public String sub_title;
    public String hp_author;
    public String auth_it;
    public String hp_author_introduce;
    public String hp_content;
    public String hp_makettime;
    public String hide_flag;
    public String wb_name;
    public String wb_img_url;
    public String last_update_date;
    public String web_url;
    public String guide_word;
    public String audio;
    public String anchor;
    public String editor_email;
    public String top_media_type;
    public String top_media_file;
    public String top_media_image;
    public String start_video;
    public String copyright;
    public List<Author> author = null;
    public String maketime;
    public List<Author> author_list = null;
    public String next_id;
    public String previous_id;
    public List<Tag> tag_list = null;
    public ShareList share_list;
    public Integer praisenum;
    public Integer sharenum;
    public Integer commentnum;

}
