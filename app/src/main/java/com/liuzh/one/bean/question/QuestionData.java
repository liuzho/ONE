
package com.liuzh.one.bean.question;

import com.liuzh.one.bean.Author;
import com.liuzh.one.bean.ShareList;
import com.liuzh.one.bean.Tag;

import java.util.List;

public class QuestionData {

    public String question_id;
    public String question_title;
    public String question_content;
    public String answer_title;
    public String answer_content;
    public String question_makettime;
    public String recommend_flag;
    public String charge_edt;
    public String charge_email;
    public String last_update_date;
    public String web_url;
    public String read_num;
    public String guide_word;
    public String audio;
    public String anchor;
    public String cover;
    public String content_bgcolor;
    public String cover_media_type;
    public String cover_media_file;
    public String start_video;
    public String copyright;
    public Author answerer;
    public Author asker;
    public List<Author> author_list = null;
    public List<Author> asker_list = null;
    public Integer next_id;
    public String previous_id;
    public List<Tag> tag_list = null;
    public ShareList share_list;
    public Integer praisenum;
    public Integer sharenum;
    public Integer commentnum;


}
