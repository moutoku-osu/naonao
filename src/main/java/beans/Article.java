package beans;

import java.io.Serializable;
import java.sql.Timestamp;

// 記事を表すクラス
public class Article implements Serializable {
    private int id; // すべての記事で一意な番号
    private String title; // タイトル
    private int titleLength; // タイトルの文字数
    private String body; // 本文
    private int bodyLength; // 本文の文字数
    private String editorId; // 著者のユーザId
    private Timestamp entryDatetime; // 登録日時

    // カラのコンストラクタが必要
    public Article() {
    }

    public Article(int id, String title, String body, String editorId, Timestamp entryDatetime) {
        super();
        this.id = id;
        setTitle(title); // setTitleでtitleLengthを更新
        setBody(body);   // setBodyでbodyLengthを更新
        this.editorId = editorId;
        this.entryDatetime = entryDatetime;
    }

    public Article(String title, String body, String editorId) {
        this(-1, title, body, editorId, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.titleLength = title != null ? title.length() : 0; // タイトル文字数を更新
    }

    public int getTitleLength() {
        return titleLength;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        this.bodyLength = body != null ? body.length() : 0; // 本文文字数を更新
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public String getEditorId() {
        return editorId;
    }

    public void setEditorId(String editorId) {
        this.editorId = editorId;
    }

    public Timestamp getEntryDatetime() {
        return entryDatetime;
    }

    public void setEntryDatetime(Timestamp entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    @Override
    public String toString() {
        return "記事ID: " + id + "\n"
                + "タイトル: " + title + " (" + titleLength + "文字)\n"
                + "本文: " + body + " (" + bodyLength + "文字)\n"
                + "著者ID: " + editorId + "\n"
                + "登録日時: " + (entryDatetime != null ? entryDatetime.toString() : "未登録");
    }
}
