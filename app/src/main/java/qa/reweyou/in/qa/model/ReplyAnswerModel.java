package qa.reweyou.in.qa.model;

/**
 * Created by master on 2/8/17.
 */

public class ReplyAnswerModel {

private String id;
private String queid;
private String ansid;
private String answer;
private String upvotes;
private String time;
private String views;
private String uid;
private String comments;
private String visibility;

    public String getTime() {
        return time;
    }

    public String getQueid() {
        return queid;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getAnsid() {
        return ansid;
    }

    public String getAnswer() {
        return answer;
    }

    public String getComments() {
        return comments;
    }

    public String getViews() {
        return views;
    }

    public String getVisibility() {
        return visibility;
    }
}
